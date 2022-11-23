package de.myasar.mybookmanagement.gui;

import de.myasar.mybookmanagement.logic.BookHolder;
import de.myasar.mybookmanagement.model.Book;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for the Detail-Scene
 */
public class DetailController implements Initializable {
    //region Attributes
    @FXML
    private ChoiceBox<String> genreChoiceBox;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtPublishYear;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtBookCount;
    private Book selectedBook;

    private String[] genres = {
            "Action & Adventure",
            "Art",
            "Biography",
            "Business & Money",
            "Children's Book",
            "Classics",
            "Cooking",
            "Education & Teaching",
            "Family & Relationship",
            "Hobbies & Home",
            "Detective & Mystery",
            "Drama",
            "Fantasy",
            "Graphic Novel",
            "Health",
            "History",
            "Horror & Thriller",
            "Literary Fiction",
            "Personal Development",
            "Poetry",
            "Politics",
            "Romance",
            "Science-Fiction",
            "Spirituality",
            "Textbook",
            "Travel"
    };
    //endregion

    //region Methods
    /**
     * Sets the selected book and fills the labels with the values of its properties
     *
     * @param selectedBook : {@link Book} : Book selected at the Overview-Scene
     */
    public void setSelectedBookAndDetails(Book selectedBook) {
        this.selectedBook = selectedBook;

        if (selectedBook != null) {
            genreChoiceBox.setValue(selectedBook.getGenre());
            txtTitle.setText(selectedBook.getTitle());
            txtPublishYear.setText(String.valueOf(selectedBook.getPublishYear()));
            txtAuthor.setText(selectedBook.getAuthor());
            txtPrice.setText(String.valueOf(selectedBook.getPrice()));
            txtBookCount.setText(String.valueOf(selectedBook.getBookCount()));
        }
    }

    /**
     * Saves the entered book at the Details-Scene to the Book-list
     */
    @FXML
    private void saveBook() {
        if (selectedBook != null) {
            // Edit selected book
            if (choiceBoxIsNotBlankEmptyOrEqual(selectedBook.getGenre()))
                selectedBook.setGenre(genreChoiceBox.getValue());

            if (textFieldIsNotBlankEmptyOrEqual(txtTitle, selectedBook.getTitle()))
                selectedBook.setTitle(txtTitle.getText());

            if (textFieldIsNotBlankEmptyOrEqual(txtPublishYear, String.valueOf(selectedBook.getPublishYear())))
                selectedBook.setPublishYear(Integer.parseInt(txtPublishYear.getText()));

            if (textFieldIsNotBlankEmptyOrEqual(txtAuthor, selectedBook.getAuthor()))
                selectedBook.setAuthor(txtAuthor.getText());

            if (textFieldIsNotBlankEmptyOrEqual(txtPrice, String.valueOf(selectedBook.getPrice())))
                selectedBook.setPrice(Double.parseDouble(txtPrice.getText()));

            if (textFieldIsNotBlankEmptyOrEqual(txtBookCount, String.valueOf(selectedBook.getBookCount())))
                selectedBook.setBookCount(Integer.parseInt(txtBookCount.getText()));

        } else {
            // Create a new book
            Book newBook = new Book(
                    genreChoiceBox.getValue(),
                    txtTitle.getText(),
                    Integer.parseInt(txtPublishYear.getText()),
                    txtAuthor.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtBookCount.getText())
            );

            // add the new book
            BookHolder.getInstance().getBooks().add(newBook);
        }

        switchToOverviewScene();
    }

    /**
     * Calls Confirmation-Scene in order to delete the book
     */
    @FXML
    private void deleteBook() {
        if (selectedBook != null) {
            SceneManager.getInstance().switchToConfirmationScene(selectedBook);
        }
    }

    /**
     * Produce a previously defined sample book to sure whether the CRUD methods work properly
     */
    @FXML
    private void createTestData() {
        // Create a test book
        Book testBook = new Book(
                genres[0],
                "testTitle",
                2222,
                "testAuthor",
                9.99,
                10
        );
        SceneManager.getInstance().switchToDetailScene(testBook);
        BookHolder.getInstance().getBooks().add(testBook);
    }

    /**
     * Loads Overview-Scene
     */
    @FXML
    private void switchToOverviewScene() {
        SceneManager.getInstance().switchToOverviewScene();
    }

    /**
     * Checks if any attribute of a Book sample is blank
     */
    private boolean textFieldIsNotBlankEmptyOrEqual(TextField textField, String attributeValue) {
        return !textField.getText().isBlank() &&
                !textField.getText().isEmpty() &&
                !textField.getText().equals(attributeValue);
    }

    /**
     * Checks if genre attribute of a Book sample is blank
     */
    private boolean choiceBoxIsNotBlankEmptyOrEqual(String attributeValue) {
        return !genreChoiceBox.getValue().isBlank() &&
                !genreChoiceBox.getValue().isEmpty() &&
                !genreChoiceBox.getValue().equals(attributeValue);
    }

    public void getGenre(ActionEvent event){
        String currentGenre = genreChoiceBox.getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genreChoiceBox.getItems().addAll(genres);
        genreChoiceBox.setOnAction(this::getGenre);
    }
    //endregion
}
