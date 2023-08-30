package com.example.himchistka.controller.TablesSpravochnici.PriceList;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.controller.TablesSpravochnici.Colour.ColourController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RedactPriceList {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField DateChangePrice_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private TextField Price_Field;

    @FXML
    private Button Redact_Button;

    public static String producttype;
    public static String service;

    @FXML
    void initialize()
    {
        Connection con;
        try {
            con= BD_connection.get_connection();
            Statement st=con.createStatement();
            ResultSet res=st.executeQuery("select date_format(sysdate(), '%Y-%m-%d') from dual");
            res.next();
            DateChangePrice_Field.setText(res.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Redact_Button.setOnAction(event ->
                {
                    if (Price_Field.getText().isEmpty()
                            || DateChangePrice_Field.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_product_type from product_type where " +
                                "name='"+producttype+"'");
                        res.next();
                        String idproducttype=res.getString("id_product_type");

                        st = con.createStatement();
                        res=st.executeQuery("select id_service from service where " +
                                "name='"+service+"'");
                        res.next();
                        String idservice=res.getString("id_service");

                        st = con.createStatement();
                        st.execute("update price_list set price='"
                                + Price_Field.getText() + "', date_change_price='"
                                + DateChangePrice_Field.getText() + "' where id_product_type='"
                                + idproducttype + "' and id_service='"
                                + idservice + "'");
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
