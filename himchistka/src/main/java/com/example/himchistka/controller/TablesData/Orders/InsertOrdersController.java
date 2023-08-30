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

public class InsertOrdersController {

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
    private ComboBox<String> IdContract_ComboBox;

    @FXML
    private ComboBox<String> IdIssuingPoint_ComboBox;

    @FXML
    private ComboBox<String> IdReceivingPoint_ComboBox;

    @FXML
    private Button Insert_Button;

    @FXML
    private ComboBox<String> StatusOrders_ComboBox;

    @FXML
    private TextField Sum_Field;

    @FXML
    private ComboBox<String> UrgencyOrders_ComboBox;

    @FXML
    private Label IdContract_Label;

    public static int numbercontract;

    @FXML
    void initialize()
    {
        ObservableList<String> idcontract_combobox = FXCollections.observableArrayList();
        ObservableList<String> employer_combobox = FXCollections.observableArrayList();
        ObservableList<String> urgency_combobox = FXCollections.observableArrayList();
        ObservableList<String> idreceivingissuingpoint_combobox = FXCollections.observableArrayList();
        ObservableList<String> status_combobox = FXCollections.observableArrayList();
        Connection con;
        try {
            con=BD_connection.get_connection();
            Statement st=con.createStatement();
            ResultSet res;
            if(numbercontract==-1)
            {
                res=st.executeQuery("select id_contract from contract");
                while (res.next())
                    idcontract_combobox.add(res.getString("id_contract"));
                IdContract_ComboBox.setItems(idcontract_combobox);
            }
            else
            {
                idcontract_combobox.add(String.valueOf(numbercontract));
                IdContract_ComboBox.setItems(idcontract_combobox);
                IdContract_ComboBox.getSelectionModel().select(numbercontract);
                //IdContract_ComboBox.setDisable(true);
            }

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

        Insert_Button.setOnAction(event ->
                {
                    if (IdContract_ComboBox.getSelectionModel().getSelectedItem() == null
                            || Employer_ComboBox.getSelectionModel().getSelectedItem() == null
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

                        st=con.createStatement();
                        st.execute("insert into orders(id_contract, tabel_number, id_urgency, " +
                                "id_receiving_point, id_issuing_point, id_status, sum, paid_sum, " +
                                "date_begin, date_ready, date_issue) " +
                                "values('"
                                + IdContract_ComboBox.getSelectionModel().getSelectedItem() + "', '"
                                + tabelnumber + "', '"
                                + urgency + "', '"
                                + IdReceivingPoint_ComboBox.getSelectionModel().getSelectedItem() + "', '"
                                + IdIssuingPoint_ComboBox.getSelectionModel().getSelectedItem() + "', '"
                                + status + "', '"
                                + Sum_Field.getText() + "', 0, '"
                                + DateBeginOrders_Field.getText() + "', '"
                                + DateReadyOrders_Field.getText() + "', '"
                                + DateIssueOrders_Field.getText() + "')");
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
