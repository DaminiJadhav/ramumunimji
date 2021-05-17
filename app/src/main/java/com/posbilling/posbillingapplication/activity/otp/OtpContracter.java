package com.posbilling.posbillingapplication.activity.otp;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.response.OTPSuccessResponse;

/**
 * Created by Android PC (Ankur) on 04,March,2020
 */
public interface OtpContracter {

    interface Presenter extends BasePresenter {
        void postOTP(String androidDeviceId, String mNumber, String OTP, String language, String mClientId, int i);

    }

    interface View extends BaseView {
        void OtpSuccess(OTPSuccessResponse response, int flow);
        void OtpFailure(String message);
        void OtpInvalid(String message);
    }

}
