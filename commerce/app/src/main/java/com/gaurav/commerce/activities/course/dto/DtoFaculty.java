package com.gaurav.commerce.activities.course.dto;

import java.io.Serializable;

public class DtoFaculty implements Serializable {

    private Integer id;

    private String name;

    private String urlImage;

    private String description;

    public Integer getId() {
        return id;
    }

    public DtoFaculty setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DtoFaculty setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public DtoFaculty setUrlImage(String urlImage) {
        this.urlImage = urlImage;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DtoFaculty setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "DtoFaculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
