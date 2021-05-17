package com.posbilling.posbillingapplication.activity.dashboard.ui.outstanding;

import com.posbilling.posbillingapplication.model.response.AddCustomerResponse;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;

public class OutsatndingPresenter implements OutstandingContractor.Presenter {
    private OutstandingContractor.View mView;

    public OutsatndingPresenter(OutstandingContractor.View mView) {
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
    public void getCustomerList(String clientId) {
        mView.getAPIComponent().getRetroService().getBaseUrl("CustomerRegister/").getCustomerList(clientId,"").enqueue(new Callback<CustomerListResponse>() {
            @Override
            public void onResponse(Call<CustomerListResponse> call, Response<CustomerListResponse> response) {
                if (response.isSuccessful()){
                    mView.customerListSuccess(response.body());
                }else {
                    mView.customerListFailure("Something went wrong...Please try again");
                }
            }

            @Override
            public void onFailure(Call<CustomerListResponse> call, Throwable t) {
                mView.customerListFailure(t.getMessage());
            }
        });
    }
}
