package com.posbilling.posbillingapplication.utility;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.posbilling.posbillingapplication.lib.AppPOS;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class APIModule {

    private AppPOS context;

    public APIModule(AppPOS context) {
        this.context = context;
    }



    @Provides
    @Singleton
    CustomRetroRequest getAPIServices(){
        return CustomRetroRequest.getInstance();
    }


    @Provides
    @Singleton
    SharedPreferences providePreferences(){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    Gson getMeGson(){
        return new Gson();
    }

    @Provides
    @Singleton
    AppData getAppData(){
        return new AppData(context);
    }

    @Provides
    @Singleton
    AppPOS getApp() {
        return context;
    }

}