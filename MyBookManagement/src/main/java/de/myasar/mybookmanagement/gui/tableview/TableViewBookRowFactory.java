package de.myasar.mybookmanagement.gui.tableview;

import de.myasar.mybookmanagement.model.Book;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * Builds the TableView using the row {@link TableViewBookRow} which is defined here
 */
public class TableViewBookRowFactory implements Callback<TableView<Book>, TableRow<Book>> {

    //region Methods
    /**
     * Builds and returns a new row for each element of the ObservableList of the TableView.
     *
     * @param bookTableView : {@link TableView <Book>} : TableView with the list of books
     * @return {@link TableViewBookRow}: Object of own row class
     */
    @Override
    public TableRow<Book> call(TableView<Book> bookTableView) {
        return new TableViewBookRow();
    }
    //endregion
}
