package com.example.himchistka.controller.TablesSpravochnici.Supplier;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.Customer;
import com.example.himchistka.models.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SupplierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Supplier, String> Email_Column;

    @FXML
    private TextField Email_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<Supplier, String> INN_Column;

    @FXML
    private TextField INN_Field;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableColumn<Supplier, String> KPP_Column;

    @FXML
    private TextField KPP_Field;

    @FXML
    private TableColumn<Supplier, String> OGRN_Column;

    @FXML
    private TextField OGRN_Field;

    @FXML
    private TableColumn<Supplier, String> Phone_Column;

    @FXML
    private TextField Phone_Field;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<Supplier, String> SupplierName_Column;

    @FXML
    private TextField SupplierName_Field;

    @FXML
    private TableView<Supplier> Supplier_Table;

    int index=-1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index=Supplier_Table.getSelectionModel().getSelectedIndex();
        if(index<=-1)
            return;
        SupplierName_Field.setText(SupplierName_Column.getCellData(index));
        Phone_Field.setText(Phone_Column.getCellData(index));
        Email_Field.setText(Email_Column.getCellData(index));
        INN_Field.setText(INN_Column.getCellData(index));
        KPP_Field.setText(KPP_Column.getCellData(index));
        OGRN_Field.setText(OGRN_Column.getCellData(index));
    }

    void refresh(Connection con)
    {
        ObservableList<Supplier> list= FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res=st.executeQuery("select * from supplier");
            while (res.next())
            {
                list.add(new Supplier(res.getString("name"),
                        res.getString("phone"),
                        res.getString("email"),
                        res.getString("INN"),
                        res.getString("KPP"),
                        res.getString("OGRN")));
            }
            Supplier_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if(!MainController.admin)
            Redact_Button.setDisable(true);
        SupplierName_Column.setCellValueFactory(new PropertyValueFactory<Supplier, String>("supplier_name"));
        Phone_Column.setCellValueFactory(new PropertyValueFactory<Supplier, String>("phone"));
        Email_Column.setCellValueFactory(new PropertyValueFactory<Supplier, String>("email"));
        INN_Column.setCellValueFactory(new PropertyValueFactory<Supplier, String>("inn"));
        KPP_Column.setCellValueFactory(new PropertyValueFactory<Supplier, String>("kpp"));
        OGRN_Column.setCellValueFactory(new PropertyValueFactory<Supplier, String>("ogrn"));
        Connection con;
        try {
            con= BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    if(SupplierName_Field.getText().isEmpty()
                            ||Phone_Field.getText().isEmpty()
                            ||Email_Field.getText().isEmpty()
                            ||INN_Field.getText().isEmpty()
                            ||KPP_Field.getText().isEmpty()
                            ||OGRN_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("insert into supplier(name, phone, email, INN, KPP, OGRN) " +
                                "values('"
                                +SupplierName_Field.getText()+"', '"
                                +Phone_Field.getText()+"', '"
                                +Email_Field.getText()+"', '"
                                +INN_Field.getText()+"', '"
                                +KPP_Field.getText()+"', '"
                                +OGRN_Field.getText()+"')");
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
                    index=Supplier_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите поставщика");
                        alert.showAndWait();
                        return;
                    }
                    if(SupplierName_Field.getText().isEmpty()
                            ||Phone_Field.getText().isEmpty()
                            ||Email_Field.getText().isEmpty()
                            ||INN_Field.getText().isEmpty()
                            ||KPP_Field.getText().isEmpty()
                            ||OGRN_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("update supplier set name='"
                                +SupplierName_Field.getText()+"', phone='"
                                +Phone_Field.getText()+"', email='"
                                +Email_Field.getText()+"', INN='"
                                +INN_Field.getText()+"', KPP='"
                                +KPP_Field.getText()+"', OGRN='"
                                +OGRN_Field.getText()+"' where OGRN='"
                                +OGRN_Column.getCellData(index)+"'");
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Данные успешно отредактированы");
                        alert.showAndWait();
                    } catch (SQLException e) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
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
                    SupplierName_Field.setText("");
                    Phone_Field.setText("");
                    Email_Field.setText("");
                    INN_Field.setText("");
                    KPP_Field.setText("");
                    OGRN_Field.setText("");
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
