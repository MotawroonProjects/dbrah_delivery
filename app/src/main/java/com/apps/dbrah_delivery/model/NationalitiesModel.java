package com.apps.dbrah_delivery.model;

import java.io.Serializable;
import java.util.List;

public class NationalitiesModel extends StatusResponse implements Serializable {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public static class Data implements Serializable{
        private String id;
        private String image;
        private String title_ar;
        private String title_en;
        private String created_at;
        private String updated_at;
        private List<Town> towns;

        public Data(String title_ar,String title_en) {
            this.title_ar=title_ar;
            this.title_en=title_en;
        }

        public String getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getTitle_ar() {
            return title_ar;
        }

        public String getTitle_en() {
            return title_en;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public List<Town> getTowns() {
            return towns;
        }

        public static class Town implements Serializable {
            private String id;
            private String nationality_id;
            private String title_ar;
            private String title_en;
            private String created_at;
            private String updated_at;

            public Town(String title_ar,String title_en) {
                this.title_ar=title_ar;
                this.title_en=title_en;
            }

            public String getId() {
                return id;
            }

            public String getNationality_id() {
                return nationality_id;
            }

            public String getTitle_ar() {
                return title_ar;
            }

            public String getTitle_en() {
                return title_en;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }
        }
    }
}
