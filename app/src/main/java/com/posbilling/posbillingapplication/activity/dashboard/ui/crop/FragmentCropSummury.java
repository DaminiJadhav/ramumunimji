package com.posbilling.posbillingapplication.activity.dashboard.ui.crop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.cropdetail.ActivityCropDetail;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.activity.filter.ActivityFilter;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
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

import static com.posbilling.posbillingapplication.utility.Constants.ASCENDINGDESCENDING;
import static com.posbilling.posbillingapplication.utility.Constants.CUSTOMERID;
import static com.posbilling.posbillingapplication.utility.Constants.DISTRICT;
import static com.posbilling.posbillingapplication.utility.Constants.FILTERACTVITYREQUESTCODE;
import static com.posbilling.posbillingapplication.utility.Constants.FROMDATE;
import static com.posbilling.posbillingapplication.utility.Constants.MONTH;
import static com.posbilling.posbillingapplication.utility.Constants.SEEALL;
import static com.posbilling.posbillingapplication.utility.Constants.TALUKA;
import static com.posbilling.posbillingapplication.utility.Constants.TODATE;
import static com.posbilling.posbillingapplication.utility.Constants.VILLAGE;
import static com.posbilling.posbillingapplication.utility.Constants.YEAR;

public class FragmentCropSummury extends BaseFragment implements CropCustomerContractor.View, OnRecyclerviewClick {

    private Context mContext;
    private CropCustomerContractor.Presenter mPresenter;
    private Realm realm;
    private LinearLayoutManager linearLayoutManager;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private ArrayList<CustomerListResponse.Data.tblCustomers> customerList = new ArrayList();
    private CropCustomerAdapter cropCustomerAdapter;
    private ArrayList<CustomerListResponse.Data.tblCustomers> filterArraylist = new ArrayList<>();

    @BindView(R.id.recyclerviewCropCustomer)
    RecyclerView recyclerviewCropCustomer;
    @BindView(R.id.edittext_search)
    EditText edittext_search;
    @BindView(R.id.textview_Please_add_customer)
    TextView textview_Please_add_customer;
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


    @Override
    public void onMakeServerCalls() {
        ((ActivityDashboard) getContext()).setTitleOfScreen(getString(R.string.crop));
        ((ActivityDashboard) getContext()).setVisibilityofRamumunimjiVisible(false,1);
        realm = RealmController.with(this).getRealm();
        setRecyclerView();
        setSearchFilter();
        getCustomerData();
    }

    private void getCustomerData() {
        if (Utility.getInstance().isOnline(mContext)) {
            Utility.getInstance().showProgressDialogue(mContext);
            mPresenter.getCustomerList(Utility.getInstance().getclientRegId(mContext).toString());
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
            /*RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
            for (CustomerListRealm object : customerListRealmModels) {
                CustomerListResponse.Data.tblCustomers tblCustomers = new CustomerListResponse().new Data().new tblCustomers();
                tblCustomers.setID(object.getID());
                tblCustomers.setCFirstname(object.getCFirstname());
                tblCustomers.setCMiddleName(object.getCMiddleName());
                tblCustomers.setCLastname(object.getCLastname());
                tblCustomers.setImagePath(object.getImagePath());
                tblCustomers.setClientId(object.getClientId());
                tblCustomers.setOutstanding(object.getOutstanding());
                customerList.add(tblCustomers);
            }
            cropCustomerAdapter.notifyDataSetChanged();*/
        }
    }


