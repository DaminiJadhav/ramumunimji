package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class PdfDetailOutstandingRequest {
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

        @SerializedName("indexid")
        int indexid;
        @SerializedName("date")
        String date;
        @SerializedName("counts")
        String counts;
        @SerializedName("currentdate")
        Date currentdate;
        @SerializedName("regId")
        String regId;
        @SerializedName("Firstname")
        String Firstname;
        @SerializedName("BusinessName")
        String BusinessName;
        @SerializedName("Village")
        String Village;
        @SerializedName("ContactNo")
        String ContactNo;
        @SerializedName("ID")
        String ID;
        @SerializedName("CFirstname")
        String CFirstname;
        @SerializedName("Outstanding")
        String Outstanding;
        @SerializedName("TransactionId")
        String TransactionId;
        @SerializedName("BillDate")
        Date BillDate;
        @SerializedName("CrAmt")
        String CrAmt;
        @SerializedName("DrAmt")
        String DrAmt;
        @SerializedName("Purpose")
        String Purpose;


        public int getIndexid() {
            return indexid;
        }

        public void setIndexid(int indexid) {
            this.indexid = indexid;
        }

        public Date getCurrentdate() {
            return currentdate;
        }

        public void setCurrentdate(Date currentdate) {
            this.currentdate = currentdate;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCounts() {
            return counts;
        }

        public void setCounts(String counts) {
            this.counts = counts;
        }


        public String getRegId() {
            return regId;
        }

        public void setRegId(String regId) {
            this.regId = regId;
        }

        public String getFirstname() {
            return Firstname;
        }

        public void setFirstname(String firstname) {
            Firstname = firstname;
        }

        public String getBusinessName() {
            return BusinessName;
        }

        public void setBusinessName(String businessName) {
            BusinessName = businessName;
        }

        public String getVillage() {
            return Village;
        }

        public void setVillage(String village) {
            Village = village;
        }

        public String getContactNo() {
            return ContactNo;
        }

        public void setContactNo(String contactNo) {
            ContactNo = contactNo;
        }


        public String getCFirstname() {
            return CFirstname;
        }

        public void setCFirstname(String CFirstname) {
            this.CFirstname = CFirstname;
        }

        public String getOutstanding() {
            return Outstanding;
        }

        public void setOutstanding(String outstanding) {
            Outstanding = outstanding;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public void setTransactionId(String transactionId) {
            TransactionId = transactionId;
        }

        public Date getBillDate() {
            return BillDate;
        }

        public void setBillDate(Date billDate) {
            BillDate = billDate;
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
	"regId":"",
	"PDFName":"",
	"RecordList":[
		{
			"date":"",
"counts":"",
"currentdate":"",
"regId":"",
"Firstname":"",
"BusinessName":"",
"Village":"",
"ContactNo":"",
"ID":"",
"CFirstname":"",
"Outstanding":"",
"TransactionId":"",
"BillDate":"",
"CrAmt":"",
"DrAmt":"",
"Purpose":""
		}]

}
 */


/*
{
	"regId":"15",
	"PDFName":"C15CustomReport",
	"RecordList":[
		{
            "indexid": 1,  //increment by 1 for each transaction for all transaction
            "date": "01-06-2020 To 02-06-2020",  // (filter name or date) same record in list for all transaction
            "counts": "22",   //same record in list for all transaction
            "currentdate": "08-06-2020 18:27:19",  //for pdf created on date same record in list for all transaction
            "regId": null,
            "Firstname": "विठ्ठल शेलार",
            "BusinessName": "JASMINE Dairy Farm",
            "Village": "Belwandi",
            "ContactNo": "9881901732",
            "ID": 114,
            "CFirstname": "तुकाराम शिंदे",
            "Outstanding":"4240.00",
            "TransactionId": "R3",
            "BillDate": "2020-05-07T00:00:00",
            "CrAmt": null,
            "DrAmt": 20120.00,
            "Purpose": "औषध विज्ञान बी-बियाणे बीड नंबर 140"
		},
			{
            "indexid": 2,
            "date": "01-06-2020 To 02-06-2020",
            "counts": "22",
            "currentdate": "08-06-2020 18:27:19",
            "regId": null,
            "Firstname": "विठ्ठल शेलार",
            "BusinessName": "JASMINE Dairy Farm",
            "Village": "Belwandi",
            "ContactNo": "9881901732",
            "ID": 114,
            "CFirstname": "तुकाराम शिंदे",
            "Outstanding": "0.00",
            "TransactionId": "R3",
            "BillDate": "2020-05-07T00:00:00",
            "CrAmt": null,
            "DrAmt": 20120.00,
            "Purpose": "औषध विज्ञान बी-बियाणे बीड नंबर 140"
		}]

}
 */