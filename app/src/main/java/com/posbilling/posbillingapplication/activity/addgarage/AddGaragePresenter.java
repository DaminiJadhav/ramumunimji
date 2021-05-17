package com.posbilling.posbillingapplication.activity.addgarage;

import com.posbilling.posbillingapplication.model.request.AddVehicleRequest;
import com.posbilling.posbillingapplication.model.response.AddCustomerResponse;
import com.posbilling.posbillingapplication.model.response.AddGarageResponse;
import com.posbilling.posbillingapplication.model.response.GetVehicleTypeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.posbilling.posbillingapplication.utility.Constants.RESPONSESUCCESS;

public class AddGaragePresenter implements AddGarageContractor.Presenter{
    private AddGarageContractor.View mView;

    public AddGaragePresenter(AddGarageContractor.View mView) {
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
    public void postGaragedata(AddVehicleRequest addVehicleRequest) {
        //    //http://localhost:52198/api/Garage/PosttblVehicleDetail
        mView.getAPIComponent().getRetroService().getBaseUrl("Garage/").postAddGarage(addVehicleRequest).enqueue(new Callback<AddGarageResponse>() {
            @Override
            public void onResponse(Call<AddGarageResponse> call, Response<AddGarageResponse> response) {
                if (response.isSuccessful()){
                    if(response !=null && response.body().getStatus().matches(RESPONSESUCCESS)){
                        mView.addGarageSuccess(response.body());
                    }else{
                        mView.addGarageFailure(response.body().getMessage());
                    }
                }else {
                    mView.addGarageFailure("response fails");
                }
            }

            @Override
            public void onFailure(Call<AddGarageResponse> call, Throwable t) {
              //  mView.addCustomerFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getVehicleModelData() {
        mView.getAPIComponent().getRetroService().getBaseUrl("Garage/").getVehicleModelData().enqueue(new Callback<GetVehicleTypeResponse>() {
            @Override
            public void onResponse(Call<GetVehicleTypeResponse> call, Response<GetVehicleTypeResponse> response) {
                if (response.isSuccessful()){
                    if(response !=null && response.body().getStatus().matches(RESPONSESUCCESS)){
                        mView.getVehicleSuccess(response.body());
                    }else{
                        mView.getVehicleFailure("Something went wrong");
                    }
                }else {
                    mView.getVehicleFailure("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<GetVehicleTypeResponse> call, Throwable t) {
                //  mView.addCustomerFailure(t.getMessage());
                mView.getVehicleFailure(t.getMessage());
            }
        });
    }
}
