package com.posbilling.posbillingapplication.fragment.fragmentsupplierreport;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.response.GetSupplierResponseTwo;

public interface SupplierSummuryContract {
    interface Presenter extends BasePresenter {
        void getSupplierList(String clientId);
    }

    interface View extends BaseView {
        void supplierListSuccess(GetSupplierResponseTwo body);
        void supplierListFailure(String message);
    }
}
