package com.posbilling.posbillingapplication.activity.outstandingreport;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.outstanding.OutstandingContractor;
import com.posbilling.posbillingapplication.activity.dashboard.ui.outstanding.OutstandingSummuryAdapter;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;

import java.util.ArrayList;

import butterknife.BindView;
import io.realm.Realm;

public class ActivityOutstandingReport extends BaseActivity implements OnRecyclerviewClick, OutstandingContractor.View{
    private Realm realm;
    private ArrayList<String> customerArraylist = new ArrayList<>();
    private OutstandingSummuryAdapter adapterOutstandingSummury;
    private Context mContext;
    private LinearLayoutManager linearLayoutManager;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private ArrayList<CustomerListResponse.Data.tblCustomers> filterArraylist = new ArrayList<>();
    private OutstandingContractor.Presenter mPresenter;
    private ArrayList<CustomerListResponse.Data.tblCustomers> customerList = new ArrayList();

    @BindView(R.id.recyclerviewOutstandingSummury)
    RecyclerView recyclerviewOutstandingSummury;
    @BindView(R.id.edittext_search)
    EditText edittext_search;
    @BindView(R.id.textviewCustomerNumber)
    TextView textviewCustomerNumber;
    @BindView(R.id.textviewTotal)
    TextView textviewTotal;
    @BindView(R.id.imageviewMic)
    ImageView imageviewMic;
    @BindView(R.id.textview_Please_add_customer)
    TextView textview_Please_add_customer;



    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_outstanding_report;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm();
        setRecyclerView();
/*        setSearchFilter();
        getCustomerData();*/
    }


    //setting recyclerview
    private void setRecyclerView() {
        customerList.clear();
        linearLayoutManager = new LinearLayoutManager(mContext);
        filterArraylist.addAll(customerList);
        adapterOutstandingSummury = new OutstandingSummuryAdapter(mContext, customerList, onRecyclerviewClick);
        recyclerviewOutstandingSummury.setLayoutManager(linearLayoutManager);
        recyclerviewOutstandingSummury.setAdapter(adapterOutstandingSummury);
    }

    @Override
    public void customerListSuccess(CustomerListResponse body) {

    }

    @Override
    public void customerListFailure(String message) {

    }

    @Override
    public void onReclerViewClick(View view, int position) {

    }
}
