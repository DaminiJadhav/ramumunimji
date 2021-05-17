package com.posbilling.posbillingapplication.activity.garagedcustomerdetail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.filter.ActivityFilter;
import com.posbilling.posbillingapplication.activity.outstandinglist.ActivityOutstandingList;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.request.VehicleReportPdfRequest;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.model.response.ResponseVehicleCustomerList;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.utility.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.posbilling.posbillingapplication.utility.Constants.DISTRICT;
import static com.posbilling.posbillingapplication.utility.Constants.FILTERACTVITYREQUESTCODE;
import static com.posbilling.posbillingapplication.utility.Constants.FROMDATE;
import static com.posbilling.posbillingapplication.utility.Constants.GALLERY_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.MONTH;
import static com.posbilling.posbillingapplication.utility.Constants.TALUKA;
import static com.posbilling.posbillingapplication.utility.Constants.TODATE;
import static com.posbilling.posbillingapplication.utility.Constants.VILLAGE;
import static com.posbilling.posbillingapplication.utility.Constants.YEAR;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;

public class ActivityGarageCustomerDetail extends BaseActivity implements GarageDetailContractor.View, OnRecyclerviewClick {
    private Realm realm;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private String customerId = "";
    private String vehicle_number = "";
    private String vehicle_type = "";
    private ArrayList<ResponseVehicleCustomerList.Data> vehicleSummuryList = new ArrayList<ResponseVehicleCustomerList.Data>();
    private ArrayList<ResponseVehicleCustomerList.Data> vehicleSummuryListPdf = new ArrayList<ResponseVehicleCustomerList.Data>();
    private GarageDetailContractor.Presenter mPresenter;
    private GarageDetailAdapter garageDetailAdapter;
    private String languageCode = "";
    private String fileN = null;
    private String nameTest = "";

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.imageview_profile)
    CircleImageView imageview_profile;
    @BindView(R.id.textview_name)
    TextView textview_name;
    @BindView(R.id.textview_address)
    TextView textview_address;
    @BindView(R.id.textview_mobileNumber)
    TextView textview_mobileNumber;
    @BindView(R.id.textview_id)
    TextView textview_id;
    @BindView(R.id.textview_VehicleType)
    TextView textview_VehicleType;
    @BindView(R.id.textview_Number)
    TextView textview_Number;
    @BindView(R.id.recyclerview_list)
    RecyclerView recyclerview_list;
    @BindView(R.id.imageviewWhatsaapshareActvity)
    ImageView imageviewWhatsaapshareActvity;

    @OnClick(R.id.imageview_filter)
    void imageview_filter(){
        Intent intent = new Intent(ActivityGarageCustomerDetail.this, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }

    @OnClick(R.id.imageviewWhatsaapshareActvity)
    void imageviewWhatsaapshareActvity(){
        if (Utility.getInstance().isOnline(ActivityGarageCustomerDetail.this)) {
            getPdf();
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.fabshare)
    void fabshare() {
        if (Utility.getInstance().isOnline(ActivityGarageCustomerDetail.this)) {
            getPdf();
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        imageviewWhatsaapshareActvity.setVisibility(View.VISIBLE);
    }

    private void getPdf() {

        VehicleReportPdfRequest vehicleReportPdfRequest = new VehicleReportPdfRequest();
        String currentDateForName = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivityGarageCustomerDetail.this);
        //String firstnameForPdf = sharedPreferences.getString("FirstName", "");
        nameTest = textview_name.getText().toString()+" "+ currentDateForName;
        vehicleReportPdfRequest.setPDFName(nameTest);
        vehicleReportPdfRequest.setRegId(Utility.getInstance().getclientRegId(ActivityGarageCustomerDetail.this));
        languageCode = Utility.getInstance().getLanguage(ActivityGarageCustomerDetail.this);
        if (languageCode.matches("") || languageCode.matches(langaugeCodeEnglish)) {
            vehicleReportPdfRequest.setLangID("0");
        } else if (languageCode.matches(languageCodeMarathi)) {
            vehicleReportPdfRequest.setLangID("1");
        } else {
            vehicleReportPdfRequest.setLangID("1");
        }



        ArrayList<VehicleReportPdfRequest.RecordList> arrayListRecordList = new ArrayList<>();
        int i = 1;
        for (ResponseVehicleCustomerList.Data item : vehicleSummuryListPdf){
            VehicleReportPdfRequest.RecordList recordList = new VehicleReportPdfRequest().new RecordList();
            recordList.setVsrno(String.valueOf(i));
            i++;
            recordList.setBusinessname(sharedPreferences.getString("ShopName", ""));
            recordList.setClientvillage(sharedPreferences.getString("Village", ""));
            recordList.setContactno(sharedPreferences.getString("ContactNumber", ""));
            recordList.setCustomername(textview_name.getText().toString());
            recordList.setVehicleno(textview_Number.getText().toString());
            recordList.setVehicletype(textview_VehicleType.getText().toString());
            String dateTwo = item.getServieDate().toString();
            String subDateTwo = dateTwo.substring(0, 10);
            String yyyy2 = subDateTwo.substring(0, 4);
            String mm2 = subDateTwo.substring(5, 7);
            String dd2 = subDateTwo.substring(8, 10);
            recordList.setDate(dd2 + "/" + mm2 + "/" + yyyy2);
            recordList.setServicedetail(item.getComment());
            String date = item.getDueDate().toString();
            String subDate = date.substring(0, 10);
            String yyyy = subDate.substring(0, 4);
            String mm = subDate.substring(5, 7);
            String dd = subDate.substring(8, 10);
            recordList.setNextservicedate(dd + "/" + mm + "/" + yyyy);
            recordList.setMilage(item.getCurrentKM().toString());
            arrayListRecordList.add(recordList);
        }

        vehicleReportPdfRequest.setRecordListArrayList(arrayListRecordList);
        Utility.getInstance().showProgressDialogue(ActivityGarageCustomerDetail.this);
        mPresenter.getPDFData(vehicleReportPdfRequest);
    }


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_garage_customer_detail;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new GarageDetailPresenter(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText(getString(R.string.vehilce_details));
        realm = RealmController.with(this).getRealm();
        getIntentData();
        getCusomerData();
        setRecyclerview();
        getVehicleDetail();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == FILTERACTVITYREQUESTCODE) {
            filterListFunctions(data);
        }
    }


    private void filterListFunctions(Intent data) {
        Utility.getInstance().showProgressDialogue(ActivityGarageCustomerDetail.this);
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
                SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
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
                SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
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

        ArrayList<ResponseVehicleCustomerList.Data> filterArraylist = new ArrayList<>();
        filterArraylist.clear();
        filterArraylist.addAll(ascendingDescendingFilter);
        garageDetailAdapter.filterList(filterArraylist);
        vehicleSummuryListPdf.clear();
        vehicleSummuryListPdf.addAll(filterArraylist);
     //   garageDetailAdapter.notifyDataSetChanged();
        Utility.getInstance().dismissProgress();
    }






    private void setRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityGarageCustomerDetail.this);
        garageDetailAdapter = new GarageDetailAdapter(ActivityGarageCustomerDetail.this, vehicleSummuryList, onRecyclerviewClick);
        recyclerview_list.setLayoutManager(linearLayoutManager);
        recyclerview_list.setAdapter(garageDetailAdapter);
        vehicleSummuryListPdf.clear();
        vehicleSummuryListPdf.addAll(vehicleSummuryList);
    }

    private void getVehicleDetail() {
        if (Utility.getInstance().isOnline(ActivityGarageCustomerDetail.this)) {
            Utility.getInstance().showProgressDialogue(ActivityGarageCustomerDetail.this);
            mPresenter.getVehclieDetails(Utility.getInstance().getclientRegId(ActivityGarageCustomerDetail.this), customerId);
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }


    private void getIntentData() {
        customerId = getIntent().getStringExtra("customerId");
        vehicle_number = getIntent().getStringExtra("vehicleNumber");
        vehicle_type = getIntent().getStringExtra("vehicleType");
        textview_Number.setText(getIntent().getStringExtra("vehicleNumber"));
        textview_VehicleType.setText(getIntent().getStringExtra("vehicleType"));
    }


    private void getCusomerData() {
        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class)
                .equalTo("CustomerId", customerId)
                .findAll();
        for (CustomerListRealm object : customerListRealmModels) {
            CustomerListResponse.Data.tblCustomers tblCustomers = new CustomerListResponse().new Data().new tblCustomers();
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
            Glide.with(ActivityGarageCustomerDetail.this).load(new CustomRetroRequest().imageURL + object.getImagePath())
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
            } else {
                textview_id.setText("-");
            }

            break;
        }
    }

    @Override
    public void vehicleCustomerListSuccess(ResponseVehicleCustomerList body) {
        vehicleSummuryList.clear();

        for(ResponseVehicleCustomerList.Data item : body.getDataArrayList()) {
            if (item.getCustomerId().matches(customerId) && vehicle_number.toLowerCase().trim().matches(item.getVehicleNo().trim().toLowerCase())
            && vehicle_type.toLowerCase().trim().matches(item.getVehicleType().toLowerCase().trim())) {
                vehicleSummuryList.add(item);
            }
        }
        vehicleSummuryListPdf.clear();
        vehicleSummuryListPdf.addAll(vehicleSummuryList);
        garageDetailAdapter.notifyDataSetChanged();
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void vehicleCustomerListFailure(String s) {
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void getPdfResponse(PdfResponse body) {
        Utility.getInstance().dismissProgress();
        if (body.getStatus().matches("1")) {
            donwloadPDF(body);
        } else {
            Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
        }
    }

    private void donwloadPDF(PdfResponse body) {
//        int value = checkTwoPermission(ActivityOutstandingList.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueGallery = checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        if (valueGallery == 1) {
            checkFolder();
            Toast.makeText(this, getString(R.string.Downloading), Toast.LENGTH_SHORT).show();
            String url = body.getPdfpath();
            url = url.substring(1);
            newDownload(new CustomRetroRequest().pdfURL + url);
        } else {
            //       displayNeverAskAgainDialog(ActivityOutstandingList.this, getString(R.string.We_need_permission));
        }
    }

    private void newDownload(String url) {
        final DownloadTask downloadTask = new DownloadTask(ActivityGarageCustomerDetail.this);
        downloadTask.execute(url);
    }

    public void checkFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Ramumunimji/";
        File dir = new File(path);
        boolean isDirectoryCreated = dir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdir();
        }
        if (isDirectoryCreated) {
            // do something\
            Log.d("Folder", "Already Created");
        }
    }

    private void sharedPdf() {
        File file = new File(Environment.getExternalStorageDirectory() + "/Ramumunimji/" + nameTest + ".pdf");

        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = new File(file.getPath());

        if (fileWithinMyDir.exists()) {
            intentShareFile.setType("application/pdf");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getPath()));

            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                    "Sharing File...");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

            startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }
    }


    @Override
    public void getPdfFailure(String s) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReclerViewClick(View view, int position) {

    }


    // DownloadTask for downloding video from URL
    public class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;

        //  private PowerManager.WakeLock mWakeLock;
        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                java.net.URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();

                input = connection.getInputStream();

                fileN = nameTest + ".pdf";
                File filename = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Ramumunimji/", fileN);
                output = new FileOutputStream(filename);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utility.getInstance().showProgressDialogue(ActivityGarageCustomerDetail.this);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // bnp.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            //    mWakeLock.release();
            //   downloadDialog.dismiss();
            Utility.getInstance().dismissProgress();
            if (result != null) {
             //   Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, getString(R.string.File_downloaded), Toast.LENGTH_SHORT).show();
            }
            MediaScannerConnection.scanFile(ActivityGarageCustomerDetail.this,
                    new String[]{Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "Ramumunimji" + fileN}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String newpath, Uri newuri) {
                            Log.i("ExternalStorage", "Scanned " + newpath + ":");
                            Log.i("ExternalStorage", "-> uri=" + newuri);
                        }
                    });

            sharedPdf();

        }
    }


}
