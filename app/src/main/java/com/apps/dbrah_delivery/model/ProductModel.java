package com.apps.dbrah_delivery.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable {
    private String id;
    private String main_image;
    private String title_ar;
    private String title_en;
    private String category_id;
    private String sub_category_id;
    private String details_at;
    private String details_en;
    private String created_at;
    private String updated_at;
    private int amount = 0;
    private List<Image> images;

    private String have_or_not;


    public ProductModel(String title_ar, String title_en) {
        this.title_ar = title_ar;
        this.title_en = title_en;
    }

    public String getId() {
        return id;
    }

    public String getMain_image() {
        return main_image;
    }

    public String getTitle_ar() {
        return title_ar;
    }

    public String getTitle_en() {
        return title_en;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public String getDetails_at() {
        return details_at;
    }

    public String getDetails_en() {
        return details_en;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }

    public void setTitle_ar(String title_ar) {
        this.title_ar = title_ar;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getHave_or_not() {
        return have_or_not;
    }

    public void setHave_or_not(String have_or_not) {
        this.have_or_not = have_or_not;
    }




    public static class Image implements Serializable {
        private String id;
        private String image;
        private String product_id;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
