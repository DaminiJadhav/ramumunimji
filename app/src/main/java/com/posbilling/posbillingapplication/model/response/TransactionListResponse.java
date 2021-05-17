package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TransactionListResponse {
    @SerializedName("Message")
    String Message;
    @SerializedName("status")
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("Data")
    ArrayList<TransactionList> dataList = new ArrayList<TransactionList>();


    public class TransactionList {
        boolean isOnline = true;

        public boolean isOnline() {
            return isOnline;
        }

        public void setOnline(boolean online) {
            isOnline = online;
        }

        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("ID")
        String ID;
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
        @SerializedName("TransactionId")
        String TransactionId;

        public String getTransactionId() {
            return TransactionId;
        }

        public void setTransactionId(String transactionId) {
            TransactionId = transactionId;
        }

        public String getClientId() {
            return ClientId;
        }

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<TransactionList> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<TransactionList> dataList) {
        this.dataList = dataList;
    }
}
/*
// 20200327122104
// https://ramu.sdaemon.com/api/CTransactionTouch/GetTransaction?regId=11&CustomerId=6

{
  "status": 1,
  "Message": "successed",
  "Data": [
    {
      "ClientId": 13,
      "CustomerId": 1062,
      "CFirstname": "à¤®à¤¾à¤¨à¤µ",
      "CLastname": null,
      "Imagename": null,
      "ImagePath": null,
      "CContactNo": null,
      "ID": 74,
      "TransactionId": null,
      "BillDate": "2020-03-26T00:00:00",
      "Time": "00:00:00",
      "CrAmt": 130.00,
      "DrAmt": 0.00,
      "Purpose": "à¤…à¤¸à¤‚à¤š",
      "Liter": null,
      "PaymentStatus": null
    }
  ]
}
 */