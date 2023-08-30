package com.example.himchistka.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Email_Field;

    @FXML
    private Button LoginSignUp_Button;

    @FXML
    private TextField Login_Field;

    @FXML
    private TextField Name_Field;

    @FXML
    private TextField Otchestvo_Field;

    @FXML
    private PasswordField Password_Field;

    @FXML
    private TextField Phone_Field;

    @FXML
    private TextField Surname_Field;

    @FXML
    void initialize()
    {
        LoginSignUp_Button.setOnAction(event ->
                {
                    if (Surname_Field.getText().isEmpty() || Name_Field.getText().isEmpty()
                            || Otchestvo_Field.getText().isEmpty() || Phone_Field.getText().isEmpty()
                            || Phone_Field.getText().isEmpty() || Email_Field.getText().isEmpty()
                            || Login_Field.getText().isEmpty() || Password_Field.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    Connection con;
                    try {
                        con = BD_connection.get_connection();
                        Statement st = con.createStatement();
                        st.execute("insert into employer (surname, name, otchestvo, phone, email," +
                                " login, password) values ('"
                                + Surname_Field.getText() + "', '"
                                + Name_Field.getText() + "', '"
                                + Otchestvo_Field.getText() + "', '"
                                + Phone_Field.getText() + "', '"
                                + Email_Field.getText() + "', '"
                                + Login_Field.getText() + "', '"
                                + Password_Field.getText() + "')");
                        LoginSignUp_Button.getScene().getWindow().hide();
                        con.close();
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("В системе уже есть сотрудник с таким логином!");
                        alert.showAndWait();
                        //throw new RuntimeException(e);
                    }
                }
        );
    }

}