package com.posbilling.posbillingapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ActviStatusResponse {
    @SerializedName("status")
    String status;
    @SerializedName("VersionName")
    String VersionName;
    @SerializedName("Version")
    String Version;
    @SerializedName("BusinessTypeName")
    String BusinessTypeName;

    public String getBusinessTypeName() {
        return BusinessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        BusinessTypeName = businessTypeName;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        VersionName = versionName;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    @SerializedName("Active")
    ArrayList<Active> activeArrayList = new ArrayList<Active>();

    public class Active {
        @SerializedName("Id")
        String Id;
        @SerializedName("ContactNo")
        String ContactNo;
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("Firstname")
        String Firstname;
        @SerializedName("Lastname")
        String Lastname;
        @SerializedName("SystemSMS")
        String SystemSMS;
        @SerializedName("BusinessId")
        String BusinessId;
        @SerializedName("BusinessName")
        String BusinessName;
        @SerializedName("ActiveDay")
        String ActiveDay;
        @SerializedName("IsActive")
        String IsActive;
        @SerializedName("ImagePath")
        String ImagePath;
        @SerializedName("DOB")
        String DOB;
        @SerializedName("Address")
        String Address;
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


        public String getContactNo() {
            return ContactNo;
        }

        public void setContactNo(String contactNo) {
            ContactNo = contactNo;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getClientId() {
            return ClientId;
        }

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public String getFirstname() {
            return Firstname;
        }

        public void setFirstname(String firstname) {
            Firstname = firstname;
        }

        public String getLastname() {
            return Lastname;
        }

        public void setLastname(String lastname) {
            Lastname = lastname;
        }

        public String getSystemSMS() {
            return SystemSMS;
        }

        public void setSystemSMS(String systemSMS) {
            SystemSMS = systemSMS;
        }

        public String getBusinessId() {
            return BusinessId;
        }

        public void setBusinessId(String businessId) {
            BusinessId = businessId;
        }

        public String getBusinessName() {
            return BusinessName;
        }

        public void setBusinessName(String businessName) {
            BusinessName = businessName;
        }

        public String getActiveDay() {
            return ActiveDay;
        }

        public void setActiveDay(String activeDay) {
            ActiveDay = activeDay;
        }

        public String getIsActive() {
            return IsActive;
        }

        public void setIsActive(String isActive) {
            IsActive = isActive;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String imagePath) {
            ImagePath = imagePath;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
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
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Active> getActiveArrayList() {
        return activeArrayList;
    }

    public void setActiveArrayList(ArrayList<Active> activeArrayList) {
        this.activeArrayList = activeArrayList;
    }
}

/*
{
  "status": 1,
  "Active": [
    {
      "Id": 29,
      "ClientId": "C29",
      "Firstname": "Ankur Shinde",
      "Lastname": "",
      "SystemSMS": true,
      "BusinessId": 4,
      "BusinessName": "Voice voice",
      "ActiveDay": 3,
      "IsActive": true,
      "ImagePath": "images//Ramuji//C291587740534439.jpg",
      "DOB": null,
      "Address": "Bavdhan Haveli Pune",
      "Village": "Bavdhan",
      "Taluka": "Haveli",
      "District": "Pune",
      "StateId": 14,
      "Country": "India"
    }
  ]
}
 */
