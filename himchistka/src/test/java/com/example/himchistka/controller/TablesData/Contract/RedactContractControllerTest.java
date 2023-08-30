package com.example.himchistka.controller.TablesData.Contract;

import com.example.himchistka.BD_connection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class RedactContractControllerTest
{
    String concat="concat(customer.surname, ' ', substr(customer.name,1,1), '.', " +
            "substr(customer.otchestvo,1,1), '.')";

    @Test
    void success_redact_test() {
        int id_contract=1;
        int id_customer=2;
        int id_status=1;
        String date_begin="2023-05-09";
        String date_end="2023-07-09";
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            st.execute("update contract set id_customer='"
                    + id_customer + "', id_status='"
                    + id_status + "', date_begin='"
                    + date_begin + "', date_end='"
                    + date_end + "' where id_contract='"
                    + id_contract + "'");
            Assertions.assertTrue(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void redact_right_data_test() {
        int id_contract=1;
        int id_customer=2;
        int id_status=1;
        String date_begin="2023-05-09";
        String date_end="2023-07-09";
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            st.execute("update contract set id_customer='"
                    + id_customer + "', id_status='"
                    + id_status + "', date_begin='"
                    + date_begin + "', date_end='"
                    + date_end + "' where id_contract='"
                    + id_contract + "'");
            st = con.createStatement();
            ResultSet res=st.executeQuery("select * from contract " +
                    "where id_contract=1");
            res.next();
            Assertions.assertTrue(id_contract==res.getInt("id_contract")&&
                    id_customer==res.getInt("id_customer")&&
                    id_status==res.getInt("id_status")&&
                    date_begin.equals(res.getString("date_begin")) &&
                    date_end.equals(res.getString("date_end")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void redact_nullvalues_data_test() {
        int id_contract=1;
        int id_customer=2;
        int id_status=1;
        String date_begin="2023-05-09";
        String date_end="null";
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            st.execute("update contract set id_customer='"
                    + id_customer + "', id_status='"
                    + id_status + "', date_begin='"
                    + date_begin + "', date_end="
                    + date_end + " where id_contract='"
                    + id_contract + "'");
            st = con.createStatement();
            ResultSet res=st.executeQuery("select * from contract " +
                    "where id_contract=1");
            res.next();
            Assertions.assertTrue(id_contract==res.getInt("id_contract")&&
                    id_customer==res.getInt("id_customer")&&
                    id_status==res.getInt("id_status")&&
                    date_begin.equals(res.getString("date_begin")) &&
                    res.getString("date_end")==null);
            st = con.createStatement();
            st.execute("update contract set id_customer='"
                    + id_customer + "', id_status='"
                    + id_status + "', date_begin='"
                    + date_begin + "', date_end="
                    + "'2023-07-09'" + " where id_contract='"
                    + id_contract + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}