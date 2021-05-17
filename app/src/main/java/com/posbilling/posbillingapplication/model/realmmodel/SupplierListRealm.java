package com.posbilling.posbillingapplication.model.realmmodel;

import io.realm.RealmObject;

public class SupplierListRealm extends RealmObject implements Comparable{

    String ID;
    String SupplierId;
    String ClientId;
    String SFirstname;
    String SMiddleName;
    String SLastname;
    String SContactNo;
    String SAlternetMobNo;
    String SEmail;
    String Village;
    String Taluka;
    String District;
    String StateId;
    String Country;
    String PaymentFreq;
    String SMSFlag;
    String Imagename;
    String ImagePath;
    String Outstanding;
    String CreatedOn;
    String UpdatedOn;
    String CreatedBy;
    String UpdatedBy;
    String IsActive;
    String tblClientMaster2;


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

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
