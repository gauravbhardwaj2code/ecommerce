package com.lms.exam.activities.course.dto;

import java.io.Serializable;

public class DtoVariantDetails implements Serializable {

    private String mode;

    private Double price;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
