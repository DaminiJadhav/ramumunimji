package com.posbilling.posbillingapplication.activity.dashboard.ui.garage;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.response.ResponseVehicleCustomerList;

public interface VehicleSummuryContractor {
    interface Presenter extends BasePresenter {
        void getVehclieDetails(String clientID, String garageCustomerId);
    }

    interface View extends BaseView {
        void vehicleCustomerListFailure(String message);

        void vehicleCustomerListSuccess(ResponseVehicleCustomerList body);
    }
}
