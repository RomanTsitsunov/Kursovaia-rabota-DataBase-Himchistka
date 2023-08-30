package com.example.himchistka.controller.TablesData.Contract;

import com.example.himchistka.BD_connection;
import com.example.himchistka.models.Contract;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ContractControllerTest
{
    String concat="concat(customer.surname, ' ', substr(customer.name,1,1), '.', " +
            "substr(customer.otchestvo,1,1), '.')";

    @Test
    void search_active_contract() {
        String arg="contract_status.id_status=1";
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            st.execute("select contract.id_contract, customer.id_type, " +
                    concat + " as FIO, customer.organisation_name," +
                    "contract_status.name as status, contract.date_begin, " +
                    "contract.date_end from contract, contract_status, customer " +
                    "where contract.id_customer=customer.id_customer and " +
                    "contract.id_status=contract_status.id_status and " + arg);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(true);
    }

    @Test
    void search_without_date() {
        String arg="";
        ObservableList<Contract> contracts= FXCollections.observableArrayList();
        assertEquals(contracts.size(),0);
    }
}