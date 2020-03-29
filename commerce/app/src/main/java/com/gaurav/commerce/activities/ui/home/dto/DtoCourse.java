package com.gaurav.commerce.activities.ui.home.dto;

import java.io.Serializable;

public class DtoCourse implements Serializable {


    private Long id;

    private String name;

    private String iconUrl;

    public String getName() {
        return name;
    }

    public DtoCourse setName(String name) {
        this.name = name;
        return this;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public DtoCourse setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public Long getId() {
        return id;
    }

    public DtoCourse setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "DtoCourse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
