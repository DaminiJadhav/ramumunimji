package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MergeApiResponse {
    @SerializedName("status")
    String status;
    @SerializedName("Message")
    String Message;
    @SerializedName("Data")
    ArrayList<Data> dataArrayList = new ArrayList<Data>();

    public class Data {
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("CFirstname")
        String CFirstname;
        @SerializedName("CLastname")
        String CLastname;
        @SerializedName("Imagename")
        String Imagename;
        @SerializedName("ImagePath")
        String ImagePath;
        @SerializedName("CContactNo")
        String CContactNo;
        @SerializedName("ID")
        String ID;
        @SerializedName("TransactionId")
        String TransactionId;
        @SerializedName("BillDate")
        String BillDate;
        @SerializedName("Time")
        String Time;
        @SerializedName("CrAmt")
        String CrAmt;
        @SerializedName("DrAmt")
        String DrAmt;
        @SerializedName("Purpose")
        String Purpose;
        @SerializedName("Liter")
        String Liter;
        @SerializedName("PaymentStatus")
        String PaymentStatus;

        public String getClientId() {
            return ClientId;
        }

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public String getCFirstname() {
            return CFirstname;
        }

        public void setCFirstname(String CFirstname) {
            this.CFirstname = CFirstname;
        }

        public String getCLastname() {
            return CLastname;
        }

        public void setCLastname(String CLastname) {
            this.CLastname = CLastname;
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

        public String getCContactNo() {
            return CContactNo;
        }

        public void setCContactNo(String CContactNo) {
            this.CContactNo = CContactNo;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public void setTransactionId(String transactionId) {
            TransactionId = transactionId;
        }

        public String getBillDate() {
            return BillDate;
        }

        public void setBillDate(String billDate) {
            BillDate = billDate;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
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

        public String getLiter() {
            return Liter;
        }

        public void setLiter(String liter) {
            Liter = liter;
        }

        public String getPaymentStatus() {
            return PaymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            PaymentStatus = paymentStatus;
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

        /*    "":[

    {
        "ClientId":36,
            "CustomerId":43,
            "CFirstname":"Ankur",
            "CLastname":null,
            "Imagename":null,
            "ImagePath":null,
            "CContactNo":null,
            "ID":67,
            "TransactionId":"R1",
            "BillDate":"2020-05-05T00:00:00",
            "Time":"15:56:00",
            "CrAmt":0.00,
            "DrAmt":100.00,
            "Purpose":"testing",
            "Liter":10,
            "PaymentStatus":null
    },

    {
        "ClientId":36,
            "CustomerId":43,
            "CFirstname":"Ankur",
            "CLastname":null,
            "Imagename":null,
            "ImagePath":null,
            "CContactNo":null,
            "ID":68,
            "TransactionId":"R2",
            "BillDate":"2020-05-05T00:00:00",
            "Time":"16:01:00",
            "CrAmt":0.00,
            "DrAmt":100.00,
            "Purpose":"testing",
            "Liter":10,
            "PaymentStatus":null
    },

    {
        "ClientId":36,
            "CustomerId":43,
            "CFirstname":"Ankur",
            "CLastname":null,
            "Imagename":null,
            "ImagePath":null,
            "CContactNo":null,
            "ID":69,
            "TransactionId":"R3",
            "BillDate":"2020-05-05T00:00:00",
            "Time":"16:09:00",
            "CrAmt":0.00,
            "DrAmt":200.00,
            "Purpose":"testing",
            "Liter":20,
            "PaymentStatus":null
    }
        ]
}

}*/

/*public class MergeApiResponse {
    @SerializedName("status")
    String status;
    @SerializedName("Message")
    String Message;

    @SerializedName("Data")
    Data data;

    public class Data {
        @SerializedName("tblClientMaster")
        tblClientMaster tblClientMasterObject;

        public class tblClientMaster {
            @SerializedName("tblCustomers")
            ArrayList<tblCustomers> tblCustomersArrayList = new ArrayList<tblCustomers>();

            public class tblCustomers {
                @SerializedName("ID")
                String ID;
                @SerializedName("CustomerId")
                String CustomerId;
                @SerializedName("ClientId")
                String ClientId;
                @SerializedName("CFirstname")
                String CFirstname;
                @SerializedName("CMiddleName")
                String CMiddleName;
                @SerializedName("CLastname")
                String CLastname;
                @SerializedName("CContactNo")
                String CContactNo;
                @SerializedName("CAlternetMobNo")
                String CAlternetMobNo;
                @SerializedName("CEmail")
                String CEmail;
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
                @SerializedName("Outstanding")
                String Outstanding;
                @SerializedName("CreatedOn")
                String CreatedOn;
                @SerializedName("UpdatedOn")
                String UpdatedOn;
                @SerializedName("CreatedBy")
                String CreatedBy;
                @SerializedName("UpdatedBy")
                String UpdatedBy;
                @SerializedName("IsActive")
                String IsActive;

                public String getID() {
                    return ID;
                }

                public void setID(String ID) {
                    this.ID = ID;
                }

                public String getCustomerId() {
                    return CustomerId;
                }

                public void setCustomerId(String customerId) {
                    CustomerId = customerId;
                }

                public String getClientId() {
                    return ClientId;
                }

                public void setClientId(String clientId) {
                    ClientId = clientId;
                }

                public String getCFirstname() {
                    return CFirstname;
                }

                public void setCFirstname(String CFirstname) {
                    this.CFirstname = CFirstname;
                }

                public String getCMiddleName() {
                    return CMiddleName;
                }

                public void setCMiddleName(String CMiddleName) {
                    this.CMiddleName = CMiddleName;
                }

                public String getCLastname() {
                    return CLastname;
                }

                public void setCLastname(String CLastname) {
                    this.CLastname = CLastname;
                }

                public String getCContactNo() {
                    return CContactNo;
                }

                public void setCContactNo(String CContactNo) {
                    this.CContactNo = CContactNo;
                }

                public String getCAlternetMobNo() {
                    return CAlternetMobNo;
                }

                public void setCAlternetMobNo(String CAlternetMobNo) {
                    this.CAlternetMobNo = CAlternetMobNo;
                }

                public String getCEmail() {
                    return CEmail;
                }

                public void setCEmail(String CEmail) {
                    this.CEmail = CEmail;
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

                public String getOutstanding() {
                    return Outstanding;
                }

                public void setOutstanding(String outstanding) {
                    Outstanding = outstanding;
                }

                public String getCreatedOn() {
                    return CreatedOn;
                }

                public void setCreatedOn(String createdOn) {
                    CreatedOn = createdOn;
                }

                public String getUpdatedOn() {
                    return UpdatedOn;
                }

                public void setUpdatedOn(String updatedOn) {
                    UpdatedOn = updatedOn;
                }

                public String getCreatedBy() {
                    return CreatedBy;
                }

                public void setCreatedBy(String createdBy) {
                    CreatedBy = createdBy;
                }

                public String getUpdatedBy() {
                    return UpdatedBy;
                }

                public void setUpdatedBy(String updatedBy) {
                    UpdatedBy = updatedBy;
                }

                public String getIsActive() {
                    return IsActive;
                }

                public void setIsActive(String isActive) {
                    IsActive = isActive;
                }
            }

            public ArrayList<tblCustomers> getTblCustomersArrayList() {
                return tblCustomersArrayList;
            }

            public void setTblCustomersArrayList(ArrayList<tblCustomers> tblCustomersArrayList) {
                this.tblCustomersArrayList = tblCustomersArrayList;
            }
        }

        @SerializedName("tblInvices")
        ArrayList<tblInvices> tblInvicesArrayList = new ArrayList<tblInvices>();

        public class tblInvices {
            @SerializedName("ID")
            String ID;
            @SerializedName("ClientId")
            String ClientId;
            @SerializedName("CustomerId")
            String CustomerId;
            @SerializedName("TransactionId")
            String TransactionId;
            @SerializedName("BillDate")
            String BillDate;
            @SerializedName("Time")
            String Time;
            @SerializedName("Liter")
            String Liter;
            @SerializedName("DiscountAmt")
            String DiscountAmt;
            @SerializedName("CrAmt")
            String CrAmt;
            @SerializedName("DrAmt")
            String DrAmt;
            @SerializedName("Purpose")
            String Purpose;
            @SerializedName("DeviceId")
            String DeviceId;
            @SerializedName("PaymentStatus")
            String PaymentStatus;
            @SerializedName("CreatedOn")
            String CreatedOn;
            @SerializedName("UpdatedOn")
            String UpdatedOn;
            @SerializedName("CreatedBy")
            String CreatedBy;
            @SerializedName("UpdatedBy")
            String UpdatedBy;
            @SerializedName("IsActive")
            String IsActive;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getClientId() {
                return ClientId;
            }

            public void setClientId(String clientId) {
                ClientId = clientId;
            }

            public String getCustomerId() {
                return CustomerId;
            }

            public void setCustomerId(String customerId) {
                CustomerId = customerId;
            }

            public String getTransactionId() {
                return TransactionId;
            }

            public void setTransactionId(String transactionId) {
                TransactionId = transactionId;
            }

            public String getBillDate() {
                return BillDate;
            }

            public void setBillDate(String billDate) {
                BillDate = billDate;
            }

            public String getTime() {
                return Time;
            }

            public void setTime(String time) {
                Time = time;
            }

            public String getLiter() {
                return Liter;
            }

            public void setLiter(String liter) {
                Liter = liter;
            }

            public String getDiscountAmt() {
                return DiscountAmt;
            }

            public void setDiscountAmt(String discountAmt) {
                DiscountAmt = discountAmt;
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

            public String getDeviceId() {
                return DeviceId;
            }

            public void setDeviceId(String deviceId) {
                DeviceId = deviceId;
            }

            public String getPaymentStatus() {
                return PaymentStatus;
            }

            public void setPaymentStatus(String paymentStatus) {
                PaymentStatus = paymentStatus;
            }

            public String getCreatedOn() {
                return CreatedOn;
            }

            public void setCreatedOn(String createdOn) {
                CreatedOn = createdOn;
            }

            public String getUpdatedOn() {
                return UpdatedOn;
            }

            public void setUpdatedOn(String updatedOn) {
                UpdatedOn = updatedOn;
            }

            public String getCreatedBy() {
                return CreatedBy;
            }

            public void setCreatedBy(String createdBy) {
                CreatedBy = createdBy;
            }

            public String getUpdatedBy() {
                return UpdatedBy;
            }

            public void setUpdatedBy(String updatedBy) {
                UpdatedBy = updatedBy;
            }

            public String getIsActive() {
                return IsActive;
            }

            public void setIsActive(String isActive) {
                IsActive = isActive;
            }
        }

        public tblClientMaster getTblClientMasterObject() {
            return tblClientMasterObject;
        }

        public void setTblClientMasterObject(tblClientMaster tblClientMasterObject) {
            this.tblClientMasterObject = tblClientMasterObject;
        }

        public ArrayList<tblInvices> getTblInvicesArrayList() {
            return tblInvicesArrayList;
        }

        public void setTblInvicesArrayList(ArrayList<tblInvices> tblInvicesArrayList) {
            this.tblInvicesArrayList = tblInvicesArrayList;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}*/





/*{
        "status": 1,
        "Message": "Row Udpdated",
        "Data": [
        {
        "ClientId": 36,
        "CustomerId": 43,
        "CFirstname": "Ankur",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 67,
        "TransactionId": "R1",
        "BillDate": "2020-05-05T00:00:00",
        "Time": "15:56:00",
        "CrAmt": 0.00,
        "DrAmt": 100.00,
        "Purpose": "testing",
        "Liter": 10,
        "PaymentStatus": null
        },
        {
        "ClientId": 36,
        "CustomerId": 43,
        "CFirstname": "Ankur",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 68,
        "TransactionId": "R2",
        "BillDate": "2020-05-05T00:00:00",
        "Time": "16:01:00",
        "CrAmt": 0.00,
        "DrAmt": 100.00,
        "Purpose": "testing",
        "Liter": 10,
        "PaymentStatus": null
        },
        {
        "ClientId": 36,
        "CustomerId": 43,
        "CFirstname": "Ankur",
        "CLastname": null,
        "Imagename": null,
        "ImagePath": null,
        "CContactNo": null,
        "ID": 69,
        "TransactionId": "R3",
        "BillDate": "2020-05-05T00:00:00",
        "Time": "16:09:00",
        "CrAmt": 0.00,
        "DrAmt": 200.00,
        "Purpose": "testing",
        "Liter": 20,
        "PaymentStatus": null
        }
        ]
        }*/



/*
{
    "status": 1,
    "Message": "Row Udpdated",
    "Data": {
        "tblClientMaster": {
            "BusinessDetails": [
                {
                    "Id": 14,
                    "RegId": 14,
                    "BusinessName": "Centaur",
                    "ShopeLicence": null,
                    "LanguageId": null,
                    "CreatedOn": "2020-04-19T22:51:39.723",
                    "UpdateOn": "2020-04-19T23:04:00",
                    "CreatedBy": "shelar",
                    "UpdateBy": "shelar",
                    "IsActive": true
                }
            ],
            "tblClientProfiles": [
                {
                    "Id": 14,
                    "Regid": 14,
                    "Imagename": "images.jpg",
                    "ImagePath": "images//Ramuji//images.jpg",
                    "DOB": null,
                    "AlternetContactNo": "",
                    "Address": null,
                    "Village": "Kothrud",
                    "Taluka": "Haveli",
                    "District": "Pune",
                    "StateId": 14,
                    "Country": "India",
                    "CreatedOn": "2020-04-19T22:51:39.723",
                    "UpdateOn": "2020-04-19T23:04:00",
                    "CreateBy": "shelar",
                    "UpdatedBy": "shelar",
                    "IsActive": true
                }
            ],
            "tblClientStatus": [
                {
                    "Id": 14,
                    "RegId": 14,
                    "ActiveDay": 2,
                    "MasterId": null,
                    "SessionId": null,
                    "DeviceId": null,
                    "Onetimepass": "1896",
                    "LanguageId": null,
                    "StateId": null,
                    "CreatedOn": "2020-04-19T23:34:44.83",
                    "UpdatedOn": "2020-04-19T23:34:44.83",
                    "CreatedBy": "shelar",
                    "UpdatedBy": "Shubham Pardeshi",
                    "IsActive": true
                }
            ],
            "tblCustomers": [
                {
                    "tblInvices": [],
                    "ID": 45,
                    "CustomerId": "C141",
                    "ClientId": 14,
                    "CFirstname": "",
                    "CMiddleName": null,
                    "CLastname": null,
                    "CContactNo": null,
                    "CAlternetMobNo": null,
                    "CEmail": null,
                    "Village": null,
                    "Taluka": null,
                    "District": null,
                    "StateId": null,
                    "Country": null,
                    "PaymentFreq": null,
                    "SMSFlag": null,
                    "Imagename": null,
                    "ImagePath": null,
                    "Outstanding": 1898.00,
                    "CreatedOn": "2020-04-19T23:16:28.067",
                    "UpdatedOn": "2020-04-20T14:47:02.027",
                    "CreatedBy": "Shubham Pardeshi",
                    "UpdatedBy": "Shubham RamuMunimji.Models.tblInvice",
                    "IsActive": false
                },
                {
                    "tblInvices": [],
                    "ID": 39,
                    "CustomerId": "C141",
                    "ClientId": 14,
                    "CFirstname": "Rakesh",
                    "CMiddleName": null,
                    "CLastname": null,
                    "CContactNo": "",
                    "CAlternetMobNo": "",
                    "CEmail": "",
                    "Village": "Gujarat Colony",
                    "Taluka": "Kothrud",
                    "District": "Pune",
                    "StateId": 14,
                    "Country": "INDIA",
                    "PaymentFreq": "0",
                    "SMSFlag": null,
                    "Imagename": "C141",
                    "ImagePath": "images//Ramuji//C141.jpg",
                    "Outstanding": null,
                    "CreatedOn": "2020-04-19T23:09:00",
                    "UpdatedOn": null,
                    "CreatedBy": "14",
                    "UpdatedBy": "",
                    "IsActive": true
                },
                {
                    "tblInvices": [],
                    "ID": 40,
                    "CustomerId": "C141",
                    "ClientId": 14,
                    "CFirstname": "Rakesh",
                    "CMiddleName": null,
                    "CLastname": null,
                    "CContactNo": null,
                    "CAlternetMobNo": null,
                    "CEmail": null,
                    "Village": null,
                    "Taluka": null,
                    "District": null,
                    "StateId": null,
                    "Country": null,
                    "PaymentFreq": null,
                    "SMSFlag": null,
                    "Imagename": null,
                    "ImagePath": null,
                    "Outstanding": 30.00,
                    "CreatedOn": "2020-04-19T23:12:16.37",
                    "UpdatedOn": "2020-04-19T23:12:19.857",
                    "CreatedBy": "Shubham Pardeshi",
                    "UpdatedBy": "Shubham RamuMunimji.Models.tblInvice",
                    "IsActive": true
                },
                {
                    "tblInvices": [],
                    "ID": 41,
                    "CustomerId": "C141",
                    "ClientId": 14,
                    "CFirstname": "Rakesh",
                    "CMiddleName": null,
                    "CLastname": null,
                    "CContactNo": null,
                    "CAlternetMobNo": null,
                    "CEmail": null,
                    "Village": null,
                    "Taluka": null,
                    "District": null,
                    "StateId": null,
                    "Country": null,
                    "PaymentFreq": null,
                    "SMSFlag": null,
                    "Imagename": null,
                    "ImagePath": null,
                    "Outstanding": 30.00,
                    "CreatedOn": "2020-04-19T23:12:16.543",
                    "UpdatedOn": "2020-04-19T23:12:20.153",
                    "CreatedBy": "Shubham Pardeshi",
                    "UpdatedBy": "Shubham RamuMunimji.Models.tblInvice",
                    "IsActive": true
                },
                {
                    "tblInvices": [],
                    "ID": 42,
                    "CustomerId": "C141",
                    "ClientId": 14,
                    "CFirstname": "राकेश",
                    "CMiddleName": null,
                    "CLastname": null,
                    "CContactNo": "",
                    "CAlternetMobNo": "",
                    "CEmail": "",
                    "Village": "कोथरूड",
                    "Taluka": "हवेली",
                    "District": "पुणे",
                    "StateId": 14,
                    "Country": "INDIA",
                    "PaymentFreq": "0",
                    "SMSFlag": null,
                    "Imagename": "C141",
                    "ImagePath": "images//Ramuji//C141.jpg",
                    "Outstanding": null,
                    "CreatedOn": "2020-04-19T23:14:00",
                    "UpdatedOn": null,
                    "CreatedBy": "14",
                    "UpdatedBy": "",
                    "IsActive": true
                },
                {
                    "tblInvices": [],
                    "ID": 43,
                    "CustomerId": "C141",
                    "ClientId": 14,
                    "CFirstname": "राकेश",
                    "CMiddleName": null,
                    "CLastname": null,
                    "CContactNo": null,
                    "CAlternetMobNo": null,
                    "CEmail": null,
                    "Village": null,
                    "Taluka": null,
                    "District": null,
                    "StateId": null,
                    "Country": null,
                    "PaymentFreq": null,
                    "SMSFlag": null,
                    "Imagename": null,
                    "ImagePath": null,
                    "Outstanding": 412.00,
                    "CreatedOn": "2020-04-19T23:16:23.817",
                    "UpdatedOn": "2020-04-19T23:16:24.3",
                    "CreatedBy": "Shubham Pardeshi",
                    "UpdatedBy": "Shubham RamuMunimji.Models.tblInvice",
                    "IsActive": true
                },
                {
                    "tblInvices": [],
                    "ID": 50,
                    "CustomerId": "C141",
                    "ClientId": 14,
                    "CFirstname": "राकेश",
                    "CMiddleName": null,
                    "CLastname": null,
                    "CContactNo": "",
                    "CAlternetMobNo": "",
                    "CEmail": "",
                    "Village": "कोथरूड",
                    "Taluka": "हवेली",
                    "District": "पुणे",
                    "StateId": 14,
                    "Country": "INDIA",
                    "PaymentFreq": "0",
                    "SMSFlag": null,
                    "Imagename": "C141",
                    "ImagePath": "images//Ramuji//C141.jpg",
                    "Outstanding": null,
                    "CreatedOn": "2020-04-19T23:35:00",
                    "UpdatedOn": null,
                    "CreatedBy": "14",
                    "UpdatedBy": "",
                    "IsActive": true
                },
                {
                    "tblInvices": [],
                    "ID": 57,
                    "CustomerId": "C141",
                    "ClientId": 14,
                    "CFirstname": "",
                    "CMiddleName": null,
                    "CLastname": null,
                    "CContactNo": null,
                    "CAlternetMobNo": null,
                    "CEmail": null,
                    "Village": null,
                    "Taluka": null,
                    "District": null,
                    "StateId": null,
                    "Country": null,
                    "PaymentFreq": null,
                    "SMSFlag": null,
                    "Imagename": null,
                    "ImagePath": null,
                    "Outstanding": 0.00,
                    "CreatedOn": "2020-04-19T23:46:09.917",
                    "UpdatedOn": "2020-04-19T23:46:13.15",
                    "CreatedBy": "Shubham Pardeshi",
                    "UpdatedBy": "Shubham RamuMunimji.Models.tblInvice",
                    "IsActive": true
                }
            ],
            "tblExpenditures": [],
            "tblPresitcidesAndFertilisers": [],
            "tblVehicleDetails": [],
            "Id": 14,
            "ClientId": "C14",
            "Firstname": "Shubham",
            "Midename": null,
            "Lastname": "Pardeshi",
            "RoleId": null,
            "BusinessId": 1,
            "ContactNo": "8830157756",
            "EmailId": "shubham.pardeshi@gmail.com",
            "ActiveDay": null,
            "SystemSMS": true,
            "PaymentMethod": "Yearly",
            "CreatedOn": "2020-04-19T22:51:39.723",
            "UpdatedOn": "2020-04-19T23:04:00",
            "CreatedBy": "shelar",
            "UpdatedBy": "shelar",
            "IsActive": true
        },
        "tblInvices": [
            {
                "tblSalesSummaries": [],
                "ID": 1,
                "ClientId": 14,
                "CustomerId": 44,
                "TransactionId": "R1",
                "BillDate": "2020-04-03T00:00:00",
                "Time": "11:51:09",
                "Liter": null,
                "DiscountAmt": null,
                "CrAmt": 0.00,
                "DrAmt": 400.00,
                "Purpose": "vikri",
                "DeviceId": null,
                "PaymentStatus": null,
                "CreatedOn": "2020-04-20T14:46:57.183",
                "UpdatedOn": "2020-04-21T18:21:50.5068928+05:30",
                "CreatedBy": "Shubham Pardeshi",
                "UpdatedBy": "Shubham Pardeshi",
                "IsActive": true
            },
            {
                "tblSalesSummaries": [],
                "ID": 5,
                "ClientId": 14,
                "CustomerId": 44,
                "TransactionId": "R2",
                "BillDate": "2020-04-03T00:00:00",
                "Time": "11:51:09",
                "Liter": null,
                "DiscountAmt": null,
                "CrAmt": 0.00,
                "DrAmt": 400.00,
                "Purpose": "vikri",
                "DeviceId": null,
                "PaymentStatus": null,
                "CreatedOn": "2020-04-20T16:58:31.333",
                "UpdatedOn": "2020-04-21T18:21:54.0951311+05:30",
                "CreatedBy": "Shubham Pardeshi",
                "UpdatedBy": "Shubham Pardeshi",
                "IsActive": true
            },
            {
                "tblSalesSummaries": [],
                "ID": 15,
                "ClientId": 14,
                "CustomerId": 44,
                "TransactionId": "R6",
                "BillDate": "2020-04-03T00:00:00",
                "Time": "11:51:09",
                "Liter": null,
                "DiscountAmt": null,
                "CrAmt": 0.00,
                "DrAmt": 698.00,
                "Purpose": "sugar",
                "DeviceId": null,
                "PaymentStatus": null,
                "CreatedOn": "2020-04-21T17:58:31.16",
                "UpdatedOn": "2020-04-21T18:21:56.2705442+05:30",
                "CreatedBy": "Shubham Pardeshi",
                "UpdatedBy": "Shubham Pardeshi",
                "IsActive": true
            },
            {
                "tblSalesSummaries": [],
                "ID": 20,
                "ClientId": 14,
                "CustomerId": 44,
                "TransactionId": "R8",
                "BillDate": "2020-04-03T00:00:00",
                "Time": "11:51:09",
                "Liter": null,
                "DiscountAmt": null,
                "CrAmt": 0.00,
                "DrAmt": 400.00,
                "Purpose": "vikri",
                "DeviceId": null,
                "PaymentStatus": null,
                "CreatedOn": "2020-04-21T18:17:49.607",
                "UpdatedOn": "2020-04-21T18:17:50.09",
                "CreatedBy": "Shubham Pardeshi",
                "UpdatedBy": "Shubham Pardeshi",
                "IsActive": true
            }
        ],
        "ID": 44,
        "CustomerId": "C141",
        "ClientId": 14,
        "CFirstname": "",
        "CMiddleName": null,
        "CLastname": null,
        "CContactNo": null,
        "CAlternetMobNo": null,
        "CEmail": null,
        "Village": null,
        "Taluka": null,
        "District": null,
        "StateId": null,
        "Country": null,
        "PaymentFreq": null,
        "SMSFlag": null,
        "Imagename": null,
        "ImagePath": null,
        "Outstanding": 3396.00,
        "CreatedOn": "2020-04-19T23:16:27.833",
        "UpdatedOn": "2020-04-21T18:17:55.55",
        "CreatedBy": "Shubham Pardeshi",
        "UpdatedBy": "Shubham RamuMunimji.Models.tblInvice",
        "IsActive": true
    }
}
 */
