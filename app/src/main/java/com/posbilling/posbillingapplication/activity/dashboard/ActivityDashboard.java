package com.posbilling.posbillingapplication.activity.dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.expenesreport.FragmentExpenseReport;
import com.posbilling.posbillingapplication.activity.dashboard.ui.garage.FragmentGarage;
import com.posbilling.posbillingapplication.activity.dashboard.ui.salesreport.FragmentSaleReports;
import com.posbilling.posbillingapplication.activity.login.ActivityLogin;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.utility.Utility;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;

public class ActivityDashboard extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private NavController navControllerChange;
    private Realm realm;
    private NavigationView navigationView;
    private ImageView imageView;
    private TextView texview_shop_name;
    private TextView textview_mobileNumber;
    private FragmentSaleReports fragmentSaleReports;
    private FragmentExpenseReport fragmentExpenseReport;
    private FragmentGarage fragmentGarage;

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



    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_dashboard;
    }


    @OnClick(R.id.imageviewWhatsaapshare_sales)
    void imageviewWhatsaapshare() {
        if (fragmentSaleReports.isVisible()) {
            fragmentSaleReports.methodFragment();
        }
    }

    @OnClick(R.id.imageviewWhatsaapshare_expense)
    void imageviewWhatsaapshare_expense(){
        if(fragmentExpenseReport.isVisible()){
            fragmentExpenseReport.methodFragment();
        }
    }

    @OnClick(R.id.imageviewMechanic)
    void imageviewMechanic(){
        if (fragmentGarage.isVisible()){
            fragmentGarage.methodFragment();
        }
    }


    @Override
    protected void setPresenter() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        realm = RealmController.with(this).getRealm();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        getPrefercenceData();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_outStanding, /*R.id.nav_payment_received, R.id.nav_sale,*/ R.id.nav_logout, R.id.nav_crop,
                /*R.id.nav_expenses,*/ R.id.nav_reports, R.id.nav_other, R.id.nav_setting, R.id.nav_add_customer, R.id.nav_help, R.id.nav_garage,
                R.id.nav_expensesReports, R.id.nav_salesReports)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navControllerChange = navController;

        Menu m = navigationView.getMenu();
        MenuItem logout = m.findItem(R.id.nav_logout);
        logout.setVisible(false);

        MenuItem addcustomer = m.findItem(R.id.nav_add_customer);
        MenuItem garage = m.findItem(R.id.nav_garage);
        MenuItem reports = m.findItem(R.id.nav_reports);
        MenuItem others = m.findItem(R.id.nav_other);



        addcustomer.setVisible(false);
        //   garage.setVisible(false);
        reports.setVisible(false);
        others.setVisible(false);

        MenuItem crop = m.findItem(R.id.nav_crop);
        garage.setVisible(false);
        crop.setVisible(false);

        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivityDashboard.this);
        if (sharedPreferences.getString("BusinessId", "").matches("3")) {
            ///crop type
            crop.setVisible(true);
        } else if (sharedPreferences.getString("BusinessId", "").matches("2")) {
            ///garage type
            garage.setVisible(true);
        } else {
            garage.setVisible(false);
            crop.setVisible(false);
        }


        logout.setOnMenuItemClickListener(item -> {
            drawer.closeDrawer(GravityCompat.START);
            LayoutInflater layoutInflater = LayoutInflater.from(ActivityDashboard.this);
            View view1 = layoutInflater.inflate(R.layout.logout_dialogue, null);
            TextView yes = view1.findViewById(R.id.button_yes);
            TextView no = view1.findViewById(R.id.button_no);
            MaterialDialog materialDialog = new MaterialDialog.Builder(ActivityDashboard.this)
                    .customView(view1, false)
                    .show();
            materialDialog.setCanceledOnTouchOutside(false);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialDialog.dismiss();
                    // mPresenter.getLogout(userId, privateKey, mContext);
                    realm.beginTransaction();
                    // delete all realm objects
                    realm.deleteAll();
                    //commit realm changes
                    realm.commitTransaction();
                    Utility.getInstance().removeClientID(ActivityDashboard.this);
                    startActivity(new Intent(ActivityDashboard.this, ActivityLogin.class));
                    finishAffinity();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialDialog.dismiss();
                }
            });
            return true;
        });
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navMenuView.addItemDecoration(new DividerItemDecoration(ActivityDashboard.this, DividerItemDecoration.VERTICAL));
    }

    public void updateProfile(String imagePath, String businessname) {
        Glide.with(ActivityDashboard.this)
                .load(new CustomRetroRequest().imageURL + imagePath)
                .into(imageView);

        texview_shop_name.setText(businessname);
    }


    public void castFragment(Fragment fragment, int fragKey) {
        if (fragKey == 1) {
            fragmentSaleReports = (FragmentSaleReports) fragment;
        }else if(fragKey == 2){
            fragmentExpenseReport = (FragmentExpenseReport) fragment;
        }else if(fragKey == 4){
            fragmentGarage = (FragmentGarage) fragment;
        }
    }

    private void getPrefercenceData() {
        View v = navigationView.getHeaderView(0);
        imageView = v.findViewById(R.id.imageView);
        texview_shop_name = v.findViewById(R.id.texview_shop_name);
        textview_mobileNumber = v.findViewById(R.id.textview_mobileNumber);
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivityDashboard.this);
        if (!TextUtils.isEmpty(sharedPreferences.getString("ImagePath", ""))) {
            Glide.with(ActivityDashboard.this)
                    .load(new CustomRetroRequest().imageURL + sharedPreferences.getString("ImagePath", ""))
                    .into(imageView);
        }

        if (!TextUtils.isEmpty(sharedPreferences.getString("ShopName", "-"))) {
            texview_shop_name.setText(sharedPreferences.getString("ShopName", "-"));
        }

        if (!TextUtils.isEmpty(sharedPreferences.getString("ContactNumber", "-"))) {
            textview_mobileNumber.setText(sharedPreferences.getString("ContactNumber", "-"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_dashboard, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
            } else if (flowValue == 3) {
                imageviewMechanic.setVisibility(View.GONE);
                imageviewWhatsaapshare_expense.setVisibility(View.GONE);
                imageviewWhatsaapshare_sales.setVisibility(View.VISIBLE);
            }else if (flowValue ==4){
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



    /*public void onNavigationChange(){
     *//*NavHostFragment host = NavHostFragment.create(R.navigation.mobile_navigation);
        fragmentTransaction.replace(R.id.nav_host_fragment,host).setPrimaryNavigationFragment(host).commit();*//*
        navControllerChange.navigate(R.id.nav_outStanding);
    }*/

    public void closeApplication() {
        finishAffinity();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}