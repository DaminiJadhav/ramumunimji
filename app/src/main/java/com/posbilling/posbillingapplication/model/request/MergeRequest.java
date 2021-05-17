package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

public class MergeRequest {
    @SerializedName("regId")
    String regId;
    @SerializedName("SourceCustomerId")
    String SourceCustomerId;
    @SerializedName("DestinationCustomerId")
    String DestinationCustomerId;

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public void setSourceCustomerId(String sourceCustomerId) {
        SourceCustomerId = sourceCustomerId;
    }

    public void setDestinationCustomerId(String destinationCustomerId) {
        DestinationCustomerId = destinationCustomerId;
    }
}

/*
{
        "regId":"14",
        "SourceCustomerId":"45",
        "DestinationCustomerId":"44"
}
 */