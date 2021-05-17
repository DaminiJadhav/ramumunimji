package com.posbilling.posbillingapplication.activity.login;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.response.LoginResponse;

/**
 * Created by Android PC (Ankur) on 28,February,2020
 */
public interface LoginContracter {
    interface Presenter extends BasePresenter {
        void postLogin(String androidDeviceId, String mNumber,String language);
    }

    interface View extends BaseView {
        void loginSuccess(LoginResponse loginResponse);
        void loginFailure(String message, String status);
    }
}
