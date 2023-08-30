package com.example.himchistka.models;

public class ProductOrder
{
    int id_product, id_order;
    float price;
    String product_type, colour, material, marking, completeness;

    public ProductOrder(int id_product, int id_order, float price, String product_type, String colour, String material, String marking, String completeness) {
        this.id_product = id_product;
        this.id_order = id_order;
        this.price = price;
        this.product_type = product_type;
        this.colour = colour;
        this.material = material;
        this.marking = marking;
        this.completeness = completeness;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMarking() {
        return marking;
    }

    public void setMarking(String marking) {
        this.marking = marking;
    }

    public String getCompleteness() {
        return completeness;
    }

    public void setCompleteness(String completeness) {
        this.completeness = completeness;
    }
}
