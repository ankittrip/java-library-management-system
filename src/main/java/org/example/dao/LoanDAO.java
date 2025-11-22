package org.example.dao;

import org.example.db.DBConnection;
import org.example.model.LoanRecord;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public void createLoan(int userId, int bookId) {
        String sql = "INSERT INTO loans (user_id, book_id) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creating loan: " + e.getMessage());
        }
    }

    public void closeLoan(int bookId) {
        String sql = "UPDATE loans SET returned_at = NOW() " +
                "WHERE book_id = ? AND returned_at IS NULL";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error closing loan: " + e.getMessage());
        }
    }

    public List<LoanRecord> getActiveLoans() {
        List<LoanRecord> list = new ArrayList<>();
        String sql =
                "SELECT l.id, l.user_id, l.book_id, u.name, b.title, l.issued_at, l.returned_at " +
                        "FROM loans l " +
                        "JOIN users u ON u.id = l.user_id " +
                        "JOIN books b ON b.id = l.book_id " +
                        "WHERE l.returned_at IS NULL";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new LoanRecord(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getTimestamp("issued_at").toLocalDateTime(),
                        null
                ));
            }
        } catch (Exception e) {
            System.out.println("Error fetching loans: " + e.getMessage());
        }
        return list;
    }
}
