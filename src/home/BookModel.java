package home;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookModel {
    Connection conn = null;
    private ObservableList<BookData> bookData;

    public BookModel(){
        this.conn = dbConnection.getConnection();

        if(this.conn == null){
            System.exit(0);
        }
    }

    public ObservableList<BookData> getBookList(){
        String query = "SELECT * FROM book_tbl ORDER BY title ASC";

        try {
            this.bookData = FXCollections.observableArrayList();

            ResultSet resultSet = conn.createStatement().executeQuery(query);

            while(resultSet.next()){
                this.bookData.add( new BookData(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
                ));
            }

            return bookData;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    //Search
    public ObservableList<BookData> searchBookList(String search){
        
        String query = "SELECT * FROM book_tbl WHERE LOWER(title) LIKE ?";
        
        PreparedStatement statement = null;

        try {

            this.bookData = FXCollections.observableArrayList();

            statement = conn.prepareStatement(query);

            statement.setString(1, "%" + search.toLowerCase() + "%");

            statement.executeQuery();

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                this.bookData.add( new BookData(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
                ));
            }

            return bookData;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Request
    public ObservableList<BookData> requestBookList(String user){
        String query = "SELECT * FROM book_tbl WHERE student = ?";
        PreparedStatement statement = null;

        try {

            this.bookData = FXCollections.observableArrayList();

            statement = conn.prepareStatement(query);

            statement.setString(1, user);

            statement.executeQuery();

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                this.bookData.add( new BookData(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
                ));
            }

            return bookData;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Pending
    public ObservableList<BookData> pendingBookList(){
        
        String query = "SELECT * FROM book_tbl WHERE availability = ?";
        
        PreparedStatement statement = null;

        try {

            this.bookData = FXCollections.observableArrayList();

            statement = conn.prepareStatement(query);

            statement.setString(1, "unavailable");

            statement.executeQuery();

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                this.bookData.add( new BookData(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
                ));
            }

            return bookData;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Add
    public void addBook(String title, String author, String year, String descripton, String image) {
        String query = "INSERT INTO book_tbl (title, author, year, description, image) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setInt(3, Integer.parseInt(year));
            statement.setString(4, descripton);
            statement.setString(5, image);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
