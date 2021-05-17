package com.posbilling.posbillingapplication.fragment.fragmentsupplierreport;

import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.GetSupplierResponseTwo;
import com.posbilling.posbillingapplication.model.response.GetSuppliersResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierSummuryPresenter implements SupplierSummuryContract.Presenter {
    private SupplierSummuryContract.View mView;

    public SupplierSummuryPresenter(SupplierSummuryContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void getSupplierList(String clientId) {
        mView.getAPIComponent().getRetroService().getBaseUrl("SupplierRegister/").getSupplierList(clientId, "").enqueue(new Callback<GetSupplierResponseTwo>() {
            @Override
            public void onResponse(Call<GetSupplierResponseTwo> call, Response<GetSupplierResponseTwo> response) {
                if (response.isSuccessful()) {
                    mView.supplierListSuccess(response.body());
                } else {
                    mView.supplierListFailure("Something went wrong...Please try again");
                }
            }

            @Override
            public void onFailure(Call<GetSupplierResponseTwo> call, Throwable t) {
                mView.supplierListFailure(t.getMessage());
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
