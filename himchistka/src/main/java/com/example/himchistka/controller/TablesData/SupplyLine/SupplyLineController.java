package com.example.himchistka.controller.TablesData.SupplyLine;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.OpenNewPanel;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.Supply;
import com.example.himchistka.models.SupplyLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SupplyLineController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<SupplyLine, Integer> AmountMaterials_Column;

    @FXML
    private TextField AmountMaterials_Field;

    @FXML
    private TableColumn<SupplyLine, String> ConsumMaterial_Column;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<SupplyLine, Integer> IdSupply_Column;

    @FXML
    private Button Insert_Button;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableView<SupplyLine> SupplyLine_Table;

    public static int numbersupply;

    int index = -1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent)
    {
        index = SupplyLine_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1)
            return;
        AmountMaterials_Field.setText(AmountMaterials_Column.getCellData(index).toString());
    }

    void refresh(Connection con)
    {
        ObservableList<SupplyLine> list = FXCollections.observableArrayList();
        try {
            Statement st=con.createStatement();
            ResultSet res;
                res = st.executeQuery("select supply_line.id_supply, storage.name as consum_material, " +
                        "supply_line.amount_materials from supply_line, storage " +
                        "where supply_line.id_consum_material=storage.id_consum_material and " +
                        "supply_line.id_supply="+numbersupply);
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
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.setDisable(true);
        IdSupply_Column.setCellValueFactory(new PropertyValueFactory<SupplyLine, Integer>("id_supply"));
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
                    InsertSupplyLine.numbersupply=numbersupply;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/SupplyLine/InsertSupplyLine-Panel.fxml",
                            "Добавление партии поставки для поставки №"+numbersupply);
                }
        );

        Redact_Button.setOnAction(event ->
                {
                    index=SupplyLine_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите поставку");
                        alert.showAndWait();
                        return;
                    }
                    if (AmountMaterials_Field.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Пожалуйста, заполните все пустые поля!");
                        alert.showAndWait();
                        return;
                    }
                    try {
                        Statement st = con.createStatement();
                        ResultSet res=st.executeQuery("select id_consum_material from storage where " +
                                "name='"+ConsumMaterial_Column.getCellData(index)+"'");
                        res.next();
                        String consummaterial=res.getString("id_consum_material");

                        st=con.createStatement();
                        st.execute("update supply_line set amount_materials='"
                                + AmountMaterials_Field.getText() + "' where id_supply='"
                                + numbersupply + "' and id_consum_material='"
                                + consummaterial + "'");
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
