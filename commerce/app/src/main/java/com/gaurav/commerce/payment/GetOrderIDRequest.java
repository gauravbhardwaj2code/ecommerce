package com.gaurav.commerce.payment;

import com.google.gson.annotations.SerializedName;

public class GetOrderIDRequest {

    @SerializedName("env")
    private String env;

    @SerializedName("buyer_name")
    private String buyer_name;

    @SerializedName("buyer_email")
    private String buyer_email;

    @SerializedName("buyer_phone")
    private String buyer_phone;

    @SerializedName("amount")
    private String amount;

    @SerializedName("description")
    private String description;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public String getBuyer_phone() {
        return buyer_phone;
    }

    public void setBuyer_phone(String buyer_phone) {
        this.buyer_phone = buyer_phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}