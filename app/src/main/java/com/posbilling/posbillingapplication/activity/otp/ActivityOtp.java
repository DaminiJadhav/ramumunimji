package com.posbilling.posbillingapplication.activity.otp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.posbilling.posbillingapplication.BuildConfig;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.homeactivitytwo.HomeActivityTwo;
import com.posbilling.posbillingapplication.activity.settingcontainer.ActivitySettingContainer;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;

import com.posbilling.posbillingapplication.model.response.LoginResponse;
import com.posbilling.posbillingapplication.model.response.OTPSuccessResponse;
import com.posbilling.posbillingapplication.utility.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import in.aabhasjindal.otptextview.OtpTextView;
import io.realm.Realm;

import static com.posbilling.posbillingapplication.utility.Constants.CLIENTID;
import static com.posbilling.posbillingapplication.utility.Constants.LASTCUSTOMERID;
import static com.posbilling.posbillingapplication.utility.Constants.LOGPOS;
import static com.posbilling.posbillingapplication.utility.Constants.MOBILENUMBER;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;

public class ActivityOtp extends BaseActivity implements OtpContracter.View {
    private View view;
    private OtpPresenter mPresenter;
    private String android_id = "";
    private String mNumber = "";
    private String language = langaugeCodeEnglish;
    private String mClientId = "";
    private Dialog dialog;
    private TextView textResend;


    @BindView(R.id.otp_view)
    OtpTextView otpTextView;


