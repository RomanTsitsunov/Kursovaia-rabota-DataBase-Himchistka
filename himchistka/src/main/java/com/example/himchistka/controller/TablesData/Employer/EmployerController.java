package com.example.himchistka.controller.TablesData.Employer;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.Employer;
import com.example.himchistka.models.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class EmployerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Employer, String> Email_Column;

    @FXML
    private TextField Email_Field;

    @FXML
    private TableView<Employer> Employer_Table;

    @FXML
    private Button Exit_Button;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableColumn<Employer, String> Login_Column;

    @FXML
    private TextField Login_Field;

    @FXML
    private TableColumn<Employer, String> Name_Column;

    @FXML
    private TextField Name_Field;

    @FXML
    private TableColumn<Employer, String> Otchestvo_Column;

    @FXML
    private TextField Otchestvo_Field;

    @FXML
    private TableColumn<Employer, String> Password_Column;

    @FXML
    private TextField Password_Field;

    @FXML
    private TableColumn<Employer, String> Phone_Column;

    @FXML
    private TextField Phone_Field;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<Employer, String> Surname_Column;

    @FXML
    private TextField Surname_Field;

    @FXML
    private TableColumn<Employer, Integer> TabelNumber_Column;

    int index=-1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index=Employer_Table.getSelectionModel().getSelectedIndex();
        if(index<=-1)
            return;
        Surname_Field.setText(Surname_Column.getCellData(index));
        Name_Field.setText(Name_Column.getCellData(index));
        Otchestvo_Field.setText(Otchestvo_Column.getCellData(index));
        Phone_Field.setText(Phone_Column.getCellData(index));
        Email_Field.setText(Email_Column.getCellData(index));
        Login_Field.setText(Login_Column.getCellData(index));
        Password_Field.setText(Password_Column.getCellData(index));
    }

    void refresh(Connection con)
    {
        ObservableList<Employer> list= FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res=st.executeQuery("select * from employer");
            while (res.next())
            {
                list.add(new Employer(res.getInt("tabel_number"),
                        res.getString("surname"),
                        res.getString("name"),
                        res.getString("otchestvo"),
                        res.getString("phone"),
                        res.getString("email"),
                        res.getString("login"),
                        res.getString("password")));
            }
            Employer_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if(!MainController.admin) {
            Redact_Button.setDisable(true);
            Insert_Button.setDisable(true);
        }
        TabelNumber_Column.setCellValueFactory(new PropertyValueFactory<Employer, Integer>("tabel_number"));
        Surname_Column.setCellValueFactory(new PropertyValueFactory<Employer, String>("surname"));
        Name_Column.setCellValueFactory(new PropertyValueFactory<Employer, String>("name"));
        Otchestvo_Column.setCellValueFactory(new PropertyValueFactory<Employer, String>("otchestvo"));
        Phone_Column.setCellValueFactory(new PropertyValueFactory<Employer, String>("phone"));
        Email_Column.setCellValueFactory(new PropertyValueFactory<Employer, String>("email"));
        Login_Column.setCellValueFactory(new PropertyValueFactory<Employer, String>("login"));
        Password_Column.setCellValueFactory(new PropertyValueFactory<Employer, String>("password"));
        Connection con;
        try {
            con= BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    if (Surname_Field.getText().isEmpty()
                            ||Name_Field.getText().isEmpty()
                            ||Otchestvo_Field.getText().isEmpty()
                            ||Phone_Field.getText().isEmpty()
                            ||Email_Field.getText().isEmpty()
                            ||Login_Field.getText().isEmpty()
                            ||Password_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("insert into employer(surname, name, otchestvo, phone, email," +
                                " login, password) " +
                                "values('"
                                +Surname_Field.getText()+"', '"
                                +Name_Field.getText()+"', '"
                                +Otchestvo_Field.getText()+"', '"
                                +Phone_Field.getText()+"', '"
                                +Email_Field.getText()+"', '"
                                +Login_Field.getText()+"', '"
                                +Password_Field.getText()+"')");
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
                    refresh(con);
                }
        );

        Redact_Button.setOnAction(event ->
                {
                    index=Employer_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите сотрудника");
                        alert.showAndWait();
                        return;
                    }
                    if (Surname_Field.getText().isEmpty()
                            ||Name_Field.getText().isEmpty()
                            ||Otchestvo_Field.getText().isEmpty()
                            ||Phone_Field.getText().isEmpty()
                            ||Email_Field.getText().isEmpty()
                            ||Login_Field.getText().isEmpty()
                            ||Password_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("update employer set surname='"
                                +Surname_Field.getText()+"', name='"
                                +Name_Field.getText()+"', otchestvo='"
                                +Otchestvo_Field.getText()+"', phone='"
                                +Phone_Field.getText()+"', email='"
                                +Email_Field.getText()+"', login='"
                                +Login_Field.getText()+"', password='"
                                +Password_Field.getText()+"' where login='"
                                +Login_Column.getCellData(index)+"'");
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
                    refresh(con);
                }
        );

        Refresh_Button.setOnAction(event ->
                {
                    refresh(con);
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
