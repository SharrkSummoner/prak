package com.example.prak;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainScreenController {

    @FXML
    private TextField authorSearch;

    @FXML
    private TextField titleSearch;

    @FXML
    private ComboBox genreComboBox;

    @FXML
    private GridPane grid;

    @FXML
    private ComboBox publisherComboBox;

    @FXML
    private ScrollPane scroll;

    private List<Book> books = new ArrayList<>();

    @FXML
    public void initialize() throws SQLException {
        ObservableList<String> genreItems = FXCollections.observableArrayList("Без фильтра");
        ObservableList<String> publisherItems = FXCollections.observableArrayList("Без фильтра");

        ResultSet resultSet = Connect.statement.executeQuery(
                "SELECT genre_name FROM genres;"
        );
        while (resultSet.next()){
            genreItems.add(resultSet.getString("genre_name"));
        }
        genreComboBox.setItems(genreItems);

        resultSet = Connect.statement.executeQuery(
                "SELECT publisher_name FROM publishing_homes;"
        );
        while (resultSet.next()){
            publisherItems.add(resultSet.getString("publisher_name"));
        }
        publisherComboBox.setItems(publisherItems);

        formDataChanged();
    }

    private void redrawGrid(String query) throws SQLException {
        Book book;
        grid.getChildren().clear();
        books.clear();

        ResultSet resultSet = Connect.statement.executeQuery(query);

        while (resultSet.next()) {
            book = new Book();

            book.setBookTitle(resultSet.getString("title"));
            book.setPrice(resultSet.getString("price") + " y.e.");
            book.setCount(resultSet.getString("count"));
            book.setImage(new Image("file:src/main/resources/images/" + resultSet.getString("image")));
            book.setAuthor(resultSet.getString("author"));
            book.setGenre(resultSet.getString("genre_name"));
            book.setPublisher(resultSet.getString("publisher_name"));

            books.add(book);
        }

        try {
            for (int i = 0; i < books.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                BookController bookController = fxmlLoader.getController();
                bookController.updateData(books.get(i));

                grid.add(anchorPane, 0, i);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void formDataChanged() throws SQLException {
        String request = "SELECT title, price, count, image, concat(name, ' ', surname) as author, genre_name, publisher_name FROM books b, authors a, publishing_homes p, genres g " +
                "WHERE b.author = a.id_author AND b.genre = g.genre_id AND b.publishing_home = p.publisher_id ";

        if(authorSearch.getText().length() > 0){
            request += String.format("AND (name LIKE '%%%s%%' OR surname LIKE '%%%s%%') ", authorSearch.getText(), authorSearch.getText());
        }

        if(titleSearch.getText().length() > 0){
            request += String.format("AND title LIKE '%%%s%%' ", titleSearch.getText());
        }

        if(genreComboBox.getSelectionModel().getSelectedIndex() > 0) {
            request += String.format("AND genre_name = '%s' ", genreComboBox.getSelectionModel().getSelectedItem().toString());
        }

        if(publisherComboBox.getSelectionModel().getSelectedIndex() > 0) {
            request += String.format("AND publisher_name = '%s' ", publisherComboBox.getSelectionModel().getSelectedItem().toString());
        }

        redrawGrid(request);
    }

    public void addNewBook() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addBook.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Каталог нижного магазина");
        stage.getIcons().add(new Image("file:src/main/resources/images/icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
