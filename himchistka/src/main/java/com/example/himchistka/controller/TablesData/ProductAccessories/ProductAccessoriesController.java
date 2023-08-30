package com.example.himchistka.controller.TablesData.ProductAccessories;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.ProductAccessories;
import com.example.himchistka.models.SupplyLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProductAccessoriesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ProductAccessories, String> Accessories_Column;

    @FXML
    private ComboBox<String> Accessories_ComboBox;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<ProductAccessories, Integer> IdProduct_Column;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableView<ProductAccessories> ProductAccessories_Table;

    @FXML
    private Button Refresh_Button;

    public static int numberproduct;

    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent)
    {
        index = ProductAccessories_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
    }

    void refresh(Connection con)
    {
        ObservableList<String> accessories_combobox = FXCollections.observableArrayList();
        ObservableList<ProductAccessories> list = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res;
            res = st.executeQuery("select product_accessories.id_product, " +
                    "accessories.accessories from product_accessories, accessories " +
                    "where product_accessories.id_accessories=accessories.id_accessories and " +
                    "product_accessories.id_product=" + numberproduct);
            while (res.next()) {
                list.add(new ProductAccessories(res.getInt("id_product"),
                        res.getString("accessories")));
            }
            ProductAccessories_Table.setItems(list);

            st = con.createStatement();
            res = st.executeQuery("select accessories from accessories");
            while (res.next())
                accessories_combobox.add(res.getString("accessories"));
            Accessories_ComboBox.setItems(accessories_combobox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        IdProduct_Column.setCellValueFactory(new PropertyValueFactory<ProductAccessories, Integer>("id_product"));
        Accessories_Column.setCellValueFactory(new PropertyValueFactory<ProductAccessories, String>("accessories"));
        Connection con;
        try {
            con = BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    if (Accessories_ComboBox.getSelectionModel().getSelectedItem() == null) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_accessories from accessories where " +
                                "accessories='"+Accessories_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String accessories=res.getString("id_accessories");

                        st=con.createStatement();
                        st.execute("insert into product_accessories(id_product, id_accessories) " +
                                "values('"
                                + numberproduct + "', '"
                                + accessories + "')");
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
