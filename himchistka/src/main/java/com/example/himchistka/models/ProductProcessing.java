package com.example.himchistka.models;

public class ProductProcessing
{
    int id_product, amount_materials;
    String service, consum_material;

    public ProductProcessing(int id_product, int amount_materials, String service, String consum_material) {
        this.id_product = id_product;
        this.amount_materials = amount_materials;
        this.service = service;
        this.consum_material = consum_material;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getAmount_materials() {
        return amount_materials;
    }

    public void setAmount_materials(int amount_materials) {
        this.amount_materials = amount_materials;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getConsum_material() {
        return consum_material;
    }

    public void setConsum_material(String consum_material) {
        this.consum_material = consum_material;
    }
}
