package com.posbilling.posbillingapplication.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class AddVehicleRequest {
    @SerializedName("regId")
    String regId;

    @SerializedName("garageCustomer")
    ArrayList<GarageCustomer> garageCustomerList = new ArrayList<GarageCustomer>();

    public class GarageCustomer {
        @SerializedName("ID")
        String ID;
        @SerializedName("CustomerId")
        String CustomerId;
        @SerializedName("Name")
        String Name;
        @SerializedName("CreatedOn")
        Date CreatedOn;
        @SerializedName("CreatedBy")
        String CreatedBy;
        @SerializedName("UpdatedOn")
        Date UpdatedOn;
        @SerializedName("UpdatedBy")
        String UpdatedBy;

        @SerializedName("AddGarageList")
        ArrayList<AddGarageList> addGarageLists = new ArrayList<AddGarageList>();

        public class AddGarageList {
            @SerializedName("VehicleNo")
            String VehicleNo;
            @SerializedName("VehicleType")
            String VehicleType;
            @SerializedName("VehicleModel")
            String VehicleModel;
            @SerializedName("ServieDate")
            Date ServieDate;
            @SerializedName("DueDate")
            Date DueDate;
            @SerializedName("CurrentKM")
            String CurrentKM;
            @SerializedName("NewServiceDate")
            Date NewServiceDate;
            @SerializedName("Comment")
            String Comment;
            @SerializedName("CreatedOn")
            Date CreatedOn;
            @SerializedName("UpdatedOn")
            Date UpdatedOn;
            @SerializedName("CreatedBy")
            String CreatedBy;
            @SerializedName("UpdatedBy")
            String UpdatedBy;

            public String getVehicleNo() {
                return VehicleNo;
            }

            public void setVehicleNo(String vehicleNo) {
                VehicleNo = vehicleNo;
            }

            public String getVehicleType() {
                return VehicleType;
            }

            public void setVehicleType(String vehicleType) {
                VehicleType = vehicleType;
            }

            public String getVehicleModel() {
                return VehicleModel;
            }

            public void setVehicleModel(String vehicleModel) {
                VehicleModel = vehicleModel;
            }

            public Date getServieDate() {
                return ServieDate;
            }

            public void setServieDate(Date servieDate) {
                ServieDate = servieDate;
            }

            public Date getDueDate() {
                return DueDate;
            }

            public void setDueDate(Date dueDate) {
                DueDate = dueDate;
            }

            public String getCurrentKM() {
                return CurrentKM;
            }

            public void setCurrentKM(String currentKM) {
                CurrentKM = currentKM;
            }

            public Date getNewServiceDate() {
                return NewServiceDate;
            }

            public void setNewServiceDate(Date newServiceDate) {
                NewServiceDate = newServiceDate;
            }

            public String getComment() {
                return Comment;
            }

            public void setComment(String comment) {
                Comment = comment;
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

        public Date getUpdatedOn() {
            return UpdatedOn;
        }

        public void setUpdatedOn(Date updatedOn) {
            UpdatedOn = updatedOn;
        }

        public String getUpdatedBy() {
            return UpdatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            UpdatedBy = updatedBy;
        }

        public ArrayList<AddGarageList> getAddGarageLists() {
            return addGarageLists;
        }

        public void setAddGarageLists(ArrayList<AddGarageList> addGarageLists) {
            this.addGarageLists = addGarageLists;
        }
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public ArrayList<GarageCustomer> getGarageCustomerList() {
        return garageCustomerList;
    }

    public void setGarageCustomerList(ArrayList<GarageCustomer> garageCustomerList) {
        this.garageCustomerList = garageCustomerList;
    }
}

/*
{
		"regId":"2",
		"garageCustomer":[
		{
		"ID":"",
        "CustomerId":"",
        "Name":"",
        "CreatedOn":"",
        "CreatedBy":"",
        "UpdatedOn":"",
        "UpdatedBy":"",
		"AddGarageList":[
		{
		"VehicleNo":"MH13AC6020",
		"VehicleType":"2Weeler",
		"VehicleModel":"honda",
		"ServieDate":"2020-03-28 14:23:27.207",
		"DueDate":"2020-03-28 14:23:27.207",
		"CurrentKM":"123123km",
		"NewServiceDate":"2020-04-28 14:23:27.207",
		"Comment":"clean",
		"CreatedOn":"2020-04-28 14:23:27.207",
		"UpdatedOn":"",
		"CreatedBy":"1025",
		"UpdatedBy":""
		},
		{
		"VehicleNo":"MH13BC4040",
		"VehicleType":"4weeler",
		"VehicleModel":"Maruti",
		"ServieDate":"2020-03-28 14:23:27.207",
		"DueDate":"2020-03-28 14:23:27.207",
		"CurrentKM":"123123km",
		"NewServiceDate":"2020-04-28 14:23:27.207",
		"Comment":"clean",
		"CreatedOn":"2020-04-28 14:23:27.207",
		"UpdatedOn":"",
		"CreatedBy":"1025",
		"UpdatedBy":""
		}]


		},
			{
		"ID":"",
        "CustomerId":"C3",
        "Name":"Dhammapal",
        "CreatedOn":"2020-03-27 22:00:00.000",
        "CreatedBy":"1025",
        "UpdatedOn":"",
        "UpdatedBy":"",
		"AddGarageList":[
		{
		"VehicleNo":"MH13AC4242",
		"VehicleType":"2Weeler",
		"VehicleModel":"FZ",
		"ServieDate":"2020-03-28 14:23:27.207",
		"DueDate":"2020-03-28 14:23:27.207",
		"CurrentKM":"123123km",
		"NewServiceDate":"2020-04-28 14:23:27.207",
		"Comment":"clean",
		"CreatedOn":"2020-04-28 14:23:27.207",
		"UpdatedOn":"",
		"CreatedBy":"1025",
		"UpdatedBy":""
		}]
		}]

}

 */
