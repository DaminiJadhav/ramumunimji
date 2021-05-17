package com.posbilling.posbillingapplication.activity.cropregistration;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.RequestAddCrop;
import com.posbilling.posbillingapplication.model.response.ResponseAddCrop;
import com.posbilling.posbillingapplication.model.response.ResponseCropDropDown;

public interface CropRegistrationContractor {
    interface Presenter extends BasePresenter {
        void getCropDropDown(String regId);
        void postyCropDetails(RequestAddCrop requestAddCrop);
    }

    interface View extends BaseView {
        void getCropDropDownList(ResponseCropDropDown body);
        void getCropDropDownFailure(String s);

        void postCropFailure(String message);

        void postCropSuccess(ResponseAddCrop body);
    }
}
