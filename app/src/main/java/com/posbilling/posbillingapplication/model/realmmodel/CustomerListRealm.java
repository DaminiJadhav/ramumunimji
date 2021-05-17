package com.posbilling.posbillingapplication.model.realmmodel;

import android.text.TextUtils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CustomerListRealm extends RealmObject implements Comparable {



    String CustomerId;


    String ID;
    String ClientId;
    String CFirstname;
    String CMiddleName;
    String CLastname;
    String CContactNo;
    String CAlternetMobNo;
    String CEmail;
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
    boolean isCustomerOnlineSaved;

    public boolean isCustomerOnlineSaved() {
        return isCustomerOnlineSaved;
    }

    public void setCustomerOnlineSaved(boolean customerOnlineSaved) {
        isCustomerOnlineSaved = customerOnlineSaved;
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

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getCFirstname() {
        return CFirstname;
    }

    public void setCFirstname(String CFirstname) {
        this.CFirstname = CFirstname;
    }

    public String getCMiddleName() {
        return CMiddleName;
    }

    public void setCMiddleName(String CMiddleName) {
        this.CMiddleName = CMiddleName;
    }

    public String getCLastname() {
        return CLastname;
    }

    public void setCLastname(String CLastname) {
        this.CLastname = CLastname;
    }

    public String getCContactNo() {
        return CContactNo;
    }

    public void setCContactNo(String CContactNo) {
        this.CContactNo = CContactNo;
    }

    public String getCAlternetMobNo() {
        return CAlternetMobNo;
    }

    public void setCAlternetMobNo(String CAlternetMobNo) {
        this.CAlternetMobNo = CAlternetMobNo;
    }

    public String getCEmail() {
        return CEmail;
    }

    public void setCEmail(String CEmail) {
        this.CEmail = CEmail;
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

    @Override
    public int compareTo(Object o) {
        return this.getOutstanding().compareTo(((CustomerListRealm) o).getOutstanding());
    }
}
