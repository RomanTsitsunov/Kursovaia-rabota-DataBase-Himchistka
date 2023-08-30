package com.example.himchistka;

import com.example.himchistka.controller.LoginController;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException, SQLException, DocumentException
    {
        /*
        Connection con = BD_connection.get_connection();
        Statement st=con.createStatement();
        ResultSet res;
        //st.execute("insert into customer_type values (1, 'Юридическое лицо')");
        res=st.executeQuery("select * from customer_type");
        for(;res.next();)
        {
            System.out.print(res.getInt(1)+" ");
            System.out.print(res.getString(2));
            System.out.println();
        };

        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Ошибка добавления данных");
        alert.showAndWait();
        */
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login-Panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        InputStream iconStream=getClass().getResourceAsStream("/icon.jpg");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        stage.setTitle("Вход");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}