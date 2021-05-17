package com.posbilling.posbillingapplication.activity.activityparentreport;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.crop.FragmentCropSummury;
import com.posbilling.posbillingapplication.activity.dashboard.ui.expenesreport.FragmentExpenseReport;
import com.posbilling.posbillingapplication.activity.dashboard.ui.garage.FragmentGarage;
import com.posbilling.posbillingapplication.activity.dashboard.ui.outstanding.FragmentOutstanding;
import com.posbilling.posbillingapplication.activity.dashboard.ui.salesreport.FragmentSaleReports;
import com.posbilling.posbillingapplication.activity.homeactivitytwo.HomeActivityTwo;
import com.posbilling.posbillingapplication.fragment.fragmentsupplierreport.FragmentSupplierReport;
import com.posbilling.posbillingapplication.interfaceclick.BackPressedListener;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.utility.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivityParentReport extends BaseActivity implements BackPressedListener {

    private BackPressedListener backPressedListener = this;
    private FragmentSaleReports fragmentSaleReports;
    private FragmentExpenseReport fragmentExpenseReport;
    private FragmentGarage fragmentGarage;
    private FragmentOutstanding fragmentOutstanding;
    private FragmentSupplierReport fragmentSupplierReport;
    private FragmentCropSummury fragmentCropSummury;


    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.textview_ramumunimji)
    TextView textview_ramumunimji;
    @BindView(R.id.imageviewRamumunimji)
    ImageView imageviewRamumunimji;
    @BindView(R.id.imageviewWhatsaapshare_sales)
    ImageView imageviewWhatsaapshare_sales;
    @BindView(R.id.imageviewWhatsaapshare_expense)
    ImageView imageviewWhatsaapshare_expense;
    @BindView(R.id.imageviewMechanic)
    ImageView imageviewMechanic;
    @BindView(R.id.imageviewFilter)
    ImageView imageviewFilter;

    @OnClick(R.id.imageviewWhatsaapshare_sales)
    void imageviewWhatsaapshare() {
        if (fragmentSaleReports.isVisible()) {
            fragmentSaleReports.methodFragment();
        }
    }

    @OnClick(R.id.imageviewWhatsaapshare_expense)
    void imageviewWhatsaapshare_expense() {
        if (fragmentExpenseReport.isVisible()) {
            fragmentExpenseReport.methodFragment();
        }
    }

    @OnClick(R.id.imageviewMechanic)
    void imageviewMechanic() {
        if (fragmentGarage.isVisible()) {
            fragmentGarage.methodFragment();
        }
    }

    @OnClick(R.id.imageviewFilter)
    void imageviewFilter() {
        if (fragmentOutstanding != null && fragmentOutstanding.isVisible()) {
            fragmentOutstanding.methodSetFilter();
        } else if (fragmentSupplierReport != null && fragmentSupplierReport.isVisible()) {
            fragmentSupplierReport.methodSetFilter();
        } else if (fragmentExpenseReport != null && fragmentExpenseReport.isVisible()) {
            fragmentExpenseReport.methodSetFilter();
        } else if (fragmentSaleReports != null && fragmentSaleReports.isVisible()) {
//            fragmentSaleReports.methodSetFilter();
        }
    }


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_parent_report;
    }

    @Override
    protected void setPresenter() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        setTab();

    }

    private void setTab() {

        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_hoome));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_help));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_profile_tab));

        int tabIconColor = ContextCompat.getColor(ActivityParentReport.this, R.color.white);
        tabs.getTabAt(0).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    closeActivity(0);
                } else if (tab.getPosition() == 1) {
                    closeActivity(1);
                } else if (tab.getPosition() == 2) {
                    closeActivity(2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    closeActivity(0);
                } else if (tab.getPosition() == 1) {
                    closeActivity(1);
                } else if (tab.getPosition() == 2) {
                    closeActivity(2);
                }
            }
        });

