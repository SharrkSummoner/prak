package com.example.prak;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddBookController {
    @FXML
    private ComboBox bookAuthor;

    @FXML
    private TextField bookCount;

    @FXML
    private ComboBox bookGenre;

    @FXML
    private TextField bookImage;

    @FXML
    private TextField bookPrice;

    @FXML
    private ComboBox bookPublisher;

    @FXML
    private TextField bookTitle;

    @FXML
    public void initialize() throws SQLException {
        ObservableList<String> genreItems = FXCollections.observableArrayList();
        ObservableList<String> publisherItems = FXCollections.observableArrayList();
        ObservableList<String> authorItems = FXCollections.observableArrayList();

        ResultSet resultSet = Connect.statement.executeQuery(
                "SELECT genre_name FROM genres;"
        );
        while (resultSet.next()){
            genreItems.add(resultSet.getString("genre_name"));
        }
        bookGenre.setItems(genreItems);

        resultSet = Connect.statement.executeQuery(
                "SELECT publisher_name FROM publishing_homes;"
        );
        while (resultSet.next()){
            publisherItems.add(resultSet.getString("publisher_name"));
        }
        bookPublisher.setItems(publisherItems);

        resultSet = Connect.statement.executeQuery(
                "SELECT concat(name, ' ', surname) AS author FROM authors;"
        );
        while (resultSet.next()){
            authorItems.add(resultSet.getString("author"));
        }
        bookAuthor.setItems(authorItems);
    }

    @FXML
    public void addBookButtonClicked() throws SQLException, IOException {
        String title = bookTitle.getText();
        String price = bookPrice.getText();
        String count = bookCount.getText();
        String image = bookImage.getText();
        Integer author = bookAuthor.getSelectionModel().getSelectedIndex() + 1;
        Integer genre = bookGenre.getSelectionModel().getSelectedIndex() + 1;
        Integer publisher = bookPublisher.getSelectionModel().getSelectedIndex() + 1;

        Connect.statement.execute(String.format(
                "INSERT INTO books VALUES(NULL, '%s', '%s', '%s', '%s', '%d', '%d', '%d')",
                title,
                price,
                count,
                image,
                author,
                genre,
                publisher
        ));
    }
}
