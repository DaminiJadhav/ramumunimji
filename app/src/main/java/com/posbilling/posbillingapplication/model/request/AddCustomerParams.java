package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class AddCustomerParams {
    @SerializedName("Essential")
    Essential essential;

     public class Essential {
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("DeviceId")
        String DeviceId;
        @SerializedName("SessionId")
        String SessionId;
        @SerializedName("MasterId")
        String MasterId;
        @SerializedName("LanguageId")
        String LanguageId;

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public void setDeviceId(String deviceId) {
            DeviceId = deviceId;
        }

        public void setSessionId(String sessionId) {
            SessionId = sessionId;
        }

        public void setMasterId(String masterId) {
            MasterId = masterId;
        }

        public void setLanguageId(String languageId) {
            LanguageId = languageId;
        }
    }

    @SerializedName("CustomerList")
    ArrayList<CustomerList> customerList = new ArrayList<CustomerList>();

    public class CustomerList {
        @SerializedName("ID")
        String ID;

        public void setID(String ID) {
            this.ID = ID;
        }

        @SerializedName("CustomerId")
        String CustomerId;
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("CFirstname")
        String CFirstname;
        @SerializedName("CMiddleName")
        String CMiddleName;
        @SerializedName("CLastname")
        String CLastname;
        @SerializedName("CContactNo")
        String CContactNo;
        @SerializedName("CAlternetMobNo")
        String CAlternetMobNo;
        @SerializedName("PaymentFreq")
        String PaymentFreq;
        @SerializedName("SMSFlag")
        Boolean SMSFlag;

        public void setSMSFlag(Boolean SMSFlag) {
            this.SMSFlag = SMSFlag;
        }

        @SerializedName("CEmail")
        String CEmail;
        @SerializedName("Village")
        String Village;
        @SerializedName("Taluka")
        String Taluka;
        @SerializedName("District")
        String District;
        @SerializedName("StateId")
        String StateId;
        @SerializedName("Country")
        String Country;
        @SerializedName("Imagename")
        String Imagename;
        @SerializedName("ImagePath")
        String ImagePath;
        @SerializedName("CreatedOn")
        Date CreatedOn;
        @SerializedName("UpdatedOn")
        String UpdatedOn;
        @SerializedName("CreatedBy")
        String CreatedBy;
        @SerializedName("UpdatedBy")
        String UpdatedBy;

        public void setCustomerId(String customerId) {
            CustomerId = customerId;
        }

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public void setCFirstname(String CFirstname) {
            this.CFirstname = CFirstname;
        }

        public void setCMiddleName(String CMiddleName) {
            this.CMiddleName = CMiddleName;
        }

        public void setCLastname(String CLastname) {
            this.CLastname = CLastname;
        }

        public void setCContactNo(String CContactNo) {
            this.CContactNo = CContactNo;
        }

        public void setCAlternetMobNo(String CAlternetMobNo) {
            this.CAlternetMobNo = CAlternetMobNo;
        }

        public void setPaymentFreq(String paymentFreq) {
            PaymentFreq = paymentFreq;
        }

        public void setCEmail(String CEmail) {
            this.CEmail = CEmail;
        }

        public void setVillage(String village) {
            Village = village;
        }

        public void setTaluka(String taluka) {
            Taluka = taluka;
        }

        public void setDistrict(String district) {
            District = district;
        }

        public void setStateId(String stateId) {
            StateId = stateId;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public void setImagename(String imagename) {
            Imagename = imagename;
        }

        public void setImagePath(String imagePath) {
            ImagePath = imagePath;
        }

        public void setCreatedOn(Date createdOn) {
            CreatedOn = createdOn;
        }

        public void setUpdatedOn(String updatedOn) {
            UpdatedOn = updatedOn;
        }

        public void setCreatedBy(String createdBy) {
            CreatedBy = createdBy;
        }

        public void setUpdatedBy(String updatedBy) {
            UpdatedBy = updatedBy;
        }
    }

    public void setEssential(Essential essential) {
        this.essential = essential;
    }

    public void setCustomerList(ArrayList<CustomerList> customerList) {
        this.customerList = customerList;
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