package com.gaurav.commerce.activities.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DtoVariants implements Serializable {

    private String name;

    private String validity;

    private List<DtoVariantDetails> details=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public List<DtoVariantDetails> getDetails() {
        return details;
    }

    public void setDetails(List<DtoVariantDetails> details) {
        this.details = details;
    }
}
