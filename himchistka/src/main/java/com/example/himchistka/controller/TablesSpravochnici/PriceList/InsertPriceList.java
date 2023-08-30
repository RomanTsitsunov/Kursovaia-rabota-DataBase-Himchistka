package com.example.himchistka.controller.TablesSpravochnici.PriceList;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.models.PriceList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InsertPriceList {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField DateChangePrice_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private Button Insert_Button;

    @FXML
    private TextField Price_Field;

    @FXML
    private ComboBox<String> ProductType_ComboBox;

    @FXML
    private ComboBox<String> Service_ComboBox;

    @FXML
    void initialize()
    {
        ObservableList<String> producttype_combobox = FXCollections.observableArrayList();
        ObservableList<String> service_combobox = FXCollections.observableArrayList();
        Connection con;
        try {
            con= BD_connection.get_connection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("select name from product_type");
            while (res.next())
                producttype_combobox.add(res.getString("name"));
            ProductType_ComboBox.setItems(producttype_combobox);

            st = con.createStatement();
            res = st.executeQuery("select name from service");
            while (res.next())
                service_combobox.add(res.getString("name"));
            Service_ComboBox.setItems(service_combobox);

            st=con.createStatement();
            res=st.executeQuery("select date_format(sysdate(), '%Y-%m-%d') from dual");
            res.next();
            DateChangePrice_Field.setText(res.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Insert_Button.setOnAction(event ->
                {
                    if (ProductType_ComboBox.getSelectionModel().getSelectedItem() == null
                            || Service_ComboBox.getSelectionModel().getSelectedItem() == null
                            || Price_Field.getText().isEmpty()
                            || DateChangePrice_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_product_type from product_type where " +
                                "name='"+ProductType_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String producttype=res.getString("id_product_type");

                        st = con.createStatement();
                        res=st.executeQuery("select id_service from service where " +
                                "name='"+Service_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String service=res.getString("id_service");

                        st=con.createStatement();
                        st.execute("insert into price_list(id_product_type, id_service, price, date_change_price) " +
                                "values('"
                                + producttype + "', '"
                                + service + "', '"
                                + Price_Field.getText() + "', '"
                                + DateChangePrice_Field.getText() + "')");
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Данные успешно добавлены");
                        alert.showAndWait();
                    } catch (SQLException e) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Ошибка добавления данных");
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