    private void checkLanguageCode() {
        language = Utility.getInstance().getLanguage(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getWindow().getDecorView().findViewById(android.R.id.content);
        getIntentData();
        generateCommanDeviceId();
        checkLanguageCode();
        showdialogOtp();
    }

    private void showdialogOtp() {
        dialog = new Dialog(this);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_otp_popup);
        Button btnSubmit = dialog.findViewById(R.id.btn_submit);
        EditText edittext_OTP = dialog.findViewById(R.id.edittext_OTP);
        textResend = dialog.findViewById(R.id.textResend);
        TextView textNumber = dialog.findViewById(R.id.textNumber);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                textResend.setClickable(false);
                textResend.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                textResend.setClickable(true);
                textResend.setText("Resend");
            }
        }.start();


        textNumber.setText(mNumber.toString());

        textResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.getInstance().isOnline(ActivityOtp.this)) {
                    Utility.getInstance().showProgressDialogue(ActivityOtp.this);
                    mPresenter.postOTP("", mNumber, "", language, "", 1);
                } else {
                    Utility.getInstance().showSnackbar(view, getString(R.string.please_check_internet));
                }
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.getInstance().isOnline(ActivityOtp.this)) {
                    if (!TextUtils.isEmpty(edittext_OTP.getText()) && (edittext_OTP.getText().toString().length() == 4)) {
                        Utility.getInstance().showProgressDialogue(ActivityOtp.this);
                        mPresenter.postOTP(android_id, mNumber, edittext_OTP.getText().toString(), language, mClientId, 2);
                    } else {
                        Utility.getInstance().showSnackbar(view, getString(R.string.please_enter_4_digit_otp));
                    }
                } else {
                    Utility.getInstance().showSnackbar(view, getString(R.string.please_check_internet));
                }

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_otp;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new OtpPresenter(this);
    }

    ///generated Comman device Id
    private void generateCommanDeviceId() {
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    public void getIntentData() {
        if (getIntent() != null) {
            mNumber = getIntent().getStringExtra(MOBILENUMBER);
            mClientId = getIntent().getStringExtra(CLIENTID);
        } else {
            Log.e(LOGPOS, "Intent is null");
            //Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }
    }

    ////Otp Success
    @Override
    public void OtpSuccess(OTPSuccessResponse response, int flow) {
        if (flow == 2) {
            SharedPreferences sharedPreferences1 = Utility.getInstance().getSharedPReference(ActivityOtp.this);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            int versionCode = BuildConfig.VERSION_CODE;
            editor1.putString("versionCode", String.valueOf(versionCode));
            editor1.commit();


            Realm realm = RealmController.with(this).getRealm();
            RealmController.with(this).clearCustomerList();
            ArrayList<LoginResponse.ContentDetail.tblCustomers> customerList = new ArrayList();
            customerList.addAll(response.getContentDetail().getTblCustomersArrayList());
            ArrayList<CustomerListRealm> customerListRealmArrayList = new ArrayList<>();
            for (int i = 0; i < customerList.size(); i++) {
                CustomerListRealm customerListRealm = new CustomerListRealm();
                customerListRealm.setID(customerList.get(i).getID());
                customerListRealm.setCustomerId(customerList.get(i).getCustomerId());

                customerListRealm.setCustomerOnlineSaved(true);

                customerListRealm.setClientId(customerList.get(i).getClientId());
                customerListRealm.setCFirstname(customerList.get(i).getCFirstname());
                customerListRealm.setStateId(customerList.get(i).getStateId());
                customerListRealm.setCMiddleName(customerList.get(i).getCMiddleName());
                customerListRealm.setCLastname(customerList.get(i).getCLastname());
                customerListRealm.setImagePath(customerList.get(i).getImagePath());
                customerListRealm.setOutstanding(customerList.get(i).getOutstanding());
                customerListRealm.setVillage(customerList.get(i).getVillage());
                customerListRealm.setTaluka(customerList.get(i).getTaluka());
                customerListRealm.setDistrict(customerList.get(i).getDistrict());
                customerListRealm.setCAlternetMobNo(customerList.get(i).getCAlternetMobNo());
                customerListRealm.setCContactNo(customerList.get(i).getCContactNo());
                customerListRealmArrayList.add(customerListRealm);
            }
            //   Log.i(getClass().getName(), "=========tempListHelpScreenModel SIZE: " + tempListHelpScreenModel.size());
            for (CustomerListRealm customerListRealm : customerListRealmArrayList) {
                // Persist your data easily
                realm.beginTransaction();
                realm.copyToRealm(customerListRealm);
                realm.commitTransaction();
            }


            if (customerList.size() >= 1) {
                String customerIdFromresponse = customerList.get(customerList.size() - 1).getCustomerId();
                SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(this);
                if (!TextUtils.isEmpty(sharedPreferences.getString(LASTCUSTOMERID, ""))) {
                    String removeCFromResponse = customerIdFromresponse.substring(1);
                    if (Integer.parseInt(sharedPreferences.getString(LASTCUSTOMERID, "")) < Integer.parseInt(removeCFromResponse)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(LASTCUSTOMERID, removeCFromResponse);
                        editor.commit();
                    }
                }
            }


            //    Utility.getInstance().dismissProgress();
            settingLanguageLocale();
            Utility.getInstance().setclientRegId(ActivityOtp.this, response);

            if (response.getIsLogFirst() == null) {
                Utility.getInstance().dismissProgress();
                Toast.makeText(this, "" + getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
            } else if (response.getIsLogFirst().trim().toLowerCase().equals("true")) {
                Intent intent = new Intent(ActivityOtp.this, ActivitySettingContainer.class);
                startActivity(intent);
                finishAffinity();
            } else if (response.getIsLogFirst().trim().toLowerCase().equals("false")) {
                Intent intent = new Intent(ActivityOtp.this, HomeActivityTwo.class);
                startActivity(intent);
                finishAffinity();
            } else {
                Intent intent = new Intent(ActivityOtp.this, ActivitySettingContainer.class);
                startActivity(intent);
                finishAffinity();
            }
        } else {
            Toast.makeText(this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            Utility.getInstance().dismissProgress();
            new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {
                    textResend.setClickable(false);
                    textResend.setText("" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    textResend.setText("Resend");
                    textResend.setClickable(true);
                }
            }.start();
        }
    }


    //Checking and setting language locale
    private void settingLanguageLocale() {
/*        languageCode = Utility.getInstance().getLanguage(ActivitySplash.this);
        if (languageCode.matches("") || languageCode.matches(langaugeCodeEnglish)) {
            Utility.getInstance().localisation(ActivitySplash.this, langaugeCodeEnglish);
            Utility.getInstance().setLanguage(ActivitySplash.this, langaugeCodeEnglish);
        } else if (languageCode.matches(languageCodeMarathi)) {*/
        Utility.getInstance().localisation(ActivityOtp.this, languageCodeMarathi);
        Utility.getInstance().setLanguage(ActivityOtp.this, languageCodeMarathi);
        /*} else {
            Log.e(LOGPOS, "settingLanguageLocale: Something went wrong in language setting locale");
        }*/
    }


    //on Otp Success
    @Override
    public void OtpFailure(String message) {
        Utility.getInstance().dismissProgress();
        showDebugToast(getString(R.string.something_went_wrong_please_try_again));
    }

    @Override
    public void OtpInvalid(String message) {
        Utility.getInstance().dismissProgress();
        showDebugToast(message);
    }

}
