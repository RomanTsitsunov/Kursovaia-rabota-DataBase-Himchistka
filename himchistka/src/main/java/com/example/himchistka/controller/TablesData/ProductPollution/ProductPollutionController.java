package com.example.himchistka.controller.TablesData.ProductPollution;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.models.ProductDefect;
import com.example.himchistka.models.ProductPollution;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProductPollutionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<ProductPollution, Integer> IdProduct_Column;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableColumn<ProductPollution, String> Pollution_Column;

    @FXML
    private ComboBox<String> Pollution_ComboBox;

    @FXML
    private TableView<ProductPollution> ProductPollution_Table;

    @FXML
    private Button Refresh_Button;

    public static int numberproduct;

    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent)
    {
        index = ProductPollution_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
    }

    void refresh(Connection con)
    {
        ObservableList<String> pollution_combobox = FXCollections.observableArrayList();
        ObservableList<ProductPollution> list = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res;
            res = st.executeQuery("select product_pollution.id_product, " +
                    "pollution.pollution from product_pollution, pollution " +
                    "where product_pollution.id_pollution=pollution.id_pollution and " +
                    "product_pollution.id_product=" + numberproduct);
            while (res.next()) {
                list.add(new ProductPollution(res.getInt("id_product"),
                        res.getString("pollution")));
            }
            ProductPollution_Table.setItems(list);

            st = con.createStatement();
            res = st.executeQuery("select pollution from pollution");
            while (res.next())
                pollution_combobox.add(res.getString("pollution"));
            Pollution_ComboBox.setItems(pollution_combobox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        IdProduct_Column.setCellValueFactory(new PropertyValueFactory<ProductPollution, Integer>("id_product"));
        Pollution_Column.setCellValueFactory(new PropertyValueFactory<ProductPollution, String>("pollution"));
        Connection con;
        try {
            con = BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    if (Pollution_ComboBox.getSelectionModel().getSelectedItem() == null) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_pollution from pollution where " +
                                "pollution='"+Pollution_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String pollution=res.getString("id_pollution");

                        st=con.createStatement();
                        st.execute("insert into product_pollution(id_product, id_pollution) " +
                                "values('"
                                + numberproduct + "', '"
                                + pollution + "')");
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
                    Pollution_ComboBox.getSelectionModel().select(null);
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
