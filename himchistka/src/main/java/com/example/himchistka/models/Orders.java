package com.example.himchistka.models;

public class Orders
{
    int id_order, id_contract, id_receiving_point, id_issuing_point;
    float sum, paid_sum;
    String employer, urgency, status, date_begin, date_ready, date_issue;

    public Orders(int id_order, int id_contract, int id_receiving_point, int id_issuing_point, float sum, float paid_sum, String employer, String urgency, String status, String date_begin, String date_ready, String date_issue) {
        this.id_order = id_order;
        this.id_contract = id_contract;
        this.id_receiving_point = id_receiving_point;
        this.id_issuing_point = id_issuing_point;
        this.sum = sum;
        this.paid_sum = paid_sum;
        this.employer = employer;
        this.urgency = urgency;
        this.status = status;
        this.date_begin = date_begin;
        this.date_ready = date_ready;
        this.date_issue = date_issue;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_contract() {
        return id_contract;
    }

    public void setId_contract(int id_contract) {
        this.id_contract = id_contract;
    }

    public int getId_receiving_point() {
        return id_receiving_point;
    }

    public void setId_receiving_point(int id_receiving_point) {
        this.id_receiving_point = id_receiving_point;
    }

    public int getId_issuing_point() {
        return id_issuing_point;
    }

    public void setId_issuing_point(int id_issuing_point) {
        this.id_issuing_point = id_issuing_point;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public float getPaid_sum() {
        return paid_sum;
    }

    public void setPaid_sum(float paid_sum) {
        this.paid_sum = paid_sum;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
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

    public String getDate_ready() {
        return date_ready;
    }

    public void setDate_ready(String date_ready) {
        this.date_ready = date_ready;
    }

    public String getDate_issue() {
        return date_issue;
    }

    public void setDate_issue(String date_issue) {
        this.date_issue = date_issue;
    }
}
