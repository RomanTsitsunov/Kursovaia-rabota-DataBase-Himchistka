package com.example.himchistka.models;

public class Marking
{
    String marking, description;

    public Marking(String marking, String description) {
        this.marking = marking;
        this.description = description;
    }

    public String getMarking() {
        return marking;
    }

    public void setMarking(String marking) {
        this.marking = marking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
