package com.lms.exam.activities.ui.home.dto;

import java.io.Serializable;

public class DtoHomePageBanner implements Serializable {

    private String image;
    private String name;
    private String link;
    private String category;

    public String getImage() {
        return image;
    }

    public DtoHomePageBanner setImage(String image) {
        this.image = image;
        return this;
    }

    public String getName() {
        return name;
    }

    public DtoHomePageBanner setName(String name) {
        this.name = name;
        return this;
    }

    public String getLink() {
        return link;
    }

    public DtoHomePageBanner setLink(String link) {
        this.link = link;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public DtoHomePageBanner setCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public String toString() {
        return "DtoHomePageBanner{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
