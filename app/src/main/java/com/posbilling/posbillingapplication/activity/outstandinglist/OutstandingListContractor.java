package com.posbilling.posbillingapplication.activity.outstandinglist;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.PdfDetailOutstandingRequest;
import com.posbilling.posbillingapplication.model.response.LoginResponse;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.model.response.TransactionListResponse;

public interface OutstandingListContractor {
    interface Presenter extends BasePresenter {
        void getTransactionList(String regId , String customerID);

        void getPDFData(PdfDetailOutstandingRequest pdfDetailOutstandingRequest);
    }

    interface View extends BaseView {
        void getTraansactionListSuccess(TransactionListResponse response);
        void getTraansactionListFailure(String message);

        void getPdfResponse(PdfResponse body);

        void getPdfFailure(String s);
    }
}
