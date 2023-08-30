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

public class InsertUridCustomerController {

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
    private TextField KPP_Field;

    @FXML
    private TextField OGRN_Field;

    @FXML
    private TextField OrganName_Field;

    @FXML
    private TextField Phone_Field;

    @FXML
    private TextField Street_Field;

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
                            || OrganName_Field.getText().isEmpty()
                            || OGRN_Field.getText().isEmpty()
                            || KPP_Field.getText().isEmpty()
                            || BIK_Field.getText().isEmpty()
                            || INN_Field.getText().isEmpty()
                            || City_Field.getText().isEmpty()
                            || Street_Field.getText().isEmpty()
                            || House_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("insert into Customer(id_type, phone, email, " +
                                "organisation_name, OGRN, KPP, " +
                                "BIK, INN, city, street, house) values('"
                                +id+"', '"
                                +Phone_Field.getText()+"', '"
                                +Email_Field.getText()+"', '"
                                +OrganName_Field.getText()+"', '"
                                +OGRN_Field.getText()+"', '"
                                +KPP_Field.getText()+"', '"
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
