package de.myasar.mybookmanagement.gui;

import de.myasar.mybookmanagement.logic.BookHolder;
import de.myasar.mybookmanagement.model.Book;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Control logic for the Confirmation-Scene
 */
public class ConfirmationController {
    //region Attributes
    @FXML
    private Label idTxtLabel;
    @FXML
    private Label genreTxtLabel;
    @FXML
    private Label titleTxtLabel;
    @FXML
    private Label publishYearTxtLabel;
    @FXML
    private Label authorTxtLabel;
    @FXML
    private Label priceTxtLabel;
    @FXML
    private Label bookCountTxtLabel;

    private Book selectedBook;
    //endregion

    //region Methods
    /**
     * Sets the selected book and fills the text fields with the values of its properties
     *
     * @param selectedBook : {@link Book} : Book selected in the overview
     */
    public void setSelectedBookAndDetails(Book selectedBook) {
        this.selectedBook = selectedBook;

        if (selectedBook != null) {
            idTxtLabel.setText(String.valueOf(selectedBook.getId()));
            genreTxtLabel.setText(selectedBook.getGenre());
            titleTxtLabel.setText(selectedBook.getTitle());
            publishYearTxtLabel.setText(String.valueOf(selectedBook.getPublishYear()));
            authorTxtLabel.setText(selectedBook.getAuthor());
            priceTxtLabel.setText(String.valueOf(selectedBook.getPrice()));
            bookCountTxtLabel.setText(String.valueOf(selectedBook.getBookCount()));
        }
    }

    /**
     * Deletes the book from the list
     */
    @FXML
    private void deleteBook() {
        if (selectedBook != null) {
            BookHolder.getInstance().getBooks().remove(selectedBook);
            switchToOverviewScene();
        }
    }

    @FXML
    private void switchToOverviewScene() {
        SceneManager.getInstance().switchToOverviewScene();
    }
    //endregion
}
