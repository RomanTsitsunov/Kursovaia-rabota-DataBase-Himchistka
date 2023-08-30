package com.example.himchistka.models;

public class Urgency
{
    float sum_ratio;
    String urgency;

    public Urgency(float sum_ratio, String urgency) {
        this.sum_ratio = sum_ratio;
        this.urgency = urgency;
    }

    public float getSum_ratio() {
        return sum_ratio;
    }

    public void setSum_ratio(float sum_ratio) {
        this.sum_ratio = sum_ratio;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }
}
