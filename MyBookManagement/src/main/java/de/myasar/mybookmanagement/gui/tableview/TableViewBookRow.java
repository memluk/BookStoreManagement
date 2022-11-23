package de.myasar.mybookmanagement.gui.tableview;

import de.myasar.mybookmanagement.model.Book;
import de.myasar.mybookmanagement.settings.AppTexts;

import javafx.scene.control.TableRow;

/**
 * Defines a row as it should be displayed in the TableView.
 */
public class TableViewBookRow extends TableRow<Book> {
    //region Methods

    /**
     * Updates the content of a cell and displays it in the implemented way.
     * The cells are built by {@link TableViewBookRowFactory}.
     *
     * @param bookToShow : {@link Book} : Displayed Book
     * @param isEmpty : boolean: Indicates whether row should be empty or not
     */
    @Override
    protected void updateItem(Book bookToShow, boolean isEmpty) {
        super.updateItem(bookToShow, isEmpty);

        if (isEmpty && bookToShow == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(String.format(
                    AppTexts.BOOK_FORMAT_STRING,
                    bookToShow.getId(),
                    bookToShow.getGenre(),
                    bookToShow.getTitle(),
                    bookToShow.getPublishYear(),
                    bookToShow.getAuthor(),
                    bookToShow.getPrice(),
                    bookToShow.getBookCount()
            ));
        }
    }
    //endregion
}
