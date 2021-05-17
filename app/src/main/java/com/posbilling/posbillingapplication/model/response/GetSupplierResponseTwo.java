package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSupplierResponseTwo {
    @SerializedName("status")
    String status;
    @SerializedName("regId")
    String regId;

    @SerializedName("Data")
    ArrayList<Data> dataArrayList = new ArrayList<Data>();

    public class Data{
        @SerializedName("ID")
        String ID;
        @SerializedName("SupplierId")
        String SupplierId;
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("SFirstname")
        String SFirstname;
        @SerializedName("SContactNo")
        String SContactNo;
        @SerializedName("SAlternetMobNo")
        String SAlternetMobNo;
        @SerializedName("Village")
        String Village;
        @SerializedName("Taluka")
        String Taluka;
        @SerializedName("District")
        String District;
        @SerializedName("StateId")
        String StateId;
        @SerializedName("Country")
        String Country;
        @SerializedName("PaymentFreq")
        String PaymentFreq;
        @SerializedName("SMSFlag")
        String SMSFlag;
        @SerializedName("Imagename")
        String Imagename;
        @SerializedName("ImagePath")
        String ImagePath;
        @SerializedName("Kharedi")
        String Kharedi;
        @SerializedName("Jama")
        String Jama;
        @SerializedName("Outstanding")
        String Outstanding;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getSupplierId() {
            return SupplierId;
        }

        public void setSupplierId(String supplierId) {
            SupplierId = supplierId;
        }

        public String getClientId() {
            return ClientId;
        }

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public String getSFirstname() {
            return SFirstname;
        }

        public void setSFirstname(String SFirstname) {
            this.SFirstname = SFirstname;
        }

        public String getSContactNo() {
            return SContactNo;
        }

        public void setSContactNo(String SContactNo) {
            this.SContactNo = SContactNo;
        }

        public String getSAlternetMobNo() {
            return SAlternetMobNo;
        }

        public void setSAlternetMobNo(String SAlternetMobNo) {
            this.SAlternetMobNo = SAlternetMobNo;
        }

        public String getVillage() {
            return Village;
        }

        public void setVillage(String village) {
            Village = village;
        }

        public String getTaluka() {
            return Taluka;
        }

        public void setTaluka(String taluka) {
            Taluka = taluka;
        }

        public String getDistrict() {
            return District;
        }

        public void setDistrict(String district) {
            District = district;
        }

        public String getStateId() {
            return StateId;
        }

        public void setStateId(String stateId) {
            StateId = stateId;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public String getPaymentFreq() {
            return PaymentFreq;
        }

        public void setPaymentFreq(String paymentFreq) {
            PaymentFreq = paymentFreq;
        }

        public String getSMSFlag() {
            return SMSFlag;
        }

        public void setSMSFlag(String SMSFlag) {
            this.SMSFlag = SMSFlag;
        }

        public String getImagename() {
            return Imagename;
        }

        public void setImagename(String imagename) {
            Imagename = imagename;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String imagePath) {
            ImagePath = imagePath;
        }

        public String getKharedi() {
            return Kharedi;
        }

        public void setKharedi(String kharedi) {
            Kharedi = kharedi;
        }

        public String getJama() {
            return Jama;
        }

        public void setJama(String jama) {
            Jama = jama;
        }

        public String getOutstanding() {
            return Outstanding;
        }

        public void setOutstanding(String outstanding) {
            Outstanding = outstanding;
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

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }
}
/*
// 20200804184159
// https://qa.ramumunimji.com/api/SupplierRegister/GettblSupplier?regId=14&SupplierId=

{
  "status": 1,
  "regId": 14,
  "Data": [
    {
      "ID": 12,
      "SupplierId": "S141",
      "ClientId": 14,
      "SFirstname": "अंकुश शिंदे",
      "SContactNo": "",
      "SAlternetMobNo": "",
      "Village": "",
      "Taluka": "",
      "District": "",
      "StateId": 14,
      "Country": "India",
      "PaymentFreq": "monthly",
      "SMSFlag": false,
      "Imagename": "S141",
      "ImagePath": null,
      "Kharedi": 3000.00,
      "Jama": 0.00,
      "Outstanding": 3000.00
    },
    {
      "ID": 13,
      "SupplierId": "C142",
      "ClientId": 14,
      "SFirstname": "अंकुर टेस्टिंग   ",
      "SContactNo": "8055659418",
      "SAlternetMobNo": "",
      "Village": "",
      "Taluka": "",
      "District": "",
      "StateId": 14,
      "Country": "India",
      "PaymentFreq": "monthly",
      "SMSFlag": false,
      "Imagename": "C142",
      "ImagePath": null,
      "Kharedi": 3000.00,
      "Jama": 0.00,
      "Outstanding": 3000.00
    },
    {
      "ID": 18,
      "SupplierId": "C143",
      "ClientId": 14,
      "SFirstname": "ऋषिकेश",
      "SContactNo": "",
      "SAlternetMobNo": "",
      "Village": "",
      "Taluka": "",
      "District": "",
      "StateId": 14,
      "Country": "India",
      "PaymentFreq": "monthly",
      "SMSFlag": false,
      "Imagename": "C143",
      "ImagePath": null,
      "Kharedi": 6464.00,
      "Jama": 0.00,
      "Outstanding": 6464.00
    },
    {
      "ID": 19,
      "SupplierId": "C144",
      "ClientId": 14,
      "SFirstname": "baba   ",
      "SContactNo": "",
      "SAlternetMobNo": "",
      "Village": "",
      "Taluka": "",
      "District": "",
      "StateId": 14,
      "Country": "India",
      "PaymentFreq": "monthly",
      "SMSFlag": false,
      "Imagename": "C144",
      "ImagePath": null,
      "Kharedi": 94.00,
      "Jama": 0.00,
      "Outstanding": 94.00
    },
    {
      "ID": 20,
      "SupplierId": "C145",
      "ClientId": 14,
      "SFirstname": "ऋषिकेश",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 5000.00,
      "Jama": 0.00,
      "Outstanding": 5000.00
    },
    {
      "ID": 21,
      "SupplierId": "C146",
      "ClientId": 14,
      "SFirstname": "ऋषिकेश दुसरा",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 0.00,
      "Jama": 3000.00,
      "Outstanding": -3000.00
    },
    {
      "ID": 22,
      "SupplierId": "C147",
      "ClientId": 14,
      "SFirstname": "अंकुर",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 1000.00,
      "Jama": 0.00,
      "Outstanding": 1000.00
    },
    {
      "ID": 23,
      "SupplierId": "C148",
      "ClientId": 14,
      "SFirstname": "अंकुर",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 200.00,
      "Jama": 0.00,
      "Outstanding": 200.00
    },
    {
      "ID": 24,
      "SupplierId": "C149",
      "ClientId": 14,
      "SFirstname": "अंकुर shinde",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 6000.00,
      "Jama": 8000.00,
      "Outstanding": -2000.00
    },
    {
      "ID": 25,
      "SupplierId": "C1410",
      "ClientId": 14,
      "SFirstname": "अंकुर shinde",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 2000.00,
      "Jama": 0.00,
      "Outstanding": 2000.00
    },
    {
      "ID": 39,
      "SupplierId": "C1411",
      "ClientId": 14,
      "SFirstname": "jcucuci",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 88686.00,
      "Jama": 0.00,
      "Outstanding": 88686.00
    },
    {
      "ID": 49,
      "SupplierId": "C1414",
      "ClientId": 14,
      "SFirstname": "asdf",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 45.00,
      "Jama": 0.00,
      "Outstanding": 45.00
    },
    {
      "ID": 59,
      "SupplierId": "C1422",
      "ClientId": 14,
      "SFirstname": "cjdnj",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 3929.00,
      "Jama": 0.00,
      "Outstanding": 3929.00
    },
    {
      "ID": 62,
      "SupplierId": "C1424",
      "ClientId": 14,
      "SFirstname": "3nd supplier12",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 100.00,
      "Jama": 0.00,
      "Outstanding": 100.00
    },
    {
      "ID": 63,
      "SupplierId": "C1425",
      "ClientId": 14,
      "SFirstname": "baba",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 4994.00,
      "Jama": 9797.00,
      "Outstanding": -4803.00
    },
    {
      "ID": 64,
      "SupplierId": "C1426",
      "ClientId": 14,
      "SFirstname": "test supplier",
      "SContactNo": null,
      "SAlternetMobNo": null,
      "Village": null,
      "Taluka": null,
      "District": null,
      "StateId": null,
      "Country": null,
      "PaymentFreq": null,
      "SMSFlag": null,
      "Imagename": null,
      "ImagePath": null,
      "Kharedi": 1000.00,
      "Jama": 500.00,
      "Outstanding": 500.00
    }
  ]
}
 */