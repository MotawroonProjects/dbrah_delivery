package com.apps.dbrah_delivery.model;

import java.io.Serializable;
import java.util.List;

public class OrdersModel extends StatusResponse implements Serializable {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public static class Data implements Serializable {
        private String id;
        private String order_id;
        private String representative_id;
        private String status;
        private String created_at;
        private String updated_at;
        private OrderModel order;

        public String getId() {
            return id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getRepresentative_id() {
            return representative_id;
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

        public OrderModel getOrder() {
            return order;
        }
    }
}
