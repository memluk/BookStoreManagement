package de.myasar.mybookmanagement.gui;

import de.myasar.mybookmanagement.gui.tableview.TableViewBookRowFactory;
import de.myasar.mybookmanagement.logic.BookHolder;
import de.myasar.mybookmanagement.model.Book;
import de.myasar.mybookmanagement.settings.AppSettings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Control logic for the overview scene
 */
public class OverviewController implements Initializable {

    //region Attributes
    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, Integer> col_id;
    @FXML
    private TableColumn<Book,String> col_genre;
    @FXML
    private TableColumn<Book,String> col_title;
    @FXML
    private TableColumn<Book, Integer> col_year;
    @FXML
    private TableColumn<Book,String> col_author;
    @FXML
    private TableColumn<Book, Double> col_price;
    @FXML
    private TableColumn<Book, Integer> col_count;
    //endregion

    //region Methods
    /**
     * Called before opening a scene to initialize the scene's controller.
     *
     * @param url : {@link URL} : Location to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle : {@link ResourceBundle} : to locate the object, or null if the object is not located.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Connecting the FXML fields to Java code
        col_id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("Id"));
        col_genre.setCellValueFactory(new PropertyValueFactory<Book, String>("Genre"));
        col_title.setCellValueFactory(new PropertyValueFactory<Book, String>("Title"));
        col_year.setCellValueFactory(new PropertyValueFactory<Book, Integer>("PublishYear"));
        col_author.setCellValueFactory(new PropertyValueFactory<Book, String>("Author"));
        col_price.setCellValueFactory(new PropertyValueFactory<Book, Double>("Price"));
        col_count.setCellValueFactory(new PropertyValueFactory<Book, Integer>("BookCount"));

        // Assign the row factory
        bookTableView.setRowFactory(new TableViewBookRowFactory());

        // Fill tableview with elements (ObservableList)
        bookTableView.getItems().setAll(BookHolder.getInstance().getBooks());

        // Refreshes the table after scrolling to prevent view loss
        bookTableView.addEventFilter(ScrollEvent.ANY, scrollEvent -> {
            bookTableView.refresh();
        });

        // controls and checks the mouse events
        bookTableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == AppSettings.DETAIL_CLICK_COUNT &&
                    mouseEvent.getButton() == MouseButton.PRIMARY) {
                Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
                SceneManager.getInstance().switchToDetailScene(selectedBook);
            }
        });

        // controls and checks the keyboard events
        bookTableView.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
                SceneManager.getInstance().switchToDetailScene(selectedBook);
            }
        });
    }

    /**
     * triggered by clicking ADD button at Overview-Scene.
     * Opens a blank Details-Scene for adding a new book.
     */
    @FXML
    private void switchToDetailSceneForNewBook() {
        SceneManager.getInstance().switchToDetailScene(null);
    }

    /**
     * triggered by clicking EDIT button at Overview-Scene.
     * Opens a Details-Scene of a selected book for changing the data.
     */
    @FXML
    private void editBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        SceneManager.getInstance().switchToDetailScene(selectedBook);

        bookTableView.setItems(BookHolder.getInstance().getBooks());
    }

    /**
     * triggered by clicking DELETE button at Overview-Scene.
     * Opens a Confirmation-Scene of a selected book to exhibit the data to be sure to delete the book.
     */
    @FXML
    private void deleteBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        SceneManager.getInstance().switchToConfirmationScene(selectedBook);

        bookTableView.setItems(BookHolder.getInstance().getBooks());
    }

    //endregion
}