package com.example.himchistka.controller.TablesSpravochnici.CustomerType;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.himchistka.BD_connection;
import com.example.himchistka.controller.MainController;
import com.example.himchistka.models.CustomerType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerTypeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<CustomerType> CustomerType_Table;

    @FXML
    private Button Exit_Button;

    @FXML
    private TableColumn<CustomerType, Integer> Id_Column;

    @FXML
    private TextField Id_Field;

    @FXML
    private Button Insert_Button;

    @FXML
    private Button Redact_Button;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableColumn<CustomerType, String> Type_Column;

    @FXML
    private TextField Type_Field;

    /*
    ObservableList<CustomerType> list1= FXCollections.observableArrayList(
            new CustomerType(2,"qh"),
            new CustomerType(3,"dq")
    );
     */
    int index=-1;
    public void cursor(javafx.scene.input.MouseEvent mouseEvent) {
        index=CustomerType_Table.getSelectionModel().getSelectedIndex();
        if(index<=-1)
            return;
        Id_Field.setText(Id_Column.getCellData(index).toString());
        Type_Field.setText(Type_Column.getCellData(index));
    }

    void refresh(Connection con)
    {
        ObservableList<CustomerType> list= FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet res=st.executeQuery("select * from customer_type");
            while (res.next())
                list.add(new CustomerType(res.getInt("id_type"),res.getString("name")));
            CustomerType_Table.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize()
    {
        if (!MainController.admin)
            Redact_Button.visibleProperty().set(false);
        Id_Column.setCellValueFactory(new PropertyValueFactory<CustomerType, Integer>("id"));
        Type_Column.setCellValueFactory(new PropertyValueFactory<CustomerType,String>("type"));
        Connection con;
        try {
            con=BD_connection.get_connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refresh(con);
            /*
            int j,i;
            for(i=1;res.next();i++)
                for(j=0;j<2;j++)
                {
                    TextField text = new TextField();
                    text.setEditable(false);
                    text.setText(res.getString(j+1));
                    //Table_GridPane.add(text, j, i);
                }
                */
            //con.close();

        Insert_Button.setOnAction(event ->
        {
            try {
                Statement st = con.createStatement();
                st.execute("insert into Customer_Type(name) values('"
                        +Type_Field.getText()+"')");
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
                st.execute("update Customer_Type set name='"
                        +Type_Field.getText()+"' where id_type='"
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
