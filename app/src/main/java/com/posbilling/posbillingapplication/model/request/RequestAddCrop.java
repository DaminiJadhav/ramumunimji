package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class RequestAddCrop {
    String regId;
    @SerializedName("AgroCustomer")
    ArrayList<AgroCustomerObject> AgroCustomer = new ArrayList();

    public class AgroCustomerObject {
        String ID;
        String CustomerId;
        String Name;
        Date CreatedOn;
        String CreatedBy;
        String UpdatedOn;
        String UpdatedBy;

        @SerializedName("AddCropList")
        ArrayList<AddCropListObject> AddCropList = new ArrayList();

        public class AddCropListObject {
            String CropId;
            String Acres;
            String TransactionId;
            String CropPeriod;
            Date CreatedOn;
            Date UpdatedOn;
            String CreatedBy;
            String UpdatedBy;

            public String getCropId() {
                return CropId;
            }

            public void setCropId(String cropId) {
                CropId = cropId;
            }

            public String getAcres() {
                return Acres;
            }

            public void setAcres(String acres) {
                Acres = acres;
            }

            public String getTransactionId() {
                return TransactionId;
            }

            public void setTransactionId(String transactionId) {
                TransactionId = transactionId;
            }

            public String getCropPeriod() {
                return CropPeriod;
            }

            public void setCropPeriod(String cropPeriod) {
                CropPeriod = cropPeriod;
            }

            public Date getCreatedOn() {
                return CreatedOn;
            }

            public void setCreatedOn(Date createdOn) {
                CreatedOn = createdOn;
            }

            public Date getUpdatedOn() {
                return UpdatedOn;
            }

            public void setUpdatedOn(Date updatedOn) {
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
        }


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

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public Date getCreatedOn() {
            return CreatedOn;
        }

        public void setCreatedOn(Date createdOn) {
            CreatedOn = createdOn;
        }

        public String getCreatedBy() {
            return CreatedBy;
        }

        public void setCreatedBy(String createdBy) {
            CreatedBy = createdBy;
        }

        public String getUpdatedOn() {
            return UpdatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            UpdatedOn = updatedOn;
        }

        public String getUpdatedBy() {
            return UpdatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            UpdatedBy = updatedBy;
        }

        public ArrayList<AddCropListObject> getAddCropList() {
            return AddCropList;
        }

        public void setAddCropList(ArrayList<AddCropListObject> addCropList) {
            AddCropList = addCropList;
        }
    }


    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public ArrayList<AgroCustomerObject> getAgroCustomer() {
        return AgroCustomer;
    }

    public void setAgroCustomer(ArrayList<AgroCustomerObject> agroCustomer) {
        AgroCustomer = agroCustomer;
    }
}

/*
{
                "regId":"1026",
                "AgroCustomer":
                [
                                {
                                "ID":"",
                                "CustomerId":"C03",
                                "Name":"Customer03",
                                 "CreatedOn":"2020-03-27 22:00:00.000",
                         "CreatedBy":"1026",
                                "UpdatedOn":"",
                                "UpdatedBy":"",
                                "AddCropList":[
                                {
                                        "CropId":"3",
                    "Acres":"2",
                    "TransactionId":"",
                    "CropPeriod":"2 Month",
                    "CreatedOn":"2020-03-24 12:26:30.477",
                    "UpdatedOn":"",
                    "CreatedBy":"1026",
                    "UpdatedBy":""
                                }

                                ]}
                                ]

}
 */