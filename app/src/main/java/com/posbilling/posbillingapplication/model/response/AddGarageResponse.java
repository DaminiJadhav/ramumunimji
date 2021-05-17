package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddGarageResponse {
    @SerializedName("status")
    String status;
    @SerializedName("regId")
    String regId;
    @SerializedName("Message")
    String Message;


    ArrayList<Data> dataArrayList = new ArrayList<Data>();

    public class Data {
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("ID")
        String ID;
        @SerializedName("CustomerId")
        String CustomerId;
        @SerializedName("CFirstname")
        String CFirstname;
        @SerializedName("CLastname")
        String CLastname;
        @SerializedName("ImagePath")
        String ImagePath;
        @SerializedName("GarageId")
        String GarageId;
        @SerializedName("TransactionId")
        String TransactionId;
        @SerializedName("VehicleNo")
        String VehicleNo;
        @SerializedName("VehicleType")
        String VehicleType;
        @SerializedName("VehicleModel")
        String VehicleModel;
        @SerializedName("ServieDate")
        String ServieDate;
        @SerializedName("DueDate")
        SerializedName DueDate;
        @SerializedName("CurrentKM")
        String CurrentKM;
        @SerializedName("NewServiceDate")
        String NewServiceDate;
        @SerializedName("Comment")
        String Comment;
        @SerializedName("CreatedOn")
        String CreatedOn;
        @SerializedName("UpdatedOn")
        String UpdatedOn;
        @SerializedName("CreatedBy")
        String CreatedBy;
        @SerializedName("UpdatedBy")
        String UpdatedBy;

        public String getClientId() {
            return ClientId;
        }

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(String customerId) {
            CustomerId = customerId;
        }

        public String getCFirstname() {
            return CFirstname;
        }

        public void setCFirstname(String CFirstname) {
            this.CFirstname = CFirstname;
        }

        public String getCLastname() {
            return CLastname;
        }

        public void setCLastname(String CLastname) {
            this.CLastname = CLastname;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String imagePath) {
            ImagePath = imagePath;
        }

        public String getGarageId() {
            return GarageId;
        }

        public void setGarageId(String garageId) {
            GarageId = garageId;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public void setTransactionId(String transactionId) {
            TransactionId = transactionId;
        }

        public String getVehicleNo() {
            return VehicleNo;
        }

        public void setVehicleNo(String vehicleNo) {
            VehicleNo = vehicleNo;
        }

        public String getVehicleType() {
            return VehicleType;
        }

        public void setVehicleType(String vehicleType) {
            VehicleType = vehicleType;
        }

        public String getVehicleModel() {
            return VehicleModel;
        }

        public void setVehicleModel(String vehicleModel) {
            VehicleModel = vehicleModel;
        }

        public String getServieDate() {
            return ServieDate;
        }

        public void setServieDate(String servieDate) {
            ServieDate = servieDate;
        }

        public SerializedName getDueDate() {
            return DueDate;
        }

        public void setDueDate(SerializedName dueDate) {
            DueDate = dueDate;
        }

        public String getCurrentKM() {
            return CurrentKM;
        }

        public void setCurrentKM(String currentKM) {
            CurrentKM = currentKM;
        }

        public String getNewServiceDate() {
            return NewServiceDate;
        }

        public void setNewServiceDate(String newServiceDate) {
            NewServiceDate = newServiceDate;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
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

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }
}

/*
{
  "status": 1,
  "regId": 54,
  "Data": [
    {
      "ClientId": 54,
      "ID": 112,
      "CustomerId": "C541",
      "CFirstname": "Ankur",
      "CLastname": null,
      "ImagePath": null,
      "GarageId": 7,
      "TransactionId": null,
      "VehicleNo": "949794",
      "VehicleType": "bababa",
      "VehicleModel": "bababa",
      "ServieDate": "2020-05-13T14:15:00",
      "DueDate": "2020-05-13T14:15:00",
      "CurrentKM": "979794",
      "NewServiceDate": null,
      "Comment": "bsbababab",
      "CreatedOn": "2020-05-13T14:15:00",
      "UpdatedOn": "2020-05-13T14:15:00",
      "CreatedBy": "54",
      "UpdatedBy": "54"
    }
  ],
  "Message": "record saved"
}
 */