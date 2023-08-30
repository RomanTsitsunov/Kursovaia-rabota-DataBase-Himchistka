package com.example.himchistka.models;

public class ProductDefect
{
    int id_product;
    String defect;

    public ProductDefect(int id_product, String defect) {
        this.id_product = id_product;
        this.defect = defect;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }
}
