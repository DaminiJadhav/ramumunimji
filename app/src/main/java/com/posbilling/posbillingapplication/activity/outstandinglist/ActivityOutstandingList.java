package com.posbilling.posbillingapplication.activity.outstandinglist;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.fonts.Font;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.editcustomer.ActivityEditCustomer;
import com.posbilling.posbillingapplication.activity.edittransaction.ActivityEditTransaction;
import com.posbilling.posbillingapplication.activity.filter.ActivityFilter;
import com.posbilling.posbillingapplication.activity.merge.ActivityMerge;
import com.posbilling.posbillingapplication.activity.splash.ActivitySplash;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;

import com.posbilling.posbillingapplication.model.request.PdfDetailOutstandingRequest;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.model.response.TransactionListResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.utility.Common;
import com.posbilling.posbillingapplication.utility.Downloader;
import com.posbilling.posbillingapplication.utility.Utility;

import java.io.File;
import java.io.FileNotFoundException;
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
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.internal.Util;

import static com.itextpdf.kernel.pdf.PdfName.BaseFont;
import static com.itextpdf.styledxmlparser.css.CommonCssConstants.FONT;
import static com.posbilling.posbillingapplication.utility.Constants.ASCENDINGDESCENDING;
import static com.posbilling.posbillingapplication.utility.Constants.CUSTOMERID;
import static com.posbilling.posbillingapplication.utility.Constants.DISTRICT;
import static com.posbilling.posbillingapplication.utility.Constants.FILTERACTVITYREQUESTCODE;
import static com.posbilling.posbillingapplication.utility.Constants.FROMDATE;
import static com.posbilling.posbillingapplication.utility.Constants.GALLERY_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.LOGPOS;
import static com.posbilling.posbillingapplication.utility.Constants.MONTH;
import static com.posbilling.posbillingapplication.utility.Constants.SEEALL;
import static com.posbilling.posbillingapplication.utility.Constants.TALUKA;
import static com.posbilling.posbillingapplication.utility.Constants.TODATE;
import static com.posbilling.posbillingapplication.utility.Constants.VILLAGE;
import static com.posbilling.posbillingapplication.utility.Constants.YEAR;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;

/**
 * Created by Android PC (Ankur) on 02,March,2020
 */
public class ActivityOutstandingList extends BaseActivity implements OnRecyclerviewClick, OutstandingListContractor.View {

