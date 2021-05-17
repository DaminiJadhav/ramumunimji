package com.posbilling.posbillingapplication.activity.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.activity.homeactivitytwo.HomeActivityTwo;
import com.posbilling.posbillingapplication.activity.login.ActivityLogin;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.ActviStatusResponse;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.UserProfileDetails;
import com.posbilling.posbillingapplication.utility.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.posbilling.posbillingapplication.utility.Constants.CLIENTREGID;
import static com.posbilling.posbillingapplication.utility.Constants.LANGUAGEAVAILABILITYFIRSTTIME;
import static com.posbilling.posbillingapplication.utility.Constants.LOGPOS;
import static com.posbilling.posbillingapplication.utility.Constants.TTSDIALOGUECOUNT;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;

public class ActivitySplash extends BaseActivity implements SplashContractor.View {

    private final int SPLASH_SCREEN_TIMEOUT = 2000;
    private String languageCode = "";
    private SplashContractor.Presenter mPresenter;


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new SplashPresenter(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        settingLanguageLocale();
        checkActivStatus();
        Realm realm = RealmController.with(ActivitySplash.this).getRealm();
        UserProfileDetails results = realm.where(UserProfileDetails.class).findFirst();
        String clientId = "";
        if (results != null && results.getClientId() != null) {
            clientId = results.getClientId().toString();
        }
        String finalClientId = clientId;
        if (!Utility.getInstance().isOnline(ActivitySplash.this)) {
            splashscreenDelayRun();
        }

        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LANGUAGEAVAILABILITYFIRSTTIME, false);
        editor.putInt(TTSDIALOGUECOUNT, 1);
        editor.commit();

    }

    private void checkActivStatus() {
        if (!TextUtils.isEmpty(Utility.getInstance().getclientRegId(ActivitySplash.this).toString().trim())) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            Date date = null;
            Date time = null;
            try {
                //  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                //   date = formatter.parse(formatter.format(new Date()));
                date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(currentDate + " " + currentTime);
                // time = new SimpleDateFormat("HH:mm").parse(currentDate + " " + currentTime);

                DateFormat sdf = new SimpleDateFormat("hh:mm");
                time = sdf.parse(currentTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            mPresenter.getActiveStatus(Utility.getInstance().getclientRegId(ActivitySplash.this).toString(), currentDate, currentTime);
        } else {
            splashscreenDelayRun();
        }
    }

    //Checking and setting language locale
    private void settingLanguageLocale() {
        languageCode = Utility.getInstance().getLanguage(ActivitySplash.this);
        if (languageCode.matches("") || languageCode.matches(langaugeCodeEnglish)) {
            Utility.getInstance().localisation(ActivitySplash.this, langaugeCodeEnglish);
            Utility.getInstance().setLanguage(ActivitySplash.this, langaugeCodeEnglish);
        } else if (languageCode.matches(languageCodeMarathi)) {
            Utility.getInstance().localisation(ActivitySplash.this, languageCodeMarathi);
            Utility.getInstance().setLanguage(ActivitySplash.this, languageCodeMarathi);
        } else {
            Log.e(LOGPOS, "settingLanguageLocale: Something went wrong in language setting locale");
        }
    }

    //Splash screen delay method
    private void splashscreenDelayRun() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String id = Utility.getInstance().getclientRegId(ActivitySplash.this).toString().trim();
                SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivitySplash.this);
                String name = sharedPreferences.getString("FirstName", "");

                if (!TextUtils.isEmpty(Utility.getInstance().getclientRegId(ActivitySplash.this).toString().trim())) {
                    //    if (!TextUtils.isEmpty(finalClientId.trim())) {
                    Intent intent = new Intent(ActivitySplash.this, HomeActivityTwo.class);
                    startActivity(intent);
                    ActivitySplash.this.finish();
                } else {
                    Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                    startActivity(intent);
                    ActivitySplash.this.finish();
                }
            }
        }, SPLASH_SCREEN_TIMEOUT);
    }

    @Override
    public void getActviStatusFailure(String s) {
        splashscreenDelayRun();
    }

    @Override
    public void getActviStatusSuccess(ActviStatusResponse body) {
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ImagePath", body.getActiveArrayList().get(0).getImagePath());
        editor.putString("activeDays", body.getActiveArrayList().get(0).getActiveDay());
        editor.putString("activStatus", body.getActiveArrayList().get(0).getIsActive());
        editor.putString("FirstName", body.getActiveArrayList().get(0).getFirstname().toString());
        editor.putString("LastName", body.getActiveArrayList().get(0).getLastname());
        editor.putString("BusinessTypeName", body.getBusinessTypeName().toString());
        //     editor.putString("Address", body.getActiveArrayList().get(0).getAddress());
        editor.putString("ContactNumber", body.getActiveArrayList().get(0).getContactNo());
        editor.putString("ShopName", body.getActiveArrayList().get(0).getBusinessName());
        //    editor.putString("Village", body.getActiveArrayList().get(0).getVillage());
        //    editor.putString("Taluka", body.getActiveArrayList().get(0).getTaluka());
        //    editor.putString("District", body.getActiveArrayList().get(0).getDistrict());
        editor.putString("BusinessId", body.getActiveArrayList().get(0).getBusinessId());
        editor.putString("versionName", body.getVersionName());
        editor.putString("versionCode", body.getVersion());
        //    editor.putString("versionCode","5");


        String currentDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        Date date = null;
        Date time = null;
        try {
            //  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            //   date = formatter.parse(formatter.format(new Date()));
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(currentDate + " " + currentTime);
            // time = new SimpleDateFormat("HH:mm").parse(currentDate + " " + currentTime);

            DateFormat sdf = new SimpleDateFormat("hh:mm");
            time = sdf.parse(currentTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        editor.putString("internetUseDate", currentDate);
        editor.commit();

        if (!TextUtils.isEmpty(Utility.getInstance().getclientRegId(ActivitySplash.this).toString().trim())) {
            //    if (!TextUtils.isEmpty(finalClientId.trim())) {
            Intent intent = new Intent(ActivitySplash.this, HomeActivityTwo.class);
            startActivity(intent);
            ActivitySplash.this.finish();
        } else {
            Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
            startActivity(intent);
            ActivitySplash.this.finish();
        }
        //splashscreenDelayRun();
    }
}
