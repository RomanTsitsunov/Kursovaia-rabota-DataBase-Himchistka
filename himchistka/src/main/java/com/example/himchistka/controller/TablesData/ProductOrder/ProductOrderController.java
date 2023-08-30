package com.example.himchistka.controller.TablesData.ProductOrder;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.OpenNewPanel;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.controller.TablesData.ProductAccessories.ProductAccessoriesController;
import com.example.himchistka.controller.TablesData.ProductDefect.ProductDefectController;
import com.example.himchistka.controller.TablesData.ProductPollution.ProductPollutionController;
import com.example.himchistka.controller.TablesData.ProductProcessing.ProductProcessingController;
import com.example.himchistka.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProductOrderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ProductAccessories, String> Accessories_Column;

    @FXML
    private TableColumn<ProductProcessing, Integer> AmountMaterials_Column;

    @FXML
    private TableColumn<ProductOrder, String> Colour_Column;

    @FXML
    private ComboBox<String> Colour_ComboBox;

    @FXML
    private TableColumn<ProductOrder, String> Completeness_Column;

    @FXML
    private TextField Completeness_Field;

    @FXML
    private TableColumn<ProductProcessing, String> ConsumMaterial_Column;

    @FXML
    private TableColumn<ProductDefect, String> Defect_Column;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<ProductOrder, Integer> IdOrders_Column;

    @FXML
    private TableColumn<ProductOrder, Integer> IdProduct_ColumnProduct;

    @FXML
    private TableColumn<ProductAccessories, Integer> IdProduct_ColumnProductAccessories;

    @FXML
    private TableColumn<ProductDefect, Integer> IdProduct_ColumnProductDefect;

    @FXML
    private TableColumn<ProductPollution, Integer> IdProduct_ColumnProductPollution;

    @FXML
    private TableColumn<ProductProcessing, Integer> IdProduct_ColumnProductProcessing;

    @FXML
    private Button Insert_Button;

    @FXML
    private TableColumn<ProductOrder, String> Marking_Column;

    @FXML
    private ComboBox<String> Marking_ComboBox;

    @FXML
    private TableColumn<ProductOrder, String> Material_Column;

    @FXML
    private ComboBox<String> Material_ComboBox;

    @FXML
    private TableColumn<ProductPollution, String> Pollution_Column;

    @FXML
    private TableColumn<ProductOrder, Float> Price_Column;

    @FXML
    private TableView<ProductAccessories> ProductAccessories_Table;

    @FXML
    private TableView<ProductDefect> ProductDefect_Table;

    @FXML
    private TableView<ProductPollution> ProductPollution_Table;

    @FXML
    private TableView<ProductProcessing> ProductProcessing_Table;

    @FXML
    private TableView<ProductOrder> Product_Table;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<ProductProcessing, String> Service_Column;

    @FXML
    private TableColumn<ProductOrder, String> ProductType_Column;

    @FXML
    private ComboBox<String> ProductType_ComboBox;

    @FXML
    private Button ViewProductAccessories_Button;

    @FXML
    private Button ViewProductDefect_Button;

    @FXML
    private Button ViewProductPollution_Button;

    @FXML
    private Button ViewProductProcessing_Button;

    public static int numberorder;

    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index = Product_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
        ProductType_ComboBox.getSelectionModel().select(ProductType_Column.getCellData(index));
        Colour_ComboBox.getSelectionModel().select(Colour_Column.getCellData(index));
        Material_ComboBox.getSelectionModel().select(Material_Column.getCellData(index));
        Marking_ComboBox.getSelectionModel().select(Marking_Column.getCellData(index));
        Completeness_Field.setText(Completeness_Column.getCellData(index));

        ObservableList<ProductProcessing> productprocessing_table = FXCollections.observableArrayList();
        try {
            Connection con = BD_connection.get_connection();
            Statement st = con.createStatement();
            ResultSet res;
            res = st.executeQuery("select product_processing.id_product, service.name as service, " +
                    "storage.name as consum_material, product_processing.amount_materials " +
                    "from product_processing, service, storage " +
                    "where product_processing.id_service=service.id_service and " +
                    "product_processing.id_consum_material=storage.id_consum_material and " +
                    "product_processing.id_product="
                    + IdProduct_ColumnProduct.getCellData(index));
            while (res.next()) {
                productprocessing_table.add(new ProductProcessing(res.getInt("id_product"),
                        res.getInt("amount_materials"),
                        res.getString("service"),
                        res.getString("consum_material")));
            }
            ProductProcessing_Table.setItems(productprocessing_table);


            ObservableList<ProductPollution> productpollution_table = FXCollections.observableArrayList();

            st = con.createStatement();
            res = st.executeQuery("select product_pollution.id_product, " +
                    "pollution.pollution from product_pollution, pollution " +
                    "where product_pollution.id_pollution=pollution.id_pollution and " +
                    "product_pollution.id_product="
                    + IdProduct_ColumnProduct.getCellData(index));
            while (res.next()) {
                productpollution_table.add(new ProductPollution(res.getInt("id_product"),
                        res.getString("pollution")));
            }
            ProductPollution_Table.setItems(productpollution_table);


            ObservableList<ProductDefect> productdefect_table = FXCollections.observableArrayList();

            st = con.createStatement();
            res = st.executeQuery("select product_defect.id_product, " +
                    "defect.defect from product_defect, defect " +
                    "where product_defect.id_defect=defect.id_defect and " +
                    "product_defect.id_product="
                    + IdProduct_ColumnProduct.getCellData(index));
            while (res.next()) {
                productdefect_table.add(new ProductDefect(res.getInt("id_product"),
                        res.getString("defect")));
            }
            ProductDefect_Table.setItems(productdefect_table);


            ObservableList<ProductAccessories> productaccessories_table = FXCollections.observableArrayList();

            st = con.createStatement();
            res = st.executeQuery("select product_accessories.id_product, " +
                    "accessories.accessories from product_accessories, accessories " +
                    "where product_accessories.id_accessories=accessories.id_accessories and " +
                    "product_accessories.id_product="
                    + IdProduct_ColumnProduct.getCellData(index));
            while (res.next()) {
                productaccessories_table.add(new ProductAccessories(res.getInt("id_product"),
                        res.getString("accessories")));
            }
            ProductAccessories_Table.setItems(productaccessories_table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //передать idProduct в productprocessingcontroller, productpollutioncontroller,
        //productdefectcontroller и productaccessoriescontroller, вызывать
        //productprocessingcontroller.refresh, productpollutioncontroller.refresh,
        //productdefectcontroller.refresh и productaccessoriescontroller.refresh
    }

    void refresh(Connection con)
    {
        ObservableList<String> producttype_combobox = FXCollections.observableArrayList();
        ObservableList<String> colour_combobox = FXCollections.observableArrayList();
        ObservableList<String> material_combobox = FXCollections.observableArrayList();
        ObservableList<String> marking_combobox = FXCollections.observableArrayList();
        ObservableList<ProductOrder> list = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res;
            res = st.executeQuery("select product_order.id_product, product_order.id_order, " +
                    "product_type.name as product_type, colour.colour, material.material, " +
                    "marking.marking, product_order.completeness, product_order.price " +
                    "from product_order, product_type, colour, material, marking " +
                    "where product_order.id_product_type=product_type.id_product_type and " +
                    "product_order.id_colour=colour.id_colour and " +
                    "product_order.id_material=material.id_material and " +
                    "product_order.id_marking=marking.id_marking and " +
                    "product_order.id_order=" + numberorder);
            while (res.next()) {
                list.add(new ProductOrder(res.getInt("id_product"),
                        res.getInt("id_order"),
                        res.getFloat("price"),
                        res.getString("product_type"),
                        res.getString("colour"),
                        res.getString("material"),
                        res.getString("marking"),
                        res.getString("completeness")));
            }
            Product_Table.setItems(list);

            st = con.createStatement();
            res = st.executeQuery("select name from product_type");
            while (res.next())
                producttype_combobox.add(res.getString("name"));
            ProductType_ComboBox.setItems(producttype_combobox);

            st = con.createStatement();
            res = st.executeQuery("select colour from colour");
            while (res.next())
                colour_combobox.add(res.getString("colour"));
            Colour_ComboBox.setItems(colour_combobox);

            st = con.createStatement();
            res = st.executeQuery("select material from material");
            while (res.next())
                material_combobox.add(res.getString("material"));
            Material_ComboBox.setItems(material_combobox);

            st = con.createStatement();
            res = st.executeQuery("select marking from marking");
            while (res.next())
                marking_combobox.add(res.getString("marking"));
            Marking_ComboBox.setItems(marking_combobox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        IdProduct_ColumnProduct.setCellValueFactory(new PropertyValueFactory<ProductOrder, Integer>("id_product"));
        IdOrders_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, Integer>("id_order"));
        ProductType_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, String>("product_type"));
        Colour_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, String>("colour"));
        Material_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, String>("material"));
        Marking_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, String>("marking"));
        Completeness_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, String>("completeness"));
        Price_Column.setCellValueFactory(new PropertyValueFactory<ProductOrder, Float>("price"));

        IdProduct_ColumnProductProcessing.setCellValueFactory(new PropertyValueFactory<ProductProcessing, Integer>("id_product"));
        Service_Column.setCellValueFactory(new PropertyValueFactory<ProductProcessing, String>("service"));
        ConsumMaterial_Column.setCellValueFactory(new PropertyValueFactory<ProductProcessing, String>("consum_material"));
        AmountMaterials_Column.setCellValueFactory(new PropertyValueFactory<ProductProcessing, Integer>("amount_materials"));

        IdProduct_ColumnProductPollution.setCellValueFactory(new PropertyValueFactory<ProductPollution, Integer>("id_product"));
        Pollution_Column.setCellValueFactory(new PropertyValueFactory<ProductPollution, String>("pollution"));

        IdProduct_ColumnProductDefect.setCellValueFactory(new PropertyValueFactory<ProductDefect, Integer>("id_product"));
        Defect_Column.setCellValueFactory(new PropertyValueFactory<ProductDefect, String>("defect"));

        IdProduct_ColumnProductAccessories.setCellValueFactory(new PropertyValueFactory<ProductAccessories, Integer>("id_product"));
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
                    if (ProductType_ComboBox.getSelectionModel().getSelectedItem() == null
                    ||Colour_ComboBox.getSelectionModel().getSelectedItem() == null
                    ||Material_ComboBox.getSelectionModel().getSelectedItem() == null
                    ||Marking_ComboBox.getSelectionModel().getSelectedItem() == null) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все необходимые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_product_type from product_type where " +
                                "name='"+ProductType_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String product_type=res.getString("id_product_type");

                        st=con.createStatement();
                        res=st.executeQuery("select id_colour from colour where " +
                                "colour='"+Colour_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String colour=res.getString("id_colour");

                        st=con.createStatement();
                        res=st.executeQuery("select id_material from material where " +
                                "material='"+Material_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String material=res.getString("id_material");

                        st=con.createStatement();
                        res=st.executeQuery("select id_marking from marking where " +
                                "marking='"+Marking_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String marking=res.getString("id_marking");

                        st=con.createStatement();
                        st.execute("insert into product_order(id_order, id_product_type, id_colour, " +
                                "id_material, id_marking, completeness, price) " +
                                "values('"
                                + numberorder + "', '"
                                + product_type + "', '"
                                + colour + "', '"
                                + material + "', '"
                                + marking + "', '"
                                + Completeness_Field.getText() + "', 0)");
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
                    index=Product_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите изделие");
                        alert.showAndWait();
                        return;
                    }
                    if (ProductType_ComboBox.getSelectionModel().getSelectedItem() == null
                            ||Colour_ComboBox.getSelectionModel().getSelectedItem() == null
                            ||Material_ComboBox.getSelectionModel().getSelectedItem() == null
                            ||Marking_ComboBox.getSelectionModel().getSelectedItem() == null) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все необходимые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_product_type from product_type where " +
                                "name='"+ProductType_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String product_type=res.getString("id_product_type");

                        st=con.createStatement();
                        res=st.executeQuery("select id_colour from colour where " +
                                "colour='"+Colour_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String colour=res.getString("id_colour");

                        st=con.createStatement();
                        res=st.executeQuery("select id_material from material where " +
                                "material='"+Material_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String material=res.getString("id_material");

                        st=con.createStatement();
                        res=st.executeQuery("select id_marking from marking where " +
                                "marking='"+Marking_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String marking=res.getString("id_marking");

                        st.execute("update product_order set id_order='"
                                + numberorder + "', id_product_type='"
                                + product_type + "', id_colour='"
                                + colour + "', id_material='"
                                + material + "', id_marking='"
                                + marking + "', completeness='"
                                + Completeness_Field.getText() + "' where id_product='"
                                + IdProduct_ColumnProduct.getCellData(index) + "'");
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
                    ProductType_ComboBox.getSelectionModel().select(null);
                    Colour_ComboBox.getSelectionModel().select(null);
                    Material_ComboBox.getSelectionModel().select(null);
                    Marking_ComboBox.getSelectionModel().select(null);
                    Completeness_Field.setText("");
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

        ViewProductProcessing_Button.setOnAction(event ->
                {
                    index = Product_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1)
                        return;
                    ProductProcessingController.numberproduct=IdProduct_ColumnProduct.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductProcessing/ProductProcessing-Panel.fxml",
                            "Процесс обработки изделия №"+IdProduct_ColumnProduct.getCellData(index));
                }
        );

        ViewProductPollution_Button.setOnAction(event ->
                {
                    index = Product_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1)
                        return;
                    ProductPollutionController.numberproduct=IdProduct_ColumnProduct.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductPollution/ProductPollution-Panel.fxml",
                            "Загрязнение изделия №"+IdProduct_ColumnProduct.getCellData(index));
                }
        );

        ViewProductDefect_Button.setOnAction(event ->
                {
                    index = Product_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1)
                        return;
                    ProductDefectController.numberproduct=IdProduct_ColumnProduct.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductDefect/ProductDefect-Panel.fxml",
                            "Дефекты изделия №"+IdProduct_ColumnProduct.getCellData(index));
                }
        );

        ViewProductAccessories_Button.setOnAction(event ->
                {
                    index = Product_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1)
                        return;
                    ProductAccessoriesController.numberproduct=IdProduct_ColumnProduct.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductAccessories/ProductAccessories-Panel.fxml",
                            "Фурнитура изделия №"+IdProduct_ColumnProduct.getCellData(index));
                }
        );
    }

}
