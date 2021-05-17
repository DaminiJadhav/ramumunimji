package com.posbilling.posbillingapplication.activity.editsupplier;

import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.lib.BaseView;
import com.posbilling.posbillingapplication.model.request.SupplierEditRequest;
import com.posbilling.posbillingapplication.model.response.GetSuppliersResponse;

public interface EditSupplierContractor {
    interface Presenter extends BasePresenter {
        void postAddSupplier(SupplierEditRequest addCustomerParams);

    }

    interface View extends BaseView {
        void addSupplierSuccess(GetSuppliersResponse body);
        void addSupplierFailure(String message);
    }
}
