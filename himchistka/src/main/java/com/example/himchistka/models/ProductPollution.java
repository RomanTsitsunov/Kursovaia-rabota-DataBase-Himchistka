package com.example.himchistka.models;

public class ProductPollution
{
    int id_product;
    String pollution;

    public ProductPollution(int id_product, String pollution) {
        this.id_product = id_product;
        this.pollution = pollution;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getPollution() {
        return pollution;
    }

    public void setPollution(String pollution) {
        this.pollution = pollution;
    }
}
