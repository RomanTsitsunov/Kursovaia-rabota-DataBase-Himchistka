package com.example.himchistka;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class OpenNewPanel
{
    public static void open(String location, String title)
    {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(OpenNewPanel.class.getResource
                (location));
        try {
            Loader.load();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        Parent root= Loader.getRoot();
        Stage stage=new Stage();
        Scene scene = null;
        scene = new Scene(root);
        InputStream iconStream= OpenNewPanel.class.getResourceAsStream("/icon.jpg");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
