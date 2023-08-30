package com.example.himchistka.models;

public class Supplier
{
    String supplier_name, phone, email, inn, kpp, ogrn;

    public Supplier(String supplier_name, String phone, String email, String inn, String kpp, String ogrn) {
        this.supplier_name = supplier_name;
        this.phone = phone;
        this.email = email;
        this.inn = inn;
        this.kpp = kpp;
        this.ogrn = ogrn;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }
}
