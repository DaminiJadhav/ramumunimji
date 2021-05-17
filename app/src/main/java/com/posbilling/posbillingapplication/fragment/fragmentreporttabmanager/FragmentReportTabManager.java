package com.posbilling.posbillingapplication.fragment.fragmentreporttabmanager;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.crop.FragmentCropSummury;
import com.posbilling.posbillingapplication.activity.dashboard.ui.expenesreport.FragmentExpenseReport;
import com.posbilling.posbillingapplication.activity.dashboard.ui.garage.FragmentGarage;
import com.posbilling.posbillingapplication.activity.dashboard.ui.outstanding.FragmentOutstanding;
import com.posbilling.posbillingapplication.activity.dashboard.ui.salesreport.FragmentSaleReports;
import com.posbilling.posbillingapplication.fragment.fragmentreporttab.FragmentReportTab;
import com.posbilling.posbillingapplication.fragment.fragmentsupplierreport.FragmentSupplierReport;
import com.posbilling.posbillingapplication.interfaceclick.OnFragmentTabListener;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.utility.Constants;

import butterknife.BindView;

public class FragmentReportTabManager extends BaseFragment implements OnFragmentTabListener {

    private FragmentManager fragmentManager;
    private Context mContext;
    private OnFragmentTabListener onFragmentTabListener = this;

    @BindView(R.id.relative_container)
    RelativeLayout relative_container;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_report_tab_manager;
    }

    @Override
    public void onMakeServerCalls() {
        fragmentManager = getActivity().getSupportFragmentManager();
      //  startFragment(new FragmentReportTab(onFragmentTabListener));
    }

    public void startFragment(Fragment fragment) {
        fragmentManager.beginTransaction().add(R.id.relative_container, fragment).commit();
    }

    @Override
    public void OnFragmentTabListener(int fragNum) {
        setFragment(fragNum);
    }


    private void setFragment(int fragmentNumber) {
        switch (fragmentNumber) {
            case 1:
                FragmentOutstanding fragmentOutstanding = new FragmentOutstanding();
                startFragment(fragmentOutstanding);
                break;

            case 2:
                FragmentSaleReports fragmentSaleReports = new FragmentSaleReports();
                startFragment(fragmentSaleReports);
                break;

            case 3:
                FragmentSupplierReport fragmentSupplierReport = new FragmentSupplierReport();
                startFragment(fragmentSupplierReport);
                break;

            case 4:
                FragmentExpenseReport fragmentExpenseReport = new FragmentExpenseReport();
                startFragment(fragmentExpenseReport);
                break;

            case 5:
                FragmentGarage fragmentGarage = new FragmentGarage();
                startFragment(fragmentGarage);
                break;

            case 6:
                FragmentCropSummury fragmentCropSummury = new FragmentCropSummury();
                startFragment(fragmentCropSummury);
                break;

            default:
                Log.e(Constants.LOGPOS, "setFragment: Default in ActivityParentReport");
        }
    }


    

}
