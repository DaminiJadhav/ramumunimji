package com.posbilling.posbillingapplication.activity.splash;

import com.posbilling.posbillingapplication.model.ActviStatusResponse;
import com.posbilling.posbillingapplication.model.response.TransactionListResponse;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;

public class SplashPresenter implements SplashContractor.Presenter {

    private SplashContractor.View mView;

    public SplashPresenter(SplashContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void getActiveStatus(String regId, String date, String time) {
        mView.getAPIComponent().getRetroService().getBaseUrl("ActiveStatus/").getActiveStatus(regId,date,time)
                .enqueue(new Callback<ActviStatusResponse>() {
                    @Override
                    public void onResponse(Call<ActviStatusResponse> call, Response<ActviStatusResponse> response) {
                        if (response.isSuccessful() && response.body().getStatus().matches(RESPONSESUCCESS)){
                            mView.getActviStatusSuccess(response.body());
                        }else {
                            mView.getActviStatusFailure("Something went wrong.");
                        }
                    }

                    @Override
                    public void onFailure(Call<ActviStatusResponse> call, Throwable t) {
                        mView.getActviStatusFailure("Something went wrong.");
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
