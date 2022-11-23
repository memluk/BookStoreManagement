package de.myasar.mybookmanagement.logic;

import de.myasar.mybookmanagement.logic.db.DbManager;
import de.myasar.mybookmanagement.model.Book;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Class that provides all the books as an ObservableList
 */

// Singleton class that holds an observable list of books
public class BookHolder {
    //region Attributes
    private static BookHolder instance;
    private ObservableList<Book> books;
    private ListChangeListener<Book> bookListChangeListener;
    //endregion

    //region Constructor
    /**
     * ObservableList in the constructor
     */
    private BookHolder() {
        /**
         * Create an empty observable list that detects the changes based on the observables of its elements.
         */
        books = FXCollections.observableArrayList(book ->
                new Observable[] {book.genreProperty(), book.titleProperty(),
                        book.publishYearProperty(), book.authorProperty(),
                        book.priceProperty(), book.bookCountProperty()
                });

        // adds all books to the arraylist of Book
        books.addAll(DbManager.getInstance().readAllDataRecords());

        // Checks if there is any update (adding, removing or editing books) in the list
        bookListChangeListener = new ListChangeListener<Book>() {
            @Override
            public void onChanged(Change<? extends Book> change) {
//                System.out.println(change);

                while (change.next()) {

                    if (change.wasAdded()) {
                        // Read the book and add it to the database
                        Book bookToInsert = change.getAddedSubList().get(0);
                        DbManager.getInstance().insertDataRecord(bookToInsert);

                    } else if (change.wasRemoved()) {
                        // Read the book and delete it from the database
                        Book bookToDelete = change.getRemoved().get(0);
                        DbManager.getInstance().deleteDataRecord(bookToDelete);

                    } else if (change.wasUpdated()) {
                        // Read the book and update it in the database
                        int updatedIndex = change.getFrom();
                        Book bookToUpdate = change.getList().get(updatedIndex);
                        DbManager.getInstance().updateDataRecord(bookToUpdate);
                    }
                }
            }
        };

        addListener();
    }
    //endregion

    //region Methods
    public static synchronized BookHolder getInstance() {
        if (instance == null) instance = new BookHolder();
        return instance;
    }

    public ObservableList<Book> getBooks() {
        return books;
    }

    public void addListener() {
        books.addListener(bookListChangeListener);
    }
    //endregion
}
