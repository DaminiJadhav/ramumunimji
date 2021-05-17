package com.posbilling.posbillingapplication.activity.dashboard.ui.salesreport;

import com.posbilling.posbillingapplication.model.request.SalesReportPdfRequest;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.model.response.TransactionListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesReportPresenter implements SalesReportController.Presenter {
    private SalesReportController.View mView;

    public SalesReportPresenter(SalesReportController.View mView) {
        this.mView = mView;
    }

    //https://ramu.sdaemon.com/api/CTransactionTouch/GetTransaction?regId=11&CustomerId=6
    @Override
    public void getTransactionList(String regId, String customerID) {
        mView.getAPIComponent().getRetroService().getBaseUrl("CTransactionTouch/").getTransaction(regId, customerID)
                .enqueue(new Callback<TransactionListResponse>() {
                    @Override
                    public void onResponse(Call<TransactionListResponse> call, Response<TransactionListResponse> response) {
                        if (response.isSuccessful()) {
                            mView.getTraansactionListSuccess(response.body());
                        } else {
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
    public void getPDFData(SalesReportPdfRequest salesReportPdfRequest) {
        mView.getAPIComponent().getRetroService().getBaseUrl("SalesReport/").getSalesPdf(salesReportPdfRequest)
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
