package com.posbilling.posbillingapplication.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetVehicleTypeResponse {
    @SerializedName("status")
    String status;
    @SerializedName("Data")
    Data data;

    public class Data{
        @SerializedName("Two_Wheeler")
        ArrayList<Two_Wheeler> twoWheelerArrayList = new ArrayList<Two_Wheeler>();

        public class Two_Wheeler{
            @SerializedName("Brand")
            String Brand;
            @SerializedName("Type")
            String Type;

            public String getBrand() {
                return Brand;
            }

            public void setBrand(String brand) {
                Brand = brand;
            }

            public String getType() {
                return Type;
            }

            public void setType(String type) {
                Type = type;
            }
        }

        @SerializedName("Three_Wheeler")
        ArrayList<Three_Wheeler> three_wheelerArrayList = new ArrayList<Three_Wheeler>();

        public class Three_Wheeler{
            @SerializedName("Brand")
            String Brand;
            @SerializedName("Type")
            String Type;

            public String getBrand() {
                return Brand;
            }

            public void setBrand(String brand) {
                Brand = brand;
            }

            public String getType() {
                return Type;
            }

            public void setType(String type) {
                Type = type;
            }
        }

        @SerializedName("Four_Wheeler")
        ArrayList<Four_Wheeler> four_wheelerArrayList = new ArrayList<Four_Wheeler>();

        public class Four_Wheeler{
            @SerializedName("Brand")
            String Brand;
            @SerializedName("Type")
            String Type;

            public String getBrand() {
                return Brand;
            }

            public void setBrand(String brand) {
                Brand = brand;
            }

            public String getType() {
                return Type;
            }

            public void setType(String type) {
                Type = type;
            }
        }

        public ArrayList<Two_Wheeler> getTwoWheelerArrayList() {
            return twoWheelerArrayList;
        }

        public void setTwoWheelerArrayList(ArrayList<Two_Wheeler> twoWheelerArrayList) {
            this.twoWheelerArrayList = twoWheelerArrayList;
        }

        public ArrayList<Three_Wheeler> getThree_wheelerArrayList() {
            return three_wheelerArrayList;
        }

        public void setThree_wheelerArrayList(ArrayList<Three_Wheeler> three_wheelerArrayList) {
            this.three_wheelerArrayList = three_wheelerArrayList;
        }

        public ArrayList<Four_Wheeler> getFour_wheelerArrayList() {
            return four_wheelerArrayList;
        }

        public void setFour_wheelerArrayList(ArrayList<Four_Wheeler> four_wheelerArrayList) {
            this.four_wheelerArrayList = four_wheelerArrayList;
        }
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

/*
{
    "status": 1,
    "Data": {
        "Two_Wheeler": [
            {
                "Brand": "Bajaj",
                "Type": "Platina 100"
            },
            {
                "Brand": "Bajaj",
                "Type": "Pulsar NS160"
            },
            {
                "Brand": "Bajaj ",
                "Type": "Dominar 400"
            },
            {
                "Brand": "Bajaj ",
                "Type": "Avenger Cruise 220"
            },
            {
                "Brand": "Honda ",
                "Type": "Shine"
            },
            {
                "Brand": "Honda ",
                "Type": "Unicorn"
            },
            {
                "Brand": "TVS",
                "Type": "Victor GL"
            },
            {
                "Brand": "TVS",
                "Type": "Victor GX"
            },
            {
                "Brand": "TVS",
                "Type": "Victor GLX"
            },
            {
                "Brand": "Mahindra",
                "Type": "Mahindra Centuro"
            }
        ],
        "Three_Wheeler": [
            {
                "Brand": "Bajaj",
                "Type": "Bajaj Auto\r\n"
            },
            {
                "Brand": "TVS",
                "Type": "TVS Auto\r\n"
            },
            {
                "Brand": "Mahindra",
                "Type": "Mahindra Auto"
            }
        ],
        "Four_Wheeler": [
            {
                "Brand": "Audi ",
                "Type": "Q7 Facelift"
            },
            {
                "Brand": "Mahindra",
                "Type": "e20 NXT"
            },
            {
                "Brand": "Skoda ",
                "Type": "Karoq"
            },
            {
                "Brand": "Maruti Suzuki",
                "Type": "S-Cross Petrol"
            },
            {
                "Brand": "Tata ",
                "Type": "Tata Tigor EV Facelift"
            }
        ]
    }
}
 */
