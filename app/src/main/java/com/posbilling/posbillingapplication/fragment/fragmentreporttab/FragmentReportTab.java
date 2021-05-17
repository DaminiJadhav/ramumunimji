package com.posbilling.posbillingapplication.fragment.fragmentreporttab;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.activityparentreport.ActivityParentReport;
import com.posbilling.posbillingapplication.activity.homeactivitytwo.HomeActivityTwo;
import com.posbilling.posbillingapplication.interfaceclick.OnFragmentTabListener;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;

import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class FragmentReportTab extends BaseFragment {
    private Context mContext;
    private FragmentManager fragmentManager;
    private OnFragmentTabListener onFragmentTabListener;

/*    public FragmentReportTab(OnFragmentTabListener onFragmentTabListener) {
        this.onFragmentTabListener = onFragmentTabListener;
    }*/

    @OnClick(R.id.ll_outstandingreport)
    void ll_outstandingreport() {
//        onFragmentTabListener.OnFragmentTabListener(1);
        startActivityfunction(1);
    }

    @OnClick(R.id.ll_saleReport)
    void ll_saleReport() {
       // onFragmentTabListener.OnFragmentTabListener(2);
        startActivityfunction(2);
    }

    @OnClick(R.id.ll_purchasereport)
    void ll_purchasereport() {
//        onFragmentTabListener.OnFragmentTabListener(3);
        startActivityfunction(3);
    }

    @OnClick(R.id.ll_expensereport)
    void ll_expensereport() {
//        onFragmentTabListener.OnFragmentTabListener(4);
        startActivityfunction(4);
    }

/*    @OnClick(R.id.ll_vehicle)
    void ll_vehicle() {
        startActivityfunction(5);
    }

    @OnClick(R.id.ll_Crop)
    void ll_Crop() {
        startActivityfunction(6);
    }*/


    void startActivityfunction(int value) {
        Intent intent = new Intent(mContext, ActivityParentReport.class);
        intent.putExtra("FragmentValue", value);
        startActivityForResult(intent,303);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 303){
            ((HomeActivityTwo)getContext()).setTab(data.getIntExtra("TabNumber",5));
        }
    }




    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void setPresenter() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_report_tab;
    }

    @Override
    public void onMakeServerCalls() {

    }

}