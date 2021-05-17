package com.posbilling.posbillingapplication.activity.splash;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.ActviStatusResponse;

import java.util.Date;

public interface SplashContractor {
    interface Presenter extends BasePresenter {
        void getActiveStatus(String regId, String date, String time);
    }

    interface View extends BaseView {

        void getActviStatusFailure(String s);

        void getActviStatusSuccess(ActviStatusResponse body);
    }
}
