package com.mohamed.myapplication;

/**
 * Created by mohamed on 12/04/18.
 */

public class ServiceModel {
    private String name,image_url;

    public ServiceModel() {
    }

    public ServiceModel(String name, String image_url) {
        this.name = name;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
