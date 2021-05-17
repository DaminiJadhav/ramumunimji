package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BuisnessIdListResponse {
    @SerializedName("status")
    String status;
    @SerializedName("Message")
    String Message;

    @SerializedName("Data")
    ArrayList<Data> dataArrayList = new ArrayList<Data>();

    public class Data {
        @SerializedName("ID")
        String ID;
        @SerializedName("Typename")
        String Typename;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTypename() {
            return Typename;
        }

        public void setTypename(String typename) {
            Typename = typename;
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
  "Message": "BusinessType",
  "Data": [
    {
      "ID": 1,
      "Typename": "Kirana Store"
    },
    {
      "ID": 2,
      "Typename": "Vehicle Service Center"
    },
    {
      "ID": 3,
      "Typename": "Agro Agency"
    },
    {
      "ID": 4,
      "Typename": "Dairy Farm"
    },
    {
      "ID": 5,
      "Typename": "Nursery"
    },
    {
      "ID": 7,
      "Typename": "Pharmacy"
    },
    {
      "ID": 8,
      "Typename": "dairy"
    },
    {
      "ID": 11,
      "Typename": "kirana shoppp"
    },
    {
      "ID": 12,
      "Typename": "CSE"
    },
    {
      "ID": 13,
      "Typename": "ITE"
    },
    {
      "ID": 14,
      "Typename": "testing "
    },
    {
      "ID": 15,
      "Typename": "Agro farm"
    },
    {
      "ID": 16,
      "Typename": "Agro farm"
    },
    {
      "ID": 17,
      "Typename": "IT"
    },
    {
      "ID": 18,
      "Typename": "Kirana shopyy"
    },
    {
      "ID": 19,
      "Typename": "dairy dairyy"
    },
    {
      "ID": 20,
      "Typename": "Bakery"
    },
    {
      "ID": 21,
      "Typename": "Test"
    },
    {
      "ID": 22,
      "Typename": "medical store"
    },
    {
      "ID": 23,
      "Typename": "Test1"
    },
    {
      "ID": 24,
      "Typename": "Test2"
    },
    {
      "ID": 25,
      "Typename": "Test3"
    },
    {
      "ID": 26,
      "Typename": "test5"
    },
    {
      "ID": 27,
      "Typename": "test7"
    },
    {
      "ID": 28,
      "Typename": "test9"
    },
    {
      "ID": 29,
      "Typename": "test8"
    },
    {
      "ID": 30,
      "Typename": "Farmists"
    }
  ]
}
 */
