package com.posbilling.posbillingapplication.activity.merge;

import android.content.Intent;
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
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;

import com.posbilling.posbillingapplication.model.request.MergeRequest;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.MergeApiResponse;
import com.posbilling.posbillingapplication.utility.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.posbilling.posbillingapplication.utility.Constants.CUSTOMERID;

public class ActivityMerge extends BaseActivity implements MergeContractor.View, OnRecyclerviewClick {

    private MergeContractor.Presenter mPresenter;
    private LinearLayoutManager linearLayoutManager;
    private MergeAdapter mergeAdapter;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private ArrayList<CustomerListResponse.Data.tblCustomers> customerList = new ArrayList();
    private Realm realm;
    private ArrayList<CustomerListResponse.Data.tblCustomers> filterArraylist = new ArrayList<>();
    private String customerToStore = "";
    private String customerId = "";
    private MaterialDialog materialDialog;
    String DestinationCustomerId = "";


    @BindView(R.id.edittext_search)
    EditText edittext_search;
    @BindView(R.id.imageviewMic)
    ImageView imageviewMic;
    @BindView(R.id.recyclerviewCustomerList)
    RecyclerView recyclerviewCustomerList;

    @OnClick(R.id.imageviewMic)
    void imageviewMic() {
        speechToTextFunc(this, edittext_search, 1, imageviewMic);
    }

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
        mPresenter = new MergePresenter(this);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    private void getIntentData() {
        customerToStore = getIntent().getStringExtra("StringCustomerId");
        customerId = getIntent().getStringExtra(CUSTOMERID);
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
        mergeAdapter.filterList(filterArraylist);
    }


    private void getCustomerList() {
        if (Utility.getInstance().isOnline(this)) {
            Utility.getInstance().showProgressDialogue(this);

            mPresenter.getCustomerList(Utility.getInstance().getclientRegId(this).toString());
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
            RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
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
            //  setSizeandAddition();
  /*          for (CustomerListResponse.Data.tblCustomers item : customerList){
                if (item.getCustomerId().toString().toLowerCase().trim().matches(customerToStore.trim().toLowerCase())){
                    customerList.remove(item);
                }
            }
*/
            filterArraylist.clear();
            filterArraylist.addAll(customerList);
            mergeAdapter.notifyDataSetChanged();
        }
    }

    private void setlayout() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerviewCustomerList.setLayoutManager(linearLayoutManager);
        mergeAdapter = new MergeAdapter(this, customerList, onRecyclerviewClick, customerToStore);
        recyclerviewCustomerList.setAdapter(mergeAdapter);
    }

    @Override
    public void onReclerViewClick(View view, int position) {
        if (Utility.getInstance().isOnline(this)) {
            RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class)
                    .equalTo("isCustomerOnlineSaved",false)
                    .findAll();


            if (!(customerListRealmModels.size()>0)) {
                if (filterArraylist.get(position).getCustomerId().toString().trim().toLowerCase().matches(customerToStore.trim().toLowerCase())) {
                    Toast.makeText(this, getString(R.string.Please_select_other_customer), Toast.LENGTH_SHORT).show();
                } else {
                    LayoutInflater layoutInflater = LayoutInflater.from(ActivityMerge.this);
                    View view1 = layoutInflater.inflate(R.layout.logout_dialogue, null);
                    TextView yes = view1.findViewById(R.id.button_yes);
                    TextView text_title = view1.findViewById(R.id.text_title);
                    TextView no = view1.findViewById(R.id.button_no);
                    TextView text_logout = view1.findViewById(R.id.text_logout);
                    text_logout.setText(getString(R.string.merge_confirmation));
                    text_title.setText(getString(R.string.merge));
                    yes.setText(getString(R.string.yes));
                    no.setText(getString(R.string.no));
                    materialDialog = new MaterialDialog.Builder(ActivityMerge.this)
                            .customView(view1, false)
                            .show();
                    materialDialog.setCanceledOnTouchOutside(false);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            MergeRequest mergeRequest = new MergeRequest();
                            mergeRequest.setRegId(Utility.getInstance().getclientRegId(ActivityMerge.this));
                            mergeRequest.setDestinationCustomerId(customerId);
                            DestinationCustomerId = filterArraylist.get(position).getID();
                            mergeRequest.setSourceCustomerId(filterArraylist.get(position).getID());
                            Utility.getInstance().showProgressDialogue(ActivityMerge.this);


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
    public void customerListSuccess(CustomerListResponse response) {
        customerList.clear();
        RealmController.with(this).clearCustomerList();
        double total = 0.0;
        customerList.addAll(response.getData().getTblCustomerslist());
        filterArraylist.clear();
        filterArraylist.addAll(customerList);
        ArrayList<CustomerListRealm> customerListRealmArrayList = new ArrayList<>();
        for (int i = 0; i < customerList.size(); i++) {
            CustomerListRealm customerListRealm = new CustomerListRealm();
            customerListRealm.setID(customerList.get(i).getID());
            customerListRealm.setCustomerId(customerList.get(i).getCustomerId());

            customerListRealm.setCustomerOnlineSaved(true);

            customerListRealm.setClientId(customerList.get(i).getClientId());
            customerListRealm.setCFirstname(customerList.get(i).getCFirstname());
            customerListRealm.setStateId(customerList.get(i).getStateId());
            customerListRealm.setCMiddleName(customerList.get(i).getCMiddleName());
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
        filterArraylist.addAll(customerList);
        if (!(customerList.size() <= 0)) {
            recyclerviewCustomerList.setVisibility(View.VISIBLE);
            //textview_Please_add_customer.setVisibility(View.GONE);


            mergeAdapter.notifyDataSetChanged();
            //Ankur
            // textviewCustomerNumber.setText(String.valueOf(response.getData().getTblCustomerslist().size()));
        }

        Utility.getInstance().dismissProgress();

    }


    @Override
    public void customerListFailure(String message) {
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void mergeUserSuccess(MergeApiResponse response) {
        if (response.getStatus().matches("1")) {

        }
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
