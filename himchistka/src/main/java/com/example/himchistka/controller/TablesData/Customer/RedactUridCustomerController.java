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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RedactUridCustomerController {

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
    private TextField KPP_Field;

    @FXML
    private Label Label;

    @FXML
    private TextField OGRN_Field;

    @FXML
    private TextField OrganName_Field;

    @FXML
    private TextField Phone_Field;

    @FXML
    private Button Redact_Button;

    @FXML
    private TextField Street_Field;

    public static int numbercustomer;

    @FXML
    void initialize()
    {
        Label.setText("Заказчик №"+numbercustomer);
        Connection con;
        try {
            con= BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Redact_Button.setOnAction(event ->
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
                        st.execute("update Customer set phone='"
                                +Phone_Field.getText()+"', email='"
                                +Email_Field.getText()+"', organisation_name='"
                                +OrganName_Field.getText()+"', OGRN='"
                                +OGRN_Field.getText()+"', KPP='"
                                +KPP_Field.getText()+"', BIK='"
                                +BIK_Field.getText()+"', INN='"
                                +INN_Field.getText()+"', city='"
                                +City_Field.getText()+"', street='"
                                +Street_Field.getText()+"', house='"
                                +House_Field.getText()+"' where id_customer='"
                                +numbercustomer+"'");
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
