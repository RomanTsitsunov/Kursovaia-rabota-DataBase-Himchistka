package com.example.himchistka.controller.TablesData.Contract;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.OpenNewPanel;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.controller.TablesData.Orders.OrdersController;
import com.example.himchistka.models.Contract;
import com.example.himchistka.models.Customer;
import com.example.himchistka.models.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContractController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Contract> Contract_Table;

    @FXML
    private TableColumn<Contract, String> Customer_Column;

    @FXML
    private ComboBox<String> Customer_ComboBox;

    @FXML
    private TableColumn<Contract, String> DateBeginContract_Column;

    @FXML
    private TableColumn<Orders, String> DateBeginOrders_Column;

    @FXML
    private TextField DateBegin_Field;

    @FXML
    private TableColumn<Contract, String> DateEndContract_Column;

    @FXML
    private TextField DateEnd_Field;

    @FXML
    private TableColumn<Orders, String> DateIssueOrders_Column;

    @FXML
    private TableColumn<Orders, String> DateReadyOrders_Column;

    @FXML
    private TableColumn<Orders, String> Employer_Column;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<Contract, Integer> IdContract_ColumnContract;

    @FXML
    private TableColumn<Orders, Integer> IdOrders_Column;

    @FXML
    private TextField IdContract_Field;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableColumn<Orders, Integer> IdContract_ColumnOrders;

    @FXML
    private TableColumn<Orders, Integer> IdIssuingPoint_Column;

    @FXML
    private TableColumn<Orders, Integer> IdReceivingPoint_Column;

    @FXML
    private TableView<Orders> Orders_Table;

    @FXML
    private TableColumn<Orders, Float> PaidSum_Column;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private Button Search_Button;

    @FXML
    private TableColumn<Contract, String> StatusContract_Column;

    @FXML
    private TableColumn<Orders, String> StatusOrders_Column;

    @FXML
    private ComboBox<String> Status_ComboBox;

    @FXML
    private TableColumn<Orders, Float> Sum_Column;

    @FXML
    private TableColumn<Orders, String> UrgencyOrders_Column;

    @FXML
    private Button ViewOrders_Button;

    String concat="concat(customer.surname, ' ', substr(customer.name,1,1), '.', " +
            "substr(customer.otchestvo,1,1), '.')";
    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent)
    {
        index = Contract_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
        //IdContract_Field.setText(IdContract_ColumnContract.getCellData(index).toString());
        //Customer_Field.setText(Customer_Column.getCellData(index));
        //Status_Field.setText(StatusContract_Column.getCellData(index));
        //DateBegin_Field.setText(DateBeginContract_Column.getCellData(index));
        //DateEnd_Field.setText(DateEndContract_Column.getCellData(index));

        ObservableList<Orders> list = FXCollections.observableArrayList();
        try
        {
            Connection con=BD_connection.get_connection();
            Statement st=con.createStatement();
            ResultSet res;
            res = st.executeQuery("select orders.id_order, orders.id_contract, " +
                    "employer.login as employer, urgency.name as urgency, orders.id_receiving_point, " +
                    "orders.id_issuing_point, order_status.name as status, orders.sum, " +
                    "orders.paid_sum, orders.date_begin, orders.date_ready, orders.date_issue " +
                    "from orders, employer, urgency, order_status " +
                    "where orders.tabel_number=employer.tabel_number and " +
                    "orders.id_urgency=urgency.id_urgency and " +
                    "orders.id_status=order_status.id_status and " +
                    "orders.id_contract="+IdContract_ColumnContract.getCellData(index));
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
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //передать idContract в orderscontroller и вызывать orderscontroller.refresh
    }

    void refresh(Connection con)
    {
        ObservableList<String> customer_combobox = FXCollections.observableArrayList();
        ObservableList<String> status_combobox = FXCollections.observableArrayList();
        ObservableList<Contract> list = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("select contract.id_contract, customer.id_type, " +
                    concat+" as FIO, " +
                    "customer.organisation_name, contract_status.name as status, contract.date_begin, " +
                    "contract.date_end from contract, contract_status, customer " +
                    "where contract.id_customer=customer.id_customer and " +
                    "contract.id_status=contract_status.id_status");
            String customer;
            while (res.next()) {
                if (res.getInt("id_type") == 1)
                    customer = res.getString("organisation_name");
                else
                    customer = res.getString("FIO");
                list.add(new Contract(res.getInt("id_contract"),
                        customer,
                        res.getString("status"),
                        res.getString("date_begin"),
                        res.getString("date_end")));
            }
            Contract_Table.setItems(list);

            st = con.createStatement();
            res=st.executeQuery("select " +
                    concat+" as FIO from customer where " +
                    "customer.id_type=2");
            customer_combobox.add(null);
            while (res.next())
                customer_combobox.add(res.getString("FIO"));

            st = con.createStatement();
            res=st.executeQuery("select organisation_name from customer where " +
                    "customer.id_type=1");
            while (res.next())
                customer_combobox.add(res.getString("organisation_name"));
            Customer_ComboBox.setItems(customer_combobox);

            st = con.createStatement();
            res=st.executeQuery("select name from contract_status");
            status_combobox.add(null);
            while (res.next())
                status_combobox.add(res.getString("name"));
            Status_ComboBox.setItems(status_combobox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        IdContract_ColumnContract.setCellValueFactory(new PropertyValueFactory<Contract, Integer>("id"));
        Customer_Column.setCellValueFactory(new PropertyValueFactory<Contract, String>("customer"));
        StatusContract_Column.setCellValueFactory(new PropertyValueFactory<Contract, String>("status"));
        DateBeginContract_Column.setCellValueFactory(new PropertyValueFactory<Contract, String>("date_begin"));
        DateEndContract_Column.setCellValueFactory(new PropertyValueFactory<Contract, String>("date_end"));

        IdOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("id_order"));
        IdContract_ColumnOrders.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("id_contract"));
        Employer_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("employer"));
        UrgencyOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("urgency"));
        IdReceivingPoint_Column.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("id_receiving_point"));
        IdIssuingPoint_Column.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("id_issuing_point"));
        StatusOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("status"));
        Sum_Column.setCellValueFactory(new PropertyValueFactory<Orders, Float>("sum"));
        PaidSum_Column.setCellValueFactory(new PropertyValueFactory<Orders, Float>("paid_sum"));
        DateBeginOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("date_begin"));
        DateReadyOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("date_ready"));
        DateIssueOrders_Column.setCellValueFactory(new PropertyValueFactory<Orders, String>("date_issue"));
        Connection con;
        try {
            con = BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);
        /*
        Customer_ComboBox.setOnAction(event ->
        {
            String s=Customer_ComboBox.getSelectionModel().getSelectedItem();
            Customer_Field.setText(s);
        }
        );

        Status_ComboBox.setOnAction(event ->
        {
            String s=Status_ComboBox.getSelectionModel().getSelectedItem();
            Status_Field.setText(s);
        }
        );
        */
        Insert_Button.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Contract/InsertContract-Panel.fxml",
                            "Добавление договора");
                }
        );

        Redact_Button.setOnAction(event ->
                {
                    index = Contract_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите договор");
                        alert.showAndWait();
                        return;
                    }
                    RedactContractController.numbercontract=IdContract_ColumnContract.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Contract/RedactContract-Panel.fxml",
                            "Редактироваение договора №"+IdContract_ColumnContract.getCellData(index));
                }
        );

        Search_Button.setOnAction(event ->
                {
                    try
                    {
                        Statement st;
                        ResultSet res;
                        String arg="";
                        if(!IdContract_Field.getText().isEmpty()) {
                            arg = arg + "contract.id_contract='" + IdContract_Field.getText() + "'";
                        }
                        if(Customer_ComboBox.getSelectionModel().getSelectedItem()!=null) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            st=con.createStatement();
                            res = st.executeQuery("select id_customer from customer where " +
                                    "organisation_name='" + Customer_ComboBox.getSelectionModel().getSelectedItem() + "' or " +
                                    concat+"='" + Customer_ComboBox.getSelectionModel().getSelectedItem() + "'");
                            res.next();
                            arg = arg + "customer.id_customer='" + res.getString("id_customer") + "'";
                        }
                        if(Status_ComboBox.getSelectionModel().getSelectedItem()!=null) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            st=con.createStatement();
                            res = st.executeQuery("select id_status from contract_status where " +
                                    "name='" + Status_ComboBox.getSelectionModel().getSelectedItem() + "'");
                            res.next();
                            arg = arg + "contract_status.id_status='" + res.getString("id_status") + "'";
                        }
                        if(!DateBegin_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "contract.date_begin='" + DateBegin_Field.getText() + "'";
                        }
                        if(!DateEnd_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "contract.date_end='" + DateEnd_Field.getText() + "'";
                        }
                        ObservableList<Contract> contracts= FXCollections.observableArrayList();
                        if(!arg.isEmpty()) {
                            st = con.createStatement();
                            res = st.executeQuery("select contract.id_contract, customer.id_type, " +
                                    concat + " as FIO, customer.organisation_name," +
                                    "contract_status.name as status, contract.date_begin, " +
                                    "contract.date_end from contract, contract_status, customer " +
                                    "where contract.id_customer=customer.id_customer and " +
                                    "contract.id_status=contract_status.id_status and " + arg);
                            String customer;
                            while (res.next()) {
                                if (res.getInt("id_type") == 1)
                                    customer = res.getString("organisation_name");
                                else
                                    customer = res.getString("FIO");
                                contracts.add(new Contract(res.getInt("id_contract"),
                                        customer,
                                        res.getString("status"),
                                        res.getString("date_begin"),
                                        res.getString("date_end")));
                            }
                        }
                        Contract_Table.setItems(contracts);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Refresh_Button.setOnAction(event ->
                {
                    refresh(con);
                    Customer_ComboBox.getSelectionModel().select(null);
                    Status_ComboBox.getSelectionModel().select(null);
                    IdContract_Field.setText("");
                    //Customer_Field.setText("");
                    //Status_Field.setText("");
                    DateBegin_Field.setText("");
                    DateEnd_Field.setText("");
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

        ViewOrders_Button.setOnAction(event ->
        {
            index = Contract_Table.getSelectionModel().getSelectedIndex();
            if (index <= -1)
                return;
            if (OrdersController.numbercontract==-2)
            {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle(null);
                alert.setContentText("Окно заказы уже открыто");
                alert.showAndWait();
                return;
            }
            OrdersController.numbercontract= IdContract_ColumnContract.getCellData(index);
            OpenNewPanel.open("/com/example/himchistka/TablesData/Orders/Orders-Panel.fxml",
                    "Заказы по договору №"+IdContract_ColumnContract.getCellData(index));
        }
        );

    }
}
