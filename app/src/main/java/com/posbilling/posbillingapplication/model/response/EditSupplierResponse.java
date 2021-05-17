package com.posbilling.posbillingapplication.model.response;

public class EditSupplierResponse {

}

/*
{
    "status": 1,
    "regId": 18,
    "Message": "row updated",
    "Data": {
        "Id": 18,
        "ClientId": "C18",
        "Firstname": "8055659418",
        "Midename": null,
        "Lastname": null,
        "RoleId": 3,
        "BusinessId": 1,
        "ContactNo": "8055659418",
        "EmailId": null,
        "ActiveDay": null,
        "SystemSMS": null,
        "PaymentMethod": null,
        "BusinessName": null,
        "ShopeLicence": null,
        "LanguageId": null,
        "Imagename": null,
        "ImagePath": null,
        "DOB": null,
        "AlternetContactNo": null,
        "Address": null,
        "Village": null,
        "Taluka": null,
        "District": null,
        "StateId": null,
        "Country": null,
        "IsSelfRegister": null,
        "DeviceId": null,
        "Onetimepass": null,
        "CreatedOn": null,
        "UpdatedOn": null,
        "CreatedBy": null,
        "UpdatedBy": null,
        "IsActive": null,
        "tblCustomers": [],
        "tblExpenditures": [],
        "tblPresitcidesAndFertilisers": [],
        "tblVehicleDetails": [],
        "tblClientMaster21": null,
        "tblClientMaster22": null,
        "tblSuppliers": [
            {
                "ID": 10,
                "SupplierId": "C189",
                "ClientId": 18,
                "SFirstname": "fifth Supplier",
                "SMiddleName": "",
                "SLastname": "",
                "SContactNo": "8055659418",
                "SAlternetMobNo": "8194655580",
                "SEmail": "",
                "Village": "vadgoan",
                "Taluka": "haweli",
                "District": "pune",
                "StateId": null,
                "Country": "India",
                "PaymentFreq": "monthly",
                "SMSFlag": null,
                "Imagename": "",
                "ImagePath": null,
                "Outstanding": null,
                "CreatedOn": "2020-07-20T17:06:12.513",
                "UpdatedOn": "2020-07-20T17:09:38.1431693+05:30",
                "CreatedBy": "18",
                "UpdatedBy": "18",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 1,
                "SupplierId": "S181",
                "ClientId": 18,
                "SFirstname": "First Supplier",
                "SMiddleName": "",
                "SLastname": "",
                "SContactNo": "8055659418",
                "SAlternetMobNo": "8194655580",
                "SEmail": "",
                "Village": "vadgoan",
                "Taluka": "haweli",
                "District": "pune",
                "StateId": null,
                "Country": "India",
                "PaymentFreq": "monthly",
                "SMSFlag": null,
                "Imagename": "",
                "ImagePath": null,
                "Outstanding": null,
                "CreatedOn": "2020-07-10T19:26:46.14",
                "UpdatedOn": null,
                "CreatedBy": null,
                "UpdatedBy": null,
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 2,
                "SupplierId": "S182",
                "ClientId": 18,
                "SFirstname": "Second Supplier",
                "SMiddleName": "",
                "SLastname": "",
                "SContactNo": "8194655580",
                "SAlternetMobNo": "8194655580",
                "SEmail": "",
                "Village": "vadgoan",
                "Taluka": "haweli",
                "District": "pune",
                "StateId": null,
                "Country": "India",
                "PaymentFreq": "monthly",
                "SMSFlag": null,
                "Imagename": "",
                "ImagePath": null,
                "Outstanding": -205000.00,
                "CreatedOn": "2020-07-10T19:33:46.367",
                "UpdatedOn": "2020-07-11T11:35:58.077",
                "CreatedBy": "18",
                "UpdatedBy": "8055659418 RamuMunimji.Models.tblKharediInvice",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 4,
                "SupplierId": "S183",
                "ClientId": 18,
                "SFirstname": "Thered Supplier",
                "SMiddleName": null,
                "SLastname": null,
                "SContactNo": null,
                "SAlternetMobNo": null,
                "SEmail": null,
                "Village": null,
                "Taluka": null,
                "District": null,
                "StateId": null,
                "Country": null,
                "PaymentFreq": null,
                "SMSFlag": null,
                "Imagename": null,
                "ImagePath": null,
                "Outstanding": -100000.00,
                "CreatedOn": "2020-07-11T11:07:39.133",
                "UpdatedOn": null,
                "CreatedBy": "8055659418 ",
                "UpdatedBy": null,
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 5,
                "SupplierId": "S184",
                "ClientId": 18,
                "SFirstname": "Thered Supplier",
                "SMiddleName": null,
                "SLastname": null,
                "SContactNo": null,
                "SAlternetMobNo": null,
                "SEmail": null,
                "Village": null,
                "Taluka": null,
                "District": null,
                "StateId": null,
                "Country": null,
                "PaymentFreq": null,
                "SMSFlag": null,
                "Imagename": null,
                "ImagePath": null,
                "Outstanding": -100000.00,
                "CreatedOn": "2020-07-11T11:09:05.92",
                "UpdatedOn": null,
                "CreatedBy": "8055659418 ",
                "UpdatedBy": null,
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 6,
                "SupplierId": "S185",
                "ClientId": 18,
                "SFirstname": "Fourth Supplier",
                "SMiddleName": null,
                "SLastname": null,
                "SContactNo": null,
                "SAlternetMobNo": null,
                "SEmail": null,
                "Village": null,
                "Taluka": null,
                "District": null,
                "StateId": null,
                "Country": null,
                "PaymentFreq": null,
                "SMSFlag": null,
                "Imagename": null,
                "ImagePath": null,
                "Outstanding": 300000.00,
                "CreatedOn": "2020-07-11T11:29:06.297",
                "UpdatedOn": "2020-07-11T11:29:09.047",
                "CreatedBy": "8055659418 ",
                "UpdatedBy": "8055659418 RamuMunimji.Models.tblKharediInvice",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 7,
                "SupplierId": "C186",
                "ClientId": 18,
                "SFirstname": "Supplier Test",
                "SMiddleName": null,
                "SLastname": null,
                "SContactNo": null,
                "SAlternetMobNo": null,
                "SEmail": null,
                "Village": null,
                "Taluka": null,
                "District": null,
                "StateId": null,
                "Country": null,
                "PaymentFreq": null,
                "SMSFlag": null,
                "Imagename": null,
                "ImagePath": null,
                "Outstanding": 21000.00,
                "CreatedOn": "2020-07-11T18:55:16.383",
                "UpdatedOn": "2020-07-11T19:06:46.787",
                "CreatedBy": "8055659418 ",
                "UpdatedBy": "8055659418 RamuMunimji.Models.tblKharediInvice",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 8,
                "SupplierId": "C187",
                "ClientId": 18,
                "SFirstname": "Testfinal",
                "SMiddleName": null,
                "SLastname": null,
                "SContactNo": null,
                "SAlternetMobNo": null,
                "SEmail": null,
                "Village": null,
                "Taluka": null,
                "District": null,
                "StateId": null,
                "Country": null,
                "PaymentFreq": null,
                "SMSFlag": null,
                "Imagename": null,
                "ImagePath": null,
                "Outstanding": 100.00,
                "CreatedOn": "2020-07-11T19:13:35.093",
                "UpdatedOn": "2020-07-11T19:18:28.13",
                "CreatedBy": "8055659418 ",
                "UpdatedBy": "8055659418 RamuMunimji.Models.tblKharediInvice",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            },
            {
                "ID": 9,
                "SupplierId": "C188",
                "ClientId": 18,
                "SFirstname": "testforedit",
                "SMiddleName": null,
                "SLastname": null,
                "SContactNo": null,
                "SAlternetMobNo": null,
                "SEmail": null,
                "Village": null,
                "Taluka": null,
                "District": null,
                "StateId": null,
                "Country": null,
                "PaymentFreq": null,
                "SMSFlag": null,
                "Imagename": null,
                "ImagePath": null,
                "Outstanding": -3400.00,
                "CreatedOn": "2020-07-11T20:24:52.203",
                "UpdatedOn": "2020-07-20T16:48:09.127",
                "CreatedBy": "8055659418 ",
                "UpdatedBy": "8055659418 RamuMunimji.Models.tblKharediInvice",
                "IsActive": true,
                "tblClientMaster2": null,
                "tblKharediInvices": []
            }
        ]
    }
}
 */