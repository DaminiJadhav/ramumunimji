package com.posbilling.posbillingapplication.activity.cropregistration;

import com.posbilling.posbillingapplication.model.request.RequestAddCrop;
import com.posbilling.posbillingapplication.model.response.ResponseAddCrop;
import com.posbilling.posbillingapplication.model.response.ResponseCropDropDown;
import com.posbilling.posbillingapplication.model.response.ResponseCustomerCropDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;

public class CropRegistrationPresenter implements CropRegistrationContractor.Presenter{

    private CropRegistrationContractor.View mView;

    public CropRegistrationPresenter(CropRegistrationContractor.View mView) {
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
    public void getCropDropDown(String regId) {
        mView.getAPIComponent().getRetroService().getBaseUrl("AgroAgency/").getCropDropDown(regId).enqueue(new Callback<ResponseCropDropDown>() {
            @Override
            public void onResponse(Call<ResponseCropDropDown> call, Response<ResponseCropDropDown> response) {
                if (response.isSuccessful()){
                    if(response !=null && response.body().getStatus().matches(RESPONSESUCCESS)){
                        mView.getCropDropDownList(response.body());
                    }else{
                        mView.getCropDropDownFailure("Please contact adminstration.");
                    }
                }else {
                    mView.getCropDropDownFailure("Something went wrong...Please try again.");
                }
            }

            @Override
            public void onFailure(Call<ResponseCropDropDown> call, Throwable t) {
                mView.getCropDropDownFailure(t.getMessage());
            }
        });
    }

    @Override
    public void postyCropDetails(RequestAddCrop requestAddCrop) {
        mView.getAPIComponent().getRetroService().getBaseUrl("AgroAgency/").postCrop(requestAddCrop).enqueue(new Callback<ResponseAddCrop>() {
            @Override
            public void onResponse(Call<ResponseAddCrop> call, Response<ResponseAddCrop> response) {
                if (response.isSuccessful()){
                    if(response !=null && response.body().getStatus().matches(RESPONSESUCCESS)){
                        mView.postCropSuccess(response.body());
                    }else{
                        mView.postCropFailure(response.body().getMessage());
                    }
                }else {
                    mView.postCropFailure("Something went wrong...Please try again.");
                }
            }

            @Override
            public void onFailure(Call<ResponseAddCrop> call, Throwable t) {
                mView.postCropFailure(t.getMessage());
            }
        });
    }
}
