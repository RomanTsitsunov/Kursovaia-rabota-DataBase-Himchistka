package com.example.himchistka.controller.TablesData.ProductDefect;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.models.Defect;
import com.example.himchistka.models.ProductAccessories;
import com.example.himchistka.models.ProductDefect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProductDefectController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Defect_ComboBox;

    @FXML
    private TableColumn<ProductDefect, String> Defect_Column;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<ProductDefect, Integer> IdProduct_Column;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableView<ProductDefect> ProductDefect_Table;

    @FXML
    private Button Refresh_Button;

    public static int numberproduct;

    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent)
    {
        index = ProductDefect_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
    }

    void refresh(Connection con)
    {
        ObservableList<String> defect_combobox = FXCollections.observableArrayList();
        ObservableList<ProductDefect> list = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res;
            res = st.executeQuery("select product_defect.id_product, " +
                    "defect.defect from product_defect, defect " +
                    "where product_defect.id_defect=defect.id_defect and " +
                    "product_defect.id_product=" + numberproduct);
            while (res.next()) {
                list.add(new ProductDefect(res.getInt("id_product"),
                        res.getString("defect")));
            }
            ProductDefect_Table.setItems(list);

            st = con.createStatement();
            res = st.executeQuery("select defect from defect");
            while (res.next())
                defect_combobox.add(res.getString("defect"));
            Defect_ComboBox.setItems(defect_combobox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        IdProduct_Column.setCellValueFactory(new PropertyValueFactory<ProductDefect, Integer>("id_product"));
        Defect_Column.setCellValueFactory(new PropertyValueFactory<ProductDefect, String>("defect"));
        Connection con;
        try {
            con = BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    if (Defect_ComboBox.getSelectionModel().getSelectedItem() == null) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_defect from defect where " +
                                "defect='"+Defect_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String defect=res.getString("id_defect");

                        st=con.createStatement();
                        st.execute("insert into product_defect(id_product, id_defect) " +
                                "values('"
                                + numberproduct + "', '"
                                + defect + "')");
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

        Refresh_Button.setOnAction(event ->
                {
                    refresh(con);
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

