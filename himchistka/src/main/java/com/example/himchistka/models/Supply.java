package com.example.himchistka.models;

public class Supply
{
    int id_supply;
    String supplier_name, date_supply;

    public Supply(int id_supply, String supplier_name, String date_supply) {
        this.id_supply = id_supply;
        this.supplier_name = supplier_name;
        this.date_supply = date_supply;
    }

    public int getId_supply() {
        return id_supply;
    }

    public void setId_supply(int id_supply) {
        this.id_supply = id_supply;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getDate_supply() {
        return date_supply;
    }

    public void setDate_supply(String date_supply) {
        this.date_supply = date_supply;
    }
}
