package com.gaurav.commerce.activities.ui.home.dto;

import java.io.Serializable;

public class DtoCategory implements Serializable {

    private String name;

    private String imageUrl;

    private String categoryId="0";

    public String getName() {
        return name;
    }

    public DtoCategory setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public DtoCategory setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "DtoCategory{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
