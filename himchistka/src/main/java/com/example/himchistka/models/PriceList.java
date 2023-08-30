package com.example.himchistka.models;

public class PriceList
{
    float price;
    String product_type, service, date_change_price;

    public PriceList(float price, String product_type, String service, String date_change_price) {
        this.price = price;
        this.product_type = product_type;
        this.service = service;
        this.date_change_price = date_change_price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate_change_price() {
        return date_change_price;
    }

    public void setDate_change_price(String date_change_price) {
        this.date_change_price = date_change_price;
    }
}
