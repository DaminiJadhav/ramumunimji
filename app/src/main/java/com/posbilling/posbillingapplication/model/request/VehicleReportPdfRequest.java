package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class VehicleReportPdfRequest {
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

    public class RecordList{
        @SerializedName("vsrno")
        String vsrno;
        @SerializedName("contactno")
        String contactno;
        @SerializedName("businessname")
        String businessname;
        @SerializedName("clientvillage")
        String clientvillage;
        @SerializedName("customername")
        String customername;
        @SerializedName("vehicleno")
        String vehicleno;
        @SerializedName("vehicletype")
        String vehicletype;
        @SerializedName("date")
        String date;
        @SerializedName("servicedetail")
        String servicedetail;
        @SerializedName("nextservicedate")
        String nextservicedate;
        @SerializedName("milage")
        String milage;

        public String getVsrno() {
            return vsrno;
        }

        public void setVsrno(String vsrno) {
            this.vsrno = vsrno;
        }

        public String getContactno() {
            return contactno;
        }

        public void setContactno(String contactno) {
            this.contactno = contactno;
        }

        public String getBusinessname() {
            return businessname;
        }

        public void setBusinessname(String businessname) {
            this.businessname = businessname;
        }

        public String getClientvillage() {
            return clientvillage;
        }

        public void setClientvillage(String clientvillage) {
            this.clientvillage = clientvillage;
        }

        public String getCustomername() {
            return customername;
        }

        public void setCustomername(String customername) {
            this.customername = customername;
        }

        public String getVehicleno() {
            return vehicleno;
        }

        public void setVehicleno(String vehicleno) {
            this.vehicleno = vehicleno;
        }

        public String getVehicletype() {
            return vehicletype;
        }

        public void setVehicletype(String vehicletype) {
            this.vehicletype = vehicletype;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String  date) {
            this.date = date;
        }

        public String getServicedetail() {
            return servicedetail;
        }

        public void setServicedetail(String servicedetail) {
            this.servicedetail = servicedetail;
        }

        public String getNextservicedate() {
            return nextservicedate;
        }

        public void setNextservicedate(String nextservicedate) {
            this.nextservicedate = nextservicedate;
        }

        public String getMilage() {
            return milage;
        }

        public void setMilage(String milage) {
            this.milage = milage;
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
    "PDFName":"VehicleReports",
    "langID":"",
    "RecordList":[
        {
           "vsrno":"1",
  "contactno":"8055659418",
  "businessname":"Vehicle Serveice Center",
  "clientvillage":"pune",
  "customername":"nikhil saheb",
  "vehicleno":"MH14AC7804",
  "vehicletype":"Schooty plasure",
  "date":"2020-06-23 09:17:08.243",
  "servicedetail":"Servicing",
  "nextservicedate":"2020-06-23 09:17:08.243",
  "milage":"1002KM"
   },
        {
           "vsrno":"2",
  "contactno":"8055659418",
  "businessname":"Vehicle Serveice Center",
  "clientvillage":"pune",
  "customername":"nikhil saheb",
  "vehicleno":"MH14AC7804",
  "vehicletype":"Schooty plasure",
  "date":"2020-06-23 09:17:08.243",
  "servicedetail":"Servicing",
  "nextservicedate":"2020-06-23 09:17:08.243",
  "milage":"1002KM"
   }]
}
 */
