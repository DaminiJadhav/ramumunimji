package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

public class ClientUpdateResponse {
    @SerializedName("status")
    String status;
    @SerializedName("Message")
    String Message;
    @SerializedName("ImagePath")
    String ImagePath;

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
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
}

/*
{
    "status": 1,
    "Message": "Data Saved"
}
 */
