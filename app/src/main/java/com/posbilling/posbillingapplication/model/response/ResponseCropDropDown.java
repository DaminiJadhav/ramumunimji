package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseCropDropDown {
    @SerializedName("status")
    private String status;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Data")
    ArrayList<Data> dataArrayList = new ArrayList<Data>();

    public class Data {
        @SerializedName("Id")
        String Id;
        @SerializedName("Cropname")
        String Cropname;
        @SerializedName("PerticidesTimesPerAcre")
        String PerticidesTimesPerAcre;
        @SerializedName("FertiliseTimesPerAcre")
        String FertiliseTimesPerAcre;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getCropname() {
            return Cropname;
        }

        public void setCropname(String cropname) {
            Cropname = cropname;
        }

        public String getPerticidesTimesPerAcre() {
            return PerticidesTimesPerAcre;
        }

        public void setPerticidesTimesPerAcre(String perticidesTimesPerAcre) {
            PerticidesTimesPerAcre = perticidesTimesPerAcre;
        }

        public String getFertiliseTimesPerAcre() {
            return FertiliseTimesPerAcre;
        }

        public void setFertiliseTimesPerAcre(String fertiliseTimesPerAcre) {
            FertiliseTimesPerAcre = fertiliseTimesPerAcre;
        }
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

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }
}


/*
{
    "status": 1,
    "Message": "Crop Data",
    "Data": [
        {
            "Id": 1,
            "Cropname": "Tomato",
            "PerticidesTimesPerAcre": 40,
            "FertiliseTimesPerAcre": 3
        },
        {
            "Id": 2,
            "Cropname": "Lemon",
            "PerticidesTimesPerAcre": 100,
            "FertiliseTimesPerAcre": 4
        },
        {
            "Id": 3,
            "Cropname": "Sugarcanre",
            "PerticidesTimesPerAcre": 10,
            "FertiliseTimesPerAcre": 4
        },
        {
            "Id": 4,
            "Cropname": "Groundnut",
            "PerticidesTimesPerAcre": 50,
            "FertiliseTimesPerAcre": 7
        },
        {
            "Id": 5,
            "Cropname": "sugar",
            "PerticidesTimesPerAcre": 11,
            "FertiliseTimesPerAcre": 9
        },
        {
            "Id": 6,
            "Cropname": "sugarrr",
            "PerticidesTimesPerAcre": 10,
            "FertiliseTimesPerAcre": 9
        },
        {
            "Id": 7,
            "Cropname": "",
            "PerticidesTimesPerAcre": 0,
            "FertiliseTimesPerAcre": 0
        },
        {
            "Id": 8,
            "Cropname": "",
            "PerticidesTimesPerAcre": 0,
            "FertiliseTimesPerAcre": 0
        },
        {
            "Id": 9,
            "Cropname": "",
            "PerticidesTimesPerAcre": 0,
            "FertiliseTimesPerAcre": 0
        },
        {
            "Id": 10,
            "Cropname": "",
            "PerticidesTimesPerAcre": 0,
            "FertiliseTimesPerAcre": 0
        },
        {
            "Id": 11,
            "Cropname": "",
            "PerticidesTimesPerAcre": 0,
            "FertiliseTimesPerAcre": 0
        },
        {
            "Id": 12,
            "Cropname": "",
            "PerticidesTimesPerAcre": 0,
            "FertiliseTimesPerAcre": 0
        },
        {
            "Id": 13,
            "Cropname": "",
            "PerticidesTimesPerAcre": 0,
            "FertiliseTimesPerAcre": 0
        }
    ]
}
 */