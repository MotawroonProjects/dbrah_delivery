package com.app.dbrah_delivery.model;

import java.io.Serializable;

public class NotiFire implements Serializable {
    private String order_status = "";


    public NotiFire(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_status() {
        return order_status;
    }
}
