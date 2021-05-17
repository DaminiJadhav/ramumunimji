package com.posbilling.posbillingapplication.activity.merge;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.MergeRequest;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.MergeApiResponse;

public interface MergeContractor {
    interface Presenter extends BasePresenter {
        void getCustomerList(String toString);

        void postmergeOperation(MergeRequest mergeRequest);
    }

    interface View extends BaseView {
        void customerListSuccess(CustomerListResponse body);
        void customerListFailure(String s);

        void mergeUserSuccess(MergeApiResponse body);

        void mergeUserFailure(String s);
    }
}
