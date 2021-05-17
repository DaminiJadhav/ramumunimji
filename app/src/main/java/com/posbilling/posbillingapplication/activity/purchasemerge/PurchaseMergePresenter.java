package com.posbilling.posbillingapplication.activity.purchasemerge;

import com.posbilling.posbillingapplication.model.request.MergeRequest;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.GetSupplierResponseTwo;
import com.posbilling.posbillingapplication.model.response.GetSuppliersResponse;
import com.posbilling.posbillingapplication.model.response.MergeApiResponse;
import com.posbilling.posbillingapplication.model.response.PurchaseApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseMergePresenter implements PurchaseMergeContractor.Presenter {
    private PurchaseMergeContractor.View mView;
    public PurchaseMergePresenter(PurchaseMergeContractor.View mView) {
    this.mView = mView;
    }

    @Override
    public void getSupplierList(String clientId) {
        mView.getAPIComponent().getRetroService().getBaseUrl("CustomerRegister/").getSupplierList(clientId,"").enqueue(new Callback<GetSupplierResponseTwo>() {
            @Override
            public void onResponse(Call<GetSupplierResponseTwo> call, Response<GetSupplierResponseTwo> response) {
                if (response.isSuccessful()){
                    mView.supplierListSuccess(response.body());
                }else {
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
    public void postmergeOperation(MergeRequest mergeRequest) {
        mView.getAPIComponent().getRetroService().getBaseUrl("Supplier/").postPurchaseMergeOperation(mergeRequest).enqueue(new Callback<PurchaseApiResponse>() {
            @Override
            public void onResponse(Call<PurchaseApiResponse> call, Response<PurchaseApiResponse> response) {
                if (response.isSuccessful()){
                    mView.mergeUserSuccess(response.body());
                }else {
                    mView.mergeUserFailure("Something went wrong...Please try again");
                }
            }

            @Override
            public void onFailure(Call<PurchaseApiResponse> call, Throwable t) {
                mView.mergeUserFailure(t.getMessage());
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
