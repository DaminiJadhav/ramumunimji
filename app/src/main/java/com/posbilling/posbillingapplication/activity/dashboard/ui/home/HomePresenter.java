package com.posbilling.posbillingapplication.activity.dashboard.ui.home;

import com.posbilling.posbillingapplication.model.request.ExpensesRequest;
import com.posbilling.posbillingapplication.model.request.PurchaseTransactionRequest;
import com.posbilling.posbillingapplication.model.request.TransactionRequest;
import com.posbilling.posbillingapplication.model.response.ExpenseResponse;
import com.posbilling.posbillingapplication.model.response.GetSupplierTransaction;
import com.posbilling.posbillingapplication.model.response.PostPurchaseResponse;
import com.posbilling.posbillingapplication.model.response.TransactionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;

public class HomePresenter implements HomeContractor.Presenter{
    private HomeContractor.View mView;

    public HomePresenter(HomeContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void postTransaction(TransactionRequest transactionRequest, int valueofFlowOnlineOfline) {
        mView.getAPIComponent().getRetroService().getBaseUrl("CTransactionTouch/").postTransaction(transactionRequest)
                .enqueue(new Callback<TransactionResponse>() {
                    @Override
                    public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                        if (response.isSuccessful() && response.body().getStatus().matches(RESPONSESUCCESS)){
                            mView.postResponseSuccess(response.body(),valueofFlowOnlineOfline);
                        }else {
                            mView.postResponseFailure("Something went wrong. Please try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionResponse> call, Throwable t) {
                        if (t.toString().contains("SocketTimeoutException")) {
                            mView.postResponseFailuretimeout();
                        } else {
                            mView.postResponseFailure("Something went wrong. Please try again");
                        }
                    }
                });
    }

    @Override
    public void postExpense(ExpensesRequest expensesRequest, int valueofFlowOnlineOfline) {
        mView.getAPIComponent().getRetroService().getBaseUrl("ClientExpences/").postExpense(expensesRequest)
                .enqueue(new Callback<ExpenseResponse>() {
                    @Override
                    public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {
                        if (response.isSuccessful() && response.body().getStatus().matches(RESPONSESUCCESS)){
                            mView.postExpenseSuccess(response.body(),valueofFlowOnlineOfline);
                        }else {
                            mView.postResponseFailure("Something went wrong. Please try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<ExpenseResponse> call, Throwable t) {
                        if (t.toString().contains("SocketTimeoutException")) {
                            mView.postResponseFailuretimeout();
                        } else {
                            mView.postResponseFailure("Something went wrong. Please try again");
                        }
                    }
                });
    }

    @Override
    public void postPurchaseData(PurchaseTransactionRequest purchaseTransactionRequest) {
        mView.getAPIComponent().getRetroService().getBaseUrl("KharediTransaction/").postPurchase(purchaseTransactionRequest)
                .enqueue(new Callback<PostPurchaseResponse>() {
                    @Override
                    public void onResponse(Call<PostPurchaseResponse> call, Response<PostPurchaseResponse> response) {
                        if (response.isSuccessful() && response.body().getStatus().matches(RESPONSESUCCESS)){
                                mView.postPurchaseResponseSuccess(response.body());
                        }else {
                            mView.postResponseFailure("Something went wrong. Please try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<PostPurchaseResponse> call, Throwable t) {
                        if (t.toString().contains("SocketTimeoutException")) {
                            mView.postResponseFailuretimeout();
                        } else {
                            mView.postResponseFailure("Something went wrong. Please try again");
                        }
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