    private ArrayList<TransactionListResponse.TransactionList> outsandingList = new ArrayList<TransactionListResponse.TransactionList>();
    private ArrayList<TransactionListResponse.TransactionList> listForPdf = new ArrayList<TransactionListResponse.TransactionList>();
    private OutstandigListAdapter outstandigListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private String customerId;
    private OutstandingListContractor.Presenter mPresenter;
    private Realm realm;
    private String customerIdToStore = "";
    private String fileN = null;
    private String Customerfinalname = "";
    private String filternamePdf = "";
    private String villageNameForPdf = "";
    private String firstnameForPdf = "";
    private String nameTest = "";
    private String languageCode = "";



    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.recyclerview_list)
    RecyclerView recyclerview_list;
    @BindView(R.id.imageview_profile)
    CircleImageView imageview_profile;
    @BindView(R.id.textview_name)
    TextView textview_name;
    @BindView(R.id.textview_address)
    TextView textview_address;
    @BindView(R.id.textview_mobileNumber)
    TextView textview_mobileNumber;
    @BindView(R.id.textview_totalAmount)
    TextView textview_totalAmount;
    @BindView(R.id.textview_id)
    TextView textview_id;
    @BindView(R.id.textView_PeaseAddTransaction)
    TextView textView_PeaseAddTransaction;
    @BindView(R.id.ll_titleHeader)
    LinearLayout ll_titleHeader;
    @BindView(R.id.textviewHeader_Credit)
    TextView textviewHeader_Credit;
    @BindView(R.id.textviewHeader_Debit)
    TextView textviewHeader_Debit;
    @BindView(R.id.textviewHeader_Liter)
    TextView textviewHeader_Liter;
    @BindView(R.id.textviewHeader_Entry)
    TextView textviewHeader_Entry;
    @BindView(R.id.imageviewWhatsaapshareActvity)
    ImageView imageviewWhatsaapshareActvity;
    @BindView(R.id.imageviewMerge)
    ImageView imageviewMerge;
    @BindView(R.id.imageviewFilter)
    ImageView imageviewFilter;

    @OnClick(R.id.imageviewMerge)
    void imageviewMerge(){
        Intent intent = new Intent(ActivityOutstandingList.this, ActivityMerge.class);
        intent.putExtra(CUSTOMERID, customerId);
        intent.putExtra("StringCustomerId", customerIdToStore);
        startActivityForResult(intent, 401);
    }

    /*@OnClick(R.id.fabshare)
    void fabshare() {
        if (Utility.getInstance().isOnline(ActivityOutstandingList.this)) {
            getPdf();
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }*/

    @OnClick(R.id.imageviewWhatsaapshareActvity)
    void imageviewWhatsaapshareActvity(){
        if (Utility.getInstance().isOnline(ActivityOutstandingList.this)) {
            getPdf();
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.textview_merge)
    void textview_merge() {
        Intent intent = new Intent(ActivityOutstandingList.this, ActivityMerge.class);
        intent.putExtra(CUSTOMERID, customerId);
        intent.putExtra("StringCustomerId", customerIdToStore);
        startActivityForResult(intent, 401);
    }

    @OnClick(R.id.imageview_filter)
    void imagviewFilter() {
        Intent intent = new Intent(ActivityOutstandingList.this, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }


    @OnClick(R.id.imageview_profile)
    void imageview_profile() {
        Intent intent = new Intent(ActivityOutstandingList.this, ActivityEditCustomer.class);
        intent.putExtra(CUSTOMERID, customerId);
        intent.putExtra("StringCustomerId", customerIdToStore);
        startActivityForResult(intent, 101);
    }

    @OnClick(R.id.imageviewFilter)
    void imageviewFilter(){
        Intent intent = new Intent(ActivityOutstandingList.this, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }

    @OnClick(R.id.whatsapp_floatingaction_button)
    void whatsapp_floatingaction_button(){
        if (Utility.getInstance().isOnline(ActivityOutstandingList.this)) {
            getPdf();
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_outstanding_list;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new OutstandingListPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        imageviewWhatsaapshareActvity.setVisibility(View.GONE);
        imageviewMerge.setVisibility(View.GONE);
    }

    private void getPdf() {
        PdfDetailOutstandingRequest pdfDetailOutstandingRequest = new PdfDetailOutstandingRequest();
        String currentDateForName = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivityOutstandingList.this);
      //  pdfDetailOutstandingRequest.setPDFName(sharedPreferences.getString("FirstName", "")+""+currentDateForName);
        nameTest = firstnameForPdf+currentDateForName;
        pdfDetailOutstandingRequest.setPDFName(nameTest);
        pdfDetailOutstandingRequest.setRegId(Utility.getInstance().getclientRegId(ActivityOutstandingList.this));
        ArrayList<PdfDetailOutstandingRequest.RecordList> recordListArrayList = new ArrayList<>();


        languageCode = Utility.getInstance().getLanguage(ActivityOutstandingList.this);
        if (languageCode.matches("") || languageCode.matches(langaugeCodeEnglish)) {
            pdfDetailOutstandingRequest.setLangID("0");
        } else if (languageCode.matches(languageCodeMarathi)) {
            pdfDetailOutstandingRequest.setLangID("1");
        } else {
            pdfDetailOutstandingRequest.setLangID("1");
        }





        for (int i = 0; i < listForPdf.size(); i++) {
            PdfDetailOutstandingRequest.RecordList recordList = new PdfDetailOutstandingRequest().new RecordList();
            recordList.setIndexid(i + 1);
            /**
             set filter string
             */
            if (!TextUtils.isEmpty(filternamePdf)) {
                recordList.setDate(filternamePdf);
            }else {
                recordList.setDate("-");
            }
            recordList.setCounts(String.valueOf(listForPdf.size()));

            String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            Date date = null;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(currentDate + " " + currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            recordList.setCurrentdate(date);

            recordList.setRegId(listForPdf.get(i).getID());


            String buinsnessName = sharedPreferences.getString("ShopName", "-");
            String fistname = sharedPreferences.getString("FirstName", "");
            String lastname = sharedPreferences.getString("LastName", "");
            String finalName = fistname + " " + lastname;

            recordList.setFirstname(finalName);

            String clientVillageName = sharedPreferences.getString("Village","");
             String[] splited = clientVillageName.split("\\s+");

            recordList.setBusinessName(buinsnessName);
            recordList.setVillage(splited[0]);
           String cntNo = sharedPreferences.getString("ContactNumber","");
            recordList.setContactNo(cntNo);
            recordList.setID(String.valueOf(textview_id.getText()));
            recordList.setCFirstname(Customerfinalname+" "+villageNameForPdf);
            if (i == 0) {
                recordList.setOutstanding(removeContainsDot(textview_totalAmount.getText().toString())+".00");
            } else {
                recordList.setOutstanding("0.00");
            }

            String transactionid=listForPdf.get(i).getTransactionId();
            int id=i+1;
            recordList.setTransactionId("R"+id);

//            recordList.setTransactionId(listForPdf.get(i).getTransactionId());

            String billDate = listForPdf.get(i).getBillDate();
            String subDate = billDate.substring(0, 10);
            String yyyy = subDate.substring(0, 4);
            String mm = subDate.substring(5, 7);
            String dd = subDate.substring(8, 10);
//            holder.textview_date.setText();
            String time = listForPdf.get(i).getTime();
            String subTime = time.substring(0, 5);
            //           holder.textview_time.setText(subTime);

            String finalBillDate = dd + "/" + mm + "/" + yyyy;

            String billDateChange = finalBillDate;
            String billTimeChange = subTime;

            Date dateBillfinal = null;
            try {
                dateBillfinal = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(billDateChange + " " + billTimeChange);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            recordList.setBillDate(dateBillfinal);
            if (listForPdf.get(i).getCrAmt() != null) {
                recordList.setCrAmt(listForPdf.get(i).getCrAmt());
            } else {
                recordList.setCrAmt("0");
            }

            if (listForPdf.get(i).getDrAmt() != null) {
                recordList.setDrAmt(listForPdf.get(i).getDrAmt());
            } else {
                recordList.setDrAmt("0");
            }
            recordList.setPurpose(listForPdf.get(i).getPurpose());
            recordListArrayList.add(recordList);
        }

        pdfDetailOutstandingRequest.setRecordListArrayList(recordListArrayList);
        Utility.getInstance().showProgressDialogue(ActivityOutstandingList.this);
        mPresenter.getPDFData(pdfDetailOutstandingRequest);
    }




    private void getIntentData() {
        customerId = getIntent().getStringExtra(CUSTOMERID);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        toolbar_title.setText(getString(R.string.outstanding_list));
        realm = RealmController.with(this).getRealm();
        imageviewFilter.setVisibility(View.VISIBLE);
        getIntentData();
        setDynamicView();
        setAdapter();
        getCusomerData();
        getCustomerTransactionList();
    }


    private void setDynamicView() {
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivityOutstandingList.this);
        if (true) {
            ll_titleHeader.setWeightSum(1f);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_titleHeader.getLayoutParams();
            LinearLayout.LayoutParams layoutParams_two = (LinearLayout.LayoutParams) ll_titleHeader.getLayoutParams();
            LinearLayout.LayoutParams layoutParams_three = (LinearLayout.LayoutParams) ll_titleHeader.getLayoutParams();

            layoutParams.weight = 0.4f;
            layoutParams_two.weight = 0.3f;
            layoutParams_three.weight = 0.3f;
            int padding_top = getResources().getDimensionPixelSize(R.dimen._4sdp);
            int padding_bottom = getResources().getDimensionPixelSize(R.dimen._4sdp);
            int padding_left = getResources().getDimensionPixelSize(R.dimen._5sdp);
            int padding_right = getResources().getDimensionPixelSize(R.dimen._5sdp);

            ll_titleHeader.setPadding(padding_left, padding_top, padding_right, padding_bottom);
            textviewHeader_Entry.setLayoutParams(layoutParams);
            //  layoutParams.weight = 40;
            textviewHeader_Credit.setLayoutParams(layoutParams_two);
            textviewHeader_Credit.setGravity(Gravity.LEFT);
            //layoutParams.weight = 40;
            textviewHeader_Debit.setLayoutParams(layoutParams_three);

            int padding_text = getResources().getDimensionPixelSize(R.dimen._25sdp);
            textviewHeader_Debit.setPadding(padding_text, 0, 0, 0);
            textviewHeader_Debit.setGravity(Gravity.LEFT);
            textviewHeader_Liter.setVisibility(View.GONE);
        }
    }

    private void getCusomerData() {
        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
        for (CustomerListRealm object : customerListRealmModels) {
            CustomerListResponse.Data.tblCustomers tblCustomers = new CustomerListResponse().new Data().new tblCustomers();
            if (customerId.toLowerCase().trim().matches(object.getID().toLowerCase().trim())) {
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
                    firstnameForPdf = firstname;
                }
                String finalname = "";
                if (TextUtils.isEmpty(middlename)) {
                    finalname = firstname + " " + lastname;
                } else {
                    finalname = firstname + " " + middlename + " " + lastname;
                }
                Customerfinalname = finalname;
                textview_name.setText(finalname);
                String path = object.getImagePath();

                //https://ramu.sdaemon.com/images//Ramuji//C136.jpg
                Glide.with(ActivityOutstandingList.this).load(new CustomRetroRequest().imageURL + object.getImagePath())
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
                    villageNameForPdf = village;
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
                    customerIdToStore = object.getCustomerId();
                } else {
                    textview_id.setText("-");
                }
                break;
            }
        }
    }

    private void getCustomerTransactionList() {
        if (Utility.getInstance().isOnline(ActivityOutstandingList.this) && !TextUtils.isEmpty(customerId)) {
            Utility.getInstance().showProgressDialogue(ActivityOutstandingList.this);
            mPresenter.getTransactionList(Utility.getInstance().getclientRegId(ActivityOutstandingList.this), customerId);
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {
        outstandigListAdapter = new OutstandigListAdapter(ActivityOutstandingList.this, outsandingList, onRecyclerviewClick);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerview_list.setLayoutManager(linearLayoutManager);
        recyclerview_list.setAdapter(outstandigListAdapter);
    }

    @Override
    public void onReclerViewClick(View view, int position) {
        Intent intent = new Intent(ActivityOutstandingList.this, ActivityEditTransaction.class);
        intent.putExtra("transactionId", listForPdf.get(position).getTransactionId());
        intent.putExtra("debitAmount", listForPdf.get(position).getDrAmt());
        intent.putExtra("creditAmount", listForPdf.get(position).getCrAmt());
        intent.putExtra("date", listForPdf.get(position).getBillDate());
        intent.putExtra("purpose", listForPdf.get(position).getPurpose());
        intent.putExtra("customerID",customerId);
        intent.putExtra("customerIdToStore",customerIdToStore);
        intent.putExtra("customerName",textview_name.getText().toString());
        intent.putExtra("transId",listForPdf.get(position).getID());
        startActivityForResult(intent, 408);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void getTraansactionListSuccess(TransactionListResponse response) {
        // storeInRealm(response.getDataList());

        outsandingList.clear();
        listForPdf.clear();
        for (TransactionListResponse.TransactionList item : response.getDataList()) {
            item.setOnline(true);
            outsandingList.add(item);
            listForPdf.add(item);
        }
        outstandigListAdapter.notifyDataSetChanged();


        if (outsandingList.size() > 0) {
            int amount = 0;
            for (TransactionListResponse.TransactionList item : outsandingList) {
                amount = amount + Integer.valueOf(removeContainsDot(item.getDrAmt()));
                amount = amount - Integer.valueOf(removeContainsDot(item.getCrAmt()));
            }

            String input = String.valueOf(amount);
            /*            String result = input.substring(0, input.indexOf("."));*/

            textview_totalAmount.setText(removeContainsDot(input));
            recyclerview_list.setVisibility(View.VISIBLE);
            textView_PeaseAddTransaction.setVisibility(View.GONE);
        } else {
            textview_totalAmount.setText("0");
            recyclerview_list.setVisibility(View.GONE);
            textView_PeaseAddTransaction.setVisibility(View.VISIBLE);
        }
        Utility.getInstance().dismissProgress();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            getCusomerData();
        } else if (resultCode == RESULT_OK && requestCode == 401) {
            getCustomerTransactionList();
        } else if (resultCode == Activity.RESULT_OK && requestCode == FILTERACTVITYREQUESTCODE) {
            filterListFunctions(data);
        }else if (resultCode == RESULT_OK && requestCode == 408) {
            getCustomerTransactionList();
        }
    }

    private void filterListFunctions(Intent data) {
        filternamePdf = "";
        Utility.getInstance().showProgressDialogue(ActivityOutstandingList.this);
        Intent intent = data;

        ArrayList<TransactionListResponse.TransactionList> transactionListRealms = new ArrayList();
        transactionListRealms.addAll(outsandingList);
        ArrayList<TransactionListResponse.TransactionList> finalFilterList = new ArrayList<>();
        ArrayList<TransactionListResponse.TransactionList> villageFilterList = new ArrayList<>();
//        ApplicableFilterList.addAll(customerList);


        String villageName = intent.getStringExtra(VILLAGE);

        //Village filter is added here on list
        if (intent.getStringExtra(VILLAGE) != null && !TextUtils.isEmpty(intent.getStringExtra(VILLAGE))) {
            villageFilterList.addAll(transactionListRealms);
        } else {
            villageFilterList.addAll(transactionListRealms);
        }

        ///Taluka filter is added here
        ArrayList<TransactionListResponse.TransactionList> talukaFilterList = new ArrayList<>();
        if (intent.getStringExtra(TALUKA) != null && !TextUtils.isEmpty(intent.getStringExtra(TALUKA))) {
            talukaFilterList.addAll(villageFilterList);
        } else {
            talukaFilterList.addAll(villageFilterList);
        }

        ///District filter is added here
        ArrayList<TransactionListResponse.TransactionList> districtFilterList = new ArrayList<>();
        if (intent.getStringExtra(DISTRICT) != null && !TextUtils.isEmpty(intent.getStringExtra(DISTRICT))) {
            districtFilterList.addAll(talukaFilterList);
        } else {
            districtFilterList.addAll(talukaFilterList);
        }

        ///fromDatefilter
        ArrayList<TransactionListResponse.TransactionList> fromDateFilter = new ArrayList<>();
        if (intent.getStringExtra(FROMDATE) != null && !TextUtils.isEmpty(intent.getStringExtra(FROMDATE))) {
            filternamePdf = filternamePdf+intent.getStringExtra(FROMDATE);
            for (TransactionListResponse.TransactionList item : districtFilterList) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    String fromdate = intent.getStringExtra(FROMDATE);
                    Calendar d1 = Calendar.getInstance();
                    d1.setTime(sdformat.parse(fromdate));
                    String date = item.getBillDate();
                    String subDate = date.substring(0, 10);
                    Calendar d2 = Calendar.getInstance();

                    String year = subDate.substring(0, 4);
                    String month = subDate.substring(5, 7);
                    String dateString = subDate.substring(8, 10);
                    d2.setTime(sdformat.parse(dateString + "/" + month + "/" + year));

                    int i = d1.compareTo(d2);
                    if (d2.after(d1)) {
                        fromDateFilter.add(item);
                    }

                    if (d2.equals(d1)) {
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
        ArrayList<TransactionListResponse.TransactionList> toDateFilter = new ArrayList<>();
        if (intent.getStringExtra(TODATE) != null && !TextUtils.isEmpty(intent.getStringExtra(TODATE))) {
            filternamePdf = filternamePdf+"-"+intent.getStringExtra(TODATE);
            for (TransactionListResponse.TransactionList item : fromDateFilter) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    Calendar d1 = Calendar.getInstance();
                    String dateTo = intent.getStringExtra(TODATE);
                    d1.setTime(sdformat.parse(intent.getStringExtra(TODATE)));
                    String date = item.getBillDate();
                    String subDate = date.substring(0, 10);
                    Calendar d2 = Calendar.getInstance();
                    String year = subDate.substring(0, 4);
                    String month = subDate.substring(5, 7);
                    String dateString = subDate.substring(8, 10);
                    d2.setTime(sdformat.parse(dateString + "/" + month + "/" + year));

                    if (d2.before(d1)) {
                        toDateFilter.add(item);
                    }

                    if (d1.equals(d2)) {
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
        ArrayList<TransactionListResponse.TransactionList> monthFilter = new ArrayList<>();
        if (intent.getStringExtra(MONTH) != null && !TextUtils.isEmpty(intent.getStringExtra(MONTH))) {
            filternamePdf = filternamePdf+"-"+intent.getStringExtra("monthStringForPdf");
            for (TransactionListResponse.TransactionList item : toDateFilter) {
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    String date = item.getBillDate();
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
        ArrayList<TransactionListResponse.TransactionList> yearFilter = new ArrayList<>();
        if (intent.getStringExtra(YEAR) != null && !TextUtils.isEmpty(intent.getStringExtra(YEAR))) {
            filternamePdf = filternamePdf+"-"+intent.getStringExtra(YEAR);
            for (TransactionListResponse.TransactionList item : monthFilter) {
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    String date = item.getBillDate();
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
        ArrayList<TransactionListResponse.TransactionList> ascendingDescendingFilter = new ArrayList<>();
        ascendingDescendingFilter.addAll(yearFilter);

      /*  finalFilterList.addAll(ascendingDescendingFilter);
        filterArraylist.clear();*/
        ArrayList<TransactionListResponse.TransactionList> transactionFilterfunction = new ArrayList();
        for (TransactionListResponse.TransactionList item : ascendingDescendingFilter) {
            TransactionListResponse.TransactionList transactionList = new TransactionListResponse().new TransactionList();
            transactionList.setClientId(Utility.getInstance().getclientRegId(ActivityOutstandingList.this));
            transactionList.setID(item.getID());
            transactionList.setCFirstname(item.getCFirstname());
            transactionList.setCLastname(item.getCLastname());
            transactionList.setImagename("");
            transactionList.setImagePath("");
            transactionList.setCContactNo("");
            transactionList.setBillDate(item.getBillDate());
            transactionList.setTime(item.getTime());
            transactionList.setCrAmt(item.getCrAmt());
            transactionList.setDrAmt(item.getDrAmt());
            transactionList.setPurpose(item.getPurpose());
            transactionList.setLiter(item.getLiter());
            transactionList.setPaymentStatus("");
            transactionList.setTransactionId(item.getTransactionId());
            transactionFilterfunction.add(transactionList);
        }
        // setSizeandAddition();
        String ascendingdescding = intent.getStringExtra(ASCENDINGDESCENDING);
        if (intent.getStringExtra(ASCENDINGDESCENDING).matches("Ascending")) {
            for (int i = 0; i < transactionFilterfunction.size(); i++) {
                for (int j = i + 1; j < transactionFilterfunction.size(); j++) {

                    String creditAmtI = removeContainsDot(transactionFilterfunction.get(i).getCrAmt());
                    String creditAmtJ = removeContainsDot(transactionFilterfunction.get(j).getCrAmt());
                    String debitAmtI = removeContainsDot(transactionFilterfunction.get(i).getDrAmt());
                    String debitAmtJ = removeContainsDot(transactionFilterfunction.get(j).getDrAmt());


                    if (creditAmtI != null && Integer.parseInt(creditAmtI) > 0) {
                        if (Integer.parseInt(creditAmtI) > Integer.parseInt(creditAmtJ)
                                || Integer.parseInt(creditAmtI) > Integer.parseInt(debitAmtJ)
                        ) {
                            TransactionListResponse.TransactionList temp = transactionFilterfunction.get(i);
                            transactionFilterfunction.set(i, transactionFilterfunction.get(j));
                            transactionFilterfunction.set(j, temp);
                        }
                    } else {
                        if (Integer.parseInt(debitAmtI) > Integer.parseInt(debitAmtJ)
                                || Integer.parseInt(debitAmtI) > Integer.parseInt(creditAmtJ)
                        ) {
                            TransactionListResponse.TransactionList temp = transactionFilterfunction.get(i);
                            transactionFilterfunction.set(i, transactionFilterfunction.get(j));
                            transactionFilterfunction.set(j, temp);
                        }
                    }
                }
            }
        } else if (intent.getStringExtra(ASCENDINGDESCENDING).matches("Desecnding")) {
            for (int i = 0; i < transactionFilterfunction.size(); i++) {
                for (int j = i + 1; j < transactionFilterfunction.size(); j++) {

                    String creditAmtI = removeContainsDot(transactionFilterfunction.get(i).getCrAmt());
                    String creditAmtJ = removeContainsDot(transactionFilterfunction.get(j).getCrAmt());
                    String debitAmtI = removeContainsDot(transactionFilterfunction.get(i).getDrAmt());
                    String debitAmtJ = removeContainsDot(transactionFilterfunction.get(j).getDrAmt());


                    if (creditAmtI != null && Integer.parseInt(creditAmtI) > 0) {
                        if (Integer.parseInt(creditAmtI) > Integer.parseInt(creditAmtJ)
                                || Integer.parseInt(creditAmtI) > Integer.parseInt(debitAmtJ)
                        ) {
                            TransactionListResponse.TransactionList temp = transactionFilterfunction.get(i);
                            transactionFilterfunction.set(i, transactionFilterfunction.get(j));
                            transactionFilterfunction.set(j, temp);
                        }
                    } else {
                        if (Integer.parseInt(debitAmtI) > Integer.parseInt(debitAmtJ)
                                || Integer.parseInt(debitAmtI) > Integer.parseInt(creditAmtJ)
                        ) {
                            TransactionListResponse.TransactionList temp = transactionFilterfunction.get(i);
                            transactionFilterfunction.set(i, transactionFilterfunction.get(j));
                            transactionFilterfunction.set(j, temp);
                        }
                    }
                }
                Collections.reverse(transactionFilterfunction);
            }
        }

//see all filter
        ////Selected year filter
        ArrayList<TransactionListResponse.TransactionList> seeAllFilter = new ArrayList<>();
        if (intent.getStringExtra(SEEALL) != null && !TextUtils.isEmpty(intent.getStringExtra(SEEALL))) {
            for (TransactionListResponse.TransactionList item : transactionFilterfunction) {
                String creditAmtI = removeContainsDot(item.getCrAmt());
                String debitAmtI = removeContainsDot(item.getDrAmt());

                if (creditAmtI != null && debitAmtI != null) {
                    // String value1 = item.getOutstanding();
                    String value2 = intent.getStringExtra(SEEALL);
                    String value3 = intent.getStringExtra("seeall");
                    if (Integer.valueOf(intent.getStringExtra(SEEALL)) == 0) {
                        seeAllFilter.add(item);
                    } else if (Integer.parseInt(creditAmtI) <= Integer.parseInt(intent.getStringExtra(SEEALL)) && Integer.parseInt(debitAmtI) <= Integer.parseInt(intent.getStringExtra(SEEALL))) {
                        seeAllFilter.add(item);
                    }
                }
            }
        } else {
            seeAllFilter.addAll(transactionFilterfunction);
        }

        finalFilterList.clear();
        finalFilterList.addAll(seeAllFilter);
        outstandigListAdapter.filterList(finalFilterList);
        listForPdf.clear();
        listForPdf.addAll(finalFilterList);
        outstandigListAdapter.notifyDataSetChanged();
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
    public void getTraansactionListFailure(String message) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
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

    @Override
    public void getPdfFailure(String s) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
    }

    private void donwloadPDF(PdfResponse body) {
//        int value = checkTwoPermission(ActivityOutstandingList.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueGallery = checkPermission(ActivityOutstandingList.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        if (valueGallery == 1) {
            checkFolder();
            Toast.makeText(ActivityOutstandingList.this, getString(R.string.Downloading), Toast.LENGTH_SHORT).show();
            String url = body.getPdfpath();
            url = url.substring(1);
            newDownload(new CustomRetroRequest().pdfURL + url);
        } else {
     //       displayNeverAskAgainDialog(ActivityOutstandingList.this, getString(R.string.We_need_permission));
        }
    }


    private void newDownload(String url) {
        final DownloadTask downloadTask = new DownloadTask(ActivityOutstandingList.this);
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
            Utility.getInstance().showProgressDialogue(ActivityOutstandingList.this);
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
                //Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
                Log.e(LOGPOS, "onPostExecute: "+"Download error: " );
            } else {
                Toast.makeText(context, getString(R.string.File_downloaded), Toast.LENGTH_SHORT).show();
            }
            MediaScannerConnection.scanFile(ActivityOutstandingList.this,
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
