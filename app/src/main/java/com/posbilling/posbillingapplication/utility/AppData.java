package com.posbilling.posbillingapplication.utility;

import android.content.Context;
import android.content.SharedPreferences;

import static android.support.v4.media.session.MediaSessionCompat.KEY_TOKEN;

public class AppData {
    private static final String KEY_FCM_TOKEN = "KEY_FCM_TOKEN";
    private static final String KEYLANGUAGE = "en";
    private static final String KEY_TOKEN = "token";
    private static final String KEYUSER = "user";
    private final String KEYEMAIL = "email";
    private final String KEYPASS = "password";
    private final String KEYREMEMBER = "remember";
    private final String KEYSPECIALIZATION="specialization";
    private static final String DEFAULTPREFERENCE = "default";
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPreferenceEditor;
    private static final String TAG = AppData.class.getSimpleName();
    public AppData(Context context) {
        loginPreferences = context.getSharedPreferences(DEFAULTPREFERENCE, Context.MODE_PRIVATE);
        loginPreferenceEditor = loginPreferences.edit();
        loginPreferenceEditor.commit();
    }

    //token is not setted yet. kindly check thi ankur
    public String getToken(){
        return loginPreferences.getString(KEY_TOKEN,null);
    }

}
