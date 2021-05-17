package com.posbilling.posbillingapplication.activity.editsupplier;


import com.posbilling.posbillingapplication.model.request.SupplierEditRequest;
import com.posbilling.posbillingapplication.model.response.GetSuppliersResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSupplierPresenter implements EditSupplierContractor.Presenter {
    private EditSupplierContractor.View mView;

    public EditSupplierPresenter(EditSupplierContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void postAddSupplier(SupplierEditRequest addSupplierParams) {
        mView.getAPIComponent().getRetroService().getBaseUrl("Supplier/").editSupplier(addSupplierParams).enqueue(new Callback<GetSuppliersResponse>() {
            @Override
            public void onResponse(Call<GetSuppliersResponse> call, Response<GetSuppliersResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("1")) {
                    mView.addSupplierSuccess(response.body());
                } else {
                    mView.addSupplierFailure("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<GetSuppliersResponse> call, Throwable t) {
                mView.addSupplierFailure("Something went wrong");
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
