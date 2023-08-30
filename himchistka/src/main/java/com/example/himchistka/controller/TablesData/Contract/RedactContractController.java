package com.example.himchistka.controller.TablesData.Contract;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RedactContractController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Customer_ComboBox;

    @FXML
    private TextField DateBegin_Field;

    @FXML
    private TextField DateEnd_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private Label Label;

    @FXML
    private Button Redact_Button;

    @FXML
    private ComboBox<String> Status_ComboBox;

    public static int numbercontract;
    String concat="concat(customer.surname, ' ', substr(customer.name,1,1), '.', " +
            "substr(customer.otchestvo,1,1), '.')";

    @FXML
    void initialize()
    {
        Label.setText("Договор №"+numbercontract);
        ObservableList<String> customer_combobox = FXCollections.observableArrayList();
        ObservableList<String> status_combobox = FXCollections.observableArrayList();
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("select " +
                    concat + " as FIO from customer where " +
                    "customer.id_type=2");
            while (res.next())
                customer_combobox.add(res.getString("FIO"));

            st = con.createStatement();
            res = st.executeQuery("select organisation_name from customer where " +
                    "customer.id_type=1");
            while (res.next())
                customer_combobox.add(res.getString("organisation_name"));
            Customer_ComboBox.setItems(customer_combobox);

            st = con.createStatement();
            res = st.executeQuery("select name from contract_status");
            while (res.next())
                status_combobox.add(res.getString("name"));
            Status_ComboBox.setItems(status_combobox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Redact_Button.setOnAction(event ->
                {
                    if (Customer_ComboBox.getSelectionModel().getSelectedItem() == null
                            || Status_ComboBox.getSelectionModel().getSelectedItem() == null
                            || DateBegin_Field.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все необходимые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        Statement st1 = con.createStatement();
                        Statement st2 = con.createStatement();
                        ResultSet res1 = st1.executeQuery("select id_customer from customer where " +
                                "organisation_name='" + Customer_ComboBox.getSelectionModel().getSelectedItem() + "' or " +
                                concat + "='" + Customer_ComboBox.getSelectionModel().getSelectedItem() + "'");
                        res1.next();
                        ResultSet res2 = st2.executeQuery("select id_status from contract_status where " +
                                "name='" + Status_ComboBox.getSelectionModel().getSelectedItem() + "'");
                        res2.next();
                        String id_customer = res1.getString("id_customer");
                        String id_status = res2.getString("id_status");
                        String dateend;
                        if (DateEnd_Field.getText().isEmpty())
                            dateend="null";
                        else
                            dateend="'"+DateEnd_Field.getText()+"'";
                        st.execute("update contract set id_customer='"
                                + id_customer + "', id_status='"
                                + id_status + "', date_begin='"
                                + DateBegin_Field.getText() + "', date_end="
                                + dateend + " where id_contract='"
                                + numbercontract + "'");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Данные успешно отредактированы");
                        alert.showAndWait();
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Ошибка редактирования данных");
                        alert.showAndWait();
                    }
                }
        );

        Exit_Button.setOnAction(event ->
                {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Exit_Button.getScene().getWindow().hide();
                }
        );
    }

}
