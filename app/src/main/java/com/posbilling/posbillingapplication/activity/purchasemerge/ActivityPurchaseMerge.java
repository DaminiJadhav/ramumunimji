package com.posbilling.posbillingapplication.activity.purchasemerge;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.SupplierListRealm;
import com.posbilling.posbillingapplication.model.request.MergeRequest;
import com.posbilling.posbillingapplication.model.response.GetSupplierResponseTwo;
import com.posbilling.posbillingapplication.model.response.GetSuppliersResponse;
import com.posbilling.posbillingapplication.model.response.PurchaseApiResponse;
import com.posbilling.posbillingapplication.utility.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

public class ActivityPurchaseMerge extends BaseActivity implements PurchaseMergeContractor.View, OnRecyclerviewClick{
    private PurchaseMergeContractor.Presenter mPresenter;
    private LinearLayoutManager linearLayoutManager;
    private PurchaseMergeAdapter purchaseMergeAdapter;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private ArrayList<GetSupplierResponseTwo.Data> supplierList = new ArrayList();
    private Realm realm;
    private ArrayList<GetSupplierResponseTwo.Data> filterArraylist = new ArrayList<GetSupplierResponseTwo.Data>();
    private String supplierToStore = "";
    private String supplierId = "";
    private MaterialDialog materialDialog;
    String DestinationSupplierId = "";


