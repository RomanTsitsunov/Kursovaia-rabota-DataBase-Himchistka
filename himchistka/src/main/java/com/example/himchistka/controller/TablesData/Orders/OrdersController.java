package com.example.himchistka.controller.TablesData.Orders;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.OpenNewPanel;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.controller.TablesData.Payment.PaymentController;
import com.example.himchistka.controller.TablesData.ProductOrder.ProductOrderController;
import com.example.himchistka.models.Orders;
import com.example.himchistka.models.Payment;
import com.example.himchistka.models.PriceList;
import com.example.himchistka.models.ProductOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrdersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ProductOrder, String> Colour_Column;

    @FXML
    private TableColumn<ProductOrder, String> Completeness_Column;

    @FXML
    private TableColumn<Orders, String> DateBeginOrders_Column;

    @FXML
    private TextField DateBeginOrders_Field;

    @FXML
    private TableColumn<Orders, String> DateIssueOrders_Column;

    @FXML
    private TextField DateIssueOrders_Field;

    @FXML
    private TableColumn<Payment, String> DatePayment_Column;

    @FXML
    private TableColumn<Orders, String> DateReadyOrders_Column;

    @FXML
    private TextField DateReadyOrders_Field;

    @FXML
    private TableColumn<Orders, String> Employer_Column;

    @FXML
    private ComboBox<String> Employer_ComboBox;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<Orders, Integer> IdContract_Column;

    @FXML
    private ComboBox<String> IdContract_ComboBox;

    @FXML
    private TableColumn<Orders, Integer> IdIssuingPoint_Column;

    @FXML
    private ComboBox<String> IdIssuingPoint_ComboBox;

    @FXML
    private TableColumn<Orders, Integer> IdOrders_ColumnOrders;

    @FXML
    private TableColumn<Payment, Integer> IdOrders_ColumnPayment;

    @FXML
    private TableColumn<ProductOrder, Integer> IdOrders_ColumnProduct;

    @FXML
    private TextField IdOrders_Field;

    @FXML
    private TableColumn<Payment, Integer> IdPayment_Column;

    @FXML
    private TableColumn<ProductOrder, Integer> IdProduct_Column;

    @FXML
    private TableColumn<Orders, Integer> IdReceivingPoint_Column;

    @FXML
    private ComboBox<String> IdReceivingPoint_ComboBox;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableColumn<ProductOrder, String> Marking_Column;

    @FXML
    private TableColumn<ProductOrder, String> Material_Column;

    @FXML
    private TableView<Orders> Orders_Table;

    @FXML
    private TableColumn<Orders, Float> PaidSum_Column;

    @FXML
    private TextField PaidSum_Field;

    @FXML
    private TableView<Payment> Payment_Table;

    @FXML
    private TableColumn<ProductOrder, Float> Price_Column;

    @FXML
    private TableView<ProductOrder> Product_Table;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private Button Search_Button;

    @FXML
    private TableColumn<Orders, String> StatusOrders_Column;

    @FXML
    private ComboBox<String> StatusOrders_ComboBox;

    @FXML
    private TableColumn<Orders, Float> SumOrders_Column;

    @FXML
    private TableColumn<Payment, Float> SumPayment_Column;

    @FXML
    private TextField Sum_Field;

    @FXML
    private TableColumn<ProductOrder, String> ProductType_Column;

    @FXML
    private TableColumn<Orders, String> UrgencyOrders_Column;

    @FXML
    private ComboBox<String> UrgencyOrders_ComboBox;

    @FXML
    private Button ViewPayment_Button;

    @FXML
    private Button ViewProduct_Button;

    public static int numbercontract=-1;

    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent)
    {
        index = Orders_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
        /*
        IdOrders_Field.setText(IdOrders_ColumnOrders.getCellData(index).toString());
        Sum_Field.setText(SumOrders_Column.getCellData(index).toString());
        PaidSum_Field.setText(PaidSum_Column.getCellData(index).toString());
        DateBeginOrders_Field.setText(DateBeginOrders_Column.getCellData(index));
        DateReadyOrders_Field.setText(DateReadyOrders_Column.getCellData(index));
        DateIssueOrders_Field.setText(DateIssueOrders_Column.getCellData(index));
         */

        ObservableList<ProductOrder> product_table = FXCollections.observableArrayList();
        try
        {
            Connection con=BD_connection.get_connection();
            Statement st=con.createStatement();
            ResultSet res;
            res = st.executeQuery("select product_order.id_product, product_order.id_order, " +
                    "product_type.name as product_type, colour.colour, material.material, " +
                    "marking.marking, product_order.completeness, product_order.price " +
                    "from product_order, product_type, colour, material, marking " +
                    "where product_order.id_product_type=product_type.id_product_type and " +
                    "product_order.id_colour=colour.id_colour and " +
                    "product_order.id_material=material.id_material and " +
                    "product_order.id_marking=marking.id_marking and " +
                    "product_order.id_order="+IdOrders_ColumnOrders.getCellData(index));
            while (res.next())
            {
                product_table.add(new ProductOrder(res.getInt("id_product"),
                        res.getInt("id_order"),
                        res.getFloat("price"),
                        res.getString("product_type"),
                        res.getString("colour"),
                        res.getString("material"),
                        res.getString("marking"),
                        res.getString("completeness")));
            }
            Product_Table.setItems(product_table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<Payment> payment_table = FXCollections.observableArrayList();
        try
        {
            Connection con=BD_connection.get_connection();
            Statement st=con.createStatement();
            ResultSet res;
            res = st.executeQuery("select * from payment where payment.id_order="
                    +IdOrders_ColumnOrders.getCellData(index));
            while (res.next())
            {
                payment_table.add(new Payment(res.getInt("id_payment"),
                        res.getInt("id_order"),
                        res.getFloat("sum"),
                        res.getString("date_payment")));
            }
            Payment_Table.setItems(payment_table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //передать idOrders в productcontroller и paymentcontroller, вызывать
        //productcontroller.refresh и paymentcontroller.refresh
    }

    void refresh(Connection con)
    {
        ObservableList<String> idcontract_combobox = FXCollections.observableArrayList();
        ObservableList<String> employer_combobox = FXCollections.observableArrayList();
        ObservableList<String> urgency_combobox = FXCollections.observableArrayList();
        ObservableList<String> idreceivingissuingpoint_combobox = FXCollections.observableArrayList();
        ObservableList<String> status_combobox = FXCollections.observableArrayList();
        ObservableList<Orders> list = FXCollections.observableArrayList();
        try {
            Statement st=con.createStatement();
            ResultSet res;
            if(numbercontract==-2)
            {
                res=st.executeQuery("select id_contract from contract");
                while (res.next())
                    idcontract_combobox.add(res.getString("id_contract"));
                IdContract_ComboBox.setItems(idcontract_combobox);

                st=con.createStatement();
                res = st.executeQuery("select orders.id_order, orders.id_contract, " +
                        "employer.login as employer, urgency.name as urgency, orders.id_receiving_point, " +
                        "orders.id_issuing_point, order_status.name as status, orders.sum, " +
                        "orders.paid_sum, orders.date_begin, orders.date_ready, orders.date_issue " +
                        "from orders, employer, urgency, order_status " +
                        "where orders.tabel_number=employer.tabel_number and " +
                        "orders.id_urgency=urgency.id_urgency and " +
                        "orders.id_status=order_status.id_status");
            }
            else
            {
                idcontract_combobox.add(String.valueOf(numbercontract));
                IdContract_ComboBox.setItems(idcontract_combobox);
                IdContract_ComboBox.getSelectionModel().select(numbercontract);
                //IdContract_ComboBox.setDisable(true);
                res = st.executeQuery("select orders.id_order, orders.id_contract, " +
                        "employer.login as employer, urgency.name as urgency, orders.id_receiving_point, " +
                        "orders.id_issuing_point, order_status.name as status, orders.sum, " +
                        "orders.paid_sum, orders.date_begin, orders.date_ready, orders.date_issue " +
                        "from orders, employer, urgency, order_status " +
                        "where orders.tabel_number=employer.tabel_number and " +
                        "orders.id_urgency=urgency.id_urgency and " +
                        "orders.id_status=order_status.id_status and " +
                        "orders.id_contract="+numbercontract);
            }
            while (res.next())
            {
                list.add(new Orders(res.getInt("id_order"),
                        res.getInt("id_contract"),
                        res.getInt("id_receiving_point"),
                        res.getInt("id_issuing_point"),
                        res.getFloat("sum"),
                        res.getFloat("paid_sum"),
                        res.getString("employer"),
                        res.getString("urgency"),
                        res.getString("status"),
                        res.getString("date_begin"),
                        res.getString("date_ready"),
                        res.getString("date_issue")));
            }
            Orders_Table.setItems(list);

            st = con.createStatement();
            res=st.executeQuery("select login from employer");
            while (res.next())
                employer_combobox.add(res.getString("login"));
            Employer_ComboBox.setItems(employer_combobox);

            st = con.createStatement();
            res=st.executeQuery("select name from urgency");
            while (res.next())
                urgency_combobox.add(res.getString("name"));
            UrgencyOrders_ComboBox.setItems(urgency_combobox);

            st = con.createStatement();
            res=st.executeQuery("select id_point from receiving_issuing_point");
            while (res.next())
                idreceivingissuingpoint_combobox.add(res.getString("id_point"));
            IdReceivingPoint_ComboBox.setItems(idreceivingissuingpoint_combobox);
            IdIssuingPoint_ComboBox.setItems(idreceivingissuingpoint_combobox);

            st = con.createStatement();
            res=st.executeQuery("select name from order_status");
            while (res.next())
                status_combobox.add(res.getString("name"));
            StatusOrders_ComboBox.setItems(status_combobox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        IdOrders_ColumnOrders.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("id_order"));
        IdContract_Column.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("id_contract"));
        Employer_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("employer"));
        UrgencyOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("urgency"));
        IdReceivingPoint_Column.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("id_receiving_point"));
        IdIssuingPoint_Column.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("id_issuing_point"));
        StatusOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("status"));
        SumOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, Float>("sum"));
        PaidSum_Column.setCellValueFactory(new PropertyValueFactory<Orders, Float>("paid_sum"));
        DateBeginOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("date_begin"));
        DateReadyOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("date_ready"));
        DateIssueOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("date_issue"));

        IdProduct_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, Integer>("id_product"));
        IdOrders_ColumnProduct.setCellValueFactory(new PropertyValueFactory<ProductOrder, Integer>("id_order"));
        ProductType_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, String>("product_type"));
        Colour_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, String>("colour"));
        Material_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, String>("material"));
        Marking_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, String>("marking"));
        Completeness_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, String>("completeness"));
        Price_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, Float>("price"));

        IdPayment_Column.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("id_payment"));
        IdOrders_ColumnPayment.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("id_order"));
        DatePayment_Column.setCellValueFactory(new PropertyValueFactory<Payment, String>("date_payment"));
        SumPayment_Column.setCellValueFactory(new PropertyValueFactory<Payment, Float>("sum"));
        Connection con;
        try {
            con = BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    InsertOrdersController.numbercontract=numbercontract;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Orders/InsertOrders-Panel.fxml",
                            "Добавление нового заказа");
                }
        );

        Redact_Button.setOnAction(event ->
                {
                    index=Orders_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите заказ");
                        alert.showAndWait();
                        return;
                    }
                    RedactOrdersController.numberorder=IdOrders_ColumnOrders.getCellData(index);
                    RedactOrdersController.numbercontract=IdContract_Column.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Orders/RedactOrders-Panel.fxml",
                            "Редактироваение заказа №"+IdOrders_ColumnOrders.getCellData(index));
                }
        );

        Search_Button.setOnAction(event ->
                {
                    try
                    {
                        Statement st;
                        ResultSet res;
                        String arg="";
                        if(numbercontract>0)
                            arg="orders.id_contract='"+numbercontract+"'";
                        if(!IdOrders_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "orders.id_order='" + IdOrders_Field.getText() + "'";
                        }
                        if(!IdContract_ComboBox.getSelectionModel().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "orders.id_contract='" + IdContract_ComboBox.
                                    getSelectionModel().getSelectedItem() + "'";
                        }
                        if(!Employer_ComboBox.getSelectionModel().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            st=con.createStatement();
                            res=st.executeQuery("select tabel_number from employer where " +
                                    "login='"+Employer_ComboBox.getSelectionModel().
                                    getSelectedItem()+"'");
                            res.next();
                            arg = arg + "orders.tabel_number='" +
                                    res.getString("tabel_number") + "'";
                        }
                        if(!UrgencyOrders_ComboBox.getSelectionModel().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            st=con.createStatement();
                            res=st.executeQuery("select id_urgency from urgency where " +
                                    "name='"+UrgencyOrders_ComboBox.getSelectionModel().
                                    getSelectedItem()+"'");
                            res.next();
                            arg = arg + "orders.id_urgency='" + res.getString("id_urgency") + "'";
                        }
                        if(!IdReceivingPoint_ComboBox.getSelectionModel().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "orders.id_receiving_point='" + IdReceivingPoint_ComboBox.
                                    getSelectionModel().getSelectedItem() + "'";
                        }
                        if(!IdIssuingPoint_ComboBox.getSelectionModel().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "orders.id_issuing_point='" + IdIssuingPoint_ComboBox.
                                    getSelectionModel().getSelectedItem() + "'";
                        }
                        if(!StatusOrders_ComboBox.getSelectionModel().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            st=con.createStatement();
                            res=st.executeQuery("select id_status from order_status where " +
                                    "name='"+StatusOrders_ComboBox.getSelectionModel().
                                    getSelectedItem()+"'");
                            res.next();
                            arg = arg + "orders.id_status='" + res.getString("id_status") + "'";
                        }
                        if(!Sum_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "orders.sum='" + Sum_Field.getText() + "'";
                        }
                        if(!PaidSum_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "orders.paid_sum='" + PaidSum_Field.getText() + "'";
                        }
                        if(!DateBeginOrders_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "orders.date_begin='" + DateBeginOrders_Field.getText() + "'";
                        }
                        if(!DateReadyOrders_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "orders.date_ready='" + DateReadyOrders_Field.getText() + "'";
                        }
                        if(!DateIssueOrders_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "orders.date_issue='" + DateIssueOrders_Field.getText() + "'";
                        }
                        ObservableList<Orders> orders = FXCollections.observableArrayList();
                        if(!arg.isEmpty()) {
                            st=con.createStatement();
                            res = st.executeQuery("select orders.id_order, orders.id_contract, " +
                                    "employer.login as employer, urgency.name as urgency, orders.id_receiving_point, " +
                                    "orders.id_issuing_point, order_status.name as status, orders.sum, " +
                                    "orders.paid_sum, orders.date_begin, orders.date_ready, orders.date_issue " +
                                    "from orders, employer, urgency, order_status " +
                                    "where orders.tabel_number=employer.tabel_number and " +
                                    "orders.id_urgency=urgency.id_urgency and " +
                                    "orders.id_status=order_status.id_status and "+arg);
                            while (res.next())
                            {
                                orders.add(new Orders(res.getInt("id_order"),
                                        res.getInt("id_contract"),
                                        res.getInt("id_receiving_point"),
                                        res.getInt("id_issuing_point"),
                                        res.getFloat("sum"),
                                        res.getFloat("paid_sum"),
                                        res.getString("employer"),
                                        res.getString("urgency"),
                                        res.getString("status"),
                                        res.getString("date_begin"),
                                        res.getString("date_ready"),
                                        res.getString("date_issue")));
                            }
                        }
                        Orders_Table.setItems(orders);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Refresh_Button.setOnAction(event ->
                {
                    refresh(con);
                    IdContract_ComboBox.getSelectionModel().select(null);
                    Employer_ComboBox.getSelectionModel().select(null);
                    UrgencyOrders_ComboBox.getSelectionModel().select(null);
                    IdReceivingPoint_ComboBox.getSelectionModel().select(null);
                    IdIssuingPoint_ComboBox.getSelectionModel().select(null);
                    StatusOrders_ComboBox.getSelectionModel().select(null);
                    IdOrders_Field.setText("");
                    Sum_Field.setText("");
                    PaidSum_Field.setText("");
                    DateBeginOrders_Field.setText("");
                    DateReadyOrders_Field.setText("");
                    DateIssueOrders_Field.setText("");
                }
        );

        Exit_Button.setOnAction(event ->
                {
                    numbercontract=-1;
                    try {
                        con.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Exit_Button.getScene().getWindow().hide();
                }
        );

        ViewProduct_Button.setOnAction(event ->
                {
                    index = Orders_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1)
                        return;
                    ProductOrderController.numberorder=IdOrders_ColumnOrders.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductOrder/ProductOrder-Panel.fxml",
                            "Изделия по заказу №"+IdOrders_ColumnOrders.getCellData(index));
                }
        );

        ViewPayment_Button.setOnAction(event ->
                {
                    index = Orders_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1)
                        return;
                    PaymentController.numberorder=IdOrders_ColumnOrders.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Payment/Payment-Panel.fxml",
                            "Платежи по заказу №"+IdOrders_ColumnOrders.getCellData(index));
                }
        );
    }
}
