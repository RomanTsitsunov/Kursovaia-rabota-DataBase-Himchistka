package com.example.himchistka.models;

public class Contract
{
    int id;
    String customer, status, date_begin, date_end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(String date_begin) {
        this.date_begin = date_begin;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public Contract(int id, String customer, String status, String date_begin, String date_end) {
        this.id = id;
        this.customer = customer;
        this.status = status;
        this.date_begin = date_begin;
        this.date_end = date_end;
    }
}
