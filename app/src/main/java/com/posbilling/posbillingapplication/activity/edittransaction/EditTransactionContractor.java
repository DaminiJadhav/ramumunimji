package com.posbilling.posbillingapplication.activity.edittransaction;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.TransactionRequest;
import com.posbilling.posbillingapplication.model.response.TransactionResponse;


public interface EditTransactionContractor {
    interface Presenter extends BasePresenter {
        void deleteTransaction(TransactionRequest transactionRequest);
    }

    interface View extends BaseView {

        void postDeleteSuccess(TransactionResponse body);

        void postDeleteFailure(String s);
    }
}
