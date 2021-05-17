package com.posbilling.posbillingapplication.activity.dashboard.ui.crop;

import com.posbilling.posbillingapplication.model.response.CustomerListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CropCustomerPresenter implements CropCustomerContractor.Presenter {
    private CropCustomerContractor.View mView;

    public CropCustomerPresenter(CropCustomerContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void getCustomerList(String clientId) {
        mView.getAPIComponent().getRetroService().getBaseUrl("CustomerRegister/").getCustomerList(clientId, "").enqueue(new Callback<CustomerListResponse>() {
            @Override
            public void onResponse(Call<CustomerListResponse> call, Response<CustomerListResponse> response) {
                if (response.isSuccessful()) {
                    mView.customerListSuccess(response.body());
                } else {
                    mView.customerListFailure("Something went wrong...Please try again");
                }
            }

            @Override
            public void onFailure(Call<CustomerListResponse> call, Throwable t) {
                mView.customerListFailure(t.getMessage());
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