package com.example.himchistka.controller.TablesSpravochnici.Urgency;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.Service;
import com.example.himchistka.models.Urgency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class UrgencyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Exit_Button;

    @FXML
    private Button Insert_Button;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<Urgency, Float> SumRatio_Column;

    @FXML
    private TextField SumRatio_Field;

    @FXML
    private TableColumn<Urgency, String> Urgency_Column;

    @FXML
    private TextField Urgency_Field;

    @FXML
    private TableView<Urgency> Urgency_Table;

    int index=-1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index=Urgency_Table.getSelectionModel().getSelectedIndex();
        if(index<=-1)
            return;
        Urgency_Field.setText(Urgency_Column.getCellData(index));
        SumRatio_Field.setText(SumRatio_Column.getCellData(index).toString());
    }

    void refresh(Connection con)
    {
        ObservableList<Urgency> list= FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res=st.executeQuery("select * from urgency");
            while (res.next())
                list.add(new Urgency(res.getFloat("sum_ratio"), res.getString("name")));
            Urgency_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        Urgency_Column.setCellValueFactory(new PropertyValueFactory<Urgency, String>("urgency"));
        SumRatio_Column.setCellValueFactory(new PropertyValueFactory<Urgency, Float>("sum_ratio"));
        Connection con;
        try {
            con= BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    if(Urgency_Field.getText().isEmpty()||SumRatio_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                        try {
                            Statement st = con.createStatement();
                            st.execute("insert into urgency(name, sum_ratio) values('"
                                    + Urgency_Field.getText() + "', '"
                                    + SumRatio_Field.getText() + "')");
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
                    index=Urgency_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите срочность");
                        alert.showAndWait();
                        return;
                    }
                    if(Urgency_Field.getText().isEmpty()||SumRatio_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("update urgency set name='"
                                +Urgency_Field.getText()+"', sum_ratio='"
                                +SumRatio_Field.getText()+"' where name='"
                                +Urgency_Column.getCellData(index)+"'");
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
                    Urgency_Field.setText("");
                    SumRatio_Field.setText("");
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
