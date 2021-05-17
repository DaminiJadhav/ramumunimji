package com.posbilling.posbillingapplication.lib;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.posbilling.posbillingapplication.utility.APIModule;
import com.posbilling.posbillingapplication.utility.AppData;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {APIModule.class})
public interface APIComponent {
    SharedPreferences getPreferences();
    Gson getGson();
    AppData getAppData();
    CustomRetroRequest getRetroService();
    AppPOS getApp();
}
