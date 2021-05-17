package com.posbilling.posbillingapplication.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.contactus.ActivityContactUs;
import com.posbilling.posbillingapplication.activity.otp.ActivityOtp;
import com.posbilling.posbillingapplication.activity.webview.ActivityWebview;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.response.LoginResponse;
import com.posbilling.posbillingapplication.utility.Utility;

import butterknife.BindView;
import butterknife.OnClick;

import static com.posbilling.posbillingapplication.utility.Constants.CLIENTID;
import static com.posbilling.posbillingapplication.utility.Constants.MOBILENUMBER;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;

public class ActivityLogin extends BaseActivity implements LoginContracter.View{
    private View view;
    private LoginPresenter mPresenter;
    private String android_id = "";
    private String language = langaugeCodeEnglish;
    boolean ch_flag_TermsAndCondition = false;

    @BindView(R.id.edittext_number)
    EditText edittext_number;
    @BindView(R.id.relative_parent)
    RelativeLayout relative_parent;
    @BindView(R.id.checkbox_Termsandcondition)
    CheckBox checkbox_Termsandcondition;


    @OnClick(R.id.checkbox_Termsandcondition)
    void checkbox_Termsandcondition(){
        boolean checkeddbxxxx = checkbox_Termsandcondition.isChecked();
        if (checkeddbxxxx) {
            ch_flag_TermsAndCondition = true;
        } else {
            ch_flag_TermsAndCondition = false;
        }
    }

    @OnClick(R.id.textview_termsandcondition_two)
    public void textview_termcondition() {
        Intent intent = new Intent(ActivityLogin.this, ActivityWebview.class);
        intent.putExtra("URLParse", "https://ramumunimji.com/TermsnCondition.html");
        intent.putExtra("from", "termsandcondition");
        startActivity(intent);
    }

    @OnClick(R.id.textview_termsandcondition_three)
    public void textview_privacypolicy() {
        Intent intent = new Intent(ActivityLogin.this, ActivityWebview.class);
        intent.putExtra("URLParse1", "https://ramumunimji.com/Privacypolicy.html");
        intent.putExtra("from", "privacypolicy");
        startActivity(intent);

    }

    @OnClick(R.id.textview_termsandcondition_three1)
    public void textview_termsandcondition_three1(){
        Intent intent = new Intent(ActivityLogin.this, ActivityWebview.class);
        intent.putExtra("URLParse2","https://ramumunimji.com/Privacypolicy.html");
        intent.putExtra("from","datausagepolicy");
        startActivity(intent);
    }

    @OnClick(R.id.btn_submit)
    void btn_submit(){
        if (Utility.getInstance().isOnline(this)) {
            if (TextUtils.isEmpty(edittext_number.getText().toString().trim())) {
                edittext_number.requestFocus();
                edittext_number.setError(getString(R.string.PLEASE_ENTER_MOBILE_NUMBER));
            } else if (!Utility.getInstance().isContactValid(edittext_number.getText().toString().trim())) {
                edittext_number.requestFocus();
                edittext_number.setError(getString(R.string.please_enter_10_digit_mobile_number));
            }else if(ch_flag_TermsAndCondition){
               /* Intent intent = new Intent(this, ActivityOtp.class);
                intent.putExtra(MOBILENUMBER,edittext_number.getText().toString());
                startActivity(intent);*/
                Utility.getInstance().showProgressDialogue(ActivityLogin.this);
                mPresenter.postLogin(android_id,edittext_number.getText().toString(),language);
            }else {
                Utility.getInstance().showSnackbar(view,"Please check the Terms of Service,Privacy Policy,Data Usage Policy");
            }
        }else {
            Utility.getInstance().showSnackbar(view,getString(R.string.please_check_internet));
        }
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getWindow().getDecorView().findViewById(android.R.id.content);
        generateCommanDeviceId();
        checkLanguageCode();
    }

    private void checkLanguageCode() {
        language = Utility.getInstance().getLanguage(this);
    }

    /**
     * creating comman device id
     * here we are not using device id bcz after android 10 set restriction on access of unchangable device ids
     * String uniqueID = UUID.randomUUID().toString();
     */
    private void generateCommanDeviceId() {
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    //login Api Success
    @Override
    public void loginSuccess(LoginResponse loginResponse) {
        Utility.getInstance().dismissProgress();
        showDebugToast("Login Successful");
        Intent intent = new Intent(this, ActivityOtp.class);
        intent.putExtra(MOBILENUMBER,edittext_number.getText().toString());
        intent.putExtra(CLIENTID,loginResponse.getRegId());
        startActivity(intent);
    }

    ///Login Api failure
    @Override
    public void loginFailure(String message, String status) {
        Utility.getInstance().dismissProgress();
        showDebugToast(getString(R.string.something_went_wrong_please_try_again));
        showError(relative_parent, getString(R.string.Login_failed));
        Toast.makeText(this, getString(R.string.Login_failed), Toast.LENGTH_LONG).show();
        /*if (status.matches("0")) {
            startActivity(new Intent(ActivityLogin.this, ActivityContactUs.class));
        }*/

        /*Intent intent = new Intent(this, ActivityOtp.class);
        intent.putExtra(MOBILENUMBER,edittext_number.getText().toString());
        startActivity(intent);*/
    }
}
