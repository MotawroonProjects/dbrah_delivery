package com.app.dbrah_delivery.model;

import java.io.Serializable;

public class StatusResponse implements Serializable {
    protected int status;
    protected String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
