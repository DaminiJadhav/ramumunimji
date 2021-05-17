package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ExpenseResponse {
    @SerializedName("status")
    String status;
    @SerializedName("regId")
    String regId;
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
    ArrayList<Data> dataArrayList = new ArrayList<>();

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    public class Data {
        @SerializedName("ID")
        String ID;
        @SerializedName("ExpenditureId")
        String ExpenditureId;
        @SerializedName("TransactionId")
        String TransactionId;
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("Purpose")
        String Purpose;
        @SerializedName("ExpenseType")
        String ExpenseType;
        @SerializedName("Amount")
        String Amount;
        @SerializedName("ExpendDate")
        String ExpendDate;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getExpenditureId() {
            return ExpenditureId;
        }

        public void setExpenditureId(String expenditureId) {
            ExpenditureId = expenditureId;
        }

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

        public String getPurpose() {
            return Purpose;
        }

        public void setPurpose(String purpose) {
            Purpose = purpose;
        }

        public String getExpenseType() {
            return ExpenseType;
        }

        public void setExpenseType(String expenseType) {
            ExpenseType = expenseType;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

        public String getExpendDate() {
            return ExpendDate;
        }

        public void setExpendDate(String expendDate) {
            ExpendDate = expendDate;
        }
    }

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

}
/*
{
    "status": 1,
    "regId": 13,
    "Message": "Successed",
    "Data": {
        "ID": 6,
        "ExpenditureId": "E3",
        "TransactionId": "T3",
        "ClientId": 13,
        "Purpose": "nasta",
        "ExpenseType": "pesonal",
        "Amount": 60.0,
        "ExpendDate": null,
        "IsActive": true,
        "tblClientMaster": null
    }
}
 */


/*
{
    "status": 1,
    "regId": 48,
    "Message": "Successed",
    "Data": [
        {
            "ID": 42,
            "ExpenditureId": "R19",
            "TransactionId": "R19",
            "ClientId": 48,
            "Purpose": "cloths",
            "ExpenseType": "Family",
            "Amount": 1500.00,
            "ExpendDate": null,
            "IsActive": true,
            "tblClientMaster": null
        },
        {
            "ID": 43,
            "ExpenditureId": "R20",
            "TransactionId": "R20",
            "ClientId": 48,
            "Purpose": "shoe",
            "ExpenseType": "Family",
            "Amount": 1500.00,
            "ExpendDate": null,
            "IsActive": true,
            "tblClientMaster": null
        },
        {
            "ID": 44,
            "ExpenditureId": "R21",
            "TransactionId": "R21",
            "ClientId": 48,
            "Purpose": "jevan",
            "ExpenseType": "Family",
            "Amount": 1500.00,
            "ExpendDate": null,
            "IsActive": true,
            "tblClientMaster": null
        },
        {
            "ID": 48,
            "ExpenditureId": "R22",
            "TransactionId": "R22",
            "ClientId": 48,
            "Purpose": "cloths",
            "ExpenseType": "Family",
            "Amount": 1500.00,
            "ExpendDate": null,
            "IsActive": true,
            "tblClientMaster": null
        },
        {
            "ID": 49,
            "ExpenditureId": "R23",
            "TransactionId": "R23",
            "ClientId": 48,
            "Purpose": "shoe",
            "ExpenseType": "Family",
            "Amount": 1500.00,
            "ExpendDate": null,
            "IsActive": true,
            "tblClientMaster": null
        },
        {
            "ID": 50,
            "ExpenditureId": "R24",
            "TransactionId": "R24",
            "ClientId": 48,
            "Purpose": "jevan",
            "ExpenseType": "Family",
            "Amount": 1500.00,
            "ExpendDate": null,
            "IsActive": true,
            "tblClientMaster": null
        }
    ]
}
 */