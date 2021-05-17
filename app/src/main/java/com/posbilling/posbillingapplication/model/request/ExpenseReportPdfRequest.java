package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

///
///https://qa.ramumunimji.com/api/ExpenseReport/PostExpenseReport
public class ExpenseReportPdfRequest {
    @SerializedName("regId")
    String regId;
    @SerializedName("PDFName")
    String PDFName;
    @SerializedName("langID")
    String langID;

    public String getLangID() {
        return langID;
    }

    public void setLangID(String langID) {
        this.langID = langID;
    }

    @SerializedName("RecordList")
    ArrayList<RecordList> recordListArrayList = new ArrayList<RecordList>();


    public class RecordList {
        @SerializedName("srno")
        String srno;
        @SerializedName("Businessname")
        String Businessname;
        @SerializedName("ClientVillage")
        String ClientVillage;
        @SerializedName("Contactno")
        String Contactno;
        @SerializedName("Period")
        String Period;
        @SerializedName("TotalExp")
        String TotalExp;
        @SerializedName("Date")
        String Date;
        @SerializedName("ExpType")
        String ExpType;
        @SerializedName("ExpDetail")
        String ExpDetail;
        @SerializedName("ExpAmount")
        String ExpAmount;
        @SerializedName("Refernceid")
        String Refernceid;

        public String getSrno() {
            return srno;
        }

        public void setSrno(String srno) {
            this.srno = srno;
        }

        public String getBusinessname() {
            return Businessname;
        }

        public void setBusinessname(String businessname) {
            Businessname = businessname;
        }

        public String getClientVillage() {
            return ClientVillage;
        }

        public void setClientVillage(String clientVillage) {
            ClientVillage = clientVillage;
        }

        public String getContactno() {
            return Contactno;
        }

        public void setContactno(String contactno) {
            Contactno = contactno;
        }

        public String getPeriod() {
            return Period;
        }

        public void setPeriod(String period) {
            Period = period;
        }

        public String getTotalExp() {
            return TotalExp;
        }

        public void setTotalExp(String totalExp) {
            TotalExp = totalExp;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public String getExpType() {
            return ExpType;
        }

        public void setExpType(String expType) {
            ExpType = expType;
        }

        public String getExpDetail() {
            return ExpDetail;
        }

        public void setExpDetail(String expDetail) {
            ExpDetail = expDetail;
        }

        public String getExpAmount() {
            return ExpAmount;
        }

        public void setExpAmount(String expAmount) {
            ExpAmount = expAmount;
        }

        public String getRefernceid() {
            return Refernceid;
        }

        public void setRefernceid(String refernceid) {
            Refernceid = refernceid;
        }
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getPDFName() {
        return PDFName;
    }

    public void setPDFName(String PDFName) {
        this.PDFName = PDFName;
    }

    public ArrayList<RecordList> getRecordListArrayList() {
        return recordListArrayList;
    }

    public void setRecordListArrayList(ArrayList<RecordList> recordListArrayList) {
        this.recordListArrayList = recordListArrayList;
    }
}

/*
{
    "regId":"15",
    "PDFName":"Expenses1",
    "RecordList":[
        {
  "srno":"1",
  "Businessname":"abcevechile",
  "ClientVillage":"khed",
  "Contactno":"8055659418",
  "Period":"13to14",
  "TotalExp":"25.00",
  "Date":"2020-06-17 23:07:35.680",
  "ExpType":"sdfdf",
  "ExpDetail":"sdf",
  "ExpAmount":"456.00",
  "Refernceid":"R1"
        },
            {
  "srno":"2",
  "Businessname":"abcevechile",
  "ClientVillage":"khed",
  "Contactno":"8055659418",
  "Period":"13to14",
  "TotalExp":"25.00",
  "Date":"2020-06-17 23:07:35.680",
  "ExpType":"sdfdf",
  "ExpDetail":"sdf",
  "ExpAmount":"1000.00",
  "Refernceid":"R2"
        }]
}
 */