package com.example.himchistka.models;

public class Payment
{
    int id_payment, id_order;
    float sum;
    String date_payment;

    public Payment(int id_payment, int id_order, float sum, String date_payment) {
        this.id_payment=id_payment;
        this.id_order = id_order;
        this.sum = sum;
        this.date_payment = date_payment;
    }

    public int getId_payment() {
        return id_payment;
    }

    public void setId_payment(int id_payment) {
        this.id_payment = id_payment;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getDate_payment() {
        return date_payment;
    }

    public void setDate_payment(String date_payment) {
        this.date_payment = date_payment;
    }
}
