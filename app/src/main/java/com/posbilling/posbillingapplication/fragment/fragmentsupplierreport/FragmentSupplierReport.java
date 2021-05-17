package com.posbilling.posbillingapplication.fragment.fragmentsupplierreport;

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
import com.posbilling.posbillingapplication.activity.supplieractivity.ActivitySupplierOutstandingList;
import com.posbilling.posbillingapplication.activity.activityparentreport.ActivityParentReport;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.activity.filter.ActivityFilter;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.SupplierListRealm;
import com.posbilling.posbillingapplication.model.response.GetSupplierResponseTwo;
import com.posbilling.posbillingapplication.model.response.GetSuppliersResponse;
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
import static com.posbilling.posbillingapplication.utility.Constants.DISTRICT;
import static com.posbilling.posbillingapplication.utility.Constants.FILTERACTVITYREQUESTCODE;
import static com.posbilling.posbillingapplication.utility.Constants.FROMDATE;
import static com.posbilling.posbillingapplication.utility.Constants.MONTH;
import static com.posbilling.posbillingapplication.utility.Constants.SEEALL;
import static com.posbilling.posbillingapplication.utility.Constants.TALUKA;
import static com.posbilling.posbillingapplication.utility.Constants.TODATE;
import static com.posbilling.posbillingapplication.utility.Constants.VILLAGE;
import static com.posbilling.posbillingapplication.utility.Constants.YEAR;

public class FragmentSupplierReport extends BaseFragment implements SupplierSummuryContract.View, OnRecyclerviewClick {
    private Realm realm;
    private ArrayList<GetSupplierResponseTwo.Data> suppliersList = new ArrayList();

    private SupplierSummuryAdapter adapterSupplierSummury;
    private Context mContext;
    private LinearLayoutManager linearLayoutManager;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private ArrayList<GetSupplierResponseTwo.Data> filterArraylist = new ArrayList<GetSupplierResponseTwo.Data>();
    private SupplierSummuryContract.Presenter mPresenter;

    @BindView(R.id.recyclerviewSupplierSummury)
    RecyclerView recyclerviewSupplierSummury;
    @BindView(R.id.textview_suppliernumber)
    TextView textview_suppliernumber;
    @BindView(R.id.textviewTotal)
    TextView textviewTotal;
    @BindView(R.id.textview_Please_add_customer)
    TextView textview_Please_add_customer;
    @BindView(R.id.edittext_search)
    EditText edittext_search;
    @BindView(R.id.imageviewMic)
    ImageView imageviewMic;

    @OnClick(R.id.imagviewFilter)
    void imagviewFilter() {
        Intent intent = new Intent(mContext, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }

    @OnClick(R.id.imageviewMic)
    void imageviewMic() {
        speechToTextFunc(mContext, edittext_search, 1, imageviewMic);
    }


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new SupplierSummuryPresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_supplier_report;
    }

