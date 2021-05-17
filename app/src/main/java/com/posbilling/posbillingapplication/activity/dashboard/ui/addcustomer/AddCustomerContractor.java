package com.posbilling.posbillingapplication.activity.dashboard.ui.addcustomer;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.AddCustomerParams;
import com.posbilling.posbillingapplication.model.response.AddCustomerResponse;

public interface AddCustomerContractor {
    interface Presenter extends BasePresenter {
        void postAddCustomer(AddCustomerParams addCustomerParams);

    }

    interface View extends BaseView {
        void addCustomerSuccess(AddCustomerResponse body);
        void addCustomerFailure(String message);
    }
}
