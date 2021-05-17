package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseAddCrop {
    @SerializedName("status")
    String status;
    @SerializedName("regId")
    String regId;
    @SerializedName("Data")
    Data data;
    @SerializedName("Message")
    String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public class Data {
        @SerializedName("ID")
        String ID;
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("TransactionId")
        String TransactionId;
        @SerializedName("CustomerId")
        String CustomerId;
        @SerializedName("CropId")
        String CropId;
        @SerializedName("Acres")
        String Acres;
        @SerializedName("CropPeriod")
        String CropPeriod;
        @SerializedName("CreatedOn")
        String CreatedOn;
        @SerializedName("UpdatedOn")
        String UpdatedOn;
        @SerializedName("CreatedBy")
        String CreatedBy;
        @SerializedName("UpdatedBy")
        String UpdatedBy;
        @SerializedName("IsActive")
        String IsActive;
        @SerializedName("tblClientMaster")
        String tblClientMaster;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getClientId() {
            return ClientId;
        }

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public void setTransactionId(String transactionId) {
            TransactionId = transactionId;
        }

        public String getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(String customerId) {
            CustomerId = customerId;
        }

        public String getCropId() {
            return CropId;
        }

        public void setCropId(String cropId) {
            CropId = cropId;
        }

        public String getAcres() {
            return Acres;
        }

        public void setAcres(String acres) {
            Acres = acres;
        }

        public String getCropPeriod() {
            return CropPeriod;
        }

        public void setCropPeriod(String cropPeriod) {
            CropPeriod = cropPeriod;
        }

        public String getCreatedOn() {
            return CreatedOn;
        }

        public void setCreatedOn(String createdOn) {
            CreatedOn = createdOn;
        }

        public String getUpdatedOn() {
            return UpdatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            UpdatedOn = updatedOn;
        }

        public String getCreatedBy() {
            return CreatedBy;
        }

        public void setCreatedBy(String createdBy) {
            CreatedBy = createdBy;
        }

        public String getUpdatedBy() {
            return UpdatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            UpdatedBy = updatedBy;
        }

        public String getIsActive() {
            return IsActive;
        }

        public void setIsActive(String isActive) {
            IsActive = isActive;
        }

        public String getTblClientMaster() {
            return tblClientMaster;
        }

        public void setTblClientMaster(String tblClientMaster) {
            this.tblClientMaster = tblClientMaster;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}


/*
{
    "status": 1,
    "regId": 2,
    "Data": {
        "ID": 7,
        "ClientId": 2,
        "TransactionId": null,
        "CustomerId": 66,
        "CropId": 1,
        "Acres": "6",
        "CropPeriod": "2 Month",
        "CreatedOn": "2020-03-24T12:26:30.477",
        "UpdatedOn": null,
        "CreatedBy": "dimpal",
        "UpdatedBy": "",
        "IsActive": true,
        "tblClientMaster": null
    },
    "Message": "record saved"
}
 */