    @Override
    public void onMakeServerCalls() {
        ((ActivityParentReport) getContext()).setTitleOfScreen(getString(R.string.supplier_summary));
        ((ActivityParentReport) getContext()).setVisibilityofRamumunimjiVisible(false, 1);
        realm = RealmController.with(this).getRealm();
        setRecyclerView();
        setSearchFilter();
        getSupplierData();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 101) {
            suppliersList.clear();
            getSupplierData();
        } else if (resultCode == Activity.RESULT_OK && requestCode == FILTERACTVITYREQUESTCODE) {
            filterListFunctions(data);
        }
    }


    private void filterListFunctions(Intent data) {
        Utility.getInstance().showProgressDialogue(mContext);
        Intent intent = data;

        RealmResults<SupplierListRealm> supplierListRealmsModels = realm.where(SupplierListRealm.class).findAll();

        ArrayList<SupplierListRealm> finalFilterList = new ArrayList<>();
        ArrayList<SupplierListRealm> villageFilterList = new ArrayList<>();
//        ApplicableFilterList.addAll(customerList);


        String villageName = intent.getStringExtra(VILLAGE);

        //Village filter is added here on list
        if (intent.getStringExtra(VILLAGE) != null && !TextUtils.isEmpty(intent.getStringExtra(VILLAGE))) {
            for (SupplierListRealm item : supplierListRealmsModels) {
                String village = item.getVillage();
                if (item.getVillage() != null && item.getVillage().toLowerCase().trim().contains(intent.getStringExtra(VILLAGE).toLowerCase().trim())) {
                    villageFilterList.add(item);
                }
            }
        } else {
            villageFilterList.addAll(supplierListRealmsModels);
        }

        ///Taluka filter is added here
        ArrayList<SupplierListRealm> talukaFilterList = new ArrayList<>();
        if (intent.getStringExtra(TALUKA) != null && !TextUtils.isEmpty(intent.getStringExtra(TALUKA))) {
            for (SupplierListRealm item : villageFilterList) {
                if (item.getTaluka() != null && item.getTaluka().toLowerCase().trim().contains(intent.getStringExtra(TALUKA).toLowerCase().trim())) {
                    talukaFilterList.add(item);
                }
            }
        } else {
            talukaFilterList.addAll(villageFilterList);
        }

        ///District filter is added here
        ArrayList<SupplierListRealm> districtFilterList = new ArrayList<>();
        if (intent.getStringExtra(DISTRICT) != null && !TextUtils.isEmpty(intent.getStringExtra(DISTRICT))) {
            for (SupplierListRealm item : talukaFilterList) {
                if (item.getDistrict() != null && item.getDistrict().toLowerCase().trim().contains(intent.getStringExtra(DISTRICT).toLowerCase().trim())) {
                    districtFilterList.add(item);
                }
            }
        } else {
            districtFilterList.addAll(talukaFilterList);
        }

        ///fromDatefilter
        ArrayList<SupplierListRealm> fromDateFilter = new ArrayList<>();
        if (intent.getStringExtra(FROMDATE) != null && !TextUtils.isEmpty(intent.getStringExtra(FROMDATE))) {
            for (SupplierListRealm item : districtFilterList) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    String fromdate = intent.getStringExtra(FROMDATE);
                    Calendar d1 = Calendar.getInstance();
                    d1.setTime(sdformat.parse(fromdate));
                    String date = item.getCreatedOn();
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
        ArrayList<SupplierListRealm> toDateFilter = new ArrayList<>();
        if (intent.getStringExtra(TODATE) != null && !TextUtils.isEmpty(intent.getStringExtra(TODATE))) {
            for (SupplierListRealm item : fromDateFilter) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    Calendar d1 = Calendar.getInstance();
                    String dateTo = intent.getStringExtra(TODATE);
                    d1.setTime(sdformat.parse(intent.getStringExtra(TODATE)));
                    String date = item.getCreatedOn();
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
        ArrayList<SupplierListRealm> monthFilter = new ArrayList<>();
        if (intent.getStringExtra(MONTH) != null && !TextUtils.isEmpty(intent.getStringExtra(MONTH))) {
            for (SupplierListRealm item : toDateFilter) {
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
        ArrayList<SupplierListRealm> yearFilter = new ArrayList<>();
        if (intent.getStringExtra(YEAR) != null && !TextUtils.isEmpty(intent.getStringExtra(YEAR))) {
            for (SupplierListRealm item : monthFilter) {
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
        ArrayList<SupplierListRealm> ascendingDescendingFilter = new ArrayList<>();
        ascendingDescendingFilter.addAll(yearFilter);

      /*  finalFilterList.addAll(ascendingDescendingFilter);
        filterArraylist.clear();*/
        ArrayList<GetSupplierResponseTwo.Data> supplierListFilterfunction = new ArrayList();
        for (SupplierListRealm object : ascendingDescendingFilter) {
            GetSupplierResponseTwo.Data tblCustomers = new GetSupplierResponseTwo().new Data();
            tblCustomers.setID(object.getID());
            tblCustomers.setSFirstname(object.getSFirstname());
/*            tblCustomers.setSMiddleName(object.getSMiddleName());
            tblCustomers.setSLastname(object.getSLastname());*/
            tblCustomers.setImagePath(object.getImagePath());
            tblCustomers.setClientId(object.getClientId());
            tblCustomers.setOutstanding(object.getOutstanding());
            supplierListFilterfunction.add(tblCustomers);
        }
        // setSizeandAddition();
        String ascendingdescding = intent.getStringExtra(ASCENDINGDESCENDING);
        if (intent.getStringExtra(ASCENDINGDESCENDING).matches("Ascending")) {
            for (int i = 0; i < supplierListFilterfunction.size(); i++) {
                for (int j = i + 1; j < supplierListFilterfunction.size(); j++) {
                    if (Integer.parseInt(supplierListFilterfunction.get(i).getOutstanding()) > Integer.parseInt(supplierListFilterfunction.get(j).getOutstanding())) {
                        GetSupplierResponseTwo.Data temp = supplierListFilterfunction.get(i);
                        supplierListFilterfunction.set(i, supplierListFilterfunction.get(j));
                        supplierListFilterfunction.set(j, temp);
                    }
                }
            }
        } else if (intent.getStringExtra(ASCENDINGDESCENDING).matches("Desecnding")) {
            for (int i = 0; i < supplierListFilterfunction.size(); i++) {
                for (int j = i + 1; j < supplierListFilterfunction.size(); j++) {
                    if (Integer.parseInt(supplierListFilterfunction.get(i).getOutstanding()) > Integer.parseInt(supplierListFilterfunction.get(j).getOutstanding())) {
                        GetSupplierResponseTwo.Data temp = supplierListFilterfunction.get(i);
                        supplierListFilterfunction.set(i, supplierListFilterfunction.get(j));
                        supplierListFilterfunction.set(j, temp);
                    }
                }
            }
            Collections.reverse(supplierListFilterfunction);
        }

//see all filter
        ////Selected year filter
        ArrayList<GetSupplierResponseTwo.Data> seeAllFilter = new ArrayList<>();
        if (intent.getStringExtra(SEEALL) != null && !TextUtils.isEmpty(intent.getStringExtra(SEEALL))) {
            for (GetSupplierResponseTwo.Data item : supplierListFilterfunction) {
                if (item.getOutstanding() != null) {
                    String value1 = item.getOutstanding();
                    String value2 = intent.getStringExtra(SEEALL);
                    String value3 = intent.getStringExtra("seeall");
                    if (Integer.parseInt(intent.getStringExtra(SEEALL)) == 0) {
                        seeAllFilter.add(item);
                    } else if (Integer.parseInt(item.getOutstanding().toString()) <= Integer.parseInt(intent.getStringExtra(SEEALL))) {
                        seeAllFilter.add(item);
                    }
                }
            }
        } else {
            seeAllFilter.addAll(supplierListFilterfunction);
        }

        filterArraylist.clear();
        filterArraylist.addAll(seeAllFilter);
        adapterSupplierSummury.filterList(filterArraylist);
        adapterSupplierSummury.notifyDataSetChanged();
        Utility.getInstance().dismissProgress();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext == null) {
            mContext = ((ActivityDashboard) getContext());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.mContext = context;
        super.onAttach(context);
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
        for (GetSupplierResponseTwo.Data name : suppliersList) {
            String firstname = "";
            String middlename = "";
            String lastname = "";
/*            if (name.getSLastname() != null) {
                lastname = name.getSLastname();
            }
            if (name.getSMiddleName() != null) {
                middlename = name.getSMiddleName();
            }*/
            if (name.getSFirstname() != null) {
                firstname = name.getSFirstname();
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
        adapterSupplierSummury.filterList(filterArraylist);
    }


    //setting recyclerview
    private void setRecyclerView() {
        suppliersList.clear();
        linearLayoutManager = new LinearLayoutManager(mContext);
        filterArraylist.addAll(suppliersList);
        adapterSupplierSummury = new SupplierSummuryAdapter(mContext, suppliersList, onRecyclerviewClick);
        recyclerviewSupplierSummury.setLayoutManager(linearLayoutManager);
        recyclerviewSupplierSummury.setAdapter(adapterSupplierSummury);
    }

    private void getSupplierData() {
        if (Utility.getInstance().isOnline(mContext)) {
            Utility.getInstance().showProgressDialogue(mContext);
            mPresenter.getSupplierList(Utility.getInstance().getclientRegId(mContext).toString());
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void supplierListSuccess(GetSupplierResponseTwo response) {
        suppliersList.clear();
        //RealmController.with(getActivity()).clearCustomerList()
        realm.beginTransaction();
        realm.where(SupplierListRealm.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        double total = 0.0;
        suppliersList.addAll(response.getDataArrayList());
        filterArraylist.clear();
        filterArraylist.addAll(suppliersList);
        ArrayList<SupplierListRealm> supplierListRealmArrayList = new ArrayList<>();
        for (int i = 0; i < suppliersList.size(); i++) {
            SupplierListRealm supplierListRealm = new SupplierListRealm();
            supplierListRealm.setID(suppliersList.get(i).getID());
            supplierListRealm.setSupplierId(suppliersList.get(i).getSupplierId());
            supplierListRealm.setClientId(suppliersList.get(i).getClientId());
            supplierListRealm.setSFirstname(suppliersList.get(i).getSFirstname());
            supplierListRealm.setSMiddleName("");
            supplierListRealm.setSLastname("");
            supplierListRealm.setCreatedOn("");
            supplierListRealm.setStateId(suppliersList.get(i).getStateId());
            supplierListRealm.setImagePath(suppliersList.get(i).getImagePath());
            String outstanding = suppliersList.get(i).getOutstanding();
            if (outstanding != null && outstanding.contains(".")) {
                outstanding = outstanding.substring(0, outstanding.indexOf("."));
            } else if (outstanding == null) {
                outstanding = "0";
            }


            supplierListRealm.setOutstanding(outstanding);
            supplierListRealm.setVillage(suppliersList.get(i).getVillage());
            supplierListRealm.setTaluka(suppliersList.get(i).getTaluka());
            supplierListRealm.setDistrict(suppliersList.get(i).getDistrict());
            supplierListRealm.setSAlternetMobNo(suppliersList.get(i).getSAlternetMobNo());
            supplierListRealm.setSContactNo(suppliersList.get(i).getSContactNo());
            if (suppliersList.get(i).getSMSFlag() != null && suppliersList.get(i).getSMSFlag().trim().toLowerCase().matches("true")) {
                supplierListRealm.setSMSFlag("true");
            } else {
                supplierListRealm.setSMSFlag("false");
            }

            realm.beginTransaction();
            realm.where(SupplierListRealm.class)
                    .equalTo("SupplierId", suppliersList.get(i).getSupplierId()).findAll().deleteAllFromRealm();
            realm.commitTransaction();

            supplierListRealmArrayList.add(supplierListRealm);
        }
        //   Log.i(getClass().getName(), "=========tempListHelpScreenModel SIZE: " + tempListHelpScreenModel.size());
        for (SupplierListRealm supplierListRealm : supplierListRealmArrayList) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(supplierListRealm);
            realm.commitTransaction();
        }


        suppliersList.clear();
        filterArraylist.clear();


        RealmResults<SupplierListRealm> supplierListRealmModels = realm.where(SupplierListRealm.class).findAll();
        for (SupplierListRealm object : supplierListRealmModels) {
            GetSupplierResponseTwo.Data tblSuppliers = new GetSupplierResponseTwo().new Data();
            tblSuppliers.setID(object.getID());
            tblSuppliers.setSFirstname(object.getSFirstname());
 /*           tblSuppliers.setSMiddleName(object.getSMiddleName());
            tblSuppliers.setSLastname(object.getSLastname());*/
            tblSuppliers.setImagePath(object.getImagePath());
            tblSuppliers.setClientId(object.getClientId());
            tblSuppliers.setOutstanding(object.getOutstanding());
            suppliersList.add(tblSuppliers);
        }
        setSizeandAddition();
        filterArraylist.addAll(suppliersList);
//        adapterOutstandingSummury.notifyDataSetChanged();

        if (!(suppliersList.size() <= 0)) {
            recyclerviewSupplierSummury.setVisibility(View.VISIBLE);
            textview_Please_add_customer.setVisibility(View.GONE);
            adapterSupplierSummury.notifyDataSetChanged();
            //Ankur
            // textviewCustomerNumber.setText(String.valueOf(response.getData().getTblCustomerslist().size()));
            setSizeandAddition();

        } else {
            recyclerviewSupplierSummury.setVisibility(View.GONE);
            textview_Please_add_customer.setVisibility(View.VISIBLE);
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


    private void setSizeandAddition() {
        textview_suppliernumber.setText(String.valueOf(suppliersList.size()));
        int total = 0;
        for (GetSupplierResponseTwo.Data listItem : suppliersList) {
            if (listItem.getOutstanding() != null) {
                total = total + Integer.parseInt(removeContainsDot(listItem.getOutstanding()));
            }
        }

        String outstanding = String.valueOf(total);
        //String result = outstanding.substring(0, outstanding.indexOf("."));
        textviewTotal.setText(mContext.getString(R.string.Rs) + " " + String.valueOf(removeContainsDot(outstanding)));
    }

    @Override
    public void supplierListFailure(String message) {
        Toast.makeText(mContext, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void onReclerViewClick(View view, int position) {
        Intent intent = new Intent(mContext, ActivitySupplierOutstandingList.class);
        intent.putExtra("supplierID", filterArraylist.get(position).getID().toString());
        startActivityForResult(intent, 101);
    }

    public void methodSetFilter() {
        Intent intent = new Intent(mContext, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }
}
