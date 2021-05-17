package com.posbilling.posbillingapplication.lib;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.utility.APIModule;
import com.posbilling.posbillingapplication.utility.Utility;

import java.util.Timer;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AppPOS extends Application /*implements Application.ActivityLifecycleCallbacks*/ {

    private static final String TAG = AppPOS.class.getSimpleName();
    private static APIComponent apiComponent;
    Intent locationServiceIntent;
    Timer timer;
    private static Context context;
    Double latitude = 0.0;
    Double longitude = 0.0;
    private String languageCode = "";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //registerActivityLifecycleCallbacks(this);

        AppPOS.context = getApplicationContext();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        initialize();
        
        setlocale();
    }

    private void setlocale() {
        languageCode = Utility.getInstance().getLanguage(this);
        if(!TextUtils.isEmpty(languageCode)) {
            Utility.getInstance().localisation(this, languageCode);
            Utility.getInstance().setLanguage(this, languageCode);
        }else {

        }
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        languageCode = Utility.getInstance().getLanguage(this);
        Utility.getInstance().localisation(this, languageCode);
        Utility.getInstance().setLanguage(this, languageCode);
    }

    public APIComponent getAPIComponent() {
        return apiComponent;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }



    private void initialize() {
        apiComponent = DaggerAPIComponent.builder().aPIModule(new APIModule(this)).build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channel_id = getString(R.string.app_name);
            String channel_name = getString(R.string.app_name);
            String channel_desc = getString(R.string.app_name);
            int noti_importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channel_id, channel_name, noti_importance);
            mChannel.setDescription(channel_desc);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    /*public static String getToken() {
        String token;
        if (apiComponent != null && apiComponent.getAppData().getToken() != null)
            token = apiComponent.getAppData().getToken();
        else
            token = "";
        Log.i(TAG, token + " Returning Token");
        return token;
    }*/

    private BaseActivity mCurrentActivity;

    public void setCurrentActivity(BaseActivity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    public BaseActivity getCurrentForegroundActivity() {
        return mCurrentActivity;
    }


/*
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }
    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }*/

}

