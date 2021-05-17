package com.posbilling.posbillingapplication.activity.cropdetail;

import com.posbilling.posbillingapplication.model.response.LoginResponse;
import com.posbilling.posbillingapplication.model.response.ResponseCustomerCropDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;

public class CropDetailPresenter implements CropDetailContractor.Presenter {
    private CropDetailContractor.View mView;

    public CropDetailPresenter(CropDetailContractor.View mView) {
        this.mView = mView;
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

    @Override
    public void getCropDetailList(String clientId, String customerId) {
        mView.getAPIComponent().getRetroService().getBaseUrl("CropSuggestion/").getCustomerCropList(clientId,customerId).enqueue(new Callback<ResponseCustomerCropDetails>() {
            @Override
            public void onResponse(Call<ResponseCustomerCropDetails> call, Response<ResponseCustomerCropDetails> response) {
                if (response.isSuccessful()){
                    if(response !=null && response.body().getStatus().matches(RESPONSESUCCESS)){
                        mView.getCropListSuccess(response.body());
                    }else{
                        mView.getCropListFailure(response.body().getMessage(),response.body().getStatus());
                    }
                }else {
                    mView.getCropListFailure("Something went wrong...Please try again.", "2");
                }
            }

            @Override
            public void onFailure(Call<ResponseCustomerCropDetails> call, Throwable t) {
                mView.getCropListFailure(t.getMessage(), "2");
            }
        });
    }
}
