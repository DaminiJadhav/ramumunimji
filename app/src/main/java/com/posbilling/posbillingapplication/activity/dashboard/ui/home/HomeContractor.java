package com.posbilling.posbillingapplication.activity.dashboard.ui.home;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.ExpensesRequest;
import com.posbilling.posbillingapplication.model.request.PurchaseTransactionRequest;
import com.posbilling.posbillingapplication.model.request.TransactionRequest;
import com.posbilling.posbillingapplication.model.response.ExpenseResponse;
import com.posbilling.posbillingapplication.model.response.PostPurchaseResponse;
import com.posbilling.posbillingapplication.model.response.TransactionResponse;

public interface HomeContractor {
    interface Presenter extends BasePresenter {
        void postTransaction(TransactionRequest transactionRequest, int i);
        void postExpense(ExpensesRequest expensesRequest, int i);

        void postPurchaseData(PurchaseTransactionRequest purchaseTransactionRequest);
    }

    interface View extends BaseView {
        void postResponseSuccess(TransactionResponse body, int valueofFlowOnlineOfline);
        void postResponseFailure(String message);
        void postExpenseSuccess(ExpenseResponse body, int valueofFlowOnlineOfline);

        void postPurchaseResponseSuccess(PostPurchaseResponse body);
        void postResponseFailuretimeout();
    }
}
