package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

public class ResponseVehicleCustomerList {
    @SerializedName("status")
    String status;
    @SerializedName("regId")
    String regId;

    @SerializedName("Data")
    ArrayList<Data> dataArrayList = new ArrayList<Data>();

    public class Data {
        @SerializedName("ID")
        String ID;
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("CustomerId")
        String CustomerId;
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
        String DueDate;
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
        @SerializedName("IsActive")
        String IsActive;
        @SerializedName("tblClientMaster")
        String tblClientMaster;
        @SerializedName("ImagePath")
        String ImagePath;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Objects.equals(CustomerId, data.CustomerId) &&
                    Objects.equals(VehicleNo, data.VehicleNo) &&
                    Objects.equals(VehicleType, data.VehicleType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(CustomerId, VehicleNo, VehicleType);
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String imagePath) {
            ImagePath = imagePath;
        }

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

        public String getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(String customerId) {
            CustomerId = customerId;
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

        public String getDueDate() {
            return DueDate;
        }

        public void setDueDate(String dueDate) {
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
    "regId": 1025,
    "Data": [
        {
            "ID": 9,
            "ClientId": 1025,
            "CustomerId": 62,
            "TransactionId": null,
            "VehicleNo": "MH13AC6020",
            "VehicleType": "2Weeler",
            "VehicleModel": "honda",
            "ServieDate": "2020-03-28T14:23:27.207",
            "DueDate": "2020-03-28T14:23:27.207",
            "CurrentKM": "123123km",
            "NewServiceDate": "2020-04-28T14:23:27.207",
            "Comment": "clean",
            "CreatedOn": "2020-04-28T14:23:27.207",
            "UpdatedOn": null,
            "CreatedBy": "1025",
            "UpdatedBy": "",
            "IsActive": null,
            "tblClientMaster": null
        },
        {
            "ID": 10,
            "ClientId": 1025,
            "CustomerId": 62,
            "TransactionId": null,
            "VehicleNo": "MH13AC6020",
            "VehicleType": "2Weeler",
            "VehicleModel": "honda",
            "ServieDate": "2020-03-28T14:23:27.207",
            "DueDate": "2020-03-28T14:23:27.207",
            "CurrentKM": "123123km",
            "NewServiceDate": "2020-04-28T14:23:27.207",
            "Comment": "clean",
            "CreatedOn": "2020-04-28T14:23:27.207",
            "UpdatedOn": null,
            "CreatedBy": "1025",
            "UpdatedBy": "",
            "IsActive": null,
            "tblClientMaster": null
        },
        {
            "ID": 11,
            "ClientId": 1025,
            "CustomerId": 62,
            "TransactionId": null,
            "VehicleNo": "MH13BC4040",
            "VehicleType": "4weeler",
            "VehicleModel": "Maruti",
            "ServieDate": "2020-03-28T14:23:27.207",
            "DueDate": "2020-03-28T14:23:27.207",
            "CurrentKM": "123123km",
            "NewServiceDate": "2020-04-28T14:23:27.207",
            "Comment": "clean",
            "CreatedOn": "2020-04-28T14:23:27.207",
            "UpdatedOn": null,
            "CreatedBy": "1025",
            "UpdatedBy": "",
            "IsActive": null,
            "tblClientMaster": null
        },
        {
            "ID": 12,
            "ClientId": 1025,
            "CustomerId": 62,
            "TransactionId": null,
            "VehicleNo": "MH13AC6020",
            "VehicleType": "2Weeler",
            "VehicleModel": "honda",
            "ServieDate": "2020-03-28T14:23:27.207",
            "DueDate": "2020-03-28T14:23:27.207",
            "CurrentKM": "123123km",
            "NewServiceDate": "2020-04-28T14:23:27.207",
            "Comment": "clean",
            "CreatedOn": "2020-04-28T14:23:27.207",
            "UpdatedOn": null,
            "CreatedBy": "1025",
            "UpdatedBy": "",
            "IsActive": null,
            "tblClientMaster": null
        },
        {
            "ID": 13,
            "ClientId": 1025,
            "CustomerId": 62,
            "TransactionId": null,
            "VehicleNo": "MH13BC4040",
            "VehicleType": "4weeler",
            "VehicleModel": "Maruti",
            "ServieDate": "2020-03-28T14:23:27.207",
            "DueDate": "2020-03-28T14:23:27.207",
            "CurrentKM": "123123km",
            "NewServiceDate": "2020-04-28T14:23:27.207",
            "Comment": "clean",
            "CreatedOn": "2020-04-28T14:23:27.207",
            "UpdatedOn": null,
            "CreatedBy": "1025",
            "UpdatedBy": "",
            "IsActive": null,
            "tblClientMaster": null
        },
        {
            "ID": 14,
            "ClientId": 1025,
            "CustomerId": 1092,
            "TransactionId": null,
            "VehicleNo": "MH13AC4242",
            "VehicleType": "2Weeler",
            "VehicleModel": "FZ",
            "ServieDate": "2020-03-28T14:23:27.207",
            "DueDate": "2020-03-28T14:23:27.207",
            "CurrentKM": "123123km",
            "NewServiceDate": "2020-04-28T14:23:27.207",
            "Comment": "clean",
            "CreatedOn": "2020-04-28T14:23:27.207",
            "UpdatedOn": null,
            "CreatedBy": "1025",
            "UpdatedBy": "",
            "IsActive": null,
            "tblClientMaster": null
        }
    ]
} */