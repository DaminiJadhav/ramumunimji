package com.posbilling.posbillingapplication.activity.garagedcustomerdetail;

import com.posbilling.posbillingapplication.model.request.VehicleReportPdfRequest;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.model.response.ResponseVehicleCustomerList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GarageDetailPresenter implements GarageDetailContractor.Presenter {

    private GarageDetailContractor.View mView;

    public GarageDetailPresenter(GarageDetailContractor.View mView) {
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

    @Override
    public void getPDFData(VehicleReportPdfRequest vehicleReportPdfRequest) {
        mView.getAPIComponent().getRetroService().getBaseUrl("VehicleReport/").getVehiclePdf(vehicleReportPdfRequest)
                .enqueue(new Callback<PdfResponse>() {
                    @Override
                    public void onResponse(Call<PdfResponse> call, Response<PdfResponse> response) {
                        if (response.isSuccessful()){
                            mView.getPdfResponse(response.body());
                        }else {
                            mView.getPdfFailure("Something went wrong.");
                        }
                    }

                    @Override
                    public void onFailure(Call<PdfResponse> call, Throwable t) {
                        mView.getPdfFailure("Something went wrong.");
                    }
                });
    }


}
