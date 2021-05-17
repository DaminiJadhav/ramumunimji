package com.posbilling.posbillingapplication.fragment.fragmenthometab;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.voice.FragmentVoice;
import com.posbilling.posbillingapplication.activity.homeactivitytwo.HomeActivityTwo;
import com.posbilling.posbillingapplication.fragment.fragmentreporttab.FragmentReportTab;
import com.posbilling.posbillingapplication.fragment.fragmentreporttabmanager.FragmentReportTabManager;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;


import butterknife.BindView;
import butterknife.OnClick;

public class FragmentHomeTab extends BaseFragment {
    private CustomViewpagerAdapter viewPagerAdapter;

    @BindView(R.id.viewpager_sub)
    ViewPager viewpager_sub;
    @BindView(R.id.cardview_menu)
    CardView cardview_menu;
    @BindView(R.id.cardview_report)
    CardView cardview_report;
    @BindView(R.id.textview_reports)
    TextView textview_reports;
    @BindView(R.id.textview_menu)
    TextView textview_menu;

    @OnClick(R.id.textview_menu)
    void textview_menu(){
        viewpager_sub.setCurrentItem(0);
    }

    @OnClick(R.id.textview_reports)
    void textview_reports(){
        viewpager_sub.setCurrentItem(1);
    }


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home_tab;
    }

    @Override
    public void onMakeServerCalls() {
        setViewPager();
    }

    @Override
    public void onStart() {
        super.onStart();
      //  ((HomeActivityTwo) getContext()).setTitleOfScreen(getString(R.string.menu_setting));
        ((HomeActivityTwo) getContext()).setVisibilityofRamumunimjiVisible(false,1);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void setViewPager() {
        viewPagerAdapter = new CustomViewpagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new FragmentVoice(), "Menu");
        viewPagerAdapter.addFragment(new FragmentReportTab(), "Report");
        viewpager_sub.setAdapter(viewPagerAdapter);

        viewpager_sub.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    cardview_menu.setCardBackgroundColor(getResources().getColor(R.color.mediumBlue));
                    cardview_report.setCardBackgroundColor(getResources().getColor(R.color.white));
                    textview_menu.setTextColor(getResources().getColor(R.color.white));
                    textview_reports.setTextColor(getResources().getColor(R.color.light_grey));
                } else if (position == 1) {
                    cardview_menu.setCardBackgroundColor(getResources().getColor(R.color.white));
                    cardview_report.setCardBackgroundColor(getResources().getColor(R.color.mediumBlue));
                    textview_menu.setTextColor(getResources().getColor(R.color.light_grey));
                    textview_reports.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



}
