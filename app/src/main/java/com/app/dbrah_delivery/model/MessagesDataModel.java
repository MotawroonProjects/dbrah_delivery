package com.app.dbrah_delivery.model;

import java.io.Serializable;
import java.util.List;

public class MessagesDataModel extends StatusResponse implements Serializable {
   private Data data;

    public Data getData() {
        return data;
    }

    public class Data implements Serializable{
    private List<com.app.dbrah_delivery.model.MessageModel> messages;

        public List<com.app.dbrah_delivery.model.MessageModel> getMessages() {
            return messages;
        }
    }}
