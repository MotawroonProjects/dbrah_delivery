package com.apps.dbrah_delivery.model;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    private String id;
    private String user_id;
    private String address_id;
    private String category_id;
    private String provider_id;
    private String accepted_offer_id;
    private String note;
    private String pin;
    private String status;
    private String total_price;
    private String total_before_tax;
    private String total_tax;
    private String delivered_time;
    private String created_at;
    private String updated_at;
    private String day;
    private String time;
    private boolean provider_rated;
    private AddressModel address;
    private Provider provider;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getAccepted_offer_id() {
        return accepted_offer_id;
    }

    public String getNote() {
        return note;
    }

    public String getPin() {
        return pin;
    }

    public String getStatus() {
        return status;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getTotal_before_tax() {
        return total_before_tax;
    }

    public String getTotal_tax() {
        return total_tax;
    }

    public String getDelivered_time() {
        return delivered_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public boolean isProvider_rated() {
        return provider_rated;
    }

    public AddressModel getAddress() {
        return address;
    }

    public Provider getProvider() {
        return provider;
    }

    public class Provider implements Serializable{
        private String id;
        private String name;
        private String fake_name;
        private String nationality_id;
        private String town_id;
        private String email;
        private String phone_code;
        private String phone;
        private String password;
        private String vat_number;
        private String image;
        private String created_at;
        private String updated_at;
        private String rate;
        private NationalitiesModel.Data.Town town;
        private NationalitiesModel.Data nationality;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getFake_name() {
            return fake_name;
        }

        public String getNationality_id() {
            return nationality_id;
        }

        public String getTown_id() {
            return town_id;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public String getPhone() {
            return phone;
        }

        public String getPassword() {
            return password;
        }

        public String getVat_number() {
            return vat_number;
        }

        public String getImage() {
            return image;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getRate() {
            return rate;
        }

        public NationalitiesModel.Data.Town getTown() {
            return town;
        }

        public NationalitiesModel.Data getNationality() {
            return nationality;
        }
    }

  

 

 

}
