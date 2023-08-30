package com.example.himchistka.controller.TablesData.ProductProcessing;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.OpenNewPanel;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.Payment;
import com.example.himchistka.models.ProductProcessing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProductProcessingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ProductProcessing, Integer> AmountMaterials_Column;

    @FXML
    private TextField AmountMaterials_Field;

    @FXML
    private TableColumn<ProductProcessing, String> ConsumMaterial_Column;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<ProductProcessing, Integer> IdProduct_Column;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableView<ProductProcessing> ProductProcessing_Table;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<ProductProcessing, String> Service_Column;

    public static int numberproduct;

    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index = ProductProcessing_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
        AmountMaterials_Field.setText(AmountMaterials_Column.getCellData(index).toString());
    }

    void refresh(Connection con)
    {
        ObservableList<Integer> idproduct_combobox = FXCollections.observableArrayList();
        ObservableList<String> service_combobox = FXCollections.observableArrayList();
        ObservableList<String> consummaterial_combobox = FXCollections.observableArrayList();
        ObservableList<ProductProcessing> list = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res;
            res = st.executeQuery("select product_processing.id_product, service.name as service, " +
                    "storage.name as consum_material, product_processing.amount_materials " +
                    "from product_processing, service, storage " +
                    "where product_processing.id_service=service.id_service and " +
                    "product_processing.id_consum_material=storage.id_consum_material and " +
                    "product_processing.id_product=" + numberproduct);
            while (res.next()) {
                list.add(new ProductProcessing(res.getInt("id_product"),
                        res.getInt("amount_materials"),
                        res.getString("service"),
                        res.getString("consum_material")));
            }
            ProductProcessing_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        IdProduct_Column.setCellValueFactory(new PropertyValueFactory<ProductProcessing, Integer>("id_product"));
        Service_Column.setCellValueFactory(new PropertyValueFactory<ProductProcessing, String>("service"));
        ConsumMaterial_Column.setCellValueFactory(new PropertyValueFactory<ProductProcessing, String>("consum_material"));
        AmountMaterials_Column.setCellValueFactory(new PropertyValueFactory<ProductProcessing, Integer>("amount_materials"));
        Connection con;
        try {
            con = BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    InsertProductProcessing.numberproduct=numberproduct;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductProcessing/InsertProductProcessing-Panel.fxml",
                            "Добавление обработки изделия №"+numberproduct);
                }
        );

        Redact_Button.setOnAction(event ->
                {
                    index=ProductProcessing_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите обработку");
                        alert.showAndWait();
                        return;
                    }
                    if(AmountMaterials_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_service from service where " +
                                "name='"+Service_Column.getCellData(index)+"'");
                        res.next();
                        String service=res.getString("id_service");

                        st=con.createStatement();
                        res=st.executeQuery("select id_consum_material from storage where " +
                                "name='"+ConsumMaterial_Column.getCellData(index)+"'");
                        res.next();
                        String consum_material=res.getString("id_consum_material");

                        st.execute("update product_processing set amount_materials='"
                                + AmountMaterials_Field.getText() + "' where id_product='"
                                + numberproduct + "' and id_service='"
                                + service + "' and id_consum_material='"
                                + consum_material + "'");
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
                    AmountMaterials_Field.setText("");
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
