package com.posbilling.posbillingapplication.activity.dashboard.ui.expenesreport;

import com.posbilling.posbillingapplication.model.request.ExpenseReportPdfRequest;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.ExpenseResponse;
import com.posbilling.posbillingapplication.model.response.PdfResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpesneReportPresenter implements ExpenseReportContractor.Presenter {
    private ExpenseReportContractor.View mView;

    public ExpesneReportPresenter(ExpenseReportContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void getExpnsesList(String clientId) {
        mView.getAPIComponent().getRetroService().getBaseUrl("ClientExpences/").getExpesenselist(clientId)
                .enqueue(new Callback<ExpenseResponse>() {
                    @Override
                    public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {
                        if (response.isSuccessful()) {
                            mView.expenseListSuccess(response.body());
                        } else {
                            mView.expenseListFailure("Something went wrong...Please try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<ExpenseResponse> call, Throwable t) {
                        mView.expenseListFailure("Something went wrong...Please try again");
                    }
                });

    }

    @Override
    public void getPDFData(ExpenseReportPdfRequest expenseReportPdfRequest) {
        mView.getAPIComponent().getRetroService().getBaseUrl("ExpenseReport/").getExpensePdf(expenseReportPdfRequest)
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
