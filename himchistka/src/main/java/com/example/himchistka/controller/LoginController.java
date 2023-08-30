package com.example.himchistka.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.OpenNewPanel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button LoginSignIn_Button;

    @FXML
    private Button LoginSignUp_Button;

    @FXML
    private TextField Login_Field;

    @FXML
    private PasswordField Password_Field;

    @FXML
    void initialize()
    {
        LoginSignIn_Button.setOnAction(event ->
                {
                    if (Login_Field.getText().isEmpty() || Password_Field.getText().isEmpty()) {
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
                        ResultSet res = st.executeQuery("select * from employer where login='" +
                                Login_Field.getText() + "' and password='" +
                                Password_Field.getText() + "'");
                        if (res.next()) {
                            if (Objects.equals(Login_Field.getText(), "admin"))
                                MainController.admin = true;
                            else
                                MainController.admin = false;
                            LoginSignIn_Button.getScene().getWindow().hide();
                            OpenNewPanel.open("/com/example/himchistka/Main-Panel.fxml",
                                    res.getString("login"));
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(null);
                            alert.setHeaderText(null);
                            alert.setContentText("Неверный логин или пароль!");
                            alert.showAndWait();
                        }
                        con.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
        );

        LoginSignUp_Button.setOnAction(event ->
        {
            OpenNewPanel.open("/com/example/himchistka/Registration-Panel.fxml","Регистрация");
        }
        );
    }
}