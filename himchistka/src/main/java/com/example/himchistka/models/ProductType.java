package com.example.himchistka.models;

public class ProductType
{
    String product_type, unit;

    public ProductType(String product_type, String unit) {
        this.product_type = product_type;
        this.unit = unit;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
