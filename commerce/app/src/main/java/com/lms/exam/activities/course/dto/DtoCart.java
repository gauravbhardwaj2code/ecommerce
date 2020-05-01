package com.lms.exam.activities.course.dto;

import java.io.Serializable;
import java.util.Objects;

public class DtoCart implements Serializable {

    private Integer subjectId;

    private String validity;

    private String mode;

    private Double price;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoCart dtoCart = (DtoCart) o;
        return Objects.equals(subjectId, dtoCart.subjectId) &&
                Objects.equals(validity, dtoCart.validity) &&
                Objects.equals(mode, dtoCart.mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId, validity, mode);
    }

    @Override
    public String toString() {
        return "DtoCart{" +
                "subjectId=" + subjectId +
                ", validity='" + validity + '\'' +
                ", mode='" + mode + '\'' +
                ", price=" + price +
                '}';
    }
}
