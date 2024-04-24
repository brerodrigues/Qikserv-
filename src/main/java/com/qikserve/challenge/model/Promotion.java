package com.qikserve.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Promotion {

    private String id;

    private String type;

    @JsonProperty("required_qty")
    private int requiredQty;

    private int price;

    private double amount;

    public Promotion() {

    }

    public Promotion(String id, String type, int requiredQty, int price, double amount) {
        this.id = id;
        this.type = type;
        this.requiredQty = requiredQty;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRequiredQty() {
        return requiredQty;
    }

    public void setRequiredQty(int requiredQty) {
        this.requiredQty = requiredQty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
