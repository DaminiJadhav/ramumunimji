package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSupplierTransaction {
    @SerializedName("status")
    String status;
    @SerializedName("Message")
    String Message;

    @SerializedName("Data")
    ArrayList<Data> dataArrayList = new ArrayList<Data>();

    public class Data {
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("SupplierId")
        String SupplierId;
        @SerializedName("SFirstname")
        String SFirstname;
        @SerializedName("SLastname")
        String SLastname;
        @SerializedName("Imagename")
        String Imagename;
        @SerializedName("ImagePath")
        String ImagePath;
        @SerializedName("SContactNo")
        String SContactNo;
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

        public String getSupplierId() {
            return SupplierId;
        }

        public void setSupplierId(String supplierId) {
            SupplierId = supplierId;
        }

        public String getSFirstname() {
            return SFirstname;
        }

        public void setSFirstname(String SFirstname) {
            this.SFirstname = SFirstname;
        }

        public String getSLastname() {
            return SLastname;
        }

        public void setSLastname(String SLastname) {
            this.SLastname = SLastname;
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

        public String getSContactNo() {
            return SContactNo;
        }

        public void setSContactNo(String SContactNo) {
            this.SContactNo = SContactNo;
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

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }
}

/*
{
    "status": 1,
    "Message": "successed",
    "Data": [
        {
            "ClientId": 18,
            "SupplierId": "S182",
            "SFirstname": "Second Supplier",
            "SLastname": "",
            "Imagename": "",
            "ImagePath": null,
            "SContactNo": "8194655580",
            "ID": 6,
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
            "SupplierId": "S185",
            "SFirstname": "Fourth Supplier",
            "SLastname": null,
            "Imagename": null,
            "ImagePath": null,
            "SContactNo": null,
            "ID": 8,
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
            "SupplierId": "C186",
            "SFirstname": "Supplier Test",
            "SLastname": null,
            "Imagename": null,
            "ImagePath": null,
            "SContactNo": null,
            "ID": 9,
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
            "SupplierId": "C187",
            "SFirstname": "Testfinal",
            "SLastname": null,
            "Imagename": null,
            "ImagePath": null,
            "SContactNo": null,
            "ID": 11,
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
            "SupplierId": "C187",
            "SFirstname": "Testfinal",
            "SLastname": null,
            "Imagename": null,
            "ImagePath": null,
            "SContactNo": null,
            "ID": 12,
            "TransactionId": "R8",
            "BillDate": "2020-06-28T00:00:00",
            "Time": "11:23:00",
            "CrAmt": 400.00,
            "DrAmt": 0.00,
            "Purpose": "500 Rs dile baki 500 rahile ",
            "Liter": 30,
            "PaymentStatus": null
        },
        {
            "ClientId": 18,
            "SupplierId": "C188",
            "SFirstname": "testforedit",
            "SLastname": null,
            "Imagename": null,
            "ImagePath": null,
            "SContactNo": null,
            "ID": 13,
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
            "SupplierId": "C188",
            "SFirstname": "testforedit",
            "SLastname": null,
            "Imagename": null,
            "ImagePath": null,
            "SContactNo": null,
            "ID": 14,
            "TransactionId": "R10",
            "BillDate": "2020-06-28T00:00:00",
            "Time": "11:23:00",
            "CrAmt": 400.00,
            "DrAmt": 0.00,
            "Purpose": "500 Rs dile baki 500 rahile ",
            "Liter": 30,
            "PaymentStatus": null
        }
    ]
}
 */