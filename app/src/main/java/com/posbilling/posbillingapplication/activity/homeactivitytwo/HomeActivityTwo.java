package com.posbilling.posbillingapplication.activity.homeactivitytwo;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.help.FragmentHelp;
import com.posbilling.posbillingapplication.activity.dashboard.ui.setting.FragmentSetting;
import com.posbilling.posbillingapplication.activity.dashboard.ui.voice.FragmentVoice;
import com.posbilling.posbillingapplication.fragment.fragmenthometab.FragmentHomeTab;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.utility.Utility;

import butterknife.BindView;

public class HomeActivityTwo extends BaseActivity {
    private ViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.viewpager)
    CustomViewpager viewpager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.imageviewWhatsaapshare_sales)
    ImageView imageviewWhatsaapshare_sales;
    @BindView(R.id.imageviewWhatsaapshare_expense)
    ImageView imageviewWhatsaapshare_expense;
    @BindView(R.id.imageviewMechanic)
    ImageView imageviewMechanic;
    @BindView(R.id.textview_ramumunimji)
    TextView textview_ramumunimji;
    @BindView(R.id.imageviewRamumunimji)
    ImageView imageviewRamumunimji;
    @BindView(R.id.imageview_logo)
    ImageView imageview_logo;


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_home_two;
    }

    @Override
    protected void setPresenter() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewPager();
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(HomeActivityTwo.this);
        String fistname = sharedPreferences.getString("ShopName", "");
        //    String lastname = sharedPreferences.getString("LastName", "");
        String finalName = fistname; /*+ " " + lastname*/
        setTitleOfScreen(finalName);

        setVisibilityofRamumunimjiVisible(false, 1);
    }

    private void setViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentHomeTab(), "");
        viewPagerAdapter.addFragment(new FragmentHelp(), "");
        viewPagerAdapter.addFragment(new FragmentSetting(), "");
        viewpager.setAdapter(viewPagerAdapter);

/*        View view1 = getLayoutInflater().inflate(R.layout.customtab, null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_hoome);
        tabs.addTab(tabs.newTab().setCustomView(view1));


        View view2 = getLayoutInflater().inflate(R.layout.customtab, null);
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_help);
        tabs.addTab(tabs.newTab().setCustomView(view2));


        View view3 = getLayoutInflater().inflate(R.layout.customtab, null);
        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_profile_tab);
        tabs.addTab(tabs.newTab().setCustomView(view3));*/


        tabs.setupWithViewPager(viewpager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_hoome);
        tabs.getTabAt(1).setIcon(R.drawable.ic_help);
        tabs.getTabAt(2).setIcon(R.drawable.ic_profile_tab);

        viewpager.disableScroll(true);

/*        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });*/


        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager) {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
/*                int tabIconColor = ContextCompat.getColor(HomeActivityTwo.this, R.color.white);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);*/

                if (tab.getPosition() == 0) {
//                    SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(HomeActivityTwo.this);
//                    String fistname = sharedPreferences.getString("FirstName", "");
//                    String lastname = sharedPreferences.getString("LastName", "");
//                    String finalName = fistname + " " + lastname;
                    SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(HomeActivityTwo.this);
                    String fistname = sharedPreferences.getString("ShopName", "");
                    String finalName = fistname;
                    Log.i("HomeActivityTwo", "" + finalName);
                    setTitleOfScreen(finalName);
                    setVisibilityofRamumunimjiVisible(false, 1);
                } else if (tab.getPosition() == 1) {
                    setTitleOfScreen(getString(R.string.menu_help));
                    setVisibilityofRamumunimjiVisible(false, 1);
                } else if (tab.getPosition() == 2) {
                    setTitleOfScreen(getString(R.string.menu_setting));
                    setVisibilityofRamumunimjiVisible(false, 1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
 /*               int tabIconColor = ContextCompat.getColor(HomeActivityTwo.this, R.color.tab_icon_color);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);*/
            }
        });


        int tabIconColor = ContextCompat.getColor(HomeActivityTwo.this, R.color.white);
        tabs.getTabAt(0).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

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

    public void setTab(int tabNumber) {
        if (tabNumber != 5) {
            TabLayout.Tab tab = tabs.getTabAt(tabNumber); // Count Starts From 0
            tab.select();
        }
    }

    public void setVisibilityofRamumunimjiVisible(boolean value, int flowValue) {
        if (value) {
            if (flowValue == 2) {
                imageviewMechanic.setVisibility(View.GONE);
                imageviewWhatsaapshare_sales.setVisibility(View.GONE);
                imageviewWhatsaapshare_expense.setVisibility(View.VISIBLE);
            } else if (flowValue == 3) {
                imageviewMechanic.setVisibility(View.GONE);
                imageviewWhatsaapshare_expense.setVisibility(View.GONE);
                imageviewWhatsaapshare_sales.setVisibility(View.VISIBLE);
            } else if (flowValue == 4) {
                imageviewMechanic.setVisibility(View.VISIBLE);
                imageviewWhatsaapshare_expense.setVisibility(View.GONE);
                imageviewWhatsaapshare_sales.setVisibility(View.GONE);
            } else {
                imageviewMechanic.setVisibility(View.GONE);
                imageviewWhatsaapshare_expense.setVisibility(View.GONE);
                imageviewWhatsaapshare_sales.setVisibility(View.GONE);
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


    @Override
    public void onBackPressed() {
        if (viewpager.getCurrentItem() != 0) {
            viewpager.setCurrentItem(0, false);
        } else {
            finish();
        }
    }


    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }*/
}
