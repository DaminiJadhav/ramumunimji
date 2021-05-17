package com.posbilling.posbillingapplication.activity.addgarage;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.AddVehicleRequest;
import com.posbilling.posbillingapplication.model.response.AddGarageResponse;
import com.posbilling.posbillingapplication.model.response.GetVehicleTypeResponse;


public interface AddGarageContractor {
    interface Presenter extends BasePresenter {
        void postGaragedata(AddVehicleRequest addVehicleRequest);

        void getVehicleModelData();
    }

    interface View extends BaseView {

        void addGarageFailure(String response_fails);

        void addGarageSuccess(AddGarageResponse body);

        void getVehicleSuccess(GetVehicleTypeResponse body);

        void getVehicleFailure(String something_went_wrong);
    }
}
