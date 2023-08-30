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

public class UridCustomerController {

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
    private TableColumn<Customer, String> KPP_Column;

    @FXML
    private TextField KPP_Field;

    @FXML
    private TableColumn<Customer, String> OGRN_Column;

    @FXML
    private TextField OGRN_Field;

    @FXML
    private TableColumn<Customer, String> OrganName_Column;

    @FXML
    private TextField OrganName_Field;

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
    private TableView<Customer> UridCustomer_TableView;

    int index=-1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index=UridCustomer_TableView.getSelectionModel().getSelectedIndex();
        if(index<=-1)
            return;
        /*
        IdCustomer_Field.setText(IdCustomer_Column.getCellData(index).toString());
        Type_Field.setText(Type_Column.getCellData(index));
        Phone_Field.setText(Phone_Column.getCellData(index));
        Email_Field.setText(Email_Column.getCellData(index));
        OrganName_Field.setText(OrganName_Column.getCellData(index));
        OGRN_Field.setText(OGRN_Column.getCellData(index));
        KPP_Field.setText(KPP_Column.getCellData(index));
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
            String s=res_type.getString("name");
            while (res.next())
            {
                customers.add(new Customer(res.getInt("id_customer"),
                        s,
                        res.getString("phone"),
                        res.getString("email"),
                        res.getString("organisation_name"),
                        res.getString("OGRN"),
                        res.getString("KPP"),
                        res.getString("BIK"),
                        res.getString("INN"),
                        res.getString("city"),
                        res.getString("street"),
                        res.getString("house")));
            }
            UridCustomer_TableView.setItems(customers);
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
            con= BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    InsertUridCustomerController.id=id;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Customer/InsertUridCustomer-Panel.fxml",
                            "Добавление данных о заказчике(юридическое лицо)");
                }
        );

        Redact_Button.setOnAction(event ->
                {
                    index = UridCustomer_TableView.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите заказчика");
                        alert.showAndWait();
                        return;
                    }
                    RedactUridCustomerController.numbercustomer=IdCustomer_Column.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Customer/RedactUridCustomer-Panel.fxml",
                            "Редактирование данных о заказчике #"+IdCustomer_Column.getCellData(index));
                }
        );

        Search_Button.setOnAction(event ->
                {
                    try
                    {
                        Statement st = con.createStatement();
                        String arg="id_type="+id+"'";
                        if(!IdCustomer_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "id_customer='" + IdCustomer_Field + "'";
                        }
                        if(!Phone_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "phone='" + Phone_Field.getText() + "'";
                        }
                        if(!Email_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "email='" + Email_Field.getText() + "'";
                        }
                        if(!OrganName_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "organisation_name='" + OrganName_Field.getText() + "'";
                        }
                        if(!OGRN_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "OGRN='" + OGRN_Field.getText() + "'";
                        }
                        if(!KPP_Field.getText().isEmpty()) {
                            arg = arg + " and ";
                            arg = arg + "KPP='" + KPP_Field.getText() + "'";
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
                                        "Юридическое лицо",
                                        res.getString("phone"),
                                        res.getString("email"),
                                        res.getString("organisation_name"),
                                        res.getString("OGRN"),
                                        res.getString("KPP"),
                                        res.getString("BIK"),
                                        res.getString("INN"),
                                        res.getString("city"),
                                        res.getString("street"),
                                        res.getString("house")));
                            }
                        UridCustomer_TableView.setItems(customers);
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
                    OrganName_Field.setText("");
                    OGRN_Field.setText("");
                    KPP_Field.setText("");
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