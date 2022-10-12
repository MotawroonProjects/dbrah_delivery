package com.app.dbrah_delivery.model;

import java.io.Serializable;

public class UserModel extends StatusResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        private String id;
        private String image;
        private String name;
        private String phone_code;
        private String phone;
        private String nationality_id;
        private String town_id;
        private String residence_number;
        private String delivery_range;
        private String provider_id;
        private String status;
        private String created_at;
        private String updated_at;
        private static String firebase_token;

        public String getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getName() {
            return name;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public String getPhone() {
            return phone;
        }

        public String getNationality_id() {
            return nationality_id;
        }

        public String getTown_id() {
            return town_id;
        }

        public String getResidence_number() {
            return residence_number;
        }

        public String getDelivery_range() {
            return delivery_range;
        }

        public String getProvider_id() {
            return provider_id;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getFirebase_token() {
            return firebase_token;
        }

        public void setFirebase_token(String firebase_token) {
            this.firebase_token = firebase_token;
        }
    }

}
