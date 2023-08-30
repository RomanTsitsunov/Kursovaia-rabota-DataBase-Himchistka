package com.example.himchistka.controller.TablesData.Storage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RedactStorage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField AmountMaterials_Field;

    @FXML
    private TextField ConsumMaterial_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private Button Redact_Button;

    @FXML
    private TextField Unit_Field;

    @FXML
    void initialize()
    {
        Connection con;
        try {
            con= BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Redact_Button.setOnAction(event ->
                {
                    if (ConsumMaterial_Field.getText().isEmpty()
                            || Unit_Field.getText().isEmpty()
                            || AmountMaterials_Field.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("update storage set name='"
                                +ConsumMaterial_Field.getText()+"', unit='"
                                +Unit_Field.getText()+"', amount_materials='"
                                +AmountMaterials_Field.getText()+"' where name='"
                                +ConsumMaterial_Field.getText()+"'");
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
