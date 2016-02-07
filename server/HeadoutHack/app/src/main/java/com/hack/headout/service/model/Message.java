package com.hack.headout.service.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;

/**
 * Created by Arun Kumar on 2/7/2016.
 */
public class Message {

    private String sourceId;
    private String destinationId;
    private String messageType;
    private String message;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        try {
            Gson gson = new GsonBuilder().create();
            return gson.toJson(this);
        } catch (Exception ex) {
           return "";
        }
    }

    public byte[] toByeArray() {
        try {
            return toJson().getBytes("utf-8");
        }
        catch(UnsupportedEncodingException ignore){

        }

        return new byte[1];
    }

    public enum MessageType {
        ORDER(0), ORDER_SERVED(1), ORDER_DELAYED(2), SEND_INVOICE(3), MAKE_PAYMENT(4), PAYMENT_RECEIVED(5), CHAT_MESSAGE(6), OFFER_ANNOUNCE(7);

        private int code;

        MessageType(int c) {
            this.code = c;
        }

        public int getCode() {
            return code;
        }
    }
}
