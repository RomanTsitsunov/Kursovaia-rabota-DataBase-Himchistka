package com.example.himchistka.controller.TablesSpravochnici.PriceList;

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
import com.example.himchistka.models.PriceList;
import com.example.himchistka.models.Supply;
import com.example.himchistka.models.SupplyLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class PriceListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<PriceList, String> DateChangePrice_Column;

    @FXML
    private TextField DateChangePrice_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableView<PriceList> PriceList_Table;

    @FXML
    private TableColumn<PriceList, Float> Price_Column;

    @FXML
    private TextField Price_Field;

    @FXML
    private TableColumn<PriceList, String> ProductType_Column;

    @FXML
    private ComboBox<String> ProductType_ComboBox;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private Button Search_Button;

    @FXML
    private TableColumn<PriceList, String> Service_Column;

    @FXML
    private ComboBox<String> Service_ComboBox;

    @FXML
    private Label Redact_Label;

    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent)
    {
        index = PriceList_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
        //Price_Field.setText(Price_Column.getCellData(index).toString());
        //DateChangePrice_Field.setText((DateChangePrice_Column.getCellData(index)));
    }

    void refresh(Connection con)
    {
        ObservableList<String> producttype_combobox = FXCollections.observableArrayList();
        ObservableList<String> service_combobox = FXCollections.observableArrayList();
        ObservableList<PriceList> list = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("select product_type.name as product_type, " +
                    "service.name as service, price_list.price, price_list.date_change_price " +
                    "from price_list, product_type, service " +
                    "where price_list.id_product_type=product_type.id_product_type and " +
                    "price_list.id_service=service.id_service");
            while (res.next())
            {
                list.add(new PriceList(res.getFloat("price"),
                        res.getString("product_type"),
                        res.getString("service"),
                        res.getString("date_change_price")));
            }
            PriceList_Table.setItems(list);

            st = con.createStatement();
            res = st.executeQuery("select name from product_type");
            while (res.next())
                producttype_combobox.add(res.getString("name"));
            ProductType_ComboBox.setItems(producttype_combobox);

            st = con.createStatement();
            res = st.executeQuery("select name from service");
            while (res.next())
                service_combobox.add(res.getString("name"));
            Service_ComboBox.setItems(service_combobox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        ProductType_Column.setCellValueFactory(new PropertyValueFactory<PriceList, String>("product_type"));
        Service_Column.setCellValueFactory(new PropertyValueFactory<PriceList, String>("service"));
        Price_Column.setCellValueFactory(new PropertyValueFactory<PriceList, Float>("price"));
        DateChangePrice_Column.setCellValueFactory(new PropertyValueFactory<PriceList, String>("date_change_price"));
        Connection con;
        try {
            con = BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/PriceList/InsertPriceList-Panel.fxml",
                            "Добавление услуги в прайс-лист");
                }
        );

        Redact_Button.setOnAction(event ->
                {
                    index=PriceList_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите ценовую категорию");
                        alert.showAndWait();
                        return;
                    }
                    RedactPriceList.producttype=ProductType_Column.getCellData(index);
                    RedactPriceList.service=Service_Column.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/PriceList/RedactPriceList-Panel.fxml",
                            "Редактироваение прайс-листа");
                }
        );

        Search_Button.setOnAction(event ->
                {
                    try
                    {
                        Statement st;
                        ResultSet res;
                        String arg="";
                        if(!ProductType_ComboBox.getSelectionModel().isEmpty()) {
                            st=con.createStatement();
                            res=st.executeQuery("select id_product_type from product_type where " +
                                    "name='"+ProductType_ComboBox.
                                    getSelectionModel().getSelectedItem()+"'");
                            res.next();
                            arg = arg + "price_list.id_product_type='" +
                                    res.getString("id_product_type") + "'";
                        }
                        if(!Service_ComboBox.getSelectionModel().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            st=con.createStatement();
                            res=st.executeQuery("select id_service from service where " +
                                    "service.name='"+Service_ComboBox.
                                    getSelectionModel().getSelectedItem()+"'");
                            res.next();
                            arg = arg + "price_list.id_service='" + res.getString("id_service") + "'";
                        }
                        if(!Price_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "price_list.price='" + Price_Field.getText() + "'";
                        }
                        if(!DateChangePrice_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "price_list.date_change_price='" + DateChangePrice_Field.getText() + "'";
                        }
                        ObservableList<PriceList> pricelist = FXCollections.observableArrayList();
                        if(!arg.isEmpty()) {
                            st = con.createStatement();
                            res = st.executeQuery("select product_type.name as product_type, " +
                                    "service.name as service, price_list.price, price_list.date_change_price " +
                                    "from price_list, product_type, service " +
                                    "where price_list.id_product_type=product_type.id_product_type and " +
                                    "price_list.id_service=service.id_service and "+arg);
                            while (res.next())
                            {
                                pricelist.add(new PriceList(res.getFloat("price"),
                                        res.getString("product_type"),
                                        res.getString("service"),
                                        res.getString("date_change_price")));
                            }
                        }
                        PriceList_Table.setItems(pricelist);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Refresh_Button.setOnAction(event ->
                {
                    refresh(con);
                    ProductType_ComboBox.getSelectionModel().select(null);
                    Service_ComboBox.getSelectionModel().select(null);
                    Price_Field.setText("");
                    DateChangePrice_Field.setText("");
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
