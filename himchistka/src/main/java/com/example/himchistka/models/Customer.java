package com.example.himchistka.models;

public class Customer {
    int id;
    String type, phone, email, surname, name, otchestvo, passport_series, passport_number,
            organisation_name, ogrn, kpp, bik, inn, city, street, house;

    public Customer(int id, String type, String phone, String email, String surname,
                    String name, String otchestvo, String passport_series,
                    String passport_number, String organisation_name, String ogrn,
                    String kpp, String bik, String inn, String city, String street, String house) {
        this.id = id;
        this.type = type;
        this.phone = phone;
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.otchestvo = otchestvo;
        this.passport_series = passport_series;
        this.passport_number = passport_number;
        this.organisation_name = organisation_name;
        this.ogrn = ogrn;
        this.kpp = kpp;
        this.bik = bik;
        this.inn = inn;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public Customer(int id, String type, String phone, String email, String surname,
                    String name, String otchestvo, String passport_series,
                    String passport_number, String bik, String inn, String city,
                    String street, String house) {
        this.id = id;
        this.type = type;
        this.phone = phone;
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.otchestvo = otchestvo;
        this.passport_series = passport_series;
        this.passport_number = passport_number;
        this.bik = bik;
        this.inn = inn;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public Customer(int id, String type, String phone, String email,
                    String organisation_name,String ogrn, String kpp,
                    String bik, String inn, String city, String street, String house) {
        this.id = id;
        this.type = type;
        this.phone = phone;
        this.email = email;
        this.organisation_name = organisation_name;
        this.ogrn = ogrn;
        this.kpp = kpp;
        this.bik = bik;
        this.inn = inn;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

    public String getPassport_series() {
        return passport_series;
    }

    public void setPassport_series(String passport_series) {
        this.passport_series = passport_series;
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        this.passport_number = passport_number;
    }

    public String getOrganisation_name() {
        return organisation_name;
    }

    public void setOrganisation_name(String organisation_name) {
        this.organisation_name = organisation_name;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
