package com.posbilling.posbillingapplication.activity.dashboard.ui.addcustomer;

import com.posbilling.posbillingapplication.lib.APIComponent;
import com.posbilling.posbillingapplication.model.request.AddCustomerParams;
import com.posbilling.posbillingapplication.model.response.AddCustomerResponse;
import com.posbilling.posbillingapplication.model.response.LoginResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.retrofit.RetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;

public class AddCustomerPresenter implements AddCustomerContractor.Presenter {
    private AddCustomerContractor.View mView;

    public AddCustomerPresenter(AddCustomerContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void start() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }


    @Override
    public void postAddCustomer(AddCustomerParams addCustomerParams) {
        /*APIComponent apiComponent = mView.getAPIComponent();
        CustomRetroRequest customRetroRequest = apiComponent.getRetroService();

        RetrofitAPI retrofitAPI = customRetroRequest.getBaseUrl("CustomerRegister/");*/
        mView.getAPIComponent().getRetroService().getBaseUrl("CustomerRegister/").postCustomer(addCustomerParams).enqueue(new Callback<AddCustomerResponse>() {
            @Override
            public void onResponse(Call<AddCustomerResponse> call, Response<AddCustomerResponse> response) {
                if (response.isSuccessful()){
                    if(response !=null && response.body().getStatus().matches(RESPONSESUCCESS)){
                        mView.addCustomerSuccess(response.body());
                    }else{
                        mView.addCustomerFailure(response.body().getMessage());
                    }
                }else {
                    mView.addCustomerFailure("response fails");
                }
            }

            @Override
            public void onFailure(Call<AddCustomerResponse> call, Throwable t) {
                mView.addCustomerFailure(t.getMessage());
            }
        });
    }
}


/*
{

"Essential":{
                 "ClientId":"11",
                 "DeviceId":"",
         "SessionId":"",
         "MasterId":"",
         "LanguageId":1
},

        "CustomerList":[
                {

        "CustomerId":"A1",
        "ClientId":"19",
        "CFirstname":"sukhi",
        "CMiddleName":"Nai",
        "CLastname":"Naik",
        "CContactNo":"7276858467",
        "CAlternetMobNo":"7276858467",
        "PaymentFreq":"Monthly",
        "CEmail":"",
        "Village":"Ravet",
        "Taluka":"Baner",
        "District":"pune",
        "StateId":"14",
        "Country":"India",
        "Imagename":"pic.jpg",
        "ImagePath":"",
        "CreatedOn":"",
        "UpdatedOn":"",
        "CreatedBy":"",
        "UpdatedBy":""
                },
                {

                "CustomerId":"A2",
        "ClientId":"19",
        "CFirstname":"prasanan",
        "CMiddleName":"Nat",
        "CLastname":"Raskar",
        "CContactNo":"7276858467",
        "CAlternetMobNo":"7276858467",
        "PaymentFreq":"Monthly",
        "CEmail":"",
        "Village":"Ravet",
        "Taluka":"Baner",
        "District":"pune",
        "StateId":"14",
        "Country":"India",
        "Imagename":"pic.jpg",
        "ImagePath":"",
        "CreatedOn":"",
        "UpdatedOn":"",
        "CreatedBy":"",
        "UpdatedBy":""

                }
        ]

}
 */