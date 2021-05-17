package com.posbilling.posbillingapplication.model.request;

/**
 * Created by Android PC (Ankur) on 02,March,2020
 */
public class LoginParams {
    String Number;
    String OTP;
    String LanguageId;
    String ClientId;

    public void setNumber(String number) {
        Number = number;
    }
    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public void setLanguageId(String languageId) {
        LanguageId = languageId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }
}


/*
{
          "Number":"8055659418",
      "LanguageId":"1",
      "ClientId":"11",
      "OTP":"8153"
}*/


/*
{
          "Number":"9284112401",
      "LanguageId":"1",
      "ClientId":"",
      "OTP":""
}


{
          "Number":"9284112401",
      "LanguageId":"1",
      "ClientId":"13",
      "OTP":"1597"
}
 */