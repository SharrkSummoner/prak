package com.example.prak;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class startmenuController {
    @FXML
    private Button but;

    @FXML
    private TextField log;

    @FXML
    private PasswordField pas;

    @FXML
    void initialize() {

        but.setOnAction(event -> {
            String loginText = log.getText().trim();
            String loginPassword = pas.getText().trim();

            //Проверка на пустоту полей
            if (!loginText.equals("") && !loginPassword.equals("")) {
                try {
                    loginUser(loginText, loginPassword);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Не все поля заполнены.");
                alert.show();
            }
        });
    }

    public void loginUser(String loginText, String loginPassword) throws SQLException, IOException {
        Connect dbHandler = new Connect();
        ResultSet result = dbHandler.getUser(loginText, loginPassword);

        if (result.next()) {
            but.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("mainScreen.fxml"));
            loader.load();

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Каталог");
            stage.getIcons().add(new Image("file:src/main/resources/images/icon.png"));
            stage.setResizable(false);
            stage.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Некорректные данные для входа!");
            alert.show();
        }
    }
}