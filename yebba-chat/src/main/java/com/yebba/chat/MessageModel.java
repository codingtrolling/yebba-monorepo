package com.yebba.chat;

public class MessageModel {
    public String sender;
    public String message;
    public long timestamp;

    // Required for Firebase
    public MessageModel() {}

    public MessageModel(String sender, String message, long timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }
}
