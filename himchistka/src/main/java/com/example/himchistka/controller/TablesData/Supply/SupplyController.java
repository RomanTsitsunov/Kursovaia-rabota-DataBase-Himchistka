package com.example.himchistka.controller.TablesData.Supply;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.OpenNewPanel;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.controller.TablesData.ProductOrder.ProductOrderController;
import com.example.himchistka.controller.TablesData.SupplyLine.SupplyLineController;
import com.example.himchistka.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SupplyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<SupplyLine, Integer> AmountMaterials_Column;

    @FXML
    private TableColumn<SupplyLine, String> ConsumMaterial_Column;

    @FXML
    private TableColumn<Supply, String> DateSupply_Column;

    @FXML
    private TextField DateSupply_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<Supply, Integer> IdSupply_ColumnSupply;

    @FXML
    private TableColumn<SupplyLine, Integer> IdSupply_ColumnSupplyLine;

    @FXML
    private Button Insert_Button;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<Supply, String> Supplier_Column;

    @FXML
    private ComboBox<String> Supplier_ComboBox;

    @FXML
    private TableView<SupplyLine> SupplyLine_Table;

    @FXML
    private TableView<Supply> Supply_Table;

    @FXML
    private Button ViewSupplyLine_Button;

    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent)
    {
        index = Supply_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
        Supplier_ComboBox.getSelectionModel().select(Supplier_Column.getCellData(index));
        DateSupply_Field.setText(DateSupply_Column.getCellData(index));

        ObservableList<SupplyLine> list = FXCollections.observableArrayList();
        try
        {
            Connection con=BD_connection.get_connection();
            Statement st=con.createStatement();
            ResultSet res;
            res = st.executeQuery("select supply_line.id_supply, storage.name as consum_material, " +
                    "supply_line.amount_materials from supply_line, storage " +
                    "where supply_line.id_consum_material=storage.id_consum_material and " +
                    "supply_line.id_supply="+IdSupply_ColumnSupply.getCellData(index));
            while (res.next())
            {
                list.add(new SupplyLine(res.getInt("id_supply"),
                        res.getInt("amount_materials"),
                        res.getString("consum_material")));
            }
            SupplyLine_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //передать idsupply в supplylinecontroller и вызывать supplylinecontroller.refresh
    }

    void refresh(Connection con)
    {
        ObservableList<String> supplier_combobox = FXCollections.observableArrayList();
        ObservableList<Supply> list = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("select supply.id_supply, supplier.name as supplier, " +
                    "supply.date_supply from supply, supplier " +
                    "where supply.id_supplier=supplier.id_supplier");
            while (res.next())
            {
                list.add(new Supply(res.getInt("id_supply"),
                        res.getString("supplier"),
                        res.getString("date_supply")));
            }
            Supply_Table.setItems(list);

            st = con.createStatement();
            res=st.executeQuery("select name from supplier");
            while (res.next())
                supplier_combobox.add(res.getString("name"));
            Supplier_ComboBox.setItems(supplier_combobox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        IdSupply_ColumnSupply.setCellValueFactory(new PropertyValueFactory<Supply, Integer>("id_supply"));
        Supplier_Column.setCellValueFactory(new PropertyValueFactory<Supply, String>("supplier_name"));
        DateSupply_Column.setCellValueFactory(new PropertyValueFactory<Supply, String>("date_supply"));
        IdSupply_ColumnSupplyLine.setCellValueFactory(new PropertyValueFactory<SupplyLine, Integer>("id_supply"));
        ConsumMaterial_Column.setCellValueFactory(new PropertyValueFactory<SupplyLine, String>("consum_material"));
        AmountMaterials_Column.setCellValueFactory(new PropertyValueFactory<SupplyLine, Integer>("amount_materials"));
        Connection con;
        try {
            con = BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    if(Supplier_ComboBox.getSelectionModel().getSelectedItem().isEmpty()
                            ||DateSupply_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_supplier from supplier where " +
                                "name='"+Supplier_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String supplier=res.getString("id_supplier");

                        st=con.createStatement();
                        st.execute("insert into supply(id_supplier, date_supply) " +
                                "values('"
                                + supplier + "', '"
                                + DateSupply_Field.getText() + "')");
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
                    index=Supply_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите поставку");
                        alert.showAndWait();
                        return;
                    }
                    if(Supplier_ComboBox.getSelectionModel().getSelectedItem().isEmpty()
                            ||DateSupply_Field.getText().isEmpty()) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_supplier from supplier where " +
                                "name='"+Supplier_ComboBox.getSelectionModel().getSelectedItem()+"'");
                        res.next();
                        String supplier=res.getString("id_supplier");

                        st=con.createStatement();
                        st.execute("update supply set id_supplier='"
                                + supplier + "', date_supply='"
                                + DateSupply_Field.getText() + "' where id_supply='"
                                + IdSupply_ColumnSupply.getCellData(index) + "'");
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
                    Supplier_ComboBox.getSelectionModel().select(null);
                    DateSupply_Field.setText("");
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

        ViewSupplyLine_Button.setOnAction(event ->
                {
                    index = Supply_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1)
                        return;
                    SupplyLineController.numbersupply=IdSupply_ColumnSupply.getCellData(index);
                    OpenNewPanel.open("/com/example/himchistka/TablesData/SupplyLine/SupplyLine-Panel.fxml",
                            "Содержимое поставки №"+IdSupply_ColumnSupply.getCellData(index));
                }
        );
    }

}
