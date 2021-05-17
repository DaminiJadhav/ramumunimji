package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Android PC (Ankur) on 02,March,2020
 */
public class LoginResponse {
    @SerializedName("status")
    String status;
    @SerializedName("regId")
    String regId;
    @SerializedName("Message")
    String Message;
    @SerializedName("ContentDetail")
    ContentDetail ContentDetail;
    @SerializedName("BusinessTypeName")
    String BusinessTypeName;

    @SerializedName("IsLogFirst")
    String IsLogFirst;

    public String getIsLogFirst() {
        return IsLogFirst;
    }



    public String getBusinessTypeName() {
        return BusinessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        BusinessTypeName = businessTypeName;
    }

    //       "BusinessTypeName": "Kirana Store",


    public class ContentDetail {


        @SerializedName("Id")
        String Id;
        @SerializedName("ClientId")
        String ClientId;
        @SerializedName("Firstname")
        String Firstname;
        @SerializedName("Midename")
        String Midename;
        @SerializedName("Lastname")
        String Lastname;
        @SerializedName("ContactNo")
        String ContactNo;
        @SerializedName("EmailId")
        String EmailId;
        @SerializedName("RoleId")
        String RoleId;
        @SerializedName("BusinessId")
        String BusinessId;
        @SerializedName("ActiveDay")
        String ActiveDay;
        @SerializedName("SystemSMS")
        String SystemSMS;
        @SerializedName("PaymentMethod")
        String PaymentMethod;
        @SerializedName("BusinessName")
        String BusinessName;
        @SerializedName("ShopeLicence")
        String ShopeLicence;
        @SerializedName("LanguageId")
        String LanguageId;
        @SerializedName("Imagename")
        String Imagename;
        @SerializedName("ImagePath")
        String ImagePath;
        @SerializedName("DOB")
        String DOB;
        @SerializedName("AlternetContactNo")
        String AlternetContactNo;
        @SerializedName("Address")
        String Address;
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
        @SerializedName("IsSelfRegister")
        String IsSelfRegister;
        @SerializedName("DeviceId")
        String DeviceId;
        @SerializedName("Onetimepass")
        String Onetimepass;
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


        public String getRoleId() {
            return RoleId;
        }

        public void setRoleId(String roleId) {
            RoleId = roleId;
        }

        public String getBusinessId() {
            return BusinessId;
        }

        public void setBusinessId(String businessId) {
            BusinessId = businessId;
        }

        public String getBusinessName() {
            return BusinessName;
        }

        public void setBusinessName(String businessName) {
            BusinessName = businessName;
        }

        public String getShopeLicence() {
            return ShopeLicence;
        }

        public void setShopeLicence(String shopeLicence) {
            ShopeLicence = shopeLicence;
        }

        public String getLanguageId() {
            return LanguageId;
        }