    @BindView(R.id.edittext_search)
    EditText edittext_search;
    @BindView(R.id.imageviewMic)
    ImageView imageviewMic;
    @BindView(R.id.recyclerviewCustomerList)
    RecyclerView recyclerviewCustomerList;



    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_merge;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new PurchaseMergePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm();
        getIntentData();
        setlayout();
        setSearchFilter();
        getCustomerList();
    }

    //textchangelistner on listo
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
        for (GetSupplierResponseTwo.Data name : supplierList) {
            String firstname = "";
            String middlename = "";
            String lastname = "";
            /*if (name.getSLastname() != null) {
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
        purchaseMergeAdapter.filterList(filterArraylist);
    }


    private void getCustomerList() {
        if (Utility.getInstance().isOnline(this)) {
            Utility.getInstance().showProgressDialogue(this);

            mPresenter.getSupplierList(Utility.getInstance().getclientRegId(this).toString());
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
            RealmResults<SupplierListRealm> customerListRealmModels = realm.where(SupplierListRealm.class).findAll();
            for (SupplierListRealm object : customerListRealmModels) {
                GetSupplierResponseTwo.Data tblCustomers = new GetSupplierResponseTwo().new Data();
                tblCustomers.setID(object.getID());
                tblCustomers.setSFirstname(object.getSFirstname());
/*                tblCustomers.setSMiddleName(object.getSMiddleName());
                tblCustomers.setSLastname(object.getSLastname());*/
                tblCustomers.setImagePath(object.getImagePath());
                tblCustomers.setClientId(object.getClientId());
                tblCustomers.setOutstanding(object.getOutstanding());
                supplierList.add(tblCustomers);
            }
            //  setSizeandAddition();
  /*          for (CustomerListResponse.Data.tblCustomers item : customerList){
                if (item.getCustomerId().toString().toLowerCase().trim().matches(customerToStore.trim().toLowerCase())){
                    customerList.remove(item);
                }
            }
*/
            filterArraylist.clear();
            filterArraylist.addAll(supplierList);
            purchaseMergeAdapter.notifyDataSetChanged();
        }
    }


    private void setlayout() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerviewCustomerList.setLayoutManager(linearLayoutManager);
        purchaseMergeAdapter = new PurchaseMergeAdapter(this, supplierList, onRecyclerviewClick, supplierToStore);
        recyclerviewCustomerList.setAdapter(purchaseMergeAdapter);
    }


    private void getIntentData() {
        supplierToStore = getIntent().getStringExtra("StringSupplierId");
        supplierId = getIntent().getStringExtra("SUPPLIERID");
    }

    @Override
    public void onReclerViewClick(View view, int position) {
        if (Utility.getInstance().isOnline(this)) {
            RealmResults<SupplierListRealm> customerListRealmModels = realm.where(SupplierListRealm.class)
                    .findAll();


            if (!(customerListRealmModels.size()>0)) {
                if (filterArraylist.get(position).getSupplierId().toString().trim().toLowerCase().matches(supplierToStore.trim().toLowerCase())) {
                    Toast.makeText(this, getString(R.string.Please_select_other_customer), Toast.LENGTH_SHORT).show();
                } else {
                    LayoutInflater layoutInflater = LayoutInflater.from(ActivityPurchaseMerge.this);
                    View view1 = layoutInflater.inflate(R.layout.logout_dialogue, null);
                    TextView yes = view1.findViewById(R.id.button_yes);
                    TextView text_title = view1.findViewById(R.id.text_title);
                    TextView no = view1.findViewById(R.id.button_no);
                    TextView text_logout = view1.findViewById(R.id.text_logout);
                    text_logout.setText(getString(R.string.merge_confirmation));
                    text_title.setText(getString(R.string.merge));
                    yes.setText(getString(R.string.yes));
                    no.setText(getString(R.string.no));
                    materialDialog = new MaterialDialog.Builder(ActivityPurchaseMerge.this)
                            .customView(view1, false)
                            .show();
                    materialDialog.setCanceledOnTouchOutside(false);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            MergeRequest mergeRequest = new MergeRequest();
                            mergeRequest.setRegId(Utility.getInstance().getclientRegId(ActivityPurchaseMerge.this));
                            mergeRequest.setDestinationCustomerId(supplierId);
                            DestinationSupplierId = filterArraylist.get(position).getID();
                            mergeRequest.setSourceCustomerId(filterArraylist.get(position).getID());
                            Utility.getInstance().showProgressDialogue(ActivityPurchaseMerge.this);


                            mPresenter.postmergeOperation(mergeRequest);

                            //materialDialog.dismiss();
                            // mPresenter.getLogout(userId, privateKey, mContext);

                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            materialDialog.dismiss();
                        }
                    });
                }
            }else{
                View view1 = getWindow().getDecorView().getRootView();
                //    Utility.getInstance().showSnackbar(view1, "Ofline data available.");
            }
        } else {
            View view1 = getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(view1, getString(R.string.please_check_internet));
        }
    }

    @Override
    public void supplierListSuccess(GetSupplierResponseTwo response) {
        supplierList.clear();
        RealmController.with(this).clearCustomerList();
        double total = 0.0;
        supplierList.addAll(response.getDataArrayList());
        filterArraylist.clear();
        filterArraylist.addAll(supplierList);
        ArrayList<SupplierListRealm> customerListRealmArrayList = new ArrayList<>();
        for (int i = 0; i < supplierList.size(); i++) {
            SupplierListRealm customerListRealm = new SupplierListRealm();
            customerListRealm.setID(supplierList.get(i).getID());
            customerListRealm.setSupplierId(supplierList.get(i).getSupplierId());



            customerListRealm.setClientId(supplierList.get(i).getClientId());
            customerListRealm.setSFirstname(supplierList.get(i).getSFirstname());
            customerListRealm.setStateId(supplierList.get(i).getStateId());
            customerListRealm.setSMiddleName("");
            customerListRealm.setSLastname("");
            customerListRealm.setImagePath(supplierList.get(i).getImagePath());
            customerListRealm.setOutstanding(supplierList.get(i).getOutstanding());
            customerListRealm.setVillage(supplierList.get(i).getVillage());
            customerListRealm.setTaluka(supplierList.get(i).getTaluka());
            customerListRealm.setDistrict(supplierList.get(i).getDistrict());
            customerListRealm.setSAlternetMobNo(supplierList.get(i).getSAlternetMobNo());
            customerListRealm.setSContactNo(supplierList.get(i).getSContactNo());
            customerListRealmArrayList.add(customerListRealm);
        }
        //   Log.i(getClass().getName(), "=========tempListHelpScreenModel SIZE: " + tempListHelpScreenModel.size());
        for (SupplierListRealm customerListRealm : customerListRealmArrayList) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(customerListRealm);
            realm.commitTransaction();
        }

/*
        int i =0;
        for (CustomerListResponse.Data.tblCustomers item : customerList){
            if (item.getCustomerId().toString().toLowerCase().trim().matches(customerToStore.trim().toLowerCase())){
               // customerList.remove(item);
                customerList.remove(i);
            }
            i++;
        }*/

        filterArraylist.clear();
        filterArraylist.addAll(supplierList);
        if (!(supplierList.size() <= 0)) {
            recyclerviewCustomerList.setVisibility(View.VISIBLE);
            //textview_Please_add_customer.setVisibility(View.GONE);


            purchaseMergeAdapter.notifyDataSetChanged();
            //Ankur
            // textviewCustomerNumber.setText(String.valueOf(response.getData().getTblCustomerslist().size()));
        }

        Utility.getInstance().dismissProgress();
    }

    @Override
    public void supplierListFailure(String s) {
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void mergeUserSuccess(PurchaseApiResponse body) {
        materialDialog.dismiss();
        materialDialog.dismiss();
        Utility.getInstance().dismissProgress();
        onBackPressed();
    }

    @Override
    public void mergeUserFailure(String s) {
        materialDialog.dismiss();
        materialDialog.dismiss();
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
    }
}
