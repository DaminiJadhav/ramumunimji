package com.posbilling.posbillingapplication.activity.dashboard.ui.setting;

import com.posbilling.posbillingapplication.model.request.UpdateProfile;
import com.posbilling.posbillingapplication.model.response.AddCustomerResponse;
import com.posbilling.posbillingapplication.model.response.BuisnessIdListResponse;
import com.posbilling.posbillingapplication.model.response.ClientUpdateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;

public class SettingPresenter implements SettingContractor.Presenter {
    SettingContractor.View mView;

    public SettingPresenter(SettingContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void postProfileData(UpdateProfile updateProfile) {
        mView.getAPIComponent().getRetroService().getBaseUrl("ClientProfile/").postClientProfile(updateProfile).enqueue(new Callback<ClientUpdateResponse>() {
            @Override
            public void onResponse(Call<ClientUpdateResponse> call, Response<ClientUpdateResponse> response) {
                if (response.isSuccessful()){
                    if(response !=null && response.body().getStatus().matches(RESPONSESUCCESS)){
                        mView.profileUpdateResponse(response.body());
                    }else{
                        mView.profileUpdateFailure(response.body().getMessage());
                    }
                }else {
                    mView.profileUpdateFailure("response fails");
                }
            }

            @Override
            public void onFailure(Call<ClientUpdateResponse> call, Throwable t) {
                mView.profileUpdateFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getBuisnessIDList() {
        mView.getAPIComponent().getRetroService().getBaseUrl("ExpenseReport/").getBuisnessIDList().enqueue(new Callback<BuisnessIdListResponse>() {
            @Override
            public void onResponse(Call<BuisnessIdListResponse> call, Response<BuisnessIdListResponse> response) {
                if (response.isSuccessful()){
                    if(response !=null && response.body().getStatus().matches(RESPONSESUCCESS)){
                        mView.buisnessIDListSuccess(response.body());
                    }else{
                        mView.profileUpdateFailure(response.body().getMessage());
                    }
                }else {
                    mView.profileUpdateFailure("response fails");
                }
            }

            @Override
            public void onFailure(Call<BuisnessIdListResponse> call, Throwable t) {
                mView.profileUpdateFailure(t.getMessage());
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