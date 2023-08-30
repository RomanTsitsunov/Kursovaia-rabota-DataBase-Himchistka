package com.example.himchistka.controller.TablesData.Orders;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.models.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RedactOrdersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField DateBeginOrders_Field;

    @FXML
    private TextField DateIssueOrders_Field;

    @FXML
    private TextField DateReadyOrders_Field;

    @FXML
    private ComboBox<String> Employer_ComboBox;

    @FXML
    private Button Exit_Button;

    @FXML
    private ComboBox<String> IdIssuingPoint_ComboBox;

    @FXML
    private ComboBox<String> IdReceivingPoint_ComboBox;

    @FXML
    private Label Label;

    @FXML
    private TextField PaidSum_Field;

    @FXML
    private Button Redact_Button;

    @FXML
    private ComboBox<String> StatusOrders_ComboBox;

    @FXML
    private TextField Sum_Field;

    @FXML
    private ComboBox<String> UrgencyOrders_ComboBox;

    public static int numbercontract;
    public static int numberorder;

    @FXML
    void initialize()
    {
        Label.setText("Заказ №"+numberorder);
        ObservableList<String> employer_combobox = FXCollections.observableArrayList();
        ObservableList<String> urgency_combobox = FXCollections.observableArrayList();
        ObservableList<String> idreceivingissuingpoint_combobox = FXCollections.observableArrayList();
        ObservableList<String> status_combobox = FXCollections.observableArrayList();
        Connection con;
        try {
            con= BD_connection.get_connection();
            Statement st=con.createStatement();
            ResultSet res;

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

        Redact_Button.setOnAction(event ->
                {
                    if (Employer_ComboBox.getSelectionModel().getSelectedItem() == null
                            || UrgencyOrders_ComboBox.getSelectionModel().getSelectedItem() == null
                            || IdReceivingPoint_ComboBox.getSelectionModel().getSelectedItem() == null
                            || IdIssuingPoint_ComboBox.getSelectionModel().getSelectedItem() == null
                            || StatusOrders_ComboBox.getSelectionModel().getSelectedItem() == null
                            || DateBeginOrders_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все необходимые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select tabel_number from employer where " +
                                "login='"+Employer_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String tabelnumber=res.getString("tabel_number");

                        st=con.createStatement();
                        res=st.executeQuery("select id_urgency from urgency where " +
                                "name='"+UrgencyOrders_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String urgency=res.getString("id_urgency");

                        st=con.createStatement();
                        res=st.executeQuery("select id_status from order_status where " +
                                "name='"+StatusOrders_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String status=res.getString("id_status");

                        st.execute("update orders set id_contract='"
                                + numbercontract + "', tabel_number='"
                                + tabelnumber + "', id_urgency='"
                                + urgency + "', id_receiving_point='"
                                + IdReceivingPoint_ComboBox.getSelectionModel().getSelectedItem() + "', id_issuing_point='"
                                + IdIssuingPoint_ComboBox.getSelectionModel().getSelectedItem() + "', id_status='"
                                + status + "', sum='"
                                + Sum_Field.getText() + "', paid_sum='"
                                + PaidSum_Field.getText() + "', date_begin='"
                                + DateBeginOrders_Field.getText() + "', date_ready='"
                                + DateReadyOrders_Field.getText() + "', date_issue='"
                                + DateIssueOrders_Field.getText() + "' where id_order='"
                                + numberorder + "'");
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
