package com.example.himchistka.controller.TablesData.Storage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.OpenNewPanel;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.Customer;
import com.example.himchistka.models.Storage;
import com.example.himchistka.models.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class StorageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Storage, Integer> AmountMaterials_Column;

    @FXML
    private TextField AmountMaterials_Field;

    @FXML
    private TableColumn<Storage, String> ConsumMaterial_Column;

    @FXML
    private TextField ConsumMaterial_Field;

    @FXML
    private Button Exit_Button;

    @FXML
    private Button Insert_Button;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private Button Search_Button;

    @FXML
    private TableView<Storage> Storage_Table;

    @FXML
    private TableColumn<Storage, String> Unit_Column;

    @FXML
    private TextField Unit_Field;

    int index=-1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index=Storage_Table.getSelectionModel().getSelectedIndex();
        if(index<=-1)
            return;
        //ConsumMaterial_Field.setText(ConsumMaterial_Column.getCellData(index));
        //Unit_Field.setText(Unit_Column.getCellData(index));
        //AmountMaterials_Field.setText(AmountMaterials_Column.getCellData(index).toString());
    }

    void refresh(Connection con)
    {
        ObservableList<Storage> list= FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res=st.executeQuery("select * from storage");
            while (res.next())
            {
                list.add(new Storage(res.getInt("amount_materials"),
                        res.getString("name"),
                        res.getString("unit")));
            }
            Storage_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if(!MainController.admin) {
            Redact_Button.setDisable(true);
            Insert_Button.setDisable(true);
        }
        ConsumMaterial_Column.setCellValueFactory(new PropertyValueFactory<Storage, String>("consum_material"));
        Unit_Column.setCellValueFactory(new PropertyValueFactory<Storage, String>("unit"));
        AmountMaterials_Column.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("amount_materials"));
        Connection con;
        try {
            con= BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Storage/InsertStorage-Panel.fxml",
                            "Добавление расходного материала на склад");
                }
        );

        Redact_Button.setOnAction(event ->
                {
                    index=Storage_Table.getSelectionModel().getSelectedIndex();
                    if (index <= -1) {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Выберите расходный материал");
                        alert.showAndWait();
                        return;
                    }
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Storage/RedactStorage-Panel.fxml",
                            "Редактироваение данных о расходных материалах на складе");
                }
        );

        Search_Button.setOnAction(event ->
                {
                    try
                    {
                        Statement st = con.createStatement();
                        String arg="";
                        if(!ConsumMaterial_Field.getText().isEmpty()) {
                            arg = arg + "storage.name='" + ConsumMaterial_Field.getText() + "'";
                        }
                        if(!Unit_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "storage.unit='" + Unit_Field.getText() + "'";
                        }
                        if(!AmountMaterials_Field.getText().isEmpty()) {
                            if (!arg.isEmpty())
                                arg = arg + " and ";
                            arg = arg + "storage.amount_materials='" + AmountMaterials_Field.getText() + "'";
                        }
                        ObservableList<Storage> storage = FXCollections.observableArrayList();
                        if(!arg.isEmpty()) {
                            ResultSet res=st.executeQuery("select * from storage where "+arg);
                            while (res.next())
                            {
                                storage.add(new Storage(res.getInt("amount_materials"),
                                        res.getString("name"),
                                        res.getString("unit")));
                            }
                        }
                        Storage_Table.setItems(storage);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Refresh_Button.setOnAction(event ->
                {
                    refresh(con);
                    ConsumMaterial_Field.setText("");
                    Unit_Field.setText("");
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
