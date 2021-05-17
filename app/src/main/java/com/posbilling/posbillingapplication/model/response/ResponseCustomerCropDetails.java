package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseCustomerCropDetails {
    @SerializedName("status")
    String status;
    @SerializedName("regId")
    String regId;
    @SerializedName("Message")
    String Message;

    @SerializedName("Data")
    ArrayList<Data> arrayListData = new ArrayList<Data>();

    public class Data {
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("CustomerId")
        String CustomerId;
        @SerializedName("Id")
        String Id;
        @SerializedName("TransactionId")
        String TransactionId;
        @SerializedName("Cropname")
        String Cropname;
        @SerializedName("CropPeriod")
        String CropPeriod;
        @SerializedName("FertiliseTimesPerAcre")
        String FertiliseTimesPerAcre;
        @SerializedName("PerticidesTimesPerAcre")
        String PerticidesTimesPerAcre;
        @SerializedName("CreatedOn")
        String CreatedOn;
        @SerializedName("Acres")
        String Acres;

        public String getTransactionId() {
            return TransactionId;
        }

        public void setTransactionId(String transactionId) {
            TransactionId = transactionId;
        }

        public String getAcres() {
            return Acres;
        }

        public void setAcres(String acres) {
            Acres = acres;
        }

        public String getClientId() {
            return ClientId;
        }

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public String getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(String customerId) {
            CustomerId = customerId;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getCropname() {
            return Cropname;
        }

        public void setCropname(String cropname) {
            Cropname = cropname;
        }

        public String getCropPeriod() {
            return CropPeriod;
        }

        public void setCropPeriod(String cropPeriod) {
            CropPeriod = cropPeriod;
        }

        public String getFertiliseTimesPerAcre() {
            return FertiliseTimesPerAcre;
        }

        public void setFertiliseTimesPerAcre(String fertiliseTimesPerAcre) {
            FertiliseTimesPerAcre = fertiliseTimesPerAcre;
        }

        public String getPerticidesTimesPerAcre() {
            return PerticidesTimesPerAcre;
        }

        public void setPerticidesTimesPerAcre(String perticidesTimesPerAcre) {
            PerticidesTimesPerAcre = perticidesTimesPerAcre;
        }

        public String getCreatedOn() {
            return CreatedOn;
        }

        public void setCreatedOn(String createdOn) {
            CreatedOn = createdOn;
        }
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<Data> getArrayListData() {
        return arrayListData;
    }

    public void setArrayListData(ArrayList<Data> arrayListData) {
        this.arrayListData = arrayListData;
    }
}

/*
{
    "status": 1,
    "regId": 1026,
    "Message": "Crop Data",
    "Data": [
        {
            "ClientId": 1026,
            "CustomerId": 22,
            "Id": 1,
            "Cropname": "Tomato",
            "CropPeriod": "3 Months",
            "FertiliseTimesPerAcre": 3,
            "PerticidesTimesPerAcre": 40,
            "CreatedOn": "2020-03-12T11:03:17.327"
        },
        {
            "ClientId": 1026,
            "CustomerId": 1099,
            "Id": 1,
            "Cropname": "Tomato",
            "CropPeriod": "3 Month",
            "FertiliseTimesPerAcre": 3,
            "PerticidesTimesPerAcre": 40,
            "CreatedOn": "2020-03-12T11:03:17.327"
        },
        {
            "ClientId": 1026,
            "CustomerId": 1098,
            "Id": 2,
            "Cropname": "Lemon",
            "CropPeriod": "3 Month",
            "FertiliseTimesPerAcre": 4,
            "PerticidesTimesPerAcre": 100,
            "CreatedOn": "2020-03-12T11:03:17.327"
        },
        {
            "ClientId": 1026,
            "CustomerId": 1100,
            "Id": 3,
            "Cropname": "Sugarcanre",
            "CropPeriod": "2 Month",
            "FertiliseTimesPerAcre": 4,
            "PerticidesTimesPerAcre": 10,
            "CreatedOn": "2020-03-12T11:03:17.327"
        },
        {
            "ClientId": 1026,
            "CustomerId": 1101,
            "Id": 3,
            "Cropname": "Sugarcanre",
            "CropPeriod": "2 Month",
            "FertiliseTimesPerAcre": 4,
            "PerticidesTimesPerAcre": 10,
            "CreatedOn": "2020-03-12T11:03:17.327"
        }
    ]
}
 */
