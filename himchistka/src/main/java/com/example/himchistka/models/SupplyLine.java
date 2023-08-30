package com.example.himchistka.models;

public class SupplyLine
{
    int id_supply, amount_materials;
    String consum_material;

    public SupplyLine(int id_supply, int amount_materials, String consum_material) {
        this.id_supply = id_supply;
        this.amount_materials = amount_materials;
        this.consum_material = consum_material;
    }

    public int getId_supply() {
        return id_supply;
    }

    public void setId_supply(int id_supply) {
        this.id_supply = id_supply;
    }

    public int getAmount_materials() {
        return amount_materials;
    }

    public void setAmount_materials(int amount_materials) {
        this.amount_materials = amount_materials;
    }

    public String getConsum_material() {
        return consum_material;
    }

    public void setConsum_material(String consum_material) {
        this.consum_material = consum_material;
    }
}
