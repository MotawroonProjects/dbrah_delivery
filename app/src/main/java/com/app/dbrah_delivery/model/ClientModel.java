package com.app.dbrah_delivery.model;

public class  ClientModel extends StatusResponse {

        private String id;
        private String name;
        private String email;
        private String phone_code;
        private String phone;
        private String vat_number;
        private String image;
        private String created_at;
        private String updated_at;
        private static String firebase_token;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
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

        public String getFirebase_token() {
            return firebase_token;
        }



}
