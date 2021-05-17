package com.posbilling.posbillingapplication.activity.supplieractivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
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
import com.posbilling.posbillingapplication.activity.editsupplier.ActivityEditSupplier;
import com.posbilling.posbillingapplication.activity.filter.ActivityFilter;
import com.posbilling.posbillingapplication.activity.purchasemerge.ActivityPurchaseMerge;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.SupplierListRealm;
import com.posbilling.posbillingapplication.model.response.GetSuppliersOutstandingResponse;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
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
import static com.posbilling.posbillingapplication.utility.Constants.DISTRICT;
import static com.posbilling.posbillingapplication.utility.Constants.FILTERACTVITYREQUESTCODE;
import static com.posbilling.posbillingapplication.utility.Constants.FROMDATE;
import static com.posbilling.posbillingapplication.utility.Constants.MONTH;
import static com.posbilling.posbillingapplication.utility.Constants.SEEALL;
import static com.posbilling.posbillingapplication.utility.Constants.TALUKA;
import static com.posbilling.posbillingapplication.utility.Constants.TODATE;
import static com.posbilling.posbillingapplication.utility.Constants.VILLAGE;
import static com.posbilling.posbillingapplication.utility.Constants.YEAR;


public class ActivitySupplierOutstandingList extends BaseActivity implements SupplierContractor.View, OnRecyclerviewClick {

    private String firstnameForPdf = "";
    private Realm realm;
    private String supplierId;
    private SupplierListAdapter supplierListAdapter;
    private String supplierIdToStore = "";
    private String supplierfinalname = "";
    private LinearLayoutManager linearLayoutManager;
    private String villageNameForPdf = "";
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private SupplierContractor.Presenter mPresenter;
    private ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> supplierDetailList = new ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices>();
    private ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> listForPdf = new ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices>();
    private String filternamePdf = "";

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
    void imageviewMerge() {
    /*    Intent intent = new Intent(ActivitySupplierOutstandingList.this, ActivityMerge.class);
        intent.putExtra(CUSTOMERID, customerId);
        intent.putExtra("StringCustomerId", customerIdToStore);
        startActivityForResult(intent, 401);*/
    }

    @OnClick(R.id.fabshare)
    void fabshare() {
/*        if (Utility.getInstance().isOnline(ActivityOutstandingList.this)) {
            getPdf();
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }*/
    }

    @OnClick(R.id.imageviewWhatsaapshareActvity)
    void imageviewWhatsaapshareActvity() {
   /*     if (Utility.getInstance().isOnline(ActivityOutstandingList.this)) {
            getPdf();
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }*/
    }

    @OnClick(R.id.textview_merge)
    void textview_merge() {
        Intent intent = new Intent(ActivitySupplierOutstandingList.this, ActivityPurchaseMerge.class);
        intent.putExtra("SUPPLIERID", supplierId);
        intent.putExtra("StringSupplierId", supplierIdToStore);
        startActivityForResult(intent, 401);
    }

