package com.posbilling.posbillingapplication.activity.dashboard.ui.garage;

import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.ResponseVehicleCustomerList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleSummuryPresenter implements VehicleSummuryContractor.Presenter {
    private VehicleSummuryContractor.View mView;

    public VehicleSummuryPresenter(VehicleSummuryContractor.View mView) {
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
    public void getVehclieDetails(String clientID, String garageCustomerId) {
        mView.getAPIComponent().getRetroService().getBaseUrl("Garage/").getVehicleCustomerList(clientID, garageCustomerId).enqueue(new Callback<ResponseVehicleCustomerList>() {
            @Override
            public void onResponse(Call<ResponseVehicleCustomerList> call, Response<ResponseVehicleCustomerList> response) {
                if (response.isSuccessful()) {
                    mView.vehicleCustomerListSuccess(response.body());
                } else {
                    mView.vehicleCustomerListFailure("Something went wrong...Please try again");
                }
            }

            @Override
            public void onFailure(Call<ResponseVehicleCustomerList> call, Throwable t) {
                mView.vehicleCustomerListFailure(t.getMessage());
            }
        });
    }
}
