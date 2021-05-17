package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class PurchaseTransactionRequest {
    @SerializedName("BusinessType")
    String BusinessType;
    @SerializedName("DeviceId")
    String DeviceId;
    @SerializedName("Lang")
    String Lang;
    @SerializedName("regId")
    String regId;

    @SerializedName("SupplierList")
    ArrayList<SupplierList> supplierListArrayList = new ArrayList<SupplierList>();

    public class SupplierList {

        @SerializedName("SupplierId")
        String SupplierId;
        @SerializedName("Id")
        String Id;
        @SerializedName("Name")
        String Name;

        @SerializedName("TransactionsList")
        ArrayList<TransactionsList> transactionsListArrayList = new ArrayList<TransactionsList>();

        public class TransactionsList {
            @SerializedName("TransId")
            String TransId;
            @SerializedName("Amount")
            String Amount;
            @SerializedName("Date")
            Date Date;
            @SerializedName("Liter")
            String Liter;
            @SerializedName("Purpose")
            String Purpose;
            @SerializedName("Time")
            String Time;
            @SerializedName("TransactionId")
            String TransactionId;
            @SerializedName("TransactionType")
            String TransactionType;


            public String getTransId() {
                return TransId;
            }

            public void setTransId(String transId) {
                TransId = transId;
            }

            public String getAmount() {
                return Amount;
            }

            public void setAmount(String amount) {
                Amount = amount;
            }

            public Date getDate() {
                return Date;
            }

            public void setDate(Date date) {
                Date = date;
            }

            public String getLiter() {
                return Liter;
            }

            public void setLiter(String liter) {
                Liter = liter;
            }

            public String getPurpose() {
                return Purpose;
            }

            public void setPurpose(String purpose) {
                Purpose = purpose;
            }

            public String getTime() {
                return Time;
            }

            public void setTime(String time) {
                Time = time;
            }

            public String getTransactionId() {
                return TransactionId;
            }

            public void setTransactionId(String transactionId) {
                TransactionId = transactionId;
            }

            public String getTransactionType() {
                return TransactionType;
            }

            public void setTransactionType(String transactionType) {
                TransactionType = transactionType;
            }
        }

        public String getSupplierId() {
            return SupplierId;
        }

        public void setSupplierId(String supplierId) {
            SupplierId = supplierId;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public ArrayList<TransactionsList> getTransactionsListArrayList() {
            return transactionsListArrayList;
        }

        public void setTransactionsListArrayList(ArrayList<TransactionsList> transactionsListArrayList) {
            this.transactionsListArrayList = transactionsListArrayList;
        }
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getLang() {
        return Lang;
    }

    public void setLang(String lang) {
        Lang = lang;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
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
  "BusinessType": "4",
  "DeviceId": "",
  "Lang": "1",
  "regId": "18"
  "SupplierList": [
    {
      "SupplierId": "",
      "Id": "9",
      "Name": "testforedit",
      "TransactionsList": [

           {
          "TransId":"14",
          "Amount": "400",
          "Date": "June 28, 2020 11:23:00 AM",
          "Liter": "30",
          "Purpose": "500 Rs dile baki 500 rahile ",
          "Time": "11:23",
          "TransactionId": "R4",
          "TransactionType": "1"
        }
      ]
    }
  ],

}
 */