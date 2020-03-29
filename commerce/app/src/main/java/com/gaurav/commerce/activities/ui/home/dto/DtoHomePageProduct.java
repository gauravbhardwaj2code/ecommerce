package com.gaurav.commerce.activities.ui.home.dto;

import java.io.Serializable;

public class DtoHomePageProduct implements Serializable {

    private String name;

    private String teacher;

    private Double sPrice=1.0;

    private Double cPrice=1.0;

    private String marketingText;

    private String subjectId;

    private String url;

    private Double rating=1.0;

    private String language="Hindi";

    public String getName() {
        return name;
    }

    public DtoHomePageProduct setName(String name) {
        this.name = name;
        return this;
    }

    public String getTeacher() {
        return teacher;
    }

    public DtoHomePageProduct setTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }

    public Double getsPrice() {
        return sPrice;
    }

    public DtoHomePageProduct setsPrice(Double sPrice) {
        this.sPrice = sPrice;
        return this;
    }

    public Double getcPrice() {
        return cPrice;
    }

    public DtoHomePageProduct setcPrice(Double cPrice) {
        this.cPrice = cPrice;
        return this;
    }

    public String getMarketingText() {
        return marketingText;
    }

    public DtoHomePageProduct setMarketingText(String marketingText) {
        this.marketingText = marketingText;
        return this;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public DtoHomePageProduct setSubjectId(String subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DtoHomePageProduct setUrl(String url) {
        this.url = url;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public DtoHomePageProduct setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public DtoHomePageProduct setLanguage(String language) {
        this.language = language;
        return this;
    }

    @Override
    public String toString() {
        return "DtoHomePageProduct{" +
                "name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", sPrice=" + sPrice +
                ", cPrice=" + cPrice +
                ", marketingText='" + marketingText + '\'' +
                ", subjectId='" + subjectId + '\'' +
                '}';
    }
}