/*        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabs.getSelectedTabPosition() == 0){
                    closeActivity(0);
                }else if(tabs.getSelectedTabPosition() == 1){
                    closeActivity(1);
                }else if(tabs.getSelectedTabPosition() == 2){
                    closeActivity(2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
    }

    private void closeActivity(int tabnumber) {
        Intent intent = new Intent();
        intent.putExtra("TabNumber", tabnumber);
        setResult(RESULT_OK, intent);
        finish();
    }


    private void getIntentData() {
        int fragmentNumber = getIntent().getIntExtra("FragmentValue", 0);
        setFragment(fragmentNumber);
    }

    private void setFragment(int fragmentNumber) {
        switch (fragmentNumber) {
            case 1:
                fragmentOutstanding = new FragmentOutstanding();
                startFragment(fragmentOutstanding);
                break;

            case 2:
                fragmentSaleReports = new FragmentSaleReports();
                startFragment(fragmentSaleReports);
                break;

            case 3:
                fragmentSupplierReport = new FragmentSupplierReport();
                startFragment(fragmentSupplierReport);
                break;

            case 4:
                fragmentExpenseReport = new FragmentExpenseReport();
                startFragment(fragmentExpenseReport);
                break;

            case 5:
                fragmentGarage = new FragmentGarage();
                startFragment(fragmentGarage);
                break;

            case 6:
                fragmentCropSummury = new FragmentCropSummury();
                startFragment(fragmentCropSummury);
                break;

            default:
                Log.e(Constants.LOGPOS, "setFragment: Default in ActivityParentReport");
        }
    }

    private void startFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_container, fragment).commit();
    }

    @Override
    public void onBackPressedListener() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void castFragment(Fragment fragment, int fragKey) {
        if (fragKey == 1) {
            fragmentSaleReports = (FragmentSaleReports) fragment;
        } else if (fragKey == 2) {
            fragmentExpenseReport = (FragmentExpenseReport) fragment;
        } else if (fragKey == 4) {
            fragmentGarage = (FragmentGarage) fragment;
        }
    }


    public void setTitleOfScreen(String title) {
        if (title != null && !title.matches("")) {
            if (toolbar_title != null) {
                toolbar_title.setText(title);
            } else {
                //Toast.makeText(this, "Binding fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setVisibilityofRamumunimjiVisible(boolean value, int flowValue) {
        if (value) {
            if (flowValue == 2) {
                imageviewMechanic.setVisibility(View.GONE);
                imageviewWhatsaapshare_sales.setVisibility(View.GONE);
                imageviewWhatsaapshare_expense.setVisibility(View.VISIBLE);
                imageviewFilter.setVisibility(View.VISIBLE);
            } else if (flowValue == 3) {
                imageviewMechanic.setVisibility(View.GONE);
                imageviewWhatsaapshare_expense.setVisibility(View.GONE);
                imageviewWhatsaapshare_sales.setVisibility(View.VISIBLE);
                imageviewFilter.setVisibility(View.GONE);
            } else if (flowValue == 4) {
                imageviewMechanic.setVisibility(View.VISIBLE);
                imageviewWhatsaapshare_expense.setVisibility(View.GONE);
                imageviewWhatsaapshare_sales.setVisibility(View.GONE);
                imageviewFilter.setVisibility(View.VISIBLE);

            } else {
                imageviewMechanic.setVisibility(View.GONE);
                imageviewWhatsaapshare_expense.setVisibility(View.GONE);
                imageviewWhatsaapshare_sales.setVisibility(View.GONE);
                imageviewFilter.setVisibility(View.VISIBLE);

            }


            if (textview_ramumunimji != null) {
                textview_ramumunimji.setVisibility(View.GONE);
                imageviewRamumunimji.setVisibility(View.GONE);
            } else {
                //Toast.makeText(this, "Binding fail", Toast.LENGTH_SHORT).show();
            }
        } else {
            imageviewMechanic.setVisibility(View.GONE);
            imageviewWhatsaapshare_expense.setVisibility(View.GONE);
            imageviewWhatsaapshare_sales.setVisibility(View.GONE);

            if (textview_ramumunimji != null) {
                textview_ramumunimji.setVisibility(View.GONE);
                imageviewRamumunimji.setVisibility(View.GONE);
            } else {
                //Toast.makeText(this, "Binding fail", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
