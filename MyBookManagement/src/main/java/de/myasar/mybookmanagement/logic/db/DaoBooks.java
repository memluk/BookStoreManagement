package de.myasar.mybookmanagement.logic.db;

import de.myasar.mybookmanagement.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for books.
 * Provides methods for CRUD (creating, reading, editing, deleting) operations of books in the database.
 */
public class DaoBooks implements Dao<Book> {
    //region Constants
    public static final String TBL_NAME = "books";

    public static final String COL_ID = "pk_id";
    public static final String COL_GENRE = "genre";
    public static final String COL_TITLE = "title";
    public static final String COL_PUBLISH_YEAR = "year";
    public static final String COL_AUTHOR = "author";
    public static final String COL_PRICE = "price";
    public static final String COL_BOOK_COUNT = "count";
    public static final String STATEMENT_INSERT_BOOK = "INSERT INTO " + TBL_NAME + " (genre, title, year, author, price, count) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String STATEMENT_SELECT_ALL_BOOKS = "SELECT * FROM " + TBL_NAME;
    public static final String STATEMENT_DELETE_BOOK = "DELETE FROM " + TBL_NAME + " WHERE pk_id = ?";
    public static final String STATEMENT_UPDATE_BOOK = "UPDATE " + TBL_NAME + " SET genre = ?, title = ?, year = ?, author = ?, price = ?, count = ? WHERE pk_id = ?";
    //endregion

    //region Methods
    @Override
    public void create(Connection dbConnection, Book book) {
        PreparedStatement statement = null;

        try {

            statement = dbConnection.prepareStatement(STATEMENT_INSERT_BOOK, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getGenre());
            statement.setString(2, book.getTitle());
            statement.setInt(3, book.getPublishYear());
            statement.setString(4, book.getAuthor());
            statement.setDouble(5, book.getPrice());
            statement.setInt(6, book.getBookCount());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            int insertId;

            if (resultSet.next()) {
                insertId = resultSet.getInt("insert_id");
                book.setId(insertId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Book> readAll(Connection dbConnection) {
        List<Book> books = new ArrayList<>();

        Statement statement = null;

        try {

            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(STATEMENT_SELECT_ALL_BOOKS);

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getString(COL_GENRE),
                        resultSet.getString(COL_TITLE),
                        resultSet.getInt(COL_PUBLISH_YEAR),
                        resultSet.getString(COL_AUTHOR),
                        resultSet.getDouble(COL_PRICE),
                        resultSet.getInt(COL_BOOK_COUNT)
                );
                book.setId(resultSet.getInt(COL_ID));

                books.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return books;
    }

    @Override
    public Book read(Connection dbConnection, int id) {
        return null;
    }

    @Override
    public void update(Connection dbConnection, Book book) {
        PreparedStatement statement = null;

        try {

            statement = dbConnection.prepareStatement(STATEMENT_UPDATE_BOOK);
            statement.setString(1, book.getGenre());
            statement.setString(2, book.getTitle());
            statement.setInt(3, book.getPublishYear());
            statement.setString(4, book.getAuthor());
            statement.setInt(5, book.getBookCount());
            statement.setInt(6, book.getId());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Connection dbConnection, Book book) {
        PreparedStatement statement = null;

        try {

            statement = dbConnection.prepareStatement(STATEMENT_DELETE_BOOK);
            statement.setInt(1, book.getId());

            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    //endregion
}
