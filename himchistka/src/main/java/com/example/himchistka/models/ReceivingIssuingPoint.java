package com.example.himchistka.models;

public class ReceivingIssuingPoint
{
    int id_point;
    String city, street, house;

    public ReceivingIssuingPoint(int id_point, String city, String street, String house) {
        this.id_point = id_point;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public int getId_point() {
        return id_point;
    }

    public void setId_point(int id_point) {
        this.id_point = id_point;
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
