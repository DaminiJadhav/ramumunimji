package com.posbilling.posbillingapplication.activity.cropdetail;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.response.ResponseCustomerCropDetails;

public interface CropDetailContractor {
    interface Presenter extends BasePresenter {
        void getCropDetailList(String clientId, String customerId);
    }

    interface View extends BaseView {
        void getCropListSuccess(ResponseCustomerCropDetails body);
        void getCropListFailure(String message, String status);
    }
}
