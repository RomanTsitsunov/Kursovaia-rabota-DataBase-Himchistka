package com.example.himchistka.models;

public class ProductAccessories
{
    int id_product;
    String accessories;

    public ProductAccessories(int id_product, String accessories) {
        this.id_product = id_product;
        this.accessories = accessories;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getAccessories() {
        return accessories;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }
}
