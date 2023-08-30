package com.example.himchistka.controller.TablesSpravochnici.Material;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.Accessories;
import com.example.himchistka.models.Material;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MaterialController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Exit_Button;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableColumn<Material, String> Material_Column;

    @FXML
    private TextField Material_Field;

    @FXML
    private TableView<Material> Material_Table;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    int index=-1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index=Material_Table.getSelectionModel().getSelectedIndex();
        if(index<=-1)
            return;
        Material_Field.setText(Material_Column.getCellData(index));
    }

    void refresh(Connection con)
    {
        ObservableList<Material> list= FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res=st.executeQuery("select * from material");
            while (res.next())
                list.add(new Material(res.getString("material")));
            Material_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        Material_Column.setCellValueFactory(new PropertyValueFactory<Material, String>("material"));
        Connection con;
        try {
            con = BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    if(Material_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("insert into material(material) values('"
                                + Material_Field.getText() + "')");
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
                    index=Material_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите материал");
                        alert.showAndWait();
                        return;
                    }
                    if(Material_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        st.execute("update material set material='"
                                + Material_Field.getText() + "' where material='"
                                + Material_Column.getCellData(index) + "'");
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
                    Material_Field.setText("");
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
