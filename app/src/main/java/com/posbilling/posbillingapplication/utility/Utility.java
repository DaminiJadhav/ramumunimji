package com.posbilling.posbillingapplication.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.contactus.ActivityContactUs;
import com.posbilling.posbillingapplication.model.response.LoginResponse;
import com.posbilling.posbillingapplication.model.response.OTPSuccessResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.posbilling.posbillingapplication.utility.Constants.CLIENTREGID;
import static com.posbilling.posbillingapplication.utility.Constants.LANGUAGEAVAILABILITYFIRSTTIME;
import static com.posbilling.posbillingapplication.utility.Constants.LANGUAGECODE;
import static com.posbilling.posbillingapplication.utility.Constants.LOGPOS;
import static com.posbilling.posbillingapplication.utility.Constants.POSBILLINGPREFERENCE;
import static com.posbilling.posbillingapplication.utility.Constants.TTSDIALOGUECOUNT;

public class Utility {

    private static Utility utility = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pd;

    public static Utility getInstance() {
        return utility == null ? utility = new Utility() : utility;
    }


    private String generateCommanDeviceId(Context mContext) {
        return Settings.Secure.getString(mContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    public boolean isContactValid(String contact) {
        if (contact.matches("\\d{10}"))
            return true;
        else
            return false;
    }


    public void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    /**
     * Generate unique ID for notifications.
     *
     * @return Unique ID in mmssSS format
     */


    public int generateUniqueId() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("mmssSS", Locale.US);
        return Integer.parseInt(sdf.format(c.getTime()));
    }

    //setting languageLocale
    public void setLanguage(Context mContext, String languageCode) {
        sharedPreferences = mContext.getSharedPreferences(POSBILLINGPREFERENCE, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            editor = sharedPreferences.edit();
            editor.putString(LANGUAGECODE, languageCode);
            editor.commit();
        } else {

        }
    }

    //getting languageLocale
    public String getLanguage(Context mContext) {
        try {
            sharedPreferences = mContext.getSharedPreferences(POSBILLINGPREFERENCE, Context.MODE_PRIVATE);
        } catch (Exception ae) {
            Log.e(LOGPOS, "getLanguage: " + ae.getMessage());
        }
        return sharedPreferences.getString(LANGUAGECODE, "");
    }

    public void setTTSLanguageDialogueStatus(Context mContext, int count,boolean languageAvailabilityFirstTime,boolean langauge) {
        sharedPreferences = mContext.getSharedPreferences(POSBILLINGPREFERENCE, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            editor = sharedPreferences.edit();
            editor.putInt(TTSDIALOGUECOUNT,count);
            editor.putBoolean(LANGUAGEAVAILABILITYFIRSTTIME,languageAvailabilityFirstTime);
        }
    }




    //setting language locale
    public void localisation(Context mContext, String language_key) {
        Locale locale = new Locale(language_key);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        mContext.getResources().updateConfiguration(config,
                mContext.getResources().getDisplayMetrics());
        /*Activity activity = (Activity) mContext;

        getApplicationContext().getResources().updateConfiguration(config,
                getApplicationContext().getResources().getDisplayMetrics());*/
/*
        Locale.setDefault(locale);
        Configuration config = mContext.getResources().getConfiguration();
        if (!config.locale.equals(locale)) {
            config.locale = locale;
            mContext.getResources().updateConfiguration(config, null);

        }*/
    }

    //setting client id
    public void setclientRegId(Context mContext, OTPSuccessResponse response) {
        sharedPreferences = mContext.getSharedPreferences(POSBILLINGPREFERENCE, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            editor = sharedPreferences.edit();
            editor.putString("CLIENTREGID", response.getRegId());
            editor.putString("FirstName", response.getContentDetail().getFirstname());
            editor.putString("MiddleName", response.getContentDetail().getMidename());
            editor.putString("LastName", response.getContentDetail().getLastname());
            editor.putString("ContactNumber", response.getContentDetail().getContactNo());
            editor.putString("EmailId", response.getContentDetail().getEmailId());
            editor.putString("ImagePath", response.getContentDetail().getImagePath());
            editor.putString("Address", response.getContentDetail().getAddress());
            editor.putString("ShopName", response.getContentDetail().getBusinessName());
            editor.putString("Village", response.getContentDetail().getVillage());
            editor.putString("Taluka", response.getContentDetail().getTaluka());
            editor.putString("District", response.getContentDetail().getDistrict());
            editor.putString("BusinessId", response.getContentDetail().getBusinessId());
            editor.putString("activeDays", response.getContentDetail().getActiveDay());
            editor.putString("activStatus", response.getContentDetail().getIsActive());
            editor.putString("BusinessTypeName", response.getBusinessTypeName().toString());

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
        } else {

        }
    }

    public void forceDeactivate(Context context) {
        MaterialDialog materialDialog2;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view1 = layoutInflater.inflate(R.layout.activity_deactviate_alert, null);
        TextView deny = view1.findViewById(R.id.button_no);
        TextView allow = view1.findViewById(R.id.button_yes);
        TextView text_title = view1.findViewById(R.id.text_title);
        TextView text_logout = view1.findViewById(R.id.text_logout);
        /*text_title.setText("Logout");
        allow.setText("Logout");*/
        /*      deny.setVisibility(View.GONE);*/
        /*text_logout.setText("Your account is logged in from other device. Please logout");*/
        materialDialog2 = new MaterialDialog.Builder(context)
                .customView(view1, false)
                .show();
        materialDialog2.setCanceledOnTouchOutside(false);
        materialDialog2.setCancelable(false);
        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog2.dismiss();
                Activity activity = (Activity) context;
                activity.finishAffinity();
                Intent intent = new Intent(context, ActivityContactUs.class);
                context.startActivity(intent);
            }
        });
    }


    public void ttsLanguageDialogue(Context context, String message) {
        MaterialDialog materialDialog2;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view1 = layoutInflater.inflate(R.layout.activity_deactviate_alert, null);
        TextView deny = view1.findViewById(R.id.button_no);
        TextView allow = view1.findViewById(R.id.button_yes);
        TextView text_title = view1.findViewById(R.id.text_title);
        TextView text_logout = view1.findViewById(R.id.text_logout);
        text_title.setText(context.getString(R.string.tip));
        allow.setText(context.getString(R.string.ok));
        /*      deny.setVisibility(View.GONE);*/
        text_logout.setText(message);
        materialDialog2 = new MaterialDialog.Builder(context)
                .customView(view1, false)
                .show();
        materialDialog2.setCanceledOnTouchOutside(false);
        materialDialog2.setCancelable(false);
        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog2.dismiss();
            }
        });
    }



    public void onUpdateVersion(String message, Context context, Activity activity) {
        MaterialDialog materialDialog2;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view1 = layoutInflater.inflate(R.layout.activity_deactviate_alert, null);
        TextView deny = view1.findViewById(R.id.button_no);
        TextView allow = view1.findViewById(R.id.button_yes);
        TextView text_title = view1.findViewById(R.id.text_title);
        TextView text_logout = view1.findViewById(R.id.text_logout);
        text_title.setText("Update Available");
        allow.setText("Update");
        allow.setVisibility(View.VISIBLE);
        deny.setVisibility(View.GONE);
        text_logout.setText("New version of app available  , please update it");
        materialDialog2 = new MaterialDialog.Builder(context)
                .customView(view1, false)
                .show();
        materialDialog2.setCanceledOnTouchOutside(false);
        materialDialog2.setCancelable(false);
        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException e) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                } finally {
                    activity.finishAffinity();
                }
            }
        });
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    //getting client id
    public String getclientRegId(Context mContext) {
        try {
            sharedPreferences = mContext.getSharedPreferences(POSBILLINGPREFERENCE, Context.MODE_PRIVATE);
        } catch (Exception ae) {
            Log.e(LOGPOS, "getclientRegId: " + ae.getMessage());
        }
        return sharedPreferences.getString(CLIENTREGID, "");
    }

    public SharedPreferences getSharedPReference(Context mContext) {
        try {
            sharedPreferences = mContext.getSharedPreferences(POSBILLINGPREFERENCE, Context.MODE_PRIVATE);
        } catch (Exception ae) {
            Log.e(LOGPOS, "getclientRegId: " + ae.getMessage());
        }
        return sharedPreferences;
    }


    public void removeClientID(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(POSBILLINGPREFERENCE, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            editor = sharedPreferences.edit();
            editor.remove(CLIENTREGID);
            editor.commit();
        } else {

        }
    }


    public void showProgressDialogue(Context mContext) {
        pd = new ProgressDialog(mContext, R.style.MyGravity);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);       // Set progress dialog style horizontal
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));       // Set the progress dialog background color transparent
        pd.setCancelable(false);
        pd.show();
    }

    public void dismissProgress() {
        if (pd != null) {
            pd.dismiss();
        }
    }
}
