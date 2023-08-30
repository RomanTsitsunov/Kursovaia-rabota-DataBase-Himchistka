package com.example.himchistka.controller.TablesData.Customer;

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

public class InsertPhizCustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField BIK_Field;

    @FXML
    private TextField City_Field;

    @FXML
    private TextField Email_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private TextField House_Field;

    @FXML
    private TextField INN_Field;

    @FXML
    private Button Insert_Button;

    @FXML
    private TextField Name_Field;

    @FXML
    private TextField Otchestvo_Field;

    @FXML
    private TextField PassNumber_Field;

    @FXML
    private TextField PassSeries_Field;

    @FXML
    private TextField Phone_Field;

    @FXML
    private TextField Street_Field;

    @FXML
    private TextField Surname_Field;

    static int id;

    @FXML
    void initialize()
    {
        Connection con;
        try {
            con = BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Insert_Button.setOnAction(event ->
                {
                    if (Phone_Field.getText().isEmpty()
                            || Email_Field.getText().isEmpty()
                            || Surname_Field.getText().isEmpty()
                            || Name_Field.getText().isEmpty()
                            || Otchestvo_Field.getText().isEmpty()
                            || PassSeries_Field.getText().isEmpty()
                            || PassNumber_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все необходимые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("insert into Customer(id_type, phone, email," +
                                " surname, name, otchestvo, passport_series, passport_number," +
                                "BIK, INN, city, street, house) values('"
                                +id+"', '"
                                +Phone_Field.getText()+"', '"
                                +Email_Field.getText()+"', '"
                                +Surname_Field.getText()+"', '"
                                +Name_Field.getText()+"', '"
                                +Otchestvo_Field.getText()+"', '"
                                +PassSeries_Field.getText()+"', '"
                                +PassNumber_Field.getText()+"', '"
                                +BIK_Field.getText()+"', '"
                                +INN_Field.getText()+"', '"
                                +City_Field.getText()+"', '"
                                +Street_Field.getText()+"', '"
                                +House_Field.getText()+"')");
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
