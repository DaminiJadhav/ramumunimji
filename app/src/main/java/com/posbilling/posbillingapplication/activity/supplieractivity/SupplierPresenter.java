package com.posbilling.posbillingapplication.activity.supplieractivity;

import com.posbilling.posbillingapplication.model.request.PdfDetailOutstandingRequest;
import com.posbilling.posbillingapplication.model.response.GetSuppliersOutstandingResponse;
import com.posbilling.posbillingapplication.model.response.GetSuppliersResponse;
import com.posbilling.posbillingapplication.model.response.TransactionListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierPresenter implements SupplierContractor.Presenter{
private SupplierContractor.View mView;

    public SupplierPresenter(SupplierContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void getTransactionList(String regId, String customerID) {
        mView.getAPIComponent().getRetroService().getBaseUrl("SupplierRegister/").getSupplierOutstandingList(regId,customerID)
                .enqueue(new Callback<GetSuppliersOutstandingResponse>() {
                    @Override
                    public void onResponse(Call<GetSuppliersOutstandingResponse> call, Response<GetSuppliersOutstandingResponse> response) {
                        if (response.isSuccessful()){
                            mView.getTraansactionListSuccess(response.body());
                        }else {
                            mView.getTraansactionListFailure("Something went wrong.");
                        }
                    }

                    @Override
                    public void onFailure(Call<GetSuppliersOutstandingResponse> call, Throwable t) {
                        mView.getTraansactionListFailure("Something went wrong.");
                    }
                });
    }

    @Override
    public void getPDFData(PdfDetailOutstandingRequest pdfDetailOutstandingRequest) {

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
