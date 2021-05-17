package com.posbilling.posbillingapplication.activity.merge;

import com.posbilling.posbillingapplication.model.request.MergeRequest;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.MergeApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MergePresenter implements MergeContractor.Presenter {

    private MergeContractor.View mView;

    public MergePresenter(MergeContractor.View mView) {
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

    @Override
    public void postmergeOperation(MergeRequest mergeRequest) {
        mView.getAPIComponent().getRetroService().getBaseUrl("Invices/").postMergeOperation(mergeRequest).enqueue(new Callback<MergeApiResponse>() {
            @Override
            public void onResponse(Call<MergeApiResponse> call, Response<MergeApiResponse> response) {
                if (response.isSuccessful()){
                    mView.mergeUserSuccess(response.body());
                }else {
                    mView.mergeUserFailure("Something went wrong...Please try again");
                }
            }

            @Override
            public void onFailure(Call<MergeApiResponse> call, Throwable t) {
                mView.mergeUserFailure(t.getMessage());
            }
        });
    }

}
