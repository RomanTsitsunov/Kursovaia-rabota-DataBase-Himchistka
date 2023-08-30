package com.example.himchistka.controller.TablesData.ProductProcessing;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.models.ProductProcessing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InsertProductProcessing {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField AmountMaterials_Field;

    @FXML
    private ComboBox<String> ConsumMaterial_ComboBox;

    @FXML
    private Button Exit_Button;

    @FXML
    private Button Insert_Button;

    @FXML
    private Label Label;

    @FXML
    private ComboBox<String> Service_ComboBox;

    public static int numberproduct;

    @FXML
    void initialize()
    {
        Label.setText("Изделие №"+numberproduct);
        ObservableList<String> service_combobox = FXCollections.observableArrayList();
        ObservableList<String> consummaterial_combobox = FXCollections.observableArrayList();
        Connection con;
        try {
            con = BD_connection.get_connection();
            Statement st = con.createStatement();
            ResultSet res;
            res = st.executeQuery("select distinct service.name from service, price_list, product_order " +
                    "where product_order.id_product=" + numberproduct + " and " +
                    "price_list.id_product_type=product_order.id_product_type and " +
                    "service.id_service=price_list.id_service");
            while (res.next())
                service_combobox.add(res.getString("name"));
            Service_ComboBox.setItems(service_combobox);
            st = con.createStatement();
            res = st.executeQuery("select name from storage");
            while (res.next())
                consummaterial_combobox.add(res.getString("name"));
            ConsumMaterial_ComboBox.setItems(consummaterial_combobox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Insert_Button.setOnAction(event ->
                {
                    if (Service_ComboBox.getSelectionModel().getSelectedItem() == null
                            || ConsumMaterial_ComboBox.getSelectionModel().getSelectedItem() == null
                            || AmountMaterials_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_service from service where " +
                                "name='"+Service_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String service=res.getString("id_service");

                        st=con.createStatement();
                        res=st.executeQuery("select id_consum_material from storage where " +
                                "name='"+ConsumMaterial_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String consum_material=res.getString("id_consum_material");

                        st=con.createStatement();
                        st.execute("insert into product_processing(id_product, id_service, " +
                                "id_consum_material, amount_materials) " +
                                "values('"
                                + numberproduct + "', '"
                                + service + "', '"
                                + consum_material + "', '"
                                + AmountMaterials_Field.getText() + "')");
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
