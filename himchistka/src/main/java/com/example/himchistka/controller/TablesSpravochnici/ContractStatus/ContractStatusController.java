package com.example.himchistka.controller.TablesSpravochnici.ContractStatus;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.ContractStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContractStatusController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<ContractStatus> ContractStatus_Table;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<ContractStatus, Integer> Id_Column;

    @FXML
    private TextField Id_Field;

    @FXML
    private Button Insert_Button;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<ContractStatus, String> Status_Column;

    @FXML
    private TextField Status_Field;

    @FXML
    int index=-1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index=ContractStatus_Table.getSelectionModel().getSelectedIndex();
        if(index<=-1)
            return;
        Id_Field.setText(Id_Column.getCellData(index).toString());
        Status_Field.setText(Status_Column.getCellData(index));
    }

    void refresh(Connection con)
    {
        ObservableList<ContractStatus> list= FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res=st.executeQuery("select * from contract_status");
            while (res.next())
                list.add(new ContractStatus(res.getInt("id_status"),res.getString("name")));
            ContractStatus_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.visibleProperty().set(false);
        Id_Column.setCellValueFactory(new PropertyValueFactory<ContractStatus, Integer>("id"));
        Status_Column.setCellValueFactory(new PropertyValueFactory<ContractStatus,String>("status"));
        Connection con;
        try {
            con= BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);

        Insert_Button.setOnAction(event ->
            {
                try {
                    Statement st = con.createStatement();
                    st.execute("insert into contract_status(name) values('"
                            +Status_Field.getText()+"')");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                refresh(con);
            }
        );

        Redact_Button.setOnAction(event ->
                {
                    try {
                        Statement st = con.createStatement();
                        st.execute("update contract_status set name='"
                                +Status_Field.getText()+"' where id_status='"
                                +Id_Field.getText()+"'");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
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
