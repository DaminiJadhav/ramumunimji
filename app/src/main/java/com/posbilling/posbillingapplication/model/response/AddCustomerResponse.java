package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddCustomerResponse {
    @SerializedName("status")
    String status;
    @SerializedName("regId")
    String regId;
    @SerializedName("Message")
    String Message;

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


    @SerializedName("Data")
    Data Data;

    public class Data {
        @SerializedName("Id")
        String Id;
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("Firstname")
        String Firstname;
        @SerializedName("Midename")
        String Midename;
        @SerializedName("Lastname")
        String Lastname;
        @SerializedName("RoleId")
        String RoleId;
        @SerializedName("BusinessId")
        String BusinessId;
        @SerializedName("ContactNo")
        String ContactNo;
        @SerializedName("EmailId")
        String EmailId;
        @SerializedName("ActiveDay")
        String ActiveDay;
        @SerializedName("SystemSMS")
        String SystemSMS;
        @SerializedName("PaymentMethod")
        String PaymentMethod;
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

        @SerializedName("tblCustomers")
        ArrayList<tblCustomers> tblCustomersArrayList = new ArrayList<tblCustomers>();

        public class tblCustomers {
            @SerializedName("ID")
            String ID;
            @SerializedName("CustomerId")
            String CustomerId;
            @SerializedName("ClientId")
            String ClientId;
            @SerializedName("CFirstname")
            String CFirstname;
            @SerializedName("CMiddleName")
            String CMiddleName;
            @SerializedName("CLastname")
            String CLastname;
            @SerializedName("CContactNo")
            String CContactNo;
            @SerializedName("CAlternetMobNo")
            String CAlternetMobNo;
            @SerializedName("CEmail")
            String CEmail;
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
            @SerializedName("tblClientMaster")
            String tblClientMaster;

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

            public String getTblClientMaster() {
                return tblClientMaster;
            }

            public void setTblClientMaster(String tblClientMaster) {
                this.tblClientMaster = tblClientMaster;
            }
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

        public String getRoleId() {
            return RoleId;
        }

        public void setRoleId(String roleId) {
            RoleId = roleId;
        }

        public String getBusinessId() {
            return BusinessId;
        }

        public void setBusinessId(String businessId) {
            BusinessId = businessId;
        }

        public String getContactNo() {
            return ContactNo;
        }

        public void setContactNo(String contactNo) {
            ContactNo = contactNo;
        }

        public String getEmailId() {
            return EmailId;
        }

        public void setEmailId(String emailId) {
            EmailId = emailId;
        }

        public String getActiveDay() {
            return ActiveDay;
        }

        public void setActiveDay(String activeDay) {
            ActiveDay = activeDay;
        }

        public String getSystemSMS() {
            return SystemSMS;
        }

        public void setSystemSMS(String systemSMS) {
            SystemSMS = systemSMS;
        }

        public String getPaymentMethod() {
            return PaymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            PaymentMethod = paymentMethod;
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

        public ArrayList<tblCustomers> getTblCustomersArrayList() {
            return tblCustomersArrayList;
        }

        public void setTblCustomersArrayList(ArrayList<tblCustomers> tblCustomersArrayList) {
            this.tblCustomersArrayList = tblCustomersArrayList;
        }
    }


    public AddCustomerResponse.Data getData() {
        return Data;
    }

    public void setData(AddCustomerResponse.Data data) {
        Data = data;
    }
}

/*
{
  "status": 1,
  "regId": 29,
  "Message": "record saved",
  "Data": {
    "Id": 29,
    "ClientId": "C29",
    "Firstname": "Ankur Shinde",
    "Midename": "          ",
    "Lastname": "",
    "RoleId": null,
    "BusinessId": 4,
    "ContactNo": "9284112401",
    "EmailId": "ankur.shinde@sdaemon.com",
    "ActiveDay": null,
    "SystemSMS": null,
    "PaymentMethod": null,
    "CreatedOn": null,
    "UpdatedOn": null,
    "CreatedBy": null,
    "UpdatedBy": null,
    "IsActive": null,
    "BusinessDetails": [],
    "tblClientProfiles": [],
    "tblClientStatus": [],
    "tblCustomers": [
      {
        "ID": 11,
        "CustomerId": "C291",
        "ClientId": 29,
        "CFirstname": "Santosh",
        "CMiddleName": null,
        "CLastname": "Pawar",
        "CContactNo": "7719816806",
        "CAlternetMobNo": "",
        "CEmail": "",
        "Village": "Pune",
        "Taluka": "Pune",
        "District": "Pune",
        "StateId": 14,
        "Country": "INDIA",
        "PaymentFreq": "0",
        "SMSFlag": null,
        "Imagename": "C291",
        "ImagePath": "images//Ramuji//C291.jpg",
        "Outstanding": null,
        "CreatedOn": null,
        "UpdatedOn": null,
        "CreatedBy": null,
        "UpdatedBy": null,
        "IsActive": null,
        "tblClientMaster": null,
        "tblInvices": []
      },
      {
        "ID": 13,
        "CustomerId": "C292",
        "ClientId": 29,
        "CFirstname": "Nikhil",
        "CMiddleName": null,
        "CLastname": null,
        "CContactNo": null,
        "CAlternetMobNo": null,
        "CEmail": null,
        "Village": null,
        "Taluka": null,
        "District": null,
        "StateId": null,
        "Country": null,
        "PaymentFreq": null,
        "SMSFlag": null,
        "Imagename": null,
        "ImagePath": null,
        "Outstanding": -1000.00,
        "CreatedOn": null,
        "UpdatedOn": null,
        "CreatedBy": null,
        "UpdatedBy": null,
        "IsActive": null,
        "tblClientMaster": null,
        "tblInvices": []
      },
      {
        "ID": 14,
        "CustomerId": "C293",
        "ClientId": 29,
        "CFirstname": "Ankur Shinde",
        "CMiddleName": null,
        "CLastname": null,
        "CContactNo": null,
        "CAlternetMobNo": null,
        "CEmail": null,
        "Village": null,
        "Taluka": null,
        "District": null,
        "StateId": null,
        "Country": null,
        "PaymentFreq": null,
        "SMSFlag": null,
        "Imagename": null,
        "ImagePath": null,
        "Outstanding": 1400.00,
        "CreatedOn": null,
        "UpdatedOn": null,
        "CreatedBy": null,
        "UpdatedBy": null,
        "IsActive": null,
        "tblClientMaster": null,
        "tblInvices": []
      },
      {
        "ID": 15,
        "CustomerId": "C294",
        "ClientId": 29,
        "CFirstname": "new",
        "CMiddleName": null,
        "CLastname": "customer",
        "CContactNo": "7719816806",
        "CAlternetMobNo": "",
        "CEmail": "",
        "Village": "customer",
        "Taluka": "customer",
        "District": "customer",
        "StateId": 14,
        "Country": "INDIA",
        "PaymentFreq": "0",
        "SMSFlag": null,
        "Imagename": "C294",
        "ImagePath": "images//Ramuji//C294.jpg",
        "Outstanding": null,
        "CreatedOn": null,
        "UpdatedOn": null,
        "CreatedBy": null,
        "UpdatedBy": null,
        "IsActive": null,
        "tblClientMaster": null,
        "tblInvices": []
      },
      {
        "ID": 16,
        "CustomerId": "C295",
        "ClientId": 29,
        "CFirstname": "Aamir",
        "CMiddleName": null,
        "CLastname": null,
        "CContactNo": null,
        "CAlternetMobNo": null,
        "CEmail": null,
        "Village": null,
        "Taluka": null,
        "District": null,
        "StateId": null,
        "Country": null,
        "PaymentFreq": null,
        "SMSFlag": null,
        "Imagename": null,
        "ImagePath": null,
        "Outstanding": null,
        "CreatedOn": null,
        "UpdatedOn": null,
        "CreatedBy": null,
        "UpdatedBy": null,
        "IsActive": null,
        "tblClientMaster": null,
        "tblInvices": []
      }
    ],
    "tblExpenditures": [],
    "tblPresitcidesAndFertilisers": [],
    "tblVehicleDetails": []
  }
}

 */