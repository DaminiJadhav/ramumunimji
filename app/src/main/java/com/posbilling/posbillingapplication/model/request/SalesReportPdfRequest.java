package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SalesReportPdfRequest {
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
        @SerializedName("Month")
        String Month;
        @SerializedName("totalsales")
        String totalsales;
        @SerializedName("monthtotalsales")
        String monthtotalsales;

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

        public String getMonth() {
            return Month;
        }

        public void setMonth(String month) {
            Month = month;
        }

        public String getTotalsales() {
            return totalsales;
        }

        public void setTotalsales(String totalsales) {
            this.totalsales = totalsales;
        }

        public String getMonthtotalsales() {
            return monthtotalsales;
        }

        public void setMonthtotalsales(String monthtotalsales) {
            this.monthtotalsales = monthtotalsales;
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
    "PDFName":"SalesReport",
    "RecordList":[
        {
   "srno":"1",
  "Businessname":"abcevechile",
  "ClientVillage":"dfgdg",
  "Contactno":"8055659418",
  "Period":"13to14",
  "Month":"April ",
  "totalsales":"",
  "monthtotalsales":"1200"
        },
            {
  "srno":"2",
  "Businessname":"abcevechile",
  "ClientVillage":"khed",
  "Contactno":"8055659418",
  "Period":"13to14",
  "Month":"May",
  "totalsales":"",
  "monthtotalsales":"1250"
        }]
}
 */