        public void setLanguageId(String languageId) {
            LanguageId = languageId;
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

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getAlternetContactNo() {
            return AlternetContactNo;
        }

        public void setAlternetContactNo(String alternetContactNo) {
            AlternetContactNo = alternetContactNo;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
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

        public String getIsSelfRegister() {
            return IsSelfRegister;
        }

        public void setIsSelfRegister(String isSelfRegister) {
            IsSelfRegister = isSelfRegister;
        }

        public String getDeviceId() {
            return DeviceId;
        }

        public void setDeviceId(String deviceId) {
            DeviceId = deviceId;
        }

        public String getOnetimepass() {
            return Onetimepass;
        }

        public void setOnetimepass(String onetimepass) {
            Onetimepass = onetimepass;
        }

        public ArrayList<LoginResponse.ContentDetail.tblClientProfiles> getTblClientProfiles() {
            return tblClientProfiles;
        }

        public void setTblClientProfiles(ArrayList<LoginResponse.ContentDetail.tblClientProfiles> tblClientProfiles) {
            this.tblClientProfiles = tblClientProfiles;
        }

        /*        @SerializedName("Data")
                Data Data;

                public class Data {*/
        @SerializedName("BusinessDetails")
        ArrayList<BusinessDetails> businessDetailsArrayList = new ArrayList<BusinessDetails>();


        public class BusinessDetails {
            @SerializedName("Id")
            String Id;
            @SerializedName("RegId")
            String RegId;
            @SerializedName("BusinessName")
            String BusinessName;
            @SerializedName("ShopeLicence")
            String ShopeLicence;
            @SerializedName("LanguageId")
            String LanguageId;
            @SerializedName("CreatedOn")
            String CreatedOn;
            @SerializedName("UpdateOn")
            String UpdateOn;
            @SerializedName("CreatedBy")
            String CreatedBy;
            @SerializedName("UpdateBy")
            String UpdateBy;
            @SerializedName("IsActive")
            String IsActive;

            public String getId() {
                return Id;
            }

            public void setId(String id) {
                Id = id;
            }

            public String getRegId() {
                return RegId;
            }

            public void setRegId(String regId) {
                RegId = regId;
            }

            public String getBusinessName() {
                return BusinessName;
            }

            public void setBusinessName(String businessName) {
                BusinessName = businessName;
            }

            public String getShopeLicence() {
                return ShopeLicence;
            }

            public void setShopeLicence(String shopeLicence) {
                ShopeLicence = shopeLicence;
            }

            public String getLanguageId() {
                return LanguageId;
            }

            public void setLanguageId(String languageId) {
                LanguageId = languageId;
            }

            public String getCreatedOn() {
                return CreatedOn;
            }

            public void setCreatedOn(String createdOn) {
                CreatedOn = createdOn;
            }

            public String getUpdateOn() {
                return UpdateOn;
            }

            public void setUpdateOn(String updateOn) {
                UpdateOn = updateOn;
            }

            public String getCreatedBy() {
                return CreatedBy;
            }

            public void setCreatedBy(String createdBy) {
                CreatedBy = createdBy;
            }

            public String getUpdateBy() {
                return UpdateBy;
            }

            public void setUpdateBy(String updateBy) {
                UpdateBy = updateBy;
            }

            public String getIsActive() {
                return IsActive;
            }

            public void setIsActive(String isActive) {
                IsActive = isActive;
            }
        }

        @SerializedName("tblClientProfiles")
        ArrayList<tblClientProfiles> tblClientProfiles = new ArrayList<tblClientProfiles>();

        public class tblClientProfiles {
            @SerializedName("Id")
            String Id;
            @SerializedName("Regid")
            String Regid;
            @SerializedName("Imagename")
            String Imagename;
            @SerializedName("ImagePath")
            String ImagePath;
            @SerializedName("DOB")
            String DOB;
            @SerializedName("AlternetContactNo")
            String AlternetContactNo;
            @SerializedName("Address")
            String Address;
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
            @SerializedName("CreatedOn")
            String CreatedOn;
            @SerializedName("UpdateOn")
            String UpdateOn;
            @SerializedName("CreateBy")
            String CreateBy;
            @SerializedName("UpdatedBy")
            String UpdatedBy;
            @SerializedName("IsActive")
            String IsActive;

            public String getId() {
                return Id;
            }

            public void setId(String id) {
                Id = id;
            }

            public String getRegid() {
                return Regid;
            }

            public void setRegid(String regid) {
                Regid = regid;
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

            public String getDOB() {
                return DOB;
            }

            public void setDOB(String DOB) {
                this.DOB = DOB;
            }

            public String getAlternetContactNo() {
                return AlternetContactNo;
            }

            public void setAlternetContactNo(String alternetContactNo) {
                AlternetContactNo = alternetContactNo;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String address) {
                Address = address;
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

            public String getCreatedOn() {
                return CreatedOn;
            }

            public void setCreatedOn(String createdOn) {
                CreatedOn = createdOn;
            }

            public String getUpdateOn() {
                return UpdateOn;
            }

            public void setUpdateOn(String updateOn) {
                UpdateOn = updateOn;
            }

            public String getCreateBy() {
                return CreateBy;
            }

            public void setCreateBy(String createBy) {
                CreateBy = createBy;
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


        @SerializedName("tblClientStatus")
        ArrayList<tblClientStatus> tblClientStatusArrayList = new ArrayList<tblClientStatus>();

        public class tblClientStatus {
            @SerializedName("Id")
            String Id;
            @SerializedName("RegId")
            String RegId;
            @SerializedName("ActiveDay")
            String ActiveDay;
            @SerializedName("MasterId")
            String MasterId;
            @SerializedName("SessionId")
            String SessionId;
            @SerializedName("DeviceId")
            String DeviceId;
            @SerializedName("Onetimepass")
            String Onetimepass;
            @SerializedName("LanguageId")
            String LanguageId;
            @SerializedName("StateId")
            String StateId;
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


            public String getId() {
                return Id;
            }

            public void setId(String id) {
                Id = id;
            }

            public String getRegId() {
                return RegId;
            }

            public void setRegId(String regId) {
                RegId = regId;
            }

            public String getActiveDay() {
                return ActiveDay;
            }

            public void setActiveDay(String activeDay) {
                ActiveDay = activeDay;
            }

            public String getMasterId() {
                return MasterId;
            }

            public void setMasterId(String masterId) {
                MasterId = masterId;
            }

            public String getSessionId() {
                return SessionId;
            }

            public void setSessionId(String sessionId) {
                SessionId = sessionId;
            }

            public String getDeviceId() {
                return DeviceId;
            }

            public void setDeviceId(String deviceId) {
                DeviceId = deviceId;
            }

            public String getOnetimepass() {
                return Onetimepass;
            }

            public void setOnetimepass(String onetimepass) {
                Onetimepass = onetimepass;
            }

            public String getLanguageId() {
                return LanguageId;
            }

            public void setLanguageId(String languageId) {
                LanguageId = languageId;
            }

            public String getStateId() {
                return StateId;
            }

            public void setStateId(String stateId) {
                StateId = stateId;
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

            public ArrayList<tblInvices> getTblInvicesArrayList() {
                return tblInvicesArrayList;
            }

            public void setTblInvicesArrayList(ArrayList<tblInvices> tblInvicesArrayList) {
                this.tblInvicesArrayList = tblInvicesArrayList;
            }
        }

        @SerializedName("tblExpenditures")
        ArrayList<tblExpenditures> tblExpendituresArrayList = new ArrayList<tblExpenditures>();

        public class tblExpenditures {
            @SerializedName("ID")
            String ID;
            @SerializedName("ExpenditureId")
            String ExpenditureId;
            @SerializedName("TransactionId")
            String TransactionId;
            @SerializedName("ClientId")
            String ClientId;
            @SerializedName("Purpose")
            String Purpose;
            @SerializedName("ExpenseType")
            String ExpenseType;
            @SerializedName("Amount")
            String Amount;
            @SerializedName("ExpendDate")
            String ExpendDate;
            @SerializedName("IsActive")
            String IsActive;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getExpenditureId() {
                return ExpenditureId;
            }

            public void setExpenditureId(String expenditureId) {
                ExpenditureId = expenditureId;
            }

            public String getTransactionId() {
                return TransactionId;
            }

            public void setTransactionId(String transactionId) {
                TransactionId = transactionId;
            }

            public String getClientId() {
                return ClientId;
            }

            public void setClientId(String clientId) {
                ClientId = clientId;
            }

            public String getPurpose() {
                return Purpose;
            }

            public void setPurpose(String purpose) {
                Purpose = purpose;
            }

            public String getExpenseType() {
                return ExpenseType;
            }

            public void setExpenseType(String expenseType) {
                ExpenseType = expenseType;
            }

            public String getAmount() {
                return Amount;
            }

            public void setAmount(String amount) {
                Amount = amount;
            }

            public String getExpendDate() {
                return ExpendDate;
            }

            public void setExpendDate(String expendDate) {
                ExpendDate = expendDate;
            }

            public String getIsActive() {
                return IsActive;
            }

            public void setIsActive(String isActive) {
                IsActive = isActive;
            }
        }

        @SerializedName("tblPresitcidesAndFertilisers")
        ArrayList<tblPresitcidesAndFertilisers> tblPresitcidesAndFertilisersArrayList = new ArrayList<tblPresitcidesAndFertilisers>();

        public class tblPresitcidesAndFertilisers {

        }

        @SerializedName("tblVehicleDetails")
        ArrayList<tblVehicleDetails> tblVehicleDetailsArrayList = new ArrayList<tblVehicleDetails>();

        public class tblVehicleDetails {

        }

            /*@SerializedName("Id")
            String Id;
            @SerializedName("ClientId")
            String ClientId;
            @SerializedName("Firstname")
            String Firstname;
            @SerializedName("Midename")
            String Midename;
            @SerializedName("Lastname")
            String Lastname;
            @SerializedName("RoleId")
            String RoleId;
            @SerializedName("BusinessId")
            String BusinessId;
            @SerializedName("ContactNo")
            String ContactNo;
            @SerializedName("EmailId")
            String EmailId;
            *//*@SerializedName("ActiveDay")
            String ActiveDay;
            @SerializedName("SystemSMS")
            String SystemSMS;
            @SerializedName("PaymentMethod")
            String PaymentMethod;
            @SerializedName("CreatedOn")
            String CreatedOn;
            @SerializedName("UpdatedOn")
            String UpdatedOn;
            @SerializedName("CreatedBy")
            String CreatedBy;
            @SerializedName("UpdatedBy")
            String UpdatedBy;
            @SerializedName("IsActive")
            String IsActive;*/

        public ArrayList<BusinessDetails> getBusinessDetailsArrayList() {
            return businessDetailsArrayList;
        }

        public void setBusinessDetailsArrayList(ArrayList<BusinessDetails> businessDetailsArrayList) {
            this.businessDetailsArrayList = businessDetailsArrayList;
        }

            /*public ArrayList<LoginResponse.ContentDetail.Data.tblClientProfiles> getTblClientProfiles() {
                return tblClientProfiles;
            }

            public void setTblClientProfiles(ArrayList<LoginResponse.ContentDetail.Data.tblClientProfiles> tblClientProfiles) {
                this.tblClientProfiles = tblClientProfiles;
            }*/

        public ArrayList<tblClientStatus> getTblClientStatusArrayList() {
            return tblClientStatusArrayList;
        }

        public void setTblClientStatusArrayList(ArrayList<tblClientStatus> tblClientStatusArrayList) {
            this.tblClientStatusArrayList = tblClientStatusArrayList;
        }

        public ArrayList<tblCustomers> getTblCustomersArrayList() {
            return tblCustomersArrayList;
        }

        public void setTblCustomersArrayList(ArrayList<tblCustomers> tblCustomersArrayList) {
            this.tblCustomersArrayList = tblCustomersArrayList;
        }

        public ArrayList<tblExpenditures> getTblExpendituresArrayList() {
            return tblExpendituresArrayList;
        }

        public void setTblExpendituresArrayList(ArrayList<tblExpenditures> tblExpendituresArrayList) {
            this.tblExpendituresArrayList = tblExpendituresArrayList;
        }

        public ArrayList<tblPresitcidesAndFertilisers> getTblPresitcidesAndFertilisersArrayList() {
            return tblPresitcidesAndFertilisersArrayList;
        }

        public void setTblPresitcidesAndFertilisersArrayList(ArrayList<tblPresitcidesAndFertilisers> tblPresitcidesAndFertilisersArrayList) {
            this.tblPresitcidesAndFertilisersArrayList = tblPresitcidesAndFertilisersArrayList;
        }

        public ArrayList<tblVehicleDetails> getTblVehicleDetailsArrayList() {
            return tblVehicleDetailsArrayList;
        }

        public void setTblVehicleDetailsArrayList(ArrayList<tblVehicleDetails> tblVehicleDetailsArrayList) {
            this.tblVehicleDetailsArrayList = tblVehicleDetailsArrayList;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getClientId() {
            return ClientId;
        }

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public String getFirstname() {
            return Firstname;
        }

        public void setFirstname(String firstname) {
            Firstname = firstname;
        }

        public String getMidename() {
            return Midename;
        }

        public void setMidename(String midename) {
            Midename = midename;
        }

        public String getLastname() {
            return Lastname;
        }

        public void setLastname(String lastname) {
            Lastname = lastname;
        }

/*            public String getRoleId() {
                return RoleId;
            }

            public void setRoleId(String roleId) {
                RoleId = roleId;
            }

            public String getBusinessId() {
                return BusinessId;
            }

            public void setBusinessId(String businessId) {
                BusinessId = businessId;
            }*/

        public String getContactNo() {
            return ContactNo;
        }

        public void setContactNo(String contactNo) {
            ContactNo = contactNo;
        }

        public String getEmailId() {
            return EmailId;
        }

        public void setEmailId(String emailId) {
            EmailId = emailId;
        }

        public String getActiveDay() {
            return ActiveDay;
        }

        public void setActiveDay(String activeDay) {
            ActiveDay = activeDay;
        }

        public String getSystemSMS() {
            return SystemSMS;
        }

        public void setSystemSMS(String systemSMS) {
            SystemSMS = systemSMS;
        }

        public String getPaymentMethod() {
            return PaymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            PaymentMethod = paymentMethod;
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


        /*public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getClientId() {
            return ClientId;
        }

        public void setClientId(String clientId) {
            ClientId = clientId;
        }

        public String getFirstname() {
            return Firstname;
        }

        public void setFirstname(String firstname) {
            Firstname = firstname;
        }

        public String getMidename() {
            return Midename;
        }

        public void setMidename(String midename) {
            Midename = midename;
        }

        public String getLastname() {
            return Lastname;
        }

        public void setLastname(String lastname) {
            Lastname = lastname;
        }

        public String getContactNo() {
            return ContactNo;
        }

        public void setContactNo(String contactNo) {
            ContactNo = contactNo;
        }

        public String getEmailId() {
            return EmailId;
        }

        public void setEmailId(String emailId) {
            EmailId = emailId;
        }*/

/*        public LoginResponse.ContentDetail.Data getData() {
            return Data;
        }

        public void setData(LoginResponse.ContentDetail.Data data) {
            Data = data;
        }*/
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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public LoginResponse.ContentDetail getContentDetail() {
        return ContentDetail;
    }

    public void setContentDetail(LoginResponse.ContentDetail contentDetail) {
        ContentDetail = contentDetail;
    }
}







