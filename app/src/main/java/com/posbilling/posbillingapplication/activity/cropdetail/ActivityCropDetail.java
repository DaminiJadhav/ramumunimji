package com.posbilling.posbillingapplication.activity.cropdetail;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.cropregistration.ActivityCropRegistration;
import com.posbilling.posbillingapplication.activity.filter.ActivityFilter;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.ResponseCustomerCropDetails;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.posbilling.posbillingapplication.utility.Constants.ASCENDINGDESCENDING;
import static com.posbilling.posbillingapplication.utility.Constants.CLIENTREGID;
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

public class ActivityCropDetail extends BaseActivity implements CropDetailContractor.View, OnRecyclerviewClick {

    private Realm realm;
    private String customerId;
    private CropDetailContractor.Presenter mPresenter;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private ArrayList<ResponseCustomerCropDetails.Data> cropDetailList = new ArrayList<ResponseCustomerCropDetails.Data>();
    private CropDetailListAdapter cropDetailListAdapter;
    public static final int ADDCROPACTIVITY = 101;
    private String customerRegId = "";


    @BindView(R.id.recyclerview_list)
    RecyclerView recyclerview_list;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.textview_name)
    TextView textview_name;
    @BindView(R.id.imageview_profile)
    CircleImageView imageview_profile;
    @BindView(R.id.textview_mobileNumber)
    TextView textview_mobileNumber;
    @BindView(R.id.textview_address)
    TextView textview_address;
    @BindView(R.id.textview_id)
    TextView textview_id;

    @OnClick(R.id.imageview_filter)
    void imageview_filter(){
        Intent intent = new Intent(ActivityCropDetail.this, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }


    @OnClick(R.id.textview_addCrop)
    void textview_addCrop() {
        if (Utility.getInstance().isOnline(ActivityCropDetail.this)) {
            Intent intent = new Intent(ActivityCropDetail.this, ActivityCropRegistration.class);
            intent.putExtra("CUDTOMERREGID", customerRegId);
            intent.putExtra(CUSTOMERID, customerId);
            intent.putExtra("customerName", textview_name.getText().toString());
            startActivityForResult(intent, ADDCROPACTIVITY);
        }else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADDCROPACTIVITY) {
                SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivityCropDetail.this);
                String clientId = sharedPreferences.getString(CLIENTREGID, "");
                Utility.getInstance().showProgressDialogue(ActivityCropDetail.this);
                mPresenter.getCropDetailList(clientId, customerRegId);
            }else if (resultCode == Activity.RESULT_OK && requestCode == FILTERACTVITYREQUESTCODE) {
                filterListFunctions(data);
            }
        }

    }



    private void filterListFunctions(Intent data) {
        Utility.getInstance().showProgressDialogue(ActivityCropDetail.this);
        Intent intent = data;

//        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();

        ArrayList<CustomerListRealm> finalFilterList = new ArrayList<>();
        ArrayList<CustomerListRealm> villageFilterList = new ArrayList<>();
//        ApplicableFilterList.addAll(customerList);


        String villageName = intent.getStringExtra(VILLAGE);

/*        //Village filter is added here on list
        if (intent.getStringExtra(VILLAGE) != null && !TextUtils.isEmpty(intent.getStringExtra(VILLAGE))) {
            for (CustomerListRealm item : customerListRealmModels) {
                if (item.getVillage() != null && item.getVillage().toLowerCase().trim().contains(intent.getStringExtra(VILLAGE).toLowerCase().trim())) {
                    villageFilterList.add(item);
                }
            }
        } else {
            villageFilterList.addAll(customerListRealmModels);
        }

        ///Taluka filter is added here
        ArrayList<CustomerListRealm> talukaFilterList = new ArrayList<>();
        if (intent.getStringExtra(TALUKA) != null && !TextUtils.isEmpty(intent.getStringExtra(TALUKA))) {
            for (CustomerListRealm item : villageFilterList) {
                if (item.getTaluka() != null && item.getTaluka().toLowerCase().trim().contains(intent.getStringExtra(TALUKA).toLowerCase().trim())) {
                    talukaFilterList.add(item);
                }
            }
        } else {
            talukaFilterList.addAll(villageFilterList);
        }

        ///District filter is added here
        ArrayList<CustomerListRealm> districtFilterList = new ArrayList<>();
        if (intent.getStringExtra(DISTRICT) != null && !TextUtils.isEmpty(intent.getStringExtra(DISTRICT))) {
            for (CustomerListRealm item : talukaFilterList) {
                if (item.getDistrict() != null && item.getDistrict().toLowerCase().trim().contains(intent.getStringExtra(DISTRICT).toLowerCase().trim())) {
                    districtFilterList.add(item);
                }
            }
        } else {
            districtFilterList.addAll(talukaFilterList);
        }*/

        ///fromDatefilter
        ArrayList<ResponseCustomerCropDetails.Data> fromDateFilter = new ArrayList<>();
        if (intent.getStringExtra(FROMDATE) != null && !TextUtils.isEmpty(intent.getStringExtra(FROMDATE))) {
            for (ResponseCustomerCropDetails.Data item : cropDetailList) {
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
            fromDateFilter.addAll(cropDetailList);
        }

        ///toDatefilter
        ArrayList<ResponseCustomerCropDetails.Data> toDateFilter = new ArrayList<>();
        if (intent.getStringExtra(TODATE) != null && !TextUtils.isEmpty(intent.getStringExtra(TODATE))) {
            for (ResponseCustomerCropDetails.Data item : fromDateFilter) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    Calendar d1 = Calendar.getInstance();
                    String dateTo = intent.getStringExtra(TODATE);
                    d1.setTime(sdformat.parse(intent.getStringExtra(TODATE)));
                    String date = item.getCreatedOn();
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
        ArrayList<ResponseCustomerCropDetails.Data> monthFilter = new ArrayList<>();
        if (intent.getStringExtra(MONTH) != null && !TextUtils.isEmpty(intent.getStringExtra(MONTH))) {
            for (ResponseCustomerCropDetails.Data item : toDateFilter) {
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
        ArrayList<ResponseCustomerCropDetails.Data> yearFilter = new ArrayList<>();
        if (intent.getStringExtra(YEAR) != null && !TextUtils.isEmpty(intent.getStringExtra(YEAR))) {
            for (ResponseCustomerCropDetails.Data item : monthFilter) {
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
        ArrayList<ResponseCustomerCropDetails.Data> ascendingDescendingFilter = new ArrayList<>();
        ascendingDescendingFilter.addAll(yearFilter);

      /*  finalFilterList.addAll(ascendingDescendingFilter);
        filterArraylist.clear();*/
/*        ArrayList<CustomerListResponse.Data.tblCustomers> customerListFilterfunction = new ArrayList();
        for (CustomerListRealm object : ascendingDescendingFilter) {
            CustomerListResponse.Data.tblCustomers tblCustomers = new CustomerListResponse().new Data().new tblCustomers();
            tblCustomers.setID(object.getID());
            tblCustomers.setCFirstname(object.getCFirstname());
            tblCustomers.setCMiddleName(object.getCMiddleName());
            tblCustomers.setCLastname(object.getCLastname());
            tblCustomers.setImagePath(object.getImagePath());
            tblCustomers.setClientId(object.getClientId());
            tblCustomers.setOutstanding(object.getOutstanding());
            customerListFilterfunction.add(tblCustomers);
        }*/
        // setSizeandAddition();
  /*      String ascendingdescding = intent.getStringExtra(ASCENDINGDESCENDING);
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
        /*ArrayList<CustomerListResponse.Data.tblCustomers> seeAllFilter = new ArrayList<>();
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

        ArrayList<ResponseCustomerCropDetails.Data> filterArraylist = new ArrayList();
        filterArraylist.clear();
        filterArraylist.addAll(ascendingDescendingFilter);
        cropDetailListAdapter.filterList(filterArraylist);
       // cropCustomerAdapter.notifyDataSetChanged();
        Utility.getInstance().dismissProgress();
    }


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_crop_detail;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new CropDetailPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText(getString(R.string.crop_details));
        realm = RealmController.with(this).getRealm();
        setRecyclerview();
        getIntentData();
        getCusomerData();
        getCropData();
    }

    private void getCropData() {
        if (Utility.getInstance().isOnline(ActivityCropDetail.this)) {
            Utility.getInstance().showProgressDialogue(ActivityCropDetail.this);
            SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivityCropDetail.this);
            String clientId = sharedPreferences.getString(CLIENTREGID, "");
            if (Utility.getInstance().isOnline(ActivityCropDetail.this)) {
                mPresenter.getCropDetailList(clientId, customerRegId);
            }else {
                Toast.makeText(ActivityCropDetail.this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            View view = getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(view, getString(R.string.you_are_offline));
        }
    }

    private void getIntentData() {
        customerRegId = getIntent().getStringExtra(CUSTOMERID);
    }


    private void getCusomerData() {
        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
        for (CustomerListRealm object : customerListRealmModels) {
            CustomerListResponse.Data.tblCustomers tblCustomers = new CustomerListResponse().new Data().new tblCustomers();
            if (customerRegId.toLowerCase().trim().matches(object.getID().toLowerCase().trim())) {
                String firstname = "";
                String middlename = "";
                String lastname = "";
                if (object.getCLastname() != null) {
                    lastname = object.getCLastname();
                }
                if (object.getCMiddleName() != null) {
                    middlename = object.getCMiddleName();
                }
                if (object.getCFirstname() != null) {
                    firstname = object.getCFirstname();
                }
                String finalname = "";
                if (TextUtils.isEmpty(middlename)) {
                    finalname = firstname + " " + lastname;
                } else {
                    finalname = firstname + " " + middlename + " " + lastname;
                }
                textview_name.setText(finalname);
                String path = object.getImagePath();

                //https://ramu.sdaemon.com/images//Ramuji//C136.jpg
                Glide.with(ActivityCropDetail.this).load(new CustomRetroRequest().imageURL + object.getImagePath())
                        .into(imageview_profile);

                if (object.getCContactNo() != null && !TextUtils.isEmpty(object.getCContactNo().toString())) {
                    textview_mobileNumber.setText(object.getCContactNo());
                } else {
                    textview_mobileNumber.setText("-");
                }
                String address = "";
                String village = "";
                String district = "";
                String taluka = "";
                if (object.getTaluka() != null && !TextUtils.isEmpty(object.getTaluka())) {
                    taluka = object.getTaluka();
                }
                if (object.getVillage() != null && !TextUtils.isEmpty(object.getVillage())) {
                    village = object.getVillage();
                }
                if (object.getDistrict() != null && !TextUtils.isEmpty(object.getDistrict())) {
                    district = object.getDistrict();
                }
                address = village + " " + taluka + " " + district;
                if (!TextUtils.isEmpty(address.trim())) {
                    textview_address.setText(address.trim());
                } else {
                    textview_address.setText("-");
                }

                if (object.getCustomerId() != null && !TextUtils.isEmpty(object.getCustomerId())) {
                    textview_id.setText(object.getCustomerId());
                    customerId = object.getCustomerId();
                } else {
                    textview_id.setText("-");
                }
                break;
            }
        }
    }

    private void setRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview_list.setLayoutManager(linearLayoutManager);
        cropDetailListAdapter = new CropDetailListAdapter(ActivityCropDetail.this, cropDetailList, onRecyclerviewClick);
        recyclerview_list.setAdapter(cropDetailListAdapter);
    }

    @Override
    public void getCropListSuccess(ResponseCustomerCropDetails response) {
        cropDetailList.clear();
        cropDetailList.addAll(response.getArrayListData());
        cropDetailListAdapter.notifyDataSetChanged();
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void getCropListFailure(String message, String status) {
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void onReclerViewClick(View view, int position) {

    }
}