    @OnClick(R.id.imageview_filter)
    void imagviewFilter() {
        Intent intent = new Intent(ActivitySupplierOutstandingList.this, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }


    @OnClick(R.id.imageview_profile)
    void imageview_profile() {
        Intent intent = new Intent(ActivitySupplierOutstandingList.this, ActivityEditSupplier.class);
        intent.putExtra("supplierID", supplierId);
        intent.putExtra("StringSupplierId", supplierIdToStore);
        startActivityForResult(intent, 101);
    }

    @OnClick(R.id.imageviewFilter)
    void imageviewFilter() {
        Intent intent = new Intent(ActivitySupplierOutstandingList.this, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_supplier_outstandinglist;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new SupplierPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //     imageviewWhatsaapshareActvity.setVisibility(View.VISIBLE);
        //       imageviewMerge.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            getSupplierData();
        } else if (resultCode == RESULT_OK && requestCode == 401) {
            getSupplierTransactionList();
        } else if (resultCode == Activity.RESULT_OK && requestCode == FILTERACTVITYREQUESTCODE) {
            filterListFunctions(data);
        } else if (resultCode == RESULT_OK && requestCode == 408) {
            getSupplierTransactionList();
        }
    }

    private void filterListFunctions(Intent data) {
        filternamePdf = "";
        Utility.getInstance().showProgressDialogue(ActivitySupplierOutstandingList.this);
        Intent intent = data;

        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> transactionListRealms = new ArrayList();
        transactionListRealms.addAll(supplierDetailList);
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> finalFilterList = new ArrayList<>();
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> villageFilterList = new ArrayList<>();
//        ApplicableFilterList.addAll(customerList);


        String villageName = intent.getStringExtra(VILLAGE);

        //Village filter is added here on list
        if (intent.getStringExtra(VILLAGE) != null && !TextUtils.isEmpty(intent.getStringExtra(VILLAGE))) {
            villageFilterList.addAll(transactionListRealms);
        } else {
            villageFilterList.addAll(transactionListRealms);
        }

        ///Taluka filter is added here
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> talukaFilterList = new ArrayList<>();
        if (intent.getStringExtra(TALUKA) != null && !TextUtils.isEmpty(intent.getStringExtra(TALUKA))) {
            talukaFilterList.addAll(villageFilterList);
        } else {
            talukaFilterList.addAll(villageFilterList);
        }

        ///District filter is added here
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> districtFilterList = new ArrayList<>();
        if (intent.getStringExtra(DISTRICT) != null && !TextUtils.isEmpty(intent.getStringExtra(DISTRICT))) {
            districtFilterList.addAll(talukaFilterList);
        } else {
            districtFilterList.addAll(talukaFilterList);
        }

        ///fromDatefilter
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> fromDateFilter = new ArrayList<>();
        if (intent.getStringExtra(FROMDATE) != null && !TextUtils.isEmpty(intent.getStringExtra(FROMDATE))) {
            filternamePdf = filternamePdf + intent.getStringExtra(FROMDATE);
            for (GetSuppliersOutstandingResponse.Data.tblKharediInvices item : districtFilterList) {
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
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> toDateFilter = new ArrayList<>();
        if (intent.getStringExtra(TODATE) != null && !TextUtils.isEmpty(intent.getStringExtra(TODATE))) {
            filternamePdf = filternamePdf + "-" + intent.getStringExtra(TODATE);
            for (GetSuppliersOutstandingResponse.Data.tblKharediInvices item : fromDateFilter) {
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
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> monthFilter = new ArrayList<>();
        if (intent.getStringExtra(MONTH) != null && !TextUtils.isEmpty(intent.getStringExtra(MONTH))) {
            filternamePdf = filternamePdf + "-" + intent.getStringExtra("monthStringForPdf");
            for (GetSuppliersOutstandingResponse.Data.tblKharediInvices item : toDateFilter) {
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
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> yearFilter = new ArrayList<>();
        if (intent.getStringExtra(YEAR) != null && !TextUtils.isEmpty(intent.getStringExtra(YEAR))) {
            filternamePdf = filternamePdf + "-" + intent.getStringExtra(YEAR);
            for (GetSuppliersOutstandingResponse.Data.tblKharediInvices item : monthFilter) {
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
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> ascendingDescendingFilter = new ArrayList<>();
        ascendingDescendingFilter.addAll(yearFilter);

      /*  finalFilterList.addAll(ascendingDescendingFilter);
        filterArraylist.clear();*/
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> transactionFilterfunction = new ArrayList();
        for (GetSuppliersOutstandingResponse.Data.tblKharediInvices item : ascendingDescendingFilter) {
            GetSuppliersOutstandingResponse.Data.tblKharediInvices transactionList = new GetSuppliersOutstandingResponse().new Data().new tblKharediInvices();
            transactionList.setClientId(Utility.getInstance().getclientRegId(ActivitySupplierOutstandingList.this));
            transactionList.setID(item.getID());
/*            transactionList.setCFirstname(item.getCFirstname());
            transactionList.setCLastname(item.getCLastname());*/
/*            transactionList.setImagename("");
            transactionList.setImagePath("");
            transactionList.setCContactNo("");*/
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
                            GetSuppliersOutstandingResponse.Data.tblKharediInvices temp = transactionFilterfunction.get(i);
                            transactionFilterfunction.set(i, transactionFilterfunction.get(j));
                            transactionFilterfunction.set(j, temp);
                        }
                    } else {
                        if (Integer.parseInt(debitAmtI) > Integer.parseInt(debitAmtJ)
                                || Integer.parseInt(debitAmtI) > Integer.parseInt(creditAmtJ)
                        ) {
                            GetSuppliersOutstandingResponse.Data.tblKharediInvices temp = transactionFilterfunction.get(i);
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
                            GetSuppliersOutstandingResponse.Data.tblKharediInvices temp = transactionFilterfunction.get(i);
                            transactionFilterfunction.set(i, transactionFilterfunction.get(j));
                            transactionFilterfunction.set(j, temp);
                        }
                    } else {
                        if (Integer.parseInt(debitAmtI) > Integer.parseInt(debitAmtJ)
                                || Integer.parseInt(debitAmtI) > Integer.parseInt(creditAmtJ)
                        ) {
                            GetSuppliersOutstandingResponse.Data.tblKharediInvices temp = transactionFilterfunction.get(i);
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
        ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> seeAllFilter = new ArrayList<>();
        if (intent.getStringExtra(SEEALL) != null && !TextUtils.isEmpty(intent.getStringExtra(SEEALL))) {
            for (GetSuppliersOutstandingResponse.Data.tblKharediInvices item : transactionFilterfunction) {
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
        supplierListAdapter.filterList(finalFilterList);
        listForPdf.clear();
        listForPdf.addAll(finalFilterList);
        supplierListAdapter.notifyDataSetChanged();
        Utility.getInstance().dismissProgress();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        toolbar_title.setText(getString(R.string.supplier_list));
        realm = RealmController.with(this).getRealm();
        imageviewFilter.setVisibility(View.VISIBLE);
        getIntentData();
        setDynamicView();
        setAdapter();
        getSupplierData();
        getSupplierTransactionList();
    }

    private void getSupplierTransactionList() {
        if (Utility.getInstance().isOnline(ActivitySupplierOutstandingList.this) && !TextUtils.isEmpty(supplierId)) {
            Utility.getInstance().showProgressDialogue(ActivitySupplierOutstandingList.this);
            mPresenter.getTransactionList(Utility.getInstance().getclientRegId(ActivitySupplierOutstandingList.this), supplierId);
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void getIntentData() {
        supplierId = getIntent().getStringExtra("supplierID");
    }

    private void getSupplierData() {
        RealmResults<SupplierListRealm> supplierListRealmModels = realm.where(SupplierListRealm.class).findAll();
        for (SupplierListRealm object : supplierListRealmModels) {
            if (supplierId.toLowerCase().trim().matches(object.getID().toLowerCase().trim())) {
                String firstname = "";
                String middlename = "";
                String lastname = "";
                if (object.getSLastname() != null) {
                    lastname = object.getSLastname();
                }
                if (object.getSMiddleName() != null) {
                    middlename = object.getSMiddleName();
                }
                if (object.getSFirstname() != null) {
                    firstname = object.getSFirstname();
                    firstnameForPdf = firstname;
                }
                String finalname = "";
                if (TextUtils.isEmpty(middlename)) {
                    finalname = firstname + " " + lastname;
                } else {
                    finalname = firstname + " " + middlename + " " + lastname;
                }
                supplierfinalname = finalname;
                textview_name.setText(finalname);
                String path = object.getImagePath();

                //https://ramu.sdaemon.com/images//Ramuji//C136.jpg
                Glide.with(ActivitySupplierOutstandingList.this).load(new CustomRetroRequest().imageURL + object.getImagePath())
                        .into(imageview_profile);

                if (object.getSContactNo() != null && !TextUtils.isEmpty(object.getSContactNo().toString())) {
                    textview_mobileNumber.setText(object.getSContactNo());
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

                if (object.getSupplierId() != null && !TextUtils.isEmpty(object.getSupplierId())) {
                    textview_id.setText(object.getSupplierId());
                    supplierIdToStore = object.getSupplierId();
                } else {
                    textview_id.setText("-");
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }


    private void setDynamicView() {
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivitySupplierOutstandingList.this);
        if (!sharedPreferences.getString("BusinessId", "").matches("0")) {
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

    private void setAdapter() {
        supplierListAdapter = new SupplierListAdapter(ActivitySupplierOutstandingList.this, supplierDetailList, onRecyclerviewClick);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerview_list.setLayoutManager(linearLayoutManager);
        recyclerview_list.setAdapter(supplierListAdapter);
    }

    //Ankur Trial Comment
    @Override
    public void getTraansactionListSuccess(GetSuppliersOutstandingResponse response) {
        supplierDetailList.clear();
        listForPdf.clear();
        for (GetSuppliersOutstandingResponse.Data.tblKharediInvices item : response.getData().getTblKharediInvicesArrayList()) {
            supplierDetailList.add(item);
            listForPdf.add(item);
        }
////Ankur
        supplierListAdapter.notifyDataSetChanged();


        if (supplierDetailList.size() > 0) {
            int amount = 0;
            for (GetSuppliersOutstandingResponse.Data.tblKharediInvices item : supplierDetailList) {
                amount = amount - Integer.valueOf(removeContainsDot(item.getDrAmt()));
                amount = amount + Integer.valueOf(removeContainsDot(item.getCrAmt()));
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

    }

    @Override
    public void getPdfResponse(PdfResponse body) {

    }

    @Override
    public void getPdfFailure(String s) {

    }

    @Override
    public void onReclerViewClick(View view, int position) {

    }
}
