package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostPurchaseResponse {
    @SerializedName("status")
    String status;
    @SerializedName("Message")
    String Message;
    @SerializedName("successTranaactionId")
    String successTranaactionId;

    @SerializedName("Data")
    Data Data;

    public class Data {
        @SerializedName("Supplierlist")
        ArrayList<Supplierlist> supplierlistArrayList = new ArrayList<Supplierlist>();


        public class Supplierlist {
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

            public ArrayList<tblKharediInvices> getTblKharediInvicesArrayList() {
                return tblKharediInvicesArrayList;
            }

            public void setTblKharediInvicesArrayList(ArrayList<tblKharediInvices> tblKharediInvicesArrayList) {
                this.tblKharediInvicesArrayList = tblKharediInvicesArrayList;
            }
        }

        public ArrayList<Supplierlist> getSupplierlistArrayList() {
            return supplierlistArrayList;
        }

        public void setSupplierlistArrayList(ArrayList<Supplierlist> supplierlistArrayList) {
            this.supplierlistArrayList = supplierlistArrayList;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSuccessTranaactionId() {
        return successTranaactionId;
    }

    public void setSuccessTranaactionId(String successTranaactionId) {
        this.successTranaactionId = successTranaactionId;
    }

    public PostPurchaseResponse.Data getData() {
        return Data;
    }

    public void setData(PostPurchaseResponse.Data data) {
        Data = data;
    }
}

/*
{
    "status": 1,
    "Message": "successed",
    "successTranaactionId": "R11",
    "Data": {
        "kharediList": [
            {
                "ClientId": 18,
                "SuppilerId": "C188",
                "SFirstname": "testforedit",
                "SLastname": null,
                "Imagename": null,
                "ImagePath": null,
                "SContactNo": null,
                "ID": 9,
                "TransactionId": "R11",
                "BillDate": "2020-06-28T11:23:00",
                "Time": "11:23:00",
                "CrAmt": 2000.0,
                "DrAmt": 0.0,
                "Purpose": "usane ",
                "Liter": 30,
                "PaymentStatus": null
            },
            {
                "ClientId": 18,
                "SuppilerId": "C188",
                "SFirstname": "testforedit",
                "SLastname": null,
                "Imagename": null,
                "ImagePath": null,
                "SContactNo": null,
                "ID": 9,
                "TransactionId": "R9",
                "BillDate": "2020-06-28T00:00:00",
                "Time": "11:23:00",
                "CrAmt": 0.00,
                "DrAmt": 1000.00,
                "Purpose": "500 Rs dile baki 500 rahile ",
                "Liter": 30,
                "PaymentStatus": null
            },
            {
                "ClientId": 18,
                "SuppilerId": "C188",
                "SFirstname": "testforedit",
                "SLastname": null,
                "Imagename": null,
                "ImagePath": null,
                "SContactNo": null,
                "ID": 9,
                "TransactionId": "R10",
                "BillDate": "2020-06-28T00:00:00",
                "Time": "11:23:00",
                "CrAmt": 2000.00,
                "DrAmt": 0.00,
                "Purpose": "usane ",
                "Liter": 30,
                "PaymentStatus": null
            },
            {
                "ClientId": 18,
                "SuppilerId": "S182",
                "SFirstname": "Second Supplier",
                "SLastname": "",
                "Imagename": "",
                "ImagePath": null,
                "SContactNo": "8194655580",
                "ID": 2,
                "TransactionId": "R2",
                "BillDate": "2020-06-28T00:00:00",
                "Time": "11:23:00",
                "CrAmt": 5000.00,
                "DrAmt": 0.00,
                "Purpose": "Hollsale Grocery by goury",
                "Liter": 30,
                "PaymentStatus": null
            },
            {
                "ClientId": 18,
                "SuppilerId": "S185",
                "SFirstname": "Fourth Supplier",
                "SLastname": null,
                "Imagename": null,
                "ImagePath": null,
                "SContactNo": null,
                "ID": 6,
                "TransactionId": "R4",
                "BillDate": "2020-06-28T00:00:00",
                "Time": "11:23:00",
                "CrAmt": 0.00,
                "DrAmt": 300000.00,
                "Purpose": "Hollsale Grocery by goury",
                "Liter": 30,
                "PaymentStatus": null
            },
            {
                "ClientId": 18,
                "SuppilerId": "C186",
                "SFirstname": "Supplier Test",
                "SLastname": null,
                "Imagename": null,
                "ImagePath": null,
                "SContactNo": null,
                "ID": 7,
                "TransactionId": "R5",
                "BillDate": "2020-06-28T00:00:00",
                "Time": "11:23:00",
                "CrAmt": 0.00,
                "DrAmt": 4000.00,
                "Purpose": "Kharedi",
                "Liter": 30,
                "PaymentStatus": null
            },
            {
                "ClientId": 18,
                "SuppilerId": "C187",
                "SFirstname": "Testfinal",
                "SLastname": null,
                "Imagename": null,
                "ImagePath": null,
                "SContactNo": null,
                "ID": 8,
                "TransactionId": "R7",
                "BillDate": "2020-06-28T00:00:00",
                "Time": "11:23:00",
                "CrAmt": 0.00,
                "DrAmt": 1000.00,
                "Purpose": "1000 Rs udhary ",
                "Liter": 30,
                "PaymentStatus": null
            },
            {
                "ClientId": 18,
                "SuppilerId": "C187",
                "SFirstname": "Testfinal",
                "SLastname": null,
                "Imagename": null,
                "ImagePath": null,
                "SContactNo": null,
                "ID": 8,
                "TransactionId": "R8",
                "BillDate": "2020-06-28T00:00:00",
                "Time": "11:23:00",
                "CrAmt": 400.00,
                "DrAmt": 0.00,
                "Purpose": "500 Rs dile baki 500 rahile ",
                "Liter": 30,
                "PaymentStatus": null
            }
        ],
        "Supplierlist": [
            {
                "ID": 9,
                "SupplierId": "C188",
                "ClientId": 18,
                "SFirstname": "testforedit",
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
                "Outstanding": -3400.00,
                "CreatedOn": "2020-07-11T20:24:52.203",
                "UpdatedOn": "2020-07-20T16:48:09.1278187+05:30",
                "CreatedBy": "8055659418 ",
                "UpdatedBy": "8055659418 RamuMunimji.Models.tblKharediInvice",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 1,
                "SupplierId": "S181",
                "ClientId": 18,
                "SFirstname": "First Supplier",
                "SMiddleName": "",
                "SLastname": "",
                "SContactNo": "8055659418",
                "SAlternetMobNo": "8194655580",
                "SEmail": "",
                "Village": "vadgoan",
                "Taluka": "haweli",
                "District": "pune",
                "StateId": null,
                "Country": "India",
                "PaymentFreq": "monthly",
                "SMSFlag": null,
                "Imagename": "",
                "ImagePath": null,
                "Outstanding": null,
                "CreatedOn": "2020-07-10T19:26:46.14",
                "UpdatedOn": null,
                "CreatedBy": null,
                "UpdatedBy": null,
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 2,
                "SupplierId": "S182",
                "ClientId": 18,
                "SFirstname": "Second Supplier",
                "SMiddleName": "",
                "SLastname": "",
                "SContactNo": "8194655580",
                "SAlternetMobNo": "8194655580",
                "SEmail": "",
                "Village": "vadgoan",
                "Taluka": "haweli",
                "District": "pune",
                "StateId": null,
                "Country": "India",
                "PaymentFreq": "monthly",
                "SMSFlag": null,
                "Imagename": "",
                "ImagePath": null,
                "Outstanding": -205000.00,
                "CreatedOn": "2020-07-10T19:33:46.367",
                "UpdatedOn": "2020-07-11T11:35:58.077",
                "CreatedBy": "18",
                "UpdatedBy": "8055659418 RamuMunimji.Models.tblKharediInvice",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 4,
                "SupplierId": "S183",
                "ClientId": 18,
                "SFirstname": "Thered Supplier",
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
                "Outstanding": -100000.00,
                "CreatedOn": "2020-07-11T11:07:39.133",
                "UpdatedOn": null,
                "CreatedBy": "8055659418 ",
                "UpdatedBy": null,
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 5,
                "SupplierId": "S184",
                "ClientId": 18,
                "SFirstname": "Thered Supplier",
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
                "Outstanding": -100000.00,
                "CreatedOn": "2020-07-11T11:09:05.92",
                "UpdatedOn": null,
                "CreatedBy": "8055659418 ",
                "UpdatedBy": null,
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 6,
                "SupplierId": "S185",
                "ClientId": 18,
                "SFirstname": "Fourth Supplier",
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
                "Outstanding": 300000.00,
                "CreatedOn": "2020-07-11T11:29:06.297",
                "UpdatedOn": "2020-07-11T11:29:09.047",
                "CreatedBy": "8055659418 ",
                "UpdatedBy": "8055659418 RamuMunimji.Models.tblKharediInvice",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 7,
                "SupplierId": "C186",
                "ClientId": 18,
                "SFirstname": "Supplier Test",
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
                "Outstanding": 21000.00,
                "CreatedOn": "2020-07-11T18:55:16.383",
                "UpdatedOn": "2020-07-11T19:06:46.787",
                "CreatedBy": "8055659418 ",
                "UpdatedBy": "8055659418 RamuMunimji.Models.tblKharediInvice",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 8,
                "SupplierId": "C187",
                "ClientId": 18,
                "SFirstname": "Testfinal",
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
                "Outstanding": 100.00,
                "CreatedOn": "2020-07-11T19:13:35.093",
                "UpdatedOn": "2020-07-11T19:18:28.13",
                "CreatedBy": "8055659418 ",
                "UpdatedBy": "8055659418 RamuMunimji.Models.tblKharediInvice",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            }
        ]
    }
}
 */