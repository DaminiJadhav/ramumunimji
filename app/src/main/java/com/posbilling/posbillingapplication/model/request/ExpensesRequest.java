package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class ExpensesRequest {

    String regId;

    @SerializedName("ExpenseList")
    ArrayList<ExpenseList> expenseListArrayList = new ArrayList<ExpenseList>();

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public ArrayList<ExpenseList> getExpenseListArrayList() {
        return expenseListArrayList;
    }

    public void setExpenseListArrayList(ArrayList<ExpenseList> expenseListArrayList) {
        this.expenseListArrayList = expenseListArrayList;
    }

    public class ExpenseList {
        String ExpenditureId;
        String TransactionId;
        String ClientId;
        String Purpose;
        String ExpenseType;
        String Amount;
        Date ExpendDate;


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

        public Date getExpendDate() {
            return ExpendDate;
        }

        public void setExpendDate(Date expendDate) {
            ExpendDate = expendDate;
        }
    }
}

/*
{

"ExpenditureId":"E3",
"TransactionId":"T3",
"ClientId":"13",
"Purpose":"nasta",
"ExpenseType":"pesonal",
"Amount":"60",
"ExpendDate":""

}
 */