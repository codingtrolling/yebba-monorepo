package com.yebba.store;

public class AppModel {
    public String name;
    public String description;
    public String downloadUrl;

    // Required empty constructor for Firebase
    public AppModel() {}

    public AppModel(String name, String description, String downloadUrl) {
        this.name = name;
        this.description = description;
        this.downloadUrl = downloadUrl;
    }
}
