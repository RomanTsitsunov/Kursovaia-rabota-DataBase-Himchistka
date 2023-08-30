package com.example.himchistka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.example.himchistka.BD_connection.DB_Driver;
import static com.example.himchistka.BD_connection.DB_URL;
import static org.junit.jupiter.api.Assertions.*;

class BD_connectionTest {
    @Test
    void get_connection_test()
    {
        try
        {
            Class.forName(DB_Driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("JDBC драйвер для СУБД не найден!");
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection(DB_URL, "root", "19922099");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(true);
    }
}