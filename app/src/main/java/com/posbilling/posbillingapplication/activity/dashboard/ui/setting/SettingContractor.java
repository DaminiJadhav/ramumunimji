package com.posbilling.posbillingapplication.activity.dashboard.ui.setting;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.UpdateProfile;
import com.posbilling.posbillingapplication.model.response.BuisnessIdListResponse;
import com.posbilling.posbillingapplication.model.response.ClientUpdateResponse;
import com.posbilling.posbillingapplication.model.response.TransactionResponse;

public interface SettingContractor {
    interface Presenter extends BasePresenter {
        void postProfileData(UpdateProfile updateProfile);
        void getBuisnessIDList();
    }

    interface View extends BaseView {

        void profileUpdateResponse(ClientUpdateResponse body);

        void profileUpdateFailure(String message);

        void buisnessIDListSuccess(BuisnessIdListResponse body);
    }
}
