package com.qikserve.challenge.model;

import java.util.List;

public class Product {

    private String id;

    private String name;

    private int price;

    private int promocionalPrice;

    private List<Promotion> promotions;

    private boolean isPromotional;

    public Product() {

    }

    public Product(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.price = product.price;
        this.promotions = product.promotions;
        this.isPromotional = product.isPromotional;
        this.promocionalPrice = product.promocionalPrice;
    }

    public Product(String id, String name, int price, List<Promotion> promotions) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.promotions = promotions;
        this.isPromotional = false;
        this.promocionalPrice = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public boolean isPromotional() {
        return isPromotional;
    }

    public void setIsPromotional(boolean promotional) {
        isPromotional = promotional;
    }

    public int getPromocionalPrice() {
        return promocionalPrice;
    }

    public void setPromocionalPrice(int promocionalPrice) {
        this.promocionalPrice = promocionalPrice;
    }

}
