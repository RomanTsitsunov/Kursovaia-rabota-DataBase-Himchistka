package com.example.himchistka.models;

public class ContractStatus
{
    int id;
    String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ContractStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }
}
