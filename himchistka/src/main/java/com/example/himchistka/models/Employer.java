package com.example.himchistka.models;

public class Employer
{
    int tabel_number;
    String surname, name, otchestvo, phone, email, login, password;

    public Employer(int tabel_number, String surname, String name, String otchestvo, String phone, String email, String login, String password) {
        this.tabel_number = tabel_number;
        this.surname = surname;
        this.name = name;
        this.otchestvo = otchestvo;
        this.phone = phone;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public int getTabel_number() {
        return tabel_number;
    }

    public void setTabel_number(int tabel_number) {
        this.tabel_number = tabel_number;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
