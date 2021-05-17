package com.posbilling.posbillingapplication.retrofit;

import com.posbilling.posbillingapplication.model.ActviStatusResponse;
import com.posbilling.posbillingapplication.model.request.AddCustomerParams;
import com.posbilling.posbillingapplication.model.request.AddVehicleRequest;
import com.posbilling.posbillingapplication.model.request.ExpenseReportPdfRequest;
import com.posbilling.posbillingapplication.model.request.ExpensesRequest;
import com.posbilling.posbillingapplication.model.request.LoginParams;
import com.posbilling.posbillingapplication.model.request.MergeRequest;
import com.posbilling.posbillingapplication.model.request.PdfDetailOutstandingRequest;
import com.posbilling.posbillingapplication.model.request.PurchaseTransactionRequest;
import com.posbilling.posbillingapplication.model.request.RequestAddCrop;
import com.posbilling.posbillingapplication.model.request.SalesReportPdfRequest;
import com.posbilling.posbillingapplication.model.request.SupplierEditRequest;
import com.posbilling.posbillingapplication.model.request.TransactionRequest;
import com.posbilling.posbillingapplication.model.request.UpdateProfile;
import com.posbilling.posbillingapplication.model.request.VehicleReportPdfRequest;
import com.posbilling.posbillingapplication.model.response.AddCustomerResponse;
import com.posbilling.posbillingapplication.model.response.AddGarageResponse;
import com.posbilling.posbillingapplication.model.response.BuisnessIdListResponse;
import com.posbilling.posbillingapplication.model.response.ClientUpdateResponse;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.ExpenseResponse;
import com.posbilling.posbillingapplication.model.response.GetSupplierResponseTwo;
import com.posbilling.posbillingapplication.model.response.GetSuppliersOutstandingResponse;
import com.posbilling.posbillingapplication.model.response.GetSuppliersResponse;
import com.posbilling.posbillingapplication.model.response.GetVehicleTypeResponse;
import com.posbilling.posbillingapplication.model.response.LoginResponse;
import com.posbilling.posbillingapplication.model.response.MergeApiResponse;
import com.posbilling.posbillingapplication.model.response.OTPSuccessResponse;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.model.response.GetSupplierTransaction;
import com.posbilling.posbillingapplication.model.response.PostPurchaseResponse;
import com.posbilling.posbillingapplication.model.response.PurchaseApiResponse;
import com.posbilling.posbillingapplication.model.response.ResponseAddCrop;
import com.posbilling.posbillingapplication.model.response.ResponseCropDropDown;
import com.posbilling.posbillingapplication.model.response.ResponseCustomerCropDetails;
import com.posbilling.posbillingapplication.model.response.ResponseVehicleCustomerList;
import com.posbilling.posbillingapplication.model.response.TransactionListResponse;
import com.posbilling.posbillingapplication.model.response.TransactionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
/*
status : 0 failure
status : 1 successa
status : 2 not found
*/

    //https://ramu.sdaemon.com/api/Login/ClientLogin
    @POST("ClientLogin")
    Call<LoginResponse> getLogin(@Body LoginParams loginParams);

    //https://ramu.sdaemon.com/api/Login/ClientLogin
    @POST("ClientLogin")
    Call<OTPSuccessResponse> getOTP(@Body LoginParams loginParams);


    //https://ramu.sdaemon.com/api/CustomerRegister/PosttblCustomer
    @POST("PosttblCustomer")
    Call<AddCustomerResponse> postCustomer(@Body AddCustomerParams addCustomerParams);

    //http://localhost:52198/api/Garage/PosttblVehicleDetail
    @POST("PosttblVehicleDetail")
    Call<AddGarageResponse> postAddGarage(@Body AddVehicleRequest addVehicleRequest);


    //https://ramu.sdaemon.com/api/CustomerRegister/PuttblCustomer
    //https://ramumunimji.com/api/Customers/PosttblCustomer
    @POST("PosttblCustomer")
    Call<AddCustomerResponse> editCustomer(@Body AddCustomerParams addCustomerParams);

