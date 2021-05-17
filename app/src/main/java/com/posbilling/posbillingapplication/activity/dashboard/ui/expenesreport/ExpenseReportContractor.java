package com.posbilling.posbillingapplication.activity.dashboard.ui.expenesreport;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.ExpenseReportPdfRequest;
import com.posbilling.posbillingapplication.model.response.ExpenseResponse;
import com.posbilling.posbillingapplication.model.response.PdfResponse;

public interface ExpenseReportContractor {
    interface Presenter extends BasePresenter {
        void getExpnsesList(String clientId);

        void getPDFData(ExpenseReportPdfRequest expenseReportPdfRequest);
    }

    interface View extends BaseView {

        void expenseListSuccess(ExpenseResponse body);
        void expenseListFailure(String message);

        void getPdfResponse(PdfResponse body);

        void getPdfFailure(String s);
    }
}
