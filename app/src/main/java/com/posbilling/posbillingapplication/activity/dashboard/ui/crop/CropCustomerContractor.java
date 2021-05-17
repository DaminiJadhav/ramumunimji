package com.posbilling.posbillingapplication.activity.dashboard.ui.crop;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;

public interface CropCustomerContractor {
    interface Presenter extends BasePresenter {
        void getCustomerList(String clientId);

    }

    interface View extends BaseView {

        void customerListSuccess(CustomerListResponse body);
        void customerListFailure(String message);
    }
}
