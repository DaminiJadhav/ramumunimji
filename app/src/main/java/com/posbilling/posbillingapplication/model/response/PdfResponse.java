package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

public class PdfResponse {
    @SerializedName("status")
    String status;
    @SerializedName("Message")
    String Message;
    @SerializedName("Pdfpath")
    String Pdfpath;

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

    public String getPdfpath() {
        return Pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        Pdfpath = pdfpath;
    }
}
/*
 "": 1,
    "": "PDF",
    "": "~/pdfReport/C15CustomReport.pdf"
 */
