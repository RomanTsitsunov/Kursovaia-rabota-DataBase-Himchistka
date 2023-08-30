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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Customer, String> BIK_Column;

    @FXML
    private TableColumn<Customer, String> City_Column;

    @FXML
    private TableView<Customer> Customer_TableView;

    @FXML
    private TableColumn<Customer, String> Email_Column;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<Customer, String> House_Column;

    @FXML
    private TableColumn<Customer, String> INN_Column;

    @FXML
    private TableColumn<Customer, Integer> IdCustomer_Column;

    @FXML
    private TableColumn<Customer, String> KPP_Column;

    @FXML
    private TableColumn<Customer, String> Name_Column;

    @FXML
    private TableColumn<Customer, String> OGRN_Column;

    @FXML
    private TableColumn<Customer, String> OrganName_Column;

    @FXML
    private TableColumn<Customer, String> Otchestvo_Column;

    @FXML
    private TableColumn<Customer, String> PassNumber_Column;

    @FXML
    private TableColumn<Customer, String> PassSeries_Column;

    @FXML
    private MenuItem Phiz_MenuItem;

    @FXML
    private TableColumn<Customer, String> Phone_Column;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<Customer, String> Street_Column;

    @FXML
    private TableColumn<Customer, String> Surname_Column;

    @FXML
    private TableColumn<Customer, String> Type_Column;

    @FXML
    private MenuItem Urid_MenuItem;

    void refresh(Connection con)
    {
        ObservableList<Customer> customers= FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            ResultSet res=st.executeQuery("select * from customer");
            ResultSet res_type;
            while (res.next())
            {
                res_type=st1.executeQuery("select name from customer_type where id_type=" +
                        res.getInt("id_type"));
                res_type.next();
                customers.add(new Customer(res.getInt("id_customer"),
                        res_type.getString("name"),
                        res.getString("phone"),
                        res.getString("email"),
                        res.getString("surname"),
                        res.getString("name"),
                        res.getString("otchestvo"),
                        res.getString("passport_series"),
                        res.getString("passport_number"),
                        res.getString("organisation_name"),
                        res.getString("OGRN"),
                        res.getString("KPP"),
                        res.getString("BIK"),
                        res.getString("INN"),
                        res.getString("city"),
                        res.getString("street"),
                        res.getString("house")));
            }
            Customer_TableView.setItems(customers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    ObservableList<String> list=FXCollections.observableArrayList();
    Connection con1;

    {
        try {
            con1 = BD_connection.get_connection();
            Statement st = con1.createStatement();
            ResultSet res=st.executeQuery("select name from customer_type");
            while (res.next())
                list.add(res.getString("name"));
            CustomerType_ComboBox.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
     */

    @FXML
    void initialize()
    {
        IdCustomer_Column.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        Type_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("type"));
        Phone_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        Email_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        Surname_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("surname"));
        Name_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        Otchestvo_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("otchestvo"));
        PassSeries_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("passport_series"));
        PassNumber_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("passport_number"));
        OrganName_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("organisation_name"));
        OGRN_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("ogrn"));
        KPP_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("kpp"));
        BIK_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("bik"));
        INN_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("inn"));
        City_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("city"));
        Street_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("street"));
        House_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("house"));
        Connection con;
        try {
            con=BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

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

        Phiz_MenuItem.setOnAction(event ->
        {
            PhizCustomerController.id=2;
            OpenNewPanel.open("/com/example/himchistka/TablesData/Customer/PhizCustomer-Panel.fxml",
                    "Физические лица");
        }
        );

        Urid_MenuItem.setOnAction(event ->
        {
            UridCustomerController.id=1;
            OpenNewPanel.open("/com/example/himchistka/TablesData/Customer/UridCustomer-Panel.fxml",
                    "Юридические лица");
        }
        );
    }

}


