package com.posbilling.posbillingapplication.activity.garagedcustomerdetail;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.VehicleReportPdfRequest;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.model.response.ResponseVehicleCustomerList;

public interface GarageDetailContractor {
    interface Presenter extends BasePresenter {

        void getVehclieDetails(String s, String customerId);

        void getPDFData(VehicleReportPdfRequest vehicleReportPdfRequest);
    }

    interface View extends BaseView {
        void vehicleCustomerListSuccess(ResponseVehicleCustomerList body);

        void vehicleCustomerListFailure(String s);

        void getPdfResponse(PdfResponse body);

        void getPdfFailure(String s);
    }
}
