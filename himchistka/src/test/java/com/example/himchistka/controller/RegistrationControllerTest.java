package com.example.himchistka.controller;

import com.example.himchistka.BD_connection;
import javafx.scene.control.Alert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationControllerTest {
    @Test
    void success_insert_test()
    {
        int tabel_number=5;
        String surname="Ivanov";
        String name="Ivan";
        String otchestvo="Ivanovich";
        String phone="89053011223";
        String email="Ivan@gmail.com";
        String login="Ivan";
        String password="Ivanpass";
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            st.execute("delete from employer where " +
                    "tabel_number=5");
            st=con.createStatement();
            st.execute("insert into employer (tabel_number, surname, name, otchestvo," +
                    " phone, email," +
                    " login, password) values ('"
                    + tabel_number + "', '"
                    + surname + "', '"
                    + name + "', '"
                    + otchestvo + "', '"
                    + phone + "', '"
                    + email + "', '"
                    + login + "', '"
                    + password + "')");
            st = con.createStatement();
            st.execute("delete from employer where " +
                    "tabel_number=5");
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(true);
    }

    @Test
    void insert_right_data_test()
    {
        int tabel_number=5;
        String surname="Ivanov";
        String name="Ivan";
        String otchestvo="Ivanovich";
        String phone="89053011223";
        String email="Ivan@gmail.com";
        String login="Ivan";
        String password="Ivanpass";
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            st.execute("delete from employer where " +
                    "tabel_number=5");
            st=con.createStatement();
            st.execute("insert into employer (tabel_number, surname, name, otchestvo," +
                    " phone, email," +
                    " login, password) values ('"
                    + tabel_number + "', '"
                    + surname + "', '"
                    + name + "', '"
                    + otchestvo + "', '"
                    + phone + "', '"
                    + email + "', '"
                    + login + "', '"
                    + password + "')");
            st = con.createStatement();
            ResultSet res=st.executeQuery("select * from employer " +
                    "where tabel_number=5");
            res.next();
            Assertions.assertTrue(tabel_number==res.getInt("tabel_number")&&
                    surname.equals(res.getString("surname")) &&
                    name.equals(res.getString("name")) &&
                    otchestvo.equals(res.getString("otchestvo")) &&
                    phone.equals(res.getString("phone")) &&
                    email.equals(res.getString("email")) &&
                    login.equals(res.getString("login")) &&
                    password.equals(res.getString("password")));
            st = con.createStatement();
            st.execute("delete from employer where " +
                    "tabel_number=5");
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(true);
    }
}