package com.lms.exam.activities.course.dto;

import java.io.Serializable;

public class DtoFaculty implements Serializable {

    private Integer id;

    private String name;

    private String email;

    private String urlimage;

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
        return urlimage;
    }

    public DtoFaculty setUrlImage(String urlImage) {
        this.urlimage = urlImage;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DtoFaculty setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "DtoFaculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", urlImage='" + urlimage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
