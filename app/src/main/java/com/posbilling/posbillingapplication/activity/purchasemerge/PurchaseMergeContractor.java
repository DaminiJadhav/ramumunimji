package com.posbilling.posbillingapplication.activity.purchasemerge;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.MergeRequest;
import com.posbilling.posbillingapplication.model.response.GetSupplierResponseTwo;
import com.posbilling.posbillingapplication.model.response.PurchaseApiResponse;

public interface PurchaseMergeContractor {
    interface Presenter extends BasePresenter {
        void getSupplierList(String toString);

        void postmergeOperation(MergeRequest mergeRequest);
    }

    interface View extends BaseView {
        void supplierListSuccess(GetSupplierResponseTwo body);
        void supplierListFailure(String s);

        void mergeUserSuccess(PurchaseApiResponse body);

        void mergeUserFailure(String s);
    }
}
