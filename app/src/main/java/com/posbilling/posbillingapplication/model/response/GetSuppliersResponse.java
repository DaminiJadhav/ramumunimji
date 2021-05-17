package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSuppliersResponse {
    @SerializedName("status")
    String status;
    @SerializedName("regId")
    String regId;
    @SerializedName("Data")
    Data Data;

    public class Data {
        @SerializedName("tblSuppliers")
        ArrayList<tblSuppliers> tblSuppliersArrayList = new ArrayList<tblSuppliers>();

        public class tblSuppliers {

            @SerializedName("ID")
            String ID;
            @SerializedName("SupplierId")
            String SupplierId;
            @SerializedName("ClientId")
            String ClientId;
            @SerializedName("SFirstname")
            String SFirstname;
            @SerializedName("SMiddleName")
            String SMiddleName;
            @SerializedName("SLastname")
            String SLastname;
            @SerializedName("SContactNo")
            String SContactNo;
            @SerializedName("SAlternetMobNo")
            String SAlternetMobNo;
            @SerializedName("SEmail")
            String SEmail;
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
            @SerializedName("PaymentFreq")
            String PaymentFreq;
            @SerializedName("SMSFlag")
            String SMSFlag;
            @SerializedName("Imagename")
            String Imagename;
            @SerializedName("ImagePath")
            String ImagePath;
            @SerializedName("Outstanding")
            String Outstanding;
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
            @SerializedName("tblClientMaster2")
            String tblClientMaster2;

            @SerializedName("tblKharediInvices")
            ArrayList<tblKharediInvices> tblKharediInvicesArrayList = new ArrayList<tblKharediInvices>();

            public class tblKharediInvices {
                @SerializedName("ID")
                String ID;
                @SerializedName("ClientId")
                String ClientId;
                @SerializedName("SupplierId")
                String SupplierId;
                @SerializedName("CustomerId")
                String CustomerId;
                @SerializedName("TransactionId")
                String TransactionId;
                @SerializedName("BillDate")
                String BillDate;
                @SerializedName("Time")
                String Time;
                @SerializedName("Liter")
                String Liter;
                @SerializedName("DiscountAmt")
                String DiscountAmt;
                @SerializedName("CrAmt")
                String CrAmt;
                @SerializedName("DrAmt")
                String DrAmt;
                @SerializedName("Purpose")
                String Purpose;
                @SerializedName("DeviceId")
                String DeviceId;
                @SerializedName("PaymentStatus")
                String PaymentStatus;
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
                @SerializedName("tblSupplier")
                String tblSupplier;
            }


            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getSupplierId() {
                return SupplierId;
            }

            public void setSupplierId(String supplierId) {
                SupplierId = supplierId;
            }

            public String getClientId() {
                return ClientId;
            }

            public void setClientId(String clientId) {
                ClientId = clientId;
            }

            public String getSFirstname() {
                return SFirstname;
            }

            public void setSFirstname(String SFirstname) {
                this.SFirstname = SFirstname;
            }

            public String getSMiddleName() {
                return SMiddleName;
            }

            public void setSMiddleName(String SMiddleName) {
                this.SMiddleName = SMiddleName;
            }

            public String getSLastname() {
                return SLastname;
            }

            public void setSLastname(String SLastname) {
                this.SLastname = SLastname;
            }

            public String getSContactNo() {
                return SContactNo;
            }

            public void setSContactNo(String SContactNo) {
                this.SContactNo = SContactNo;
            }

            public String getSAlternetMobNo() {
                return SAlternetMobNo;
            }

            public void setSAlternetMobNo(String SAlternetMobNo) {
                this.SAlternetMobNo = SAlternetMobNo;
            }

            public String getSEmail() {
                return SEmail;
            }

            public void setSEmail(String SEmail) {
                this.SEmail = SEmail;
            }

            public String getVillage() {
                return Village;
            }

            public void setVillage(String village) {
                Village = village;
            }

            public String getTaluka() {
                return Taluka;
            }

            public void setTaluka(String taluka) {
                Taluka = taluka;
            }

            public String getDistrict() {
                return District;
            }

            public void setDistrict(String district) {
                District = district;
            }

            public String getStateId() {
                return StateId;
            }

            public void setStateId(String stateId) {
                StateId = stateId;
            }

            public String getCountry() {
                return Country;
            }

            public void setCountry(String country) {
                Country = country;
            }

            public String getPaymentFreq() {
                return PaymentFreq;
            }

            public void setPaymentFreq(String paymentFreq) {
                PaymentFreq = paymentFreq;
            }

            public String getSMSFlag() {
                return SMSFlag;
            }

            public void setSMSFlag(String SMSFlag) {
                this.SMSFlag = SMSFlag;
            }

            public String getImagename() {
                return Imagename;
            }

            public void setImagename(String imagename) {
                Imagename = imagename;
            }

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String imagePath) {
                ImagePath = imagePath;
            }

            public String getOutstanding() {
                return Outstanding;
            }

            public void setOutstanding(String outstanding) {
                Outstanding = outstanding;
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

            public String getTblClientMaster2() {
                return tblClientMaster2;
            }

            public void setTblClientMaster2(String tblClientMaster2) {
                this.tblClientMaster2 = tblClientMaster2;
            }
        }

        public ArrayList<tblSuppliers> getTblSuppliersArrayList() {
            return tblSuppliersArrayList;
        }

        public void setTblSuppliersArrayList(ArrayList<tblSuppliers> tblSuppliersArrayList) {
            this.tblSuppliersArrayList = tblSuppliersArrayList;
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

    public GetSuppliersResponse.Data getData() {
        return Data;
    }

    public void setData(GetSuppliersResponse.Data data) {
        Data = data;
    }
}




/*
// 20200720233529
// https://qa.ramumunimji.com/api/SupplierRegister/GettblSupplier?regId=18&SupplierId=

{
// 20200721200610
// https://qa.ramumunimji.com/api/SupplierRegister/GettblSupplier?regId=14&SupplierId=12

{
  "status": 1,
  "regId": 14,
  "Data": {
    "ID": 12,
    "SupplierId": "S141",
    "ClientId": 14,
    "SFirstname": "अंकुर",
    "SMiddleName": null,
    "SLastname": null,
    "SContactNo": null,
    "SAlternetMobNo": null,
    "SEmail": null,
    "Village": null,
    "Taluka": null,
    "District": null,
    "StateId": null,
    "Country": null,
    "PaymentFreq": null,
    "SMSFlag": null,
    "Imagename": null,
    "ImagePath": null,
    "Outstanding": -3000.00,
    "CreatedOn": null,
    "UpdatedOn": null,
    "CreatedBy": null,
    "UpdatedBy": null,
    "IsActive": true,
    "tblClientMaster2": null,
    "tblKharediInvices": [
      {
        "ID": 17,
        "ClientId": 14,
        "SupplierId": 12,
        "CustomerId": 0,
        "TransactionId": "R8",
        "BillDate": "2020-07-20T00:00:00",
        "Time": "22:25:00",
        "Liter": null,
        "DiscountAmt": null,
        "CrAmt": 3000.00,
        "DrAmt": 0.00,
        "Purpose": "टेस्ट",
        "DeviceId": null,
        "PaymentStatus": null,
        "CreatedOn": null,
        "UpdatedOn": null,
        "CreatedBy": null,
        "UpdatedBy": null,
        "IsActive": null,
        "tblSupplier": null
      }
    ]
  }
}
 */
