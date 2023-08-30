package com.example.himchistka.models;

public class Storage
{
    int amount_materials;
    String consum_material, unit;

    public Storage(int amount_materials, String consum_material, String unit) {
        this.amount_materials = amount_materials;
        this.consum_material = consum_material;
        this.unit = unit;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
