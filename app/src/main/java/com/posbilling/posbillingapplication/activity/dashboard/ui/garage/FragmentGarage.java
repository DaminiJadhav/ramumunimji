package com.posbilling.posbillingapplication.activity.dashboard.ui.garage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.addgarage.ActivityAddGarage;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.activity.filter.ActivityFilter;
import com.posbilling.posbillingapplication.activity.garagedcustomerdetail.ActivityGarageCustomerDetail;
import com.posbilling.posbillingapplication.activity.outstandinglist.ActivityOutstandingList;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.ResponseVehicleCustomerList;
import com.posbilling.posbillingapplication.utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.internal.Util;

import static android.app.Activity.RESULT_OK;
import static com.posbilling.posbillingapplication.utility.Constants.ASCENDINGDESCENDING;
import static com.posbilling.posbillingapplication.utility.Constants.DISTRICT;
import static com.posbilling.posbillingapplication.utility.Constants.FILTERACTVITYREQUESTCODE;
import static com.posbilling.posbillingapplication.utility.Constants.FROMDATE;
import static com.posbilling.posbillingapplication.utility.Constants.MONTH;
import static com.posbilling.posbillingapplication.utility.Constants.SEEALL;
import static com.posbilling.posbillingapplication.utility.Constants.TALUKA;
import static com.posbilling.posbillingapplication.utility.Constants.TODATE;
import static com.posbilling.posbillingapplication.utility.Constants.VILLAGE;
import static com.posbilling.posbillingapplication.utility.Constants.YEAR;

public class FragmentGarage extends BaseFragment implements OnRecyclerviewClick, VehicleSummuryContractor.View {

    private Context mContext;
    private Activity activity;
    private AdapterVehicleSummury adapterVehicleSummury;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private ArrayList<ResponseVehicleCustomerList.Data> vehicleSummuryList = new ArrayList<ResponseVehicleCustomerList.Data>();
    private VehicleSummuryContractor.Presenter mPresenter;
    private Realm realm;
    private ArrayList<ResponseVehicleCustomerList.Data> filterArraylist = new ArrayList<>();


    @BindView(R.id.recyclerviewVehicleSummury)
    RecyclerView recyclerviewVehicleSummury;
    @BindView(R.id.edittext_search)
    EditText edittext_search;
    @BindView(R.id.imageviewMic)
    ImageView imageviewMic;



    @OnClick(R.id.imageviewMic)
    void imageviewMic() {
        speechToTextFunc(mContext, edittext_search, 1, imageviewMic);
    }

    @OnClick(R.id.imagviewFilter)
    void imagviewFilter() {
        Intent intent = new Intent(mContext, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }

    @OnClick(R.id.addVehicleCustomer)
    void addVehicleCustomer(){
        if (Utility.getInstance().isOnline(mContext)) {
            Intent intent = new Intent(mContext, ActivityAddGarage.class);
            startActivityForResult(intent, 401);
        }else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new VehicleSummuryPresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_vehicle_summury;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext == null) {
            mContext = ((ActivityDashboard) getContext());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 401){
            getvehicleDetails();
        }else if (resultCode == Activity.RESULT_OK && requestCode == FILTERACTVITYREQUESTCODE) {
            filterListFunctions(data);
        }
    }



    private void filterListFunctions(Intent data) {
        Utility.getInstance().showProgressDialogue(mContext);
        Intent intent = data;

        //RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();

        ArrayList<ResponseVehicleCustomerList.Data> finalFilterList = new ArrayList<>();
        ArrayList<ResponseVehicleCustomerList.Data> villageFilterList = new ArrayList<>();
//        ApplicableFilterList.addAll(customerList);


        String villageName = intent.getStringExtra(VILLAGE);

        //Village filter is added here on list
        if (intent.getStringExtra(VILLAGE) != null && !TextUtils.isEmpty(intent.getStringExtra(VILLAGE))) {
            for (ResponseVehicleCustomerList.Data item : vehicleSummuryList) {
                RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).equalTo("CustomerId", item.getCustomerId())
                        .findAll();
                if (customerListRealmModels.size()>0 && customerListRealmModels.get(0).getVillage()!= null) {
                    if ( customerListRealmModels.get(0).getVillage().toLowerCase().trim().contains(intent.getStringExtra(VILLAGE).toLowerCase().trim())) {
                        villageFilterList.add(item);
                    }
                }

            }
        } else {
            villageFilterList.addAll(vehicleSummuryList);
        }

