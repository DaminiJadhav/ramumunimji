package com.posbilling.posbillingapplication.activity.outstandinglist;

import com.posbilling.posbillingapplication.model.request.PdfDetailOutstandingRequest;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.model.response.TransactionListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutstandingListPresenter implements OutstandingListContractor.Presenter {
    private OutstandingListContractor.View mView;

    public OutstandingListPresenter(OutstandingListContractor.View mView) {
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

    //https://ramu.sdaemon.com/api/CTransactionTouch/GetTransaction?regId=11&CustomerId=6
    @Override
    public void getTransactionList(String regId , String customerID) {
        mView.getAPIComponent().getRetroService().getBaseUrl("CTransactionTouch/").getTransaction(regId,customerID)
                .enqueue(new Callback<TransactionListResponse>() {
                    @Override
                    public void onResponse(Call<TransactionListResponse> call, Response<TransactionListResponse> response) {
                        if (response.isSuccessful()){
                            mView.getTraansactionListSuccess(response.body());
                        }else {
                            mView.getTraansactionListFailure("Something went wrong.");
                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionListResponse> call, Throwable t) {
                        mView.getTraansactionListFailure("Something went wrong.");
                    }
                });
    }

    @Override
    public void getPDFData(PdfDetailOutstandingRequest pdfDetailOutstandingRequest) {
        mView.getAPIComponent().getRetroService().getBaseUrl("Reposrt/").getOustsatndingPdf(pdfDetailOutstandingRequest)
                .enqueue(new Callback<PdfResponse>() {
                    @Override
                    public void onResponse(Call<PdfResponse> call, Response<PdfResponse> response) {
                        if (response.isSuccessful()){
                            mView.getPdfResponse(response.body());
                        }else {
                            mView.getPdfFailure("Something went wrong.");
                        }
                    }

                    @Override
                    public void onFailure(Call<PdfResponse> call, Throwable t) {
                        mView.getPdfFailure("Something went wrong.");
                    }
                });
    }
}