///    https://qa.ramumunimji.com/api/Supplier/PostSupplier
    @POST("PostSupplier")
    Call<GetSuppliersResponse> editSupplier(@Body SupplierEditRequest addSupplierParams);

    //https://ramu.sdaemon.com/api/ClientProfile/PosttblClientProfile
    @POST("PosttblClientProfile")
    Call<ClientUpdateResponse> postClientProfile(@Body UpdateProfile updateProfile);

    //http://devpos.sdaemon.com/api/ExpenseReport/Get
    @GET("Get")
    Call<BuisnessIdListResponse> getBuisnessIDList();

    //https://ramu.sdaemon.com/api/CustomerRegister/GettblCustomer?regId=11&CustomerId=
    @GET("GettblCustomer")
    Call<CustomerListResponse> getCustomerList(@Query("regId") String regId, @Query("CustomerId") String CustomerId);

    //https://qa.ramumunimji.com/api/SupplierRegister/GettblSupplier?regId=14&SupplierId=
    @GET("GettblSupplier")
    Call<GetSupplierResponseTwo> getSupplierList(@Query("regId") String regId, @Query("SupplierId") String SupplierId);

    //https://qa.ramumunimji.com/api/SupplierRegister/GettblSupplier?regId=14&SupplierId=12
    @GET("GettblSupplier")
    Call<GetSuppliersOutstandingResponse> getSupplierOutstandingList(@Query("regId") String regId, @Query("SupplierId") String SupplierId);

    //https://ramu.sdaemon.com/api/ClientExpences/GettblExpenditure?regId=13
    @GET("GettblExpenditure")
    Call<ExpenseResponse> getExpesenselist(@Query("regId") String regId);

    //https://ramu.sdaemon.com/api/Garage/GettblVehicleDetail?regId=1025&garageCustomerId=
    @GET("GettblVehicleDetail")
    Call<ResponseVehicleCustomerList> getVehicleCustomerList(@Query("regId") String regId, @Query("garageCustomerId") String garageCustomerId);

    //-https://ramu.sdaemon.com/api/CTransactionTouch/PostTransaction
    @POST("PostTransaction")
    Call<TransactionResponse> postTransaction(@Body TransactionRequest transactionRequest);

    //https://qa.ramumunimji.com/api/KharediTransaction/PosttblKharediTransaction
    @POST("PosttblKharediTransaction")
    Call<PostPurchaseResponse> postPurchase(@Body PurchaseTransactionRequest purchaseTransactionRequest);

    //http://localhost:52198/api/CropSuggestion/GettblPresitcidesAndFertilisersSuggestion?regId=2&AgroCustomerId=66
    @GET("GettblPresitcidesAndFertilisersSuggestion")
    Call<ResponseCustomerCropDetails> getCustomerCropList(@Query("regId") String regId, @Query("AgroCustomerId") String AgroCustomerId);

    //https://ramu.sdaemon.com/api/AgroAgency/GettblPresitcidesAndFertilisers
    @GET("GettblPresitcidesAndFertilisers")
    Call<ResponseCropDropDown> getCropDropDown(@Query("regId") String regId);

    //https://ramu.sdaemon.com/api/AgroAgency/PosttblPresitcidesAndFertiliser
    @POST("PosttblPresitcidesAndFertiliser")
    Call<ResponseAddCrop> postCrop(@Body RequestAddCrop requestAddCrop);

    //https://ramu.sdaemon.com/api/ActiveStatus/GettblActiveStatu?regId=1041&Date=2020-04-04&Time=11:11:02
    @GET("GettblActiveStatu")
    Call<ActviStatusResponse> getActiveStatus(@Query("regId") String regId, @Query("Date")String date, @Query("Time")String time);


    //https://ramu.sdaemon.com/api/CTransactionTouch/GetTransaction?regId=11&CustomerId=6
    @GET("GetTransaction")
    Call<TransactionListResponse> getTransaction(@Query("regId") String regId, @Query("CustomerId") String CustomerId);

    //http://localhost:52198/api/Reposrt/PostPDF
    @POST("PostPDF")
    Call<PdfResponse> getOustsatndingPdf(@Body PdfDetailOutstandingRequest pdfDetailOutstandingRequest);

    //http://localhost:52198/api/SalesReport/PostSalesReport
    @POST("PostSalesReport")
    Call<PdfResponse> getSalesPdf(@Body SalesReportPdfRequest salesReportPdfRequest);

    //http://localhost:52198/api/VehicleReport/PostVehicleReport
    @POST("PostVehicleReport")
    Call<PdfResponse> getVehiclePdf(@Body VehicleReportPdfRequest vehicleReportPdfRequest);


    ///https://qa.ramumunimji.com/api/ExpenseReport/PostExpenseReport
    @POST("PostExpenseReport")
    Call<PdfResponse> getExpensePdf(@Body ExpenseReportPdfRequest expenseReportPdfRequest);


    //https://ramu.sdaemon.com/api/ClientExpences/PosttblExpenditure
    @POST("PosttblExpenditure")
    Call<ExpenseResponse> postExpense(@Body ExpensesRequest expensesRequest);

    //https://ramu.sdaemon.com/api/CTransactionTouch/PutTransaction
    //https://ramumunimji.com/api/Invices/PosttblInvice
    @POST("PosttblInvice")
    Call<MergeApiResponse> postMergeOperation(@Body MergeRequest mergeRequest);

    //https://qa.ramumunimji.com/api/Supplier/PostSupplier
    @POST("PostSupplier")
    Call<PurchaseApiResponse> postPurchaseMergeOperation(@Body MergeRequest mergeRequest);

    //http://localhost:52198/api/Garage/GettblVehicleDetail
    @GET("GettblVehicleDetail")
    Call<GetVehicleTypeResponse> getVehicleModelData();
}
