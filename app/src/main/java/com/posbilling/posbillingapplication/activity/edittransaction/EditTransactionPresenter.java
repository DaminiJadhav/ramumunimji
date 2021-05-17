package com.posbilling.posbillingapplication.activity.edittransaction;

import com.posbilling.posbillingapplication.model.request.TransactionRequest;
import com.posbilling.posbillingapplication.model.response.TransactionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;

public class EditTransactionPresenter implements EditTransactionContractor.Presenter {
    private EditTransactionContractor.View mView;

    public EditTransactionPresenter(EditTransactionContractor.View mView) {
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
    public void deleteTransaction(TransactionRequest transactionRequest) {
        mView.getAPIComponent().getRetroService().getBaseUrl("CTransactionTouch/").postTransaction(transactionRequest)
                .enqueue(new Callback<TransactionResponse>() {
                    @Override
                    public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                        if (response.isSuccessful() && response.body().getStatus().matches(RESPONSESUCCESS)){
                            mView.postDeleteSuccess(response.body());
                        }else {
                            mView.postDeleteFailure("Something went wrong. Please try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionResponse> call, Throwable t) {
                        mView.postDeleteFailure("Something went wrong. Please try again");
                    }
                });
    }
}
