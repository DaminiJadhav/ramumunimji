package com.posbilling.posbillingapplication.activity.editcustomer;

import com.posbilling.posbillingapplication.model.request.AddCustomerParams;
import com.posbilling.posbillingapplication.model.response.AddCustomerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;

public class EditCustomerPresenter implements EditCustomerContractor.Presenter {
    private EditCustomerContractor.View mView;

    public EditCustomerPresenter(EditCustomerContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void postAddCustomer(AddCustomerParams addCustomerParams) {
        /*APIComponent apiComponent = mView.getAPIComponent();
        CustomRetroRequest customRetroRequest = apiComponent.getRetroService();

        RetrofitAPI retrofitAPI = customRetroRequest.getBaseUrl("CustomerRegister/");*/
        mView.getAPIComponent().getRetroService().getBaseUrl("Customers/").editCustomer(addCustomerParams).enqueue(new Callback<AddCustomerResponse>() {
            @Override
            public void onResponse(Call<AddCustomerResponse> call, Response<AddCustomerResponse> response) {
                if (response.isSuccessful()) {
                    if (response != null && response.body().getStatus().matches(RESPONSESUCCESS)) {
                        mView.addCustomerSuccess(response.body());
                    } else {
                        mView.addCustomerFailure(response.body().getMessage());
                    }
                } else {
                    mView.addCustomerFailure("response fails");
                }
            }

            @Override
            public void onFailure(Call<AddCustomerResponse> call, Throwable t) {
                mView.addCustomerFailure(t.getMessage());
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
