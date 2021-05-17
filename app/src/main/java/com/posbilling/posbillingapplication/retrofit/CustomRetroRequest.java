package com.posbilling.posbillingapplication.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomRetroRequest {
    //public final String baseUrl = "https://ramu.sdaemon.com/api/";    ///test client
    //  public final String baseUrl = "http://devpos.sdaemon.com//api/"; ///test developer
 //   public final String baseUrl = "https://ramumunimji.com//api/";  /// client server
    public final String baseUrl = "https://qa.ramumunimji.com//api/";   ////QA server


    //     public final String imageURL = "https://ramu.sdaemon.com/";   ////test client
//     public final String imageURL = "https://devpos.sdaemon.com/"; ///test developer
//    public final String imageURL = "https://ramumunimji.com/"; ///client server
    public final String imageURL = "https://qa.ramumunimji.com/"; ///QA server

 //   public final String pdfURL = "https://ramumunimji.com//"; ///client server
    public final String pdfURL = "https://qa.ramumunimji.com/"; ///QA server



    private static CustomRetroRequest customRetroRequest = null;
    public Retrofit retrofit = null;
    //Login/ Ankur

    /**
     * @return Instance of CustomRetroRequest class
     */
    public static CustomRetroRequest getInstance() {
        return (customRetroRequest == null) ? customRetroRequest = new CustomRetroRequest() : customRetroRequest;
    }


    /**
     * @return Instance of RetrofitAPI class
     */
    public RetrofitAPI getBaseUrl(String controller) {
        retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl + controller)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
        return retrofit.create(RetrofitAPI.class);
    }

    /**
     * @return Instance of OkHttpClient class with modified timeout
     */
    private OkHttpClient getClient() {
        long HTTP_TIMEOUT = 120;
        final OkHttpClient.Builder okHttpClientBuilder = new
                OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        return okHttpClientBuilder.build();
    }
}
