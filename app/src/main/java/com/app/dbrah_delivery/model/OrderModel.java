package com.app.dbrah_delivery.model;

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
    private String expected_delivery_time;
    private String created_at;
    private String updated_at;
    private String day;
    private String time;
    private boolean provider_rated;
    private AddressModel address;
    private ProviderModel provider;
    private ClientModel user;
    private Offers offer;

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

    public String getExpected_delivery_time() {
        return expected_delivery_time;
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

    public ProviderModel getProvider() {
        return provider;
    }

    public ClientModel getUser() {
        return user;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Offers getOffer() {
        return offer;
    }

    public static class Offers implements Serializable {
        private String id;
        private String order_id;
        private String provider_id;
        private String total_price;
        private String status;
        private String delivery_date_time;
        private String note;
        private String created_at;
        private String updated_at;
        private List<OfferDetail> offer_details;
        private boolean isNotFound;
        private boolean isPrice;
        private boolean isLess;
        private boolean isOther;


        public String getId() {
            return id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getProvider_id() {
            return provider_id;
        }

        public String getTotal_price() {
            return total_price;
        }

        public String getStatus() {
            return status;
        }

        public String getDelivery_date_time() {
            return delivery_date_time;
        }

        public String getNote() {
            return note;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public List<OfferDetail> getOffer_details() {
            return offer_details;
        }


        public boolean isNotFound() {
            return isNotFound;
        }

        public void setNotFound(boolean notFound) {
            isNotFound = notFound;
        }

        public boolean isPrice() {
            return isPrice;
        }

        public void setPrice(boolean price) {
            isPrice = price;
        }

        public boolean isLess() {
            return isLess;
        }

        public void setLess(boolean less) {
            isLess = less;
        }

        public boolean isOther() {
            return isOther;
        }

        public void setOther(boolean other) {
            isOther = other;
        }

        public static class OfferDetail implements Serializable {
            private String id;
            private String order_id;
            private String order_offer_id;
            private String product_id;
            private String qty;
            private String type;
            private String price;
            private String total_price;
            private String available_qty;
            private String other_product_id;
            private String created_at;
            private String updated_at;
            private ProductModel product;
            private ProductModel other_product;
            private int new_qty;

            public String getId() {
                return id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public String getOrder_offer_id() {
                return order_offer_id;
            }

            public String getProduct_id() {
                return product_id;
            }

            public String getQty() {
                return qty;
            }

            public String getType() {
                return type;
            }

            public String getPrice() {
                return price;
            }

            public String getTotal_price() {
                return total_price;
            }

            public String getAvailable_qty() {
                return available_qty;
            }

            public String getOther_product_id() {
                return other_product_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public ProductModel getProduct() {
                return product;
            }

            public ProductModel getOther_product() {
                return other_product;
            }

            public int getNew_qty() {
                return new_qty;
            }
        }

    }



}