        ///Taluka filter is added here
        ArrayList<ResponseVehicleCustomerList.Data> talukaFilterList = new ArrayList<>();
        if (intent.getStringExtra(TALUKA) != null && !TextUtils.isEmpty(intent.getStringExtra(TALUKA))) {
            for (ResponseVehicleCustomerList.Data item : villageFilterList) {
                RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).equalTo("CustomerId", item.getCustomerId())
                        .findAll();
                if (customerListRealmModels.size()>0 && customerListRealmModels.get(0).getTaluka()!= null) {
                    if ( customerListRealmModels.get(0).getTaluka().toLowerCase().trim().contains(intent.getStringExtra(TALUKA).toLowerCase().trim())) {
                        talukaFilterList.add(item);
                    }
                }
            }
        } else {
            talukaFilterList.addAll(villageFilterList);
        }

        ///District filter is added here
        ArrayList<ResponseVehicleCustomerList.Data> districtFilterList = new ArrayList<>();
        if (intent.getStringExtra(DISTRICT) != null && !TextUtils.isEmpty(intent.getStringExtra(DISTRICT))) {
            for (ResponseVehicleCustomerList.Data item : talukaFilterList) {
                RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).equalTo("CustomerId", item.getCustomerId())
                        .findAll();
                if (customerListRealmModels.size()>0 && customerListRealmModels.get(0).getDistrict()!= null) {
                    if ( customerListRealmModels.get(0).getDistrict().toLowerCase().trim().contains(intent.getStringExtra(DISTRICT).toLowerCase().trim())) {
                        districtFilterList.add(item);
                    }
                }


            }
        } else {
            districtFilterList.addAll(talukaFilterList);
        }

        ///fromDatefilter
        ArrayList<ResponseVehicleCustomerList.Data> fromDateFilter = new ArrayList<>();
        if (intent.getStringExtra(FROMDATE) != null && !TextUtils.isEmpty(intent.getStringExtra(FROMDATE))) {
            for (ResponseVehicleCustomerList.Data item : districtFilterList) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    String fromdate = intent.getStringExtra(FROMDATE);
                    Calendar d1 = Calendar.getInstance();
                    d1.setTime(sdformat.parse(fromdate));
                    String date = item.getDueDate();
                    String subDate = date.substring(0, 10);
                    Calendar d2 = Calendar.getInstance();
                    String year = subDate.substring(0,4);
                    String month = subDate.substring(5,7);
                    String dateString = subDate.substring(8,10);

                    d2.setTime(sdformat.parse(dateString+"/"+month+"/"+year));

                    int i = d1.compareTo(d2);
                    if (d2.after(d1)) {
                        fromDateFilter.add(item);
                    }

                    if (d2.equals(d1)){
                        fromDateFilter.add(item);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            fromDateFilter.addAll(districtFilterList);
        }

        ///toDatefilter
        ArrayList<ResponseVehicleCustomerList.Data> toDateFilter = new ArrayList<>();
        if (intent.getStringExtra(TODATE) != null && !TextUtils.isEmpty(intent.getStringExtra(TODATE))) {
            for (ResponseVehicleCustomerList.Data item : fromDateFilter) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    Calendar d1 = Calendar.getInstance();
                    String dateTo = intent.getStringExtra(TODATE);
                    d1.setTime(sdformat.parse(intent.getStringExtra(TODATE)));
                    String date = item.getDueDate();
                    String subDate = date.substring(0, 10);
                    Calendar d2 = Calendar.getInstance();
                    String year = subDate.substring(0,4);
                    String month = subDate.substring(5,7);
                    String dateString = subDate.substring(8,10);
                    d2.setTime(sdformat.parse(dateString+"/"+month+"/"+year));
                    if (d2.before(d1)) {
                        toDateFilter.add(item);
                    }

                    if (d1.equals(d2)){
                        toDateFilter.add(item);
                    }

                    /* else {
                        toDateFilter.add(item);
                    }*/
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            toDateFilter.addAll(fromDateFilter);
        }

        ////Selected month filter
        ArrayList<ResponseVehicleCustomerList.Data> monthFilter = new ArrayList<>();
        if (intent.getStringExtra(MONTH) != null && !TextUtils.isEmpty(intent.getStringExtra(MONTH))) {
            for (ResponseVehicleCustomerList.Data item : toDateFilter) {
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    String date = item.getDueDate();
                    String subDate = date.substring(0, 10);
                    String yyyy = subDate.substring(0, 4);
                    String mm = subDate.substring(5, 7);
                    String dd = subDate.substring(8, 10);
                    subDate = dd + "/" + mm + "/" + yyyy;
                    Calendar d2 = Calendar.getInstance();
                    d2.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(subDate));
                    String monthSelected = intent.getStringExtra(MONTH);
                    int monthNumber = d2.get(Calendar.MONTH);
                    if ((monthNumber + 1) == Integer.valueOf(intent.getStringExtra(MONTH))) {
                        monthFilter.add(item);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            monthFilter.addAll(toDateFilter);
        }


        ////Selected year filter
        ArrayList<ResponseVehicleCustomerList.Data> yearFilter = new ArrayList<>();
        if (intent.getStringExtra(YEAR) != null && !TextUtils.isEmpty(intent.getStringExtra(YEAR))) {
            for (ResponseVehicleCustomerList.Data item : monthFilter) {
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    String date = item.getDueDate();
                    String subDate = date.substring(0, 10);
                    String yyyy = subDate.substring(0, 4);
                    String mm = subDate.substring(5, 7);
                    String dd = subDate.substring(8, 10);
                    subDate = dd + "/" + mm + "/" + yyyy;
                    Calendar d2 = Calendar.getInstance();
                    d2.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(subDate));
                    int yearNumber = d2.get(Calendar.YEAR);
                    if (yearNumber == Integer.valueOf(intent.getStringExtra(YEAR))) {
                        yearFilter.add(item);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            yearFilter.addAll(monthFilter);
        }

        //setlist to ascending or descendintg
        ArrayList<ResponseVehicleCustomerList.Data> ascendingDescendingFilter = new ArrayList<>();
        ascendingDescendingFilter.addAll(yearFilter);


        // setSizeandAddition();
        /*String ascendingdescding = intent.getStringExtra(ASCENDINGDESCENDING);
        if (intent.getStringExtra(ASCENDINGDESCENDING).matches("Ascending")) {
            for (int i = 0; i < customerListFilterfunction.size(); i++) {
                for (int j = i + 1; j < customerListFilterfunction.size(); j++) {
                    if (Integer.parseInt(customerListFilterfunction.get(i).getOutstanding()) > Integer.parseInt(customerListFilterfunction.get(j).getOutstanding())) {
                        CustomerListResponse.Data.tblCustomers temp = customerListFilterfunction.get(i);
                        customerListFilterfunction.set(i, customerListFilterfunction.get(j));
                        customerListFilterfunction.set(j, temp);
                    }
                }
            }
        } else if (intent.getStringExtra(ASCENDINGDESCENDING).matches("Desecnding")) {
            for (int i = 0; i < customerListFilterfunction.size(); i++) {
                for (int j = i + 1; j < customerListFilterfunction.size(); j++) {
                    if (Integer.parseInt(customerListFilterfunction.get(i).getOutstanding()) > Integer.parseInt(customerListFilterfunction.get(j).getOutstanding())) {
                        CustomerListResponse.Data.tblCustomers temp = customerListFilterfunction.get(i);
                        customerListFilterfunction.set(i, customerListFilterfunction.get(j));
                        customerListFilterfunction.set(j, temp);
                    }
                }
            }
            Collections.reverse(customerListFilterfunction);
        }*/

//see all filter
        ////Selected year filter
/*        ArrayList<CustomerListResponse.Data.tblCustomers> seeAllFilter = new ArrayList<>();
        if (intent.getStringExtra(SEEALL) != null && !TextUtils.isEmpty(intent.getStringExtra(SEEALL))) {
            for (CustomerListResponse.Data.tblCustomers item : customerListFilterfunction) {
                if (item.getOutstanding() != null) {
                    String value1 = item.getOutstanding();
                    String value2 = intent.getStringExtra(SEEALL);
                    String value3 = intent.getStringExtra("seeall");
                    if (Integer.parseInt(item.getOutstanding().toString()) >= Integer.parseInt(intent.getStringExtra(SEEALL))) {
                        seeAllFilter.add(item);
                    }
                } else if (Integer.parseInt(intent.getStringExtra(SEEALL)) == 0) {
                    seeAllFilter.add(item);
                }
            }
        } else {
            seeAllFilter.addAll(customerListFilterfunction);
        }*/

        filterArraylist.clear();
        filterArraylist.addAll(ascendingDescendingFilter);
        adapterVehicleSummury.filterList(filterArraylist);
        adapterVehicleSummury.notifyDataSetChanged();
        Utility.getInstance().dismissProgress();
    }





    //textchangelistner on list
    private void setSearchFilter() {
        edittext_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }


    private void filter(String text) {
        filterArraylist.clear();
        for (ResponseVehicleCustomerList.Data name : vehicleSummuryList) {
            RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).equalTo("CustomerId", name.getCustomerId())
                    .findAll();

            String firstname = "";
            String middlename = "";
            String lastname = "";
            if (customerListRealmModels.get(0).getCLastname() != null) {
                lastname = customerListRealmModels.get(0).getCLastname();
            }
            if (customerListRealmModels.get(0).getCMiddleName() != null) {
                middlename = customerListRealmModels.get(0).getCMiddleName();
            }
            if (customerListRealmModels.get(0).getCFirstname() != null) {
                firstname = customerListRealmModels.get(0).getCFirstname();
            }
            String finalname = "";
            if (TextUtils.isEmpty(middlename)) {
                finalname = firstname + " " + lastname;
            } else {
                finalname = firstname + " " + middlename + " " + lastname;
            }
            if (finalname.trim().toLowerCase().contains(text.toLowerCase())) {
                filterArraylist.add(name);
            }
        }
        adapterVehicleSummury.filterList(filterArraylist);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        this.mContext = context;
        super.onAttach(context);
    }


    @Override
    public void onMakeServerCalls() {
        if (isAdded() || isVisible()) {
            ((ActivityDashboard) getContext()).setTitleOfScreen(getString(R.string.vehicle_maintainace));
            ((ActivityDashboard) getContext()).setVisibilityofRamumunimjiVisible(true,4);
            ((ActivityDashboard) getContext()).castFragment(this,4);
            activity = getActivity();
            setSearchFilter();
            realm = RealmController.with(this).getRealm();
            setRecyclerview();
            getvehicleDetails();

        }
    }

    private void getvehicleDetails() {
        if (Utility.getInstance().isOnline(mContext)) {
            Utility.getInstance().showProgressDialogue(mContext);
            mPresenter.getVehclieDetails(Utility.getInstance().getclientRegId(mContext).toString(), "");
        }else{
            View view = activity.getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(view,getString(R.string.you_are_offline));
        }
    }

    private void setRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        adapterVehicleSummury = new AdapterVehicleSummury(mContext, vehicleSummuryList, onRecyclerviewClick,realm);
        recyclerviewVehicleSummury.setLayoutManager(linearLayoutManager);
        recyclerviewVehicleSummury.setAdapter(adapterVehicleSummury);
    }

    @Override
    public void onReclerViewClick(View view, int position) {
        if(Utility.getInstance().isOnline(mContext)) {
            Intent intent = new Intent(getActivity(), ActivityGarageCustomerDetail.class);
            intent.putExtra("customerId", filterArraylist.get(position).getCustomerId());
            intent.putExtra("vehicleType", filterArraylist.get(position).getVehicleType());
            intent.putExtra("vehicleNumber", filterArraylist.get(position).getVehicleNo());
            startActivity(intent);
        }else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void vehicleCustomerListFailure(String message) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(mContext, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void vehicleCustomerListSuccess(ResponseVehicleCustomerList body) {
        vehicleSummuryList.clear();
        filterArraylist.clear();

        for (ResponseVehicleCustomerList.Data item : body.getDataArrayList()){
            if (!vehicleSummuryList.contains(item)){
                vehicleSummuryList.add(item);
            }else{
                Log.e("TAG", "vehicleCustomerListSuccess: " );
            }

        }
        filterArraylist.addAll(vehicleSummuryList);
       // vehicleSummuryList.addAll(body.getDataArrayList());
        adapterVehicleSummury.notifyDataSetChanged();
        Utility.getInstance().dismissProgress();
    }

    public void methodFragment() {
        if (Utility.getInstance().isOnline(mContext)) {
            Intent intent = new Intent(mContext, ActivityAddGarage.class);
            startActivityForResult(intent, 401);
        }else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }
}