package com.example.prak;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("startmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Каталог нижного магазина");
        stage.getIcons().add(new Image("file:src/main/resources/images/icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}