package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SupplierEditRequest {
    @SerializedName("regId")
    String regId;
    @SerializedName("LanguageId")
    String LanguageId;

    @SerializedName("SupplierList")
    ArrayList<SupplierList> supplierListArrayList = new ArrayList<SupplierList>();

    public class SupplierList{
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
    }


    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getLanguageId() {
        return LanguageId;
    }

    public void setLanguageId(String languageId) {
        LanguageId = languageId;
    }

    public ArrayList<SupplierList> getSupplierListArrayList() {
        return supplierListArrayList;
    }

    public void setSupplierListArrayList(ArrayList<SupplierList> supplierListArrayList) {
        this.supplierListArrayList = supplierListArrayList;
    }
}

/*
{
        "regId":"18",
                "LanguageId":"0",
                "SupplierList":[{
                 "ID": 2,
        "SupplierId": "C182",
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
        "Outstanding": null,
        "CreatedOn": null,
        "UpdatedOn": null,
        "CreatedBy": null,
        "UpdatedBy": null,
        "IsActive": true

                }]
}
 */
