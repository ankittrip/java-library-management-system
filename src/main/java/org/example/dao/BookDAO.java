package org.example.dao;

import org.example.db.DBConnection;
import org.example.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public void addBook(String title, String author, Integer year) {
        String sql = "INSERT INTO books (title, author, year, is_issued) VALUES (?, ?, ?, FALSE)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, author);
            if (year == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, year);
            }
            ps.executeUpdate();
            System.out.println("Book added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT id, title, author, year, is_issued FROM books ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        (Integer) rs.getObject("year"),
                        rs.getBoolean("is_issued")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error fetching books: " + e.getMessage());
        }
        return list;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT id, title, author, year, is_issued FROM books " +
                "WHERE title LIKE ? OR author LIKE ? ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            (Integer) rs.getObject("year"),
                            rs.getBoolean("is_issued")
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Error searching books: " + e.getMessage());
        }
        return list;
    }

    public Book findById(int id) {
        String sql = "SELECT id, title, author, year, is_issued FROM books WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            (Integer) rs.getObject("year"),
                            rs.getBoolean("is_issued")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error finding book: " + e.getMessage());
        }
        return null;
    }

    public void updateIssuedStatus(int bookId, boolean issued) {
        String sql = "UPDATE books SET is_issued = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, issued);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating book status: " + e.getMessage());
        }
    }
}
