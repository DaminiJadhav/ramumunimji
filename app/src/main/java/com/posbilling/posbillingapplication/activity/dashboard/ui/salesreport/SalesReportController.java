package com.posbilling.posbillingapplication.activity.dashboard.ui.salesreport;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.SalesReportPdfRequest;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.model.response.TransactionListResponse;

public interface SalesReportController {
    interface Presenter extends BasePresenter {
        void getTransactionList(String regId , String customerID);

        void getPDFData(SalesReportPdfRequest salesReportPdfRequest);
    }

    interface View extends BaseView {
        void getTraansactionListSuccess(TransactionListResponse response);
        void getTraansactionListFailure(String message);

        void getPdfResponse(PdfResponse body);

        void getPdfFailure(String s);
    }
}
