package com.posbilling.posbillingapplication.activity.otp;

import com.posbilling.posbillingapplication.model.request.LoginParams;
import com.posbilling.posbillingapplication.model.response.LoginResponse;
import com.posbilling.posbillingapplication.model.response.OTPSuccessResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;

/**
 * Created by Android PC (Ankur) on 04,March,2020
 */
public class OtpPresenter implements OtpContracter.Presenter {

    private OtpContracter.View mView;

    public OtpPresenter(OtpContracter.View mView) {
        this.mView = mView;
    }

    @Override
    public void postOTP(String androidDeviceId, String mNumber, String OTP, String language, String mClientId, int flow) {
        LoginParams loginParams = new LoginParams();
        //loginParams.setDeviceid(androidDeviceId);
        /*if (language.toLowerCase().contains(langaugeCodeEnglish.toLowerCase())){
            loginParams.setLanguageId("1");
        }else{
            loginParams.setLanguageId("2");
        }*/

        if (language.toLowerCase().contains(langaugeCodeEnglish.toLowerCase())){
            loginParams.setLanguageId("0");
        }else{
            loginParams.setLanguageId("1");
        }
        loginParams.setNumber(mNumber);
        loginParams.setOTP(OTP);
        loginParams.setClientId(mClientId);
        mView.getAPIComponent().getRetroService().getBaseUrl("Login/").getOTP(loginParams).enqueue(new Callback<OTPSuccessResponse>() {
            @Override
            public void onResponse(Call<OTPSuccessResponse> call, Response<OTPSuccessResponse> response) {
                if (response.isSuccessful()){
                    if(response !=null && response.body().getStatus().matches(RESPONSESUCCESS)){
                        mView.OtpSuccess(response.body(),flow);
                    }else{
                        mView.OtpInvalid(response.body().getMessage());
                    }
                }else{
                    mView.OtpFailure("Something went wrong...Please try again.");
                }
            }

            @Override
            public void onFailure(Call<OTPSuccessResponse> call, Throwable t) {
                mView.OtpFailure(t.getMessage());
            }
        });
    }



    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }
}
