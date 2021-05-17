package com.posbilling.posbillingapplication.model.request;

import java.util.ArrayList;

public class TransactionRequest{
       String regId;
       String DeviceId;
       String Lang;
       String BusinessType;

       ArrayList<CustomerListClass> CustomerList = new ArrayList<CustomerListClass>();

    public class CustomerListClass{
        String Id;
        String CustomerId;
        String Name;

        ArrayList<TransactionsListClass> TransactionsList =new ArrayList<TransactionsListClass>();

        public class TransactionsListClass{
            String TransId;
            String TransactionType;
            String TransactionId;
            String Purpose;
            String Amount;
            java.util.Date Date;
            String Time;
            String Liter;

            public String getTransId() {
                return TransId;
            }

            public void setTransId(String transId) {
                TransId = transId;
            }

            public String getTransactionType() {
                return TransactionType;
            }

            public void setTransactionType(String transactionType) {
                TransactionType = transactionType;
            }

            public String getTransactionId() {
                return TransactionId;
            }

            public void setTransactionId(String transactionId) {
                TransactionId = transactionId;
            }

            public String getPurpose() {
                return Purpose;
            }

            public void setPurpose(String purpose) {
                Purpose = purpose;
            }

            public String getAmount() {
                return Amount;
            }

            public void setAmount(String amount) {
                Amount = amount;
            }

            public java.util.Date getDate() {
                return Date;
            }

            public void setDate(java.util.Date date) {
                Date = date;
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
        }


        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
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

        public void setName(String Name) {
            this.Name = Name;
        }

        public ArrayList<TransactionsListClass> getTransactionsList() {
            return TransactionsList;
        }

        public void setTransactionsList(ArrayList<TransactionsListClass> transactionsList) {
            TransactionsList = transactionsList;
        }
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getLang() {
        return Lang;
    }

    public void setLang(String lang) {
        Lang = lang;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public ArrayList<CustomerListClass> getCustomerList() {
        return CustomerList;
    }

    public void setCustomerList(ArrayList<CustomerListClass> customerList) {
        CustomerList = customerList;
    }
}




/*public class TransactionRequest {
     String regId;
     String DeviceId;
     String Lang;
     String BusiType;
     String customers;



    ArrayList<TransactionModel> Transaction = new ArrayList<TransactionModel>();

   public class TransactionModel{
        String Id;
        String CustomerId;
        String TransactionId;
        String Name;
        String TransactionType;
        String TransId;
        String Purpose;
        String Amount;
        java.util.Date Date;
        String Time;
        String Liter;






        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(String customerId) {
            CustomerId = customerId;
        }

        public String getTrunsactionId() {
            return TransactionId;
        }

        public void setTrunsactionId(String trunsactionId) {
            TransactionId = trunsactionId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getTransactionType() {
            return TransactionType;
        }

        public void setTransactionType(String transactionType) {
            TransactionType = transactionType;
        }

        public String getTransId() {
            return TransId;
        }

        public void setTransId(String transId) {
            TransId = transId;
        }

        public String getPurpose() {
            return Purpose;
        }

        public void setPurpose(String purpose) {
            Purpose = purpose;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

        public java.util.Date getDate() {
            return Date;
        }

        public void setDate(java.util.Date date) {
            Date = date;
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
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getLang() {
        return Lang;
    }

    public void setLang(String lang) {
        Lang = lang;
    }

    public String getBusiType() {
        return BusiType;
    }

    public void setBusiType(String busiType) {
        BusiType = busiType;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public ArrayList<TransactionModel> getTransaction() {
        return Transaction;
    }

    public void setTransaction(ArrayList<TransactionModel> transaction) {
        Transaction = transaction;
    }
}*/


/*
{
  "BusiType": "",
  "DeviceId": "",
  "Lang": "1",
  "Transaction": [
    {
      "Amount": "4000",
      "CustomerId": "C10441",
      "Date": "Apr 10, 2020 3:12:00 PM",
      "Id": "1095",
      "Liter": "",
      "Name": "Ashok Saraf",
      "Purpose": "for testing",
      "Time": "15:12",
      "TransactionId": "R41",
      "TransactionType": "2"
    }
  ],
  "customers": "",
  "regId": "1044"
}
 */


/*
{
       "regId":"1024",
       "DeviceId":"",
       "Lang":"1",
       "BusinessType":"3",
       "CustomerList":
           [
            {
       "Id":"53",
       "CustomerId":"C2",
       "Name":"Ankur",
       "TransactionsList":
           [
             {
       "TransactionType":"1",
       "TransactionId":"R4",
       "Purpose":"kirana",
       "Amount":"500",
       "Date":"2020-04-03 11:51:09.787",
       "Time":"11:51:09",
       "Liter":""
           }
           ]
            }
           ]

}

                */
