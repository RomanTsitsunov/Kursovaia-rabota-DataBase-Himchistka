package com.example.himchistka.controller.TablesSpravochnici.ReceivingIssuingPoint;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.ReceivingIssuingPoint;
import com.example.himchistka.models.Urgency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ReceivingIssuingPointController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ReceivingIssuingPoint, String> City_Column;

    @FXML
    private TextField City_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<ReceivingIssuingPoint, String> House_Column;

    @FXML
    private TextField House_Field;

    @FXML
    private TableColumn<ReceivingIssuingPoint, Integer> IdPoint_Column;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableView<ReceivingIssuingPoint> Point_Table;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<ReceivingIssuingPoint, String> Street_Column;

    @FXML
    private TextField Street_Field;

    int index=-1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index=Point_Table.getSelectionModel().getSelectedIndex();
        if(index<=-1)
            return;
        City_Field.setText(City_Column.getCellData(index));
        Street_Field.setText(Street_Column.getCellData(index));
        House_Field.setText(House_Column.getCellData(index));
    }

    void refresh(Connection con)
    {
        ObservableList<ReceivingIssuingPoint> list= FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res=st.executeQuery("select * from receiving_issuing_point");
            while (res.next())
                list.add(new ReceivingIssuingPoint(res.getInt("id_point"), res.getString("city"),
                        res.getString("street"), res.getString("house")));
            Point_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        IdPoint_Column.setCellValueFactory(new PropertyValueFactory<ReceivingIssuingPoint, Integer>("id_point"));
        City_Column.setCellValueFactory(new PropertyValueFactory<ReceivingIssuingPoint, String>("city"));
        Street_Column.setCellValueFactory(new PropertyValueFactory<ReceivingIssuingPoint, String>("street"));
        House_Column.setCellValueFactory(new PropertyValueFactory<ReceivingIssuingPoint, String>("house"));
        Connection con;
        try {
            con= BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    if(City_Field.getText().isEmpty()
                    ||Street_Field.getText().isEmpty()
                    ||House_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("insert into receiving_issuing_point(city, street, house) values('"
                                +City_Field.getText()+"', '"
                                +Street_Field.getText()+"', '"
                                +House_Field.getText()+"')");
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
                    index=Point_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите пункт приема-выдачи");
                        alert.showAndWait();
                        return;
                    }
                    if(City_Field.getText().isEmpty()
                            ||Street_Field.getText().isEmpty()
                            ||House_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("update receiving_issuing_point set city='"
                                +City_Field.getText()+"', street='"
                                +Street_Field.getText()+"', house='"
                                +House_Field.getText()+"' where id_point='"
                                +IdPoint_Column.getCellData(index)+"'");
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