    //setting recyclerview
    private void setRecyclerView() {
        customerList.clear();
        linearLayoutManager = new LinearLayoutManager(mContext);
        filterArraylist.addAll(customerList);
        cropCustomerAdapter = new CropCustomerAdapter(mContext, customerList, onRecyclerviewClick);
        recyclerviewCropCustomer.setLayoutManager(linearLayoutManager);
        recyclerviewCropCustomer.setAdapter(cropCustomerAdapter);
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
        for (CustomerListResponse.Data.tblCustomers name : customerList) {
            String firstname = "";
            String middlename = "";
            String lastname = "";
            if (name.getCLastname() != null) {
                lastname = name.getCLastname();
            }
            if (name.getCMiddleName() != null) {
                middlename = name.getCMiddleName();
            }
            if (name.getCFirstname() != null) {
                firstname = name.getCFirstname();
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
        cropCustomerAdapter.filterList(filterArraylist);
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
        if (resultCode == Activity.RESULT_OK && requestCode == FILTERACTVITYREQUESTCODE) {
            filterListFunctions(data);
        }
    }


    private void filterListFunctions(Intent data) {
        Utility.getInstance().showProgressDialogue(mContext);
        Intent intent = data;

//        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
        ArrayList<CustomerListResponse.Data.tblCustomers> customerListRealmModels = new ArrayList<>();
                customerListRealmModels.addAll(customerList);
        ArrayList<CustomerListRealm> finalFilterList = new ArrayList<>();
        ArrayList<CustomerListResponse.Data.tblCustomers> villageFilterList = new ArrayList<>();
//        ApplicableFilterList.addAll(customerList);


        try {




        String villageName = intent.getStringExtra(VILLAGE);

        //Village filter is added here on list
        if (intent.getStringExtra(VILLAGE) != null && !TextUtils.isEmpty(intent.getStringExtra(VILLAGE))) {
            for (CustomerListResponse.Data.tblCustomers item : customerListRealmModels) {
                if (item.getVillage() != null && item.getVillage().toLowerCase().trim().contains(intent.getStringExtra(VILLAGE).toLowerCase().trim())) {
                    villageFilterList.add(item);
                }
            }
        } else {
            villageFilterList.addAll(customerListRealmModels);
        }

        ///Taluka filter is added here
        ArrayList<CustomerListResponse.Data.tblCustomers> talukaFilterList = new ArrayList<>();
        if (intent.getStringExtra(TALUKA) != null && !TextUtils.isEmpty(intent.getStringExtra(TALUKA))) {
            for (CustomerListResponse.Data.tblCustomers item : villageFilterList) {
                if (item.getTaluka() != null && item.getTaluka().toLowerCase().trim().contains(intent.getStringExtra(TALUKA).toLowerCase().trim())) {
                    talukaFilterList.add(item);
                }
            }
        } else {
            talukaFilterList.addAll(villageFilterList);
        }

        ///District filter is added here
        ArrayList<CustomerListResponse.Data.tblCustomers> districtFilterList = new ArrayList<>();
        if (intent.getStringExtra(DISTRICT) != null && !TextUtils.isEmpty(intent.getStringExtra(DISTRICT))) {
            for (CustomerListResponse.Data.tblCustomers item : talukaFilterList) {
                if (item.getDistrict() != null && item.getDistrict().toLowerCase().trim().contains(intent.getStringExtra(DISTRICT).toLowerCase().trim())) {
                    districtFilterList.add(item);
                }
            }
        } else {
            districtFilterList.addAll(talukaFilterList);
        }

        ///fromDatefilter
        ArrayList<CustomerListResponse.Data.tblCustomers> fromDateFilter = new ArrayList<>();
        if (intent.getStringExtra(FROMDATE) != null && !TextUtils.isEmpty(intent.getStringExtra(FROMDATE))) {
            for (CustomerListResponse.Data.tblCustomers item : districtFilterList) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    String fromdate = intent.getStringExtra(FROMDATE);
                    Calendar d1 = Calendar.getInstance();
                    d1.setTime(sdformat.parse(fromdate));
                    String date = item.getCreatedOn();
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
        ArrayList<CustomerListResponse.Data.tblCustomers> toDateFilter = new ArrayList<>();
        if (intent.getStringExtra(TODATE) != null && !TextUtils.isEmpty(intent.getStringExtra(TODATE))) {
            for (CustomerListResponse.Data.tblCustomers item : fromDateFilter) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    Calendar d1 = Calendar.getInstance();
                    String dateTo = intent.getStringExtra(TODATE);
                    d1.setTime(sdformat.parse(intent.getStringExtra(TODATE)));
                    String date = item.getCreatedOn();
                    String subDate = date.substring(0, 10);
                    Calendar d2 = Calendar.getInstance();
                   // d2.setTime(sdformatSecond.parse(subDate));
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
        ArrayList<CustomerListResponse.Data.tblCustomers> monthFilter = new ArrayList<>();
        if (intent.getStringExtra(MONTH) != null && !TextUtils.isEmpty(intent.getStringExtra(MONTH))) {
            for (CustomerListResponse.Data.tblCustomers item : toDateFilter) {
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    String date = item.getCreatedOn();
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
        ArrayList<CustomerListResponse.Data.tblCustomers> yearFilter = new ArrayList<>();
        if (intent.getStringExtra(YEAR) != null && !TextUtils.isEmpty(intent.getStringExtra(YEAR))) {
            for (CustomerListResponse.Data.tblCustomers item : monthFilter) {
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    String date = item.getCreatedOn();
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
        ArrayList<CustomerListResponse.Data.tblCustomers> ascendingDescendingFilter = new ArrayList<>();
        ascendingDescendingFilter.addAll(yearFilter);

      /*  finalFilterList.addAll(ascendingDescendingFilter);
        filterArraylist.clear();*/
        ArrayList<CustomerListResponse.Data.tblCustomers> customerListFilterfunction = new ArrayList();
        for (CustomerListResponse.Data.tblCustomers object : ascendingDescendingFilter) {
            CustomerListResponse.Data.tblCustomers tblCustomers = new CustomerListResponse().new Data().new tblCustomers();
            tblCustomers.setID(object.getID());
            tblCustomers.setCFirstname(object.getCFirstname());
            tblCustomers.setCMiddleName(object.getCMiddleName());
            tblCustomers.setCLastname(object.getCLastname());
            tblCustomers.setImagePath(object.getImagePath());
            tblCustomers.setClientId(object.getClientId());
            tblCustomers.setOutstanding(object.getOutstanding());
            customerListFilterfunction.add(tblCustomers);
        }
        // setSizeandAddition();
        String ascendingdescding = intent.getStringExtra(ASCENDINGDESCENDING);
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
        }

//see all filter
        ////Selected year filter
        ArrayList<CustomerListResponse.Data.tblCustomers> seeAllFilter = new ArrayList<>();
        if (intent.getStringExtra(SEEALL) != null && !TextUtils.isEmpty(intent.getStringExtra(SEEALL))) {
            for (CustomerListResponse.Data.tblCustomers item : customerListFilterfunction) {
                if (item.getOutstanding() != null) {
                    String value1 = item.getOutstanding();
                    String value2 = intent.getStringExtra(SEEALL);
                    String value3 = intent.getStringExtra("seeall");

                    if (Integer.parseInt(intent.getStringExtra(SEEALL)) == 0) {
                        seeAllFilter.add(item);
                    } else if (Integer.parseInt(removeContainsDot(item.getOutstanding().toString())) <= Integer.parseInt(removeContainsDot(intent.getStringExtra(SEEALL)))) {
                        seeAllFilter.add(item);
                    }
                }
            }
        } else {
            seeAllFilter.addAll(customerListFilterfunction);
        }

        filterArraylist.clear();
        filterArraylist.addAll(seeAllFilter);
        cropCustomerAdapter.filterList(filterArraylist);
        cropCustomerAdapter.notifyDataSetChanged();

        }catch (Exception ae){
            ae.printStackTrace();
        }

        Utility.getInstance().dismissProgress();
    }



    private String removeContainsDot(String amount) {
        if (amount != null && !TextUtils.isEmpty(amount)) {
            if (amount.contains(".")) {
                String result = amount.substring(0, amount.indexOf("."));
                return result;
            } else {
                return amount;
            }
        }
        return "0";
    }


    @Override
    public void onAttach(@NonNull Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new CropCustomerPresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.crop_customerlist;
    }


    @Override
    public void customerListSuccess(CustomerListResponse response) {
        customerList.clear();
        RealmController.with(getActivity()).clearCustomerList();
        double total = 0.0;
        customerList.addAll(response.getData().getTblCustomerslist());
        filterArraylist.clear();
        filterArraylist.addAll(customerList);
        ArrayList<CustomerListRealm> customerListRealmArrayList = new ArrayList<>();
        for (int i = 0; i < customerList.size(); i++) {
            CustomerListRealm customerListRealm = new CustomerListRealm();
            customerListRealm.setID(customerList.get(i).getID());
            customerListRealm.setCustomerId(customerList.get(i).getCustomerId());
            customerListRealm.setClientId(customerList.get(i).getClientId());
            customerListRealm.setCFirstname(customerList.get(i).getCFirstname());
            customerListRealm.setCMiddleName(customerList.get(i).getCMiddleName());
            customerListRealm.setStateId(customerList.get(i).getStateId());
            customerListRealm.setCustomerOnlineSaved(true);

            customerListRealm.setCLastname(customerList.get(i).getCLastname());
            customerListRealm.setImagePath(customerList.get(i).getImagePath());
            customerListRealm.setOutstanding(customerList.get(i).getOutstanding());
            customerListRealm.setVillage(customerList.get(i).getVillage());
            customerListRealm.setTaluka(customerList.get(i).getTaluka());
            customerListRealm.setDistrict(customerList.get(i).getDistrict());
            customerListRealm.setCAlternetMobNo(customerList.get(i).getCAlternetMobNo());
            customerListRealm.setCContactNo(customerList.get(i).getCContactNo());
            customerListRealmArrayList.add(customerListRealm);
        }
        //   Log.i(getClass().getName(), "=========tempListHelpScreenModel SIZE: " + tempListHelpScreenModel.size());
        for (CustomerListRealm customerListRealm : customerListRealmArrayList) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(customerListRealm);
            realm.commitTransaction();
        }

        if (!(customerList.size() <= 0)) {
            recyclerviewCropCustomer.setVisibility(View.VISIBLE);
            textview_Please_add_customer.setVisibility(View.GONE);
            cropCustomerAdapter.notifyDataSetChanged();
            //Ankur
            // textviewCustomerNumber.setText(String.valueOf(response.getData().getTblCustomerslist().size()));

        } else {
            recyclerviewCropCustomer.setVisibility(View.GONE);
            textview_Please_add_customer.setVisibility(View.VISIBLE);
        }
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void customerListFailure(String message) {
        Toast.makeText(mContext, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void onReclerViewClick(View view, int position) {
        Intent intent = new Intent(mContext, ActivityCropDetail.class);
        intent.putExtra(CUSTOMERID, filterArraylist.get(position).getID().toString());
        startActivity(intent);
    }
}
