package com.example.himchistka.controller.TablesData.Payment;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.Orders;
import com.example.himchistka.models.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class PaymentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Payment, String> DatePayment_Column;

    @FXML
    private TextField DatePayment_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<Payment, Integer> IdOrders_Column;

    @FXML
    private TableColumn<Payment, Integer> IdPayment_Column;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableView<Payment> Payment_Table;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<Payment, Float> SumPayment_Column;

    @FXML
    private TextField SumPayment_Field;

    public static int numberorder;

    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index = Payment_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
        DatePayment_Field.setText(DatePayment_Column.getCellData(index));
        SumPayment_Field.setText(SumPayment_Column.getCellData(index).toString());
    }

    void refresh(Connection con)
    {
        ObservableList<Payment> list = FXCollections.observableArrayList();
        try {
            Statement st=con.createStatement();
            ResultSet res;
            res = st.executeQuery("select * from payment where payment.id_order="+numberorder);
            while (res.next())
            {
                list.add(new Payment(res.getInt("id_payment"),
                        res.getInt("id_order"),
                        res.getFloat("sum"),
                        res.getString("date_payment")));
            }
            Payment_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        IdPayment_Column.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("id_payment"));
        IdOrders_Column.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("id_order"));
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
                    if (DatePayment_Field.getText().isEmpty()
                    ||SumPayment_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("insert into payment(id_order, date_payment, sum) " +
                                "values('"
                                + numberorder + "', '"
                                + DatePayment_Field.getText() + "', '"
                                + SumPayment_Field.getText() + "')");
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
                    index=Payment_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите изделие");
                        alert.showAndWait();
                        return;
                    }
                    if (DatePayment_Field.getText().isEmpty()
                            ||SumPayment_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("update payment set id_order='"
                                + numberorder + "', date_payment='"
                                + DatePayment_Field.getText() + "', sum='"
                                + SumPayment_Field.getText() + "' where id_payment='"
                                + IdPayment_Column.getCellData(index) + "'");
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
                    DatePayment_Field.setText("");
                    SumPayment_Field.setText("");
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
