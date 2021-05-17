package com.posbilling.posbillingapplication.model.request;

public class UpdateProfile {
    private String regId;
    private String Firstname;
    private String Midename;
    private String Lastname;
    private String Imagename;
    private String ImagePath;
    private String Address;
    private String UpdateOn;
    private String BusinessName;
    private String BusinessId;
    private String Village;
    private String Taluka;
    private String District;


    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
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

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getMidename() {
        return Midename;
    }

    public void setMidename(String midename) {
        Midename = midename;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUpdateOn() {
        return UpdateOn;
    }

    public void setUpdateOn(String updateOn) {
        UpdateOn = updateOn;
    }
}

/*
{
         "regId":"2",
        "Firstname":"Dimpal",
        "Midename":"",
        "Lastname":"kapadia",
        "Imagename":"boy",
        "ImagePath":"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",
        "Address":"siddharth society",
        "UpdateOn":""

}
 */
