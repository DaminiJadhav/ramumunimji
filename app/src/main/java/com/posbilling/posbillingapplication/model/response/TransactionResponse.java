package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TransactionResponse {
    @SerializedName("status")
    String status;
    @SerializedName("Message")
    String Message;
    @SerializedName("successTranaactionId")
    String successTranaactionId;


    public String getSuccessTranaactionId() {
        return successTranaactionId;
    }

    public void setSuccessTranaactionId(String successTranaactionId) {
        this.successTranaactionId = successTranaactionId;
    }

    @SerializedName("Data")
    Data data;


public class Data {


    @SerializedName("TransactionRecord")
    ArrayList<TransactionRecord> dataArrayList = new ArrayList<TransactionRecord>();


    public class TransactionRecord {
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("CustomerId")
        String CustomerId;
        @SerializedName("CFirstname")
        String CFirstname;
        @SerializedName("CLastname")
        String CLastname;
        @SerializedName("Imagename")
        String Imagename;
        @SerializedName("ImagePath")
        String ImagePath;
        @SerializedName("CContactNo")
        String CContactNo;
        @SerializedName("ID")
        String ID;
        @SerializedName("TransactionId")
        String TransactionId;
        @SerializedName("BillDate")
        String BillDate;
        @SerializedName("Time")
        String Time;
        @SerializedName("CrAmt")
        String CrAmt;
        @SerializedName("DrAmt")
        String DrAmt;
        @SerializedName("Purpose")
        String Purpose;
        @SerializedName("Liter")
        String Liter;
        @SerializedName("PaymentStatus")
        String PaymentStatus;

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

        public String getCContactNo() {
            return CContactNo;
        }

        public void setCContactNo(String CContactNo) {
            this.CContactNo = CContactNo;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public void setTransactionId(String transactionId) {
            TransactionId = transactionId;
        }

        public String getBillDate() {
            return BillDate;
        }

        public void setBillDate(String billDate) {
            BillDate = billDate;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public String getCrAmt() {
            return CrAmt;
        }

        public void setCrAmt(String crAmt) {
            CrAmt = crAmt;
        }

        public String getDrAmt() {
            return DrAmt;
        }

        public void setDrAmt(String drAmt) {
            DrAmt = drAmt;
        }

        public String getPurpose() {
            return Purpose;
        }

        public void setPurpose(String purpose) {
            Purpose = purpose;
        }

        public String getLiter() {
            return Liter;
        }

        public void setLiter(String liter) {
            Liter = liter;
        }

        public String getPaymentStatus() {
            return PaymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            PaymentStatus = paymentStatus;
        }
    }

    public ArrayList<TransactionRecord> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<TransactionRecord> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    @SerializedName("CustomerList")
    ArrayList<CustomerList> customerListArrayList = new ArrayList<CustomerList>();


    public class CustomerList{


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
        @SerializedName("tblInvices")
        ArrayList<tblInvices> tblInvicesList = new ArrayList<tblInvices>();

        class tblInvices {
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

        public String getTblClientMaster() {
            return tblClientMaster;
        }

        public void setTblClientMaster(String tblClientMaster) {
            this.tblClientMaster = tblClientMaster;
        }

        public ArrayList<tblInvices> getTblInvicesList() {
            return tblInvicesList;
        }

        public void setTblInvicesList(ArrayList<tblInvices> tblInvicesList) {
            this.tblInvicesList = tblInvicesList;
        }
    }


    public ArrayList<CustomerList> getCustomerListArrayList() {
        return customerListArrayList;
    }

    public void setCustomerListArrayList(ArrayList<CustomerList> customerListArrayList) {
        this.customerListArrayList = customerListArrayList;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }





}




/*
{
  "status": 1,
  "Message": "successed",
  "Data": [
    {
      "ClientId": 1044,
      "CustomerId": 1095,
      "CFirstname": "Ashok Saraf",
      "CLastname": null,
      "Imagename": null,
      "ImagePath": null,
      "CContactNo": null,
      "ID": 132,
      "TransactionId": "T11",
      "BillDate": "2020-04-03T00:00:00",
      "Time": "11:50:00",
      "CrAmt": 0.00,
      "DrAmt": 300.00,
      "Purpose": "for Sel purpose",
      "Liter": null,
      "PaymentStatus": null
    },
    {
      "ClientId": 1044,
      "CustomerId": 1095,
      "CFirstname": "Ashok Saraf",
      "CLastname": null,
      "Imagename": null,
      "ImagePath": null,
      "CContactNo": null,
      "ID": 133,
      "TransactionId": "T12",
      "BillDate": "2020-04-03T00:00:00",
      "Time": "11:52:00",
      "CrAmt": 500.00,
      "DrAmt": 0.00,
      "Purpose": "",
      "Liter": null,
      "PaymentStatus": null
    },
    {
      "ClientId": 1044,
      "CustomerId": 1097,
      "CFirstname": "Mahesh",
      "CLastname": "Kothari",
      "Imagename": "C10443",
      "ImagePath": "images//Ramuji//C10443.jpg",
      "CContactNo": "0968585858",
      "ID": 134,
      "TransactionId": "T41",
      "BillDate": "2020-04-03T00:00:00",
      "Time": "11:57:00",
      "CrAmt": 0.00,
      "DrAmt": 300.00,
      "Purpose": "just Timepass",
      "Liter": null,
      "PaymentStatus": null
    },
    {
      "ClientId": 1044,
      "CustomerId": 1097,
      "CFirstname": "Mahesh",
      "CLastname": "Kothari",
      "Imagename": "C10443",
      "ImagePath": "images//Ramuji//C10443.jpg",
      "CContactNo": "0968585858",
      "ID": 135,
      "TransactionId": "T32",
      "BillDate": "2020-04-03T00:00:00",
      "Time": "11:58:00",
      "CrAmt": 0.00,
      "DrAmt": 600.00,
      "Purpose": "",
      "Liter": null,
      "PaymentStatus": null
    },
    {
      "ClientId": 1044,
      "CustomerId": 1095,
      "CFirstname": "Ashok Saraf",
      "CLastname": null,
      "Imagename": null,
      "ImagePath": null,
      "CContactNo": null,
      "ID": 162,
      "TransactionId": "R41",
      "BillDate": "2020-04-10T00:00:00",
      "Time": "16:01:00",
      "CrAmt": 0.00,
      "DrAmt": 5000.00,
      "Purpose": "",
      "Liter": null,
      "PaymentStatus": null
    }
  ]
}
 */




/*
{
  "status": 1,
  "Message": "successed",
  "Data": {
    "TransactionRecord": [
      {
        "ClientId": 1041,
        "CustomerId": 1159,
        "CFirstname": "à¤ªà¥�à¤°à¥‹à¤«à¤¾à¤‡à¤²",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1159,
        "TransactionId": "R1",
        "BillDate": "2020-04-15T19:21:00",
        "Time": "19:21:00",
        "CrAmt": 0.0,
        "DrAmt": 100.0,
        "Purpose": "à¤‰à¤¦à¥�à¤¦à¥‡à¤¶ à¤¹à¥‡à¤¤à¥‚ à¤ªà¥�à¤°à¤µà¤¿à¤·à¥�à¤Ÿ à¤•à¤°à¤¾",
        "Liter": null,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1093,
        "CFirstname": "Ankur Shinde",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1093,
        "TransactionId": "T11",
        "BillDate": "2020-04-02T00:00:00",
        "Time": "22:12:00",
        "CrAmt": 0.00,
        "DrAmt": 100.00,
        "Purpose": "for dairy",
        "Liter": 3,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1094,
        "CFirstname": "Viraj Dobriyal",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1094,
        "TransactionId": "T21",
        "BillDate": "2020-04-03T00:00:00",
        "Time": "00:53:00",
        "CrAmt": 0.00,
        "DrAmt": 505.00,
        "Purpose": "daily milk",
        "Liter": 6,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1094,
        "CFirstname": "Viraj Dobriyal",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1094,
        "TransactionId": "R31",
        "BillDate": "2020-04-04T00:00:00",
        "Time": "06:49:00",
        "CrAmt": 0.00,
        "DrAmt": 50.00,
        "Purpose": "milk",
        "Liter": 2,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1094,
        "CFirstname": "Viraj Dobriyal",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1094,
        "TransactionId": "R32",
        "BillDate": "2020-04-04T00:00:00",
        "Time": "06:52:00",
        "CrAmt": 0.00,
        "DrAmt": 505.00,
        "Purpose": "milk",
        "Liter": 5,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1094,
        "CFirstname": "Viraj Dobriyal",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1094,
        "TransactionId": "R33",
        "BillDate": "2020-04-04T00:00:00",
        "Time": "11:39:00",
        "CrAmt": 0.00,
        "DrAmt": 500.00,
        "Purpose": "Timepass",
        "Liter": 60,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1149,
        "CFirstname": "à¤¨à¥�à¤¯à¥‚ à¤•à¤¸à¥�à¤Ÿà¤®à¤° à¤•à¥‡à¤…à¤°",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1149,
        "TransactionId": "R1",
        "BillDate": "2020-04-15T00:00:00",
        "Time": "12:54:00",
        "CrAmt": 0.00,
        "DrAmt": 1000.00,
        "Purpose": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤—",
        "Liter": null,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1151,
        "CFirstname": "à¤¨à¥�à¤¯à¥‚ à¤•à¤¸à¥�à¤Ÿà¤®à¤°",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1151,
        "TransactionId": "R1",
        "BillDate": "2020-04-15T00:00:00",
        "Time": "12:56:00",
        "CrAmt": 0.00,
        "DrAmt": 1000.00,
        "Purpose": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤—",
        "Liter": null,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1152,
        "CFirstname": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤— à¤¤à¤¿à¤¸à¤±à¥�à¤¯à¤¾à¤‚à¤¦à¤¾",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1152,
        "TransactionId": "R1",
        "BillDate": "2020-04-15T00:00:00",
        "Time": "12:59:00",
        "CrAmt": 0.00,
        "DrAmt": 1000.00,
        "Purpose": "à¤šà¤¾à¤‚à¤—à¤²à¤¾ à¤¹à¥‹à¤¤à¥‡",
        "Liter": null,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1153,
        "CFirstname": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤— à¤¤à¤¿à¤¸à¤±à¥�à¤¯à¤¾à¤‚à¤¦à¤¾",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1153,
        "TransactionId": "R1",
        "BillDate": "2020-04-15T00:00:00",
        "Time": "12:59:00",
        "CrAmt": 0.00,
        "DrAmt": 1000.00,
        "Purpose": "à¤šà¤¾à¤‚à¤—à¤²à¤¾ à¤¹à¥‹à¤¤à¥‡",
        "Liter": null,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1154,
        "CFirstname": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤— à¤ªà¤¾à¤šà¤µà¥�à¤¯à¤¾à¤‚à¤¦à¤¾",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1154,
        "TransactionId": "R1",
        "BillDate": "2020-04-15T00:00:00",
        "Time": "13:02:00",
        "CrAmt": 0.00,
        "DrAmt": 1000.00,
        "Purpose": "à¤šà¤¾à¤‚à¤—à¤²à¤¾ à¤‰à¤¦à¥�à¤¦à¥‡à¤¶",
        "Liter": null,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1155,
        "CFirstname": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤— à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤—",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1155,
        "TransactionId": "R1",
        "BillDate": "2020-04-15T00:00:00",
        "Time": "13:05:00",
        "CrAmt": 0.00,
        "DrAmt": 1000.00,
        "Purpose": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤— à¤šà¤¾ à¤‰à¤¦à¥�à¤¦à¥‡à¤¶",
        "Liter": null,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1156,
        "CFirstname": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤— à¤µà¤¨ à¤Ÿà¥‚ à¤¥à¥�à¤°à¥€",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1156,
        "TransactionId": "R1",
        "BillDate": "2020-04-15T00:00:00",
        "Time": "13:09:00",
        "CrAmt": 0.00,
        "DrAmt": 1000.00,
        "Purpose": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤—",
        "Liter": null,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1157,
        "CFirstname": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤— à¤ªà¤¾à¤šà¤µà¥�à¤¯à¤¾à¤‚à¤¦à¤¾ à¤¸à¤¹à¤¾à¤µà¥�à¤¯à¤¾à¤‚à¤¦à¤¾",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1157,
        "TransactionId": "R1",
        "BillDate": "2020-04-15T00:00:00",
        "Time": "19:18:00",
        "CrAmt": 0.00,
        "DrAmt": 100.00,
        "Purpose": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤—",
        "Liter": null,
        "PaymentStatus": null
      },
      {
        "ClientId": 1041,
        "CustomerId": 1158,
        "CFirstname": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤— à¤ªà¤¾à¤šà¤µà¥�à¤¯à¤¾à¤‚à¤¦à¤¾ à¤¸à¤¹à¤¾à¤µà¥�à¤¯à¤¾à¤‚à¤¦à¤¾",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 1158,
        "TransactionId": "R1",
        "BillDate": "2020-04-15T00:00:00",
        "Time": "19:18:00",
        "CrAmt": 0.00,
        "DrAmt": 100.00,
        "Purpose": "à¤Ÿà¥‡à¤¸à¥�à¤Ÿà¤¿à¤‚à¤—",
        "Liter": null,
        "PaymentStatus": null
      }
    ],
    "CustomerList": [
      {
        "ID": 1159,
        "CustomerId": "C10414",
        "ClientId": 1041,
        "CFirstname": "à¤ªà¥�à¤°à¥‹à¤«à¤¾à¤‡à¤²",
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
        "Outstanding": 100.0,
        "CreatedOn": "2020-04-15T19:21:58.8814922+05:30",
        "UpdatedOn": "2020-04-15T19:21:59.2754689+05:30",
        "CreatedBy": "Ankur Shinde",
        "UpdatedBy": "Ankur RamuMunimji.Models.tblInvice",
        "IsActive": true,
        "tblClientMaster": null,
        "tblInvices": []
      },
      {
        "ID": 1093,
        "CustomerId": "C10411",
        "ClientId": 1041,
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
        "Outstanding": 100.00,
        "CreatedOn": "2020-04-02T22:13:01.02",
        "UpdatedOn": "2020-04-02T22:13:06.153",
        "CreatedBy": "Ankur Shinde",
        "UpdatedBy": null,
        "IsActive": true,
        "tblClientMaster": null,
        "tblInvices": []
      },
      {
        "ID": 1094,
        "CustomerId": "C10412",
        "ClientId": 1041,
        "CFirstname": "Viraj Dobriyal",
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
        "Outstanding": 1560.00,
        "CreatedOn": "2020-04-03T00:54:04.443",
        "UpdatedOn": "2020-04-04T11:40:02.68",
        "CreatedBy": "Ankur Shinde",
        "UpdatedBy": null,
        "IsActive": true,
        "tblClientMaster": null,
        "tblInvices": []
      },
      {
        "ID": 1102,
        "CustomerId": "C10413",
        "ClientId": 1041,
        "CFirstname": "Nikhil",
        "CMiddleName": null,
        "CLastname": null,
        "CContactNo": "5858282828",
        "CAlternetMobNo": "",
        "CEmail": "",
        "Village": "Jalgaon",
        "Taluka": "jyada ho",
        "District": "Jalgaon",
        "StateId": 14,
        "Country": "INDIA",
        "PaymentFreq": "0",
        "SMSFlag": null,
        "Imagename": "C10413",
        "ImagePath": "images//Ramuji//C10413.jpg",
        "Outstanding": null,
        "CreatedOn": "2020-04-04T14:29:00",
        "UpdatedOn": null,
        "CreatedBy": "1041",
        "UpdatedBy": "",
        "IsActive": true,
        "tblClientMaster": null,
        "tblInvices": []
      },
      {
        "ID": 1149,
        "CustomerId": "C10414",
        "ClientId": 1041,
        "CFirstname": "à¤¨à¥�à¤¯à¥‚ à¤•à¤¸à¥�à¤Ÿà¤®à¤° à¤•à¥‡à¤…à¤°",
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
        "Outstanding": 1000.00,
        "CreatedOn": "2020-04-15T12:54:48.947",
        "UpdatedOn": "2020-04-15T12:54:49.913",
        "CreatedBy": "Ankur Shinde",
        "UpdatedBy": "Ankur RamuMunimji.Models.tblInvice",
        "IsActive": true,
        "tblClientMaster": null,
        "tblInvices": []
      },
      {
        "ID": 1151,
        "C
 */