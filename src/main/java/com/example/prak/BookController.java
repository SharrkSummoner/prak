package com.example.prak;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class BookController {
    @FXML
    private AnchorPane anchor;

    @FXML
    private Label bookAuthor;

    @FXML
    private Label bookCount;

    @FXML
    private ImageView bookImage;

    @FXML
    public Label bookTitle;

    @FXML
    private Label bookPrice;

    @FXML
    private Label bookPublisher;

    @FXML
    private Label bookGenre;

    private Book book;

    public void updateData(Book book) {
        this.book = book;
        bookTitle.setText(book.getBookTitle());
        bookAuthor.setText(book.getAuthor());
        bookCount.setText(book.getCount());
        bookPrice.setText(book.getPrice());
        bookImage.setImage(book.getImage());
        bookPublisher.setText(book.getPublisher());
        bookGenre.setText(book.getGenre());
    }
}
