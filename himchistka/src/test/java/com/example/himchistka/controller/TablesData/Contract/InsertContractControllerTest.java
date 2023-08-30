package com.example.himchistka.controller.TablesData.Contract;

import com.example.himchistka.BD_connection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class InsertContractControllerTest
{
    @Test
    void success_insert_test() {
        int id_contract=15;
        int id_customer=4;
        int id_status=2;
        String date_begin="2023-05-26";
        String date_end="2023-05-27";
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            st.execute("delete from contract where " +
                    "id_contract=15");
            st=con.createStatement();
            st.execute("insert into contract" +
                    "(id_contract, id_customer, id_status, date_begin, date_end) " +
                    "values('"+
                    id_contract+"', '" +
                    id_customer+"', '" +
                    id_status+"', '" +
                    date_begin+"', '" +
                    date_end+"')");
            Assertions.assertTrue(true);
            st = con.createStatement();
            st.execute("delete from contract where " +
                    "id_contract=15");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void insert_right_data_test() {
        int id_contract=15;
        int id_customer=4;
        int id_status=2;
        String date_begin="2023-05-26";
        String date_end="2023-05-27";
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            st.execute("delete from contract where " +
                    "id_contract=15");
            st=con.createStatement();
            st.execute("insert into contract" +
                    "(id_contract, id_customer, id_status, date_begin, date_end) " +
                    "values('"+
                    id_contract+"', '" +
                    id_customer+"', '" +
                    id_status+"', '" +
                    date_begin+"', '" +
                    date_end+"')");
            st = con.createStatement();
            ResultSet res=st.executeQuery("select * from contract " +
                    "where id_contract=15");
            res.next();
            Assertions.assertTrue(id_contract==res.getInt("id_contract")&&
                    id_customer==res.getInt("id_customer")&&
                    id_status==res.getInt("id_status")&&
                    date_begin.equals(res.getString("date_begin")) &&
                    date_end.equals(res.getString("date_end")));
            st=con.createStatement();
            st.execute("delete from contract where " +
                    "id_contract=15");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void insert_nullvaluess_data_test() {
        int id_contract=15;
        int id_customer=4;
        int id_status=2;
        String date_begin="2023-05-26";
        String date_end="null";
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            st.execute("delete from contract where " +
                    "id_contract=15");
            st=con.createStatement();
            st.execute("insert into contract" +
                    "(id_contract, id_customer, id_status, date_begin, date_end) " +
                    "values('"+
                    id_contract+"', '" +
                    id_customer+"', '" +
                    id_status+"', '" +
                    date_begin+"', " +
                    date_end+")");
            st = con.createStatement();
            ResultSet res=st.executeQuery("select * from contract " +
                    "where id_contract=15");
            res.next();
            Assertions.assertTrue(id_contract==res.getInt("id_contract")&&
                    id_customer==res.getInt("id_customer")&&
                    id_status==res.getInt("id_status")&&
                    date_begin.equals(res.getString("date_begin")) &&
                    res.getString("date_end")==null);
            st.execute("delete from contract where " +
                    "id_contract=15");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}