package com.example.himchistka.controller.TablesData.Customer;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.OpenNewPanel;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PhizCustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Customer, String> BIK_Column;

    @FXML
    private TextField BIK_Field;

    @FXML
    private TableColumn<Customer, String> City_Column;

    @FXML
    private TextField City_Field;

    @FXML
    private Button Search_Button;

    @FXML
    private TableColumn<Customer, String> Email_Column;

    @FXML
    private TextField Email_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<Customer, String> House_Column;

    @FXML
    private TextField House_Field;

    @FXML
    private TableColumn<Customer, String> INN_Column;

    @FXML
    private TextField INN_Field;

    @FXML
    private TableColumn<Customer, Integer> IdCustomer_Column;

    @FXML
    private TextField IdCustomer_Field;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableColumn<Customer, String> Name_Column;

    @FXML
    private TextField Name_Field;

    @FXML
    private TableColumn<Customer, String> Otchestvo_Column;

    @FXML
    private TextField Otchestvo_Field;

    @FXML
    private TableColumn<Customer, String> PassNumber_Column;

    @FXML
    private TextField PassNumber_Field;

    @FXML
    private TableColumn<Customer, String> PassSeries_Column;

    @FXML
    private TextField PassSeries_Field;

    @FXML
    private TableView<Customer> PhizCustomer_TableView;

    @FXML
    private TableColumn<Customer, String> Phone_Column;

    @FXML
    private TextField Phone_Field;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<Customer, String> Street_Column;

    @FXML
    private TextField Street_Field;

    @FXML
    private TableColumn<Customer, String> Surname_Column;

    @FXML
    private TextField Surname_Field;

    int index=-1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index=PhizCustomer_TableView.getSelectionModel().getSelectedIndex();
        if(index<=-1)
            return;
        /*
        IdCustomer_Field.setText(IdCustomer_Column.getCellData(index).toString());
        Type_Field.setText(Type_Column.getCellData(index));
        Phone_Field.setText(Phone_Column.getCellData(index));
        Email_Field.setText(Email_Column.getCellData(index));
        Surname_Field.setText(Surname_Column.getCellData(index));
        Name_Field.setText(Name_Column.getCellData(index));
        Otchestvo_Field.setText(Otchestvo_Column.getCellData(index));
        PassSeries_Field.setText(PassSeries_Column.getCellData(index));
        PassNumber_Field.setText(PassNumber_Column.getCellData(index));
        BIK_Field.setText(BIK_Column.getCellData(index));
        INN_Field.setText(INN_Column.getCellData(index));
        City_Field.setText(City_Column.getCellData(index));
        Street_Field.setText(Street_Column.getCellData(index));
        House_Field.setText(House_Column.getCellData(index));
        */
    }

    static int id;

    void refresh(Connection con)
    {
        ObservableList<Customer> customers= FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            ResultSet res=st.executeQuery("select * from customer where id_type="+id);
            ResultSet res_type=st1.executeQuery("select name from customer_type where id_type="+id);
            res_type.next();
            String type=res_type.getString("name");
            while (res.next())
            {
                customers.add(new Customer(res.getInt("id_customer"),
                        type,
                        res.getString("phone"),
                        res.getString("email"),
                        res.getString("surname"),
                        res.getString("name"),
                        res.getString("otchestvo"),
                        res.getString("passport_series"),
                        res.getString("passport_number"),
                        res.getString("BIK"),
                        res.getString("INN"),
                        res.getString("city"),
                        res.getString("street"),
                        res.getString("house")));
            }
            PhizCustomer_TableView.setItems(customers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if(!MainController.admin)
            Redact_Button.setDisable(true);
        IdCustomer_Column.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        Phone_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        Email_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        Surname_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("surname"));
        Name_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        Otchestvo_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("otchestvo"));
        PassSeries_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("passport_series"));
        PassNumber_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("passport_number"));
        BIK_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("bik"));
        INN_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("inn"));
        City_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("city"));
        Street_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("street"));
        House_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("house"));
        Connection con;
        try {
            con= BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    InsertPhizCustomerController.id=id;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Customer/InsertPhizCustomer-Panel.fxml",
                            "Добавление данных о заказчике(физическое лицо)");
                }
        );

        Redact_Button.setOnAction(event ->
                {
                    index = PhizCustomer_TableView.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите заказчика");
                        alert.showAndWait();
                        return;
                    }
                    RedactPhizCustomerController.numbercustomer=IdCustomer_Column.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Customer/RedactPhizCustomer-Panel.fxml",
                            "Редактирование данных о заказчике"+IdCustomer_Column.getCellData(index));
                }
        );

        Search_Button.setOnAction(event ->
                {
                    try
                    {
                        Statement st = con.createStatement();
                        String arg="id_type='"+id+"'";
                        if(!IdCustomer_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "id_customer='" + IdCustomer_Field.getText() + "'";
                        }
                        if(!Phone_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "phone='" + Phone_Field.getText() + "'";
                        }
                        if(!Email_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "email='" + Email_Field.getText() + "'";
                        }
                        if(!Surname_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "surname='" + Surname_Field.getText() + "'";
                        }
                        if(!Name_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "name='" + Name_Field.getText() + "'";
                        }
                        if(!Otchestvo_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "otchestvo='" + Otchestvo_Field.getText() + "'";
                        }
                        if(!PassSeries_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "passport_series='" + PassSeries_Field.getText() + "'";
                        }
                        if(!PassNumber_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "passport_number='" + PassNumber_Field.getText() + "'";
                        }
                        if(!BIK_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "BIK='" + BIK_Field.getText() + "'";
                        }
                        if(!INN_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "INN='" + INN_Field.getText() + "'";
                        }
                        if(!City_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "city='" + City_Field.getText() + "'";
                        }
                        if(!Street_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "street='" + Street_Field.getText() + "'";
                        }
                        if(!House_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "house='" + House_Field.getText() + "'";
                        }
                        ObservableList<Customer> customers = FXCollections.observableArrayList();
                            ResultSet res = st.executeQuery("select * from customer where " + arg);
                            while (res.next()) {
                                customers.add(new Customer(res.getInt("id_customer"),
                                        "Физическое лицо",
                                        res.getString("phone"),
                                        res.getString("email"),
                                        res.getString("surname"),
                                        res.getString("name"),
                                        res.getString("otchestvo"),
                                        res.getString("passport_series"),
                                        res.getString("passport_number"),
                                        res.getString("BIK"),
                                        res.getString("INN"),
                                        res.getString("city"),
                                        res.getString("street"),
                                        res.getString("house")));
                            }
                        PhizCustomer_TableView.setItems(customers);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Refresh_Button.setOnAction(event ->
                {
                    refresh(con);
                    IdCustomer_Field.setText("");
                    Phone_Field.setText("");
                    Email_Field.setText("");
                    Surname_Field.setText("");
                    Name_Field.setText("");
                    Otchestvo_Field.setText("");
                    PassSeries_Field.setText("");
                    PassNumber_Field.setText("");
                    BIK_Field.setText("");
                    INN_Field.setText("");
                    City_Field.setText("");
                    Street_Field.setText("");
                    House_Field.setText("");
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