package org.example.model;

import java.time.LocalDateTime;

public class LoanRecord {
    private int id;
    private int userId;
    private int bookId;
    private String userName;
    private String bookTitle;
    private LocalDateTime issuedAt;
    private LocalDateTime returnedAt;

    public LoanRecord(int id, int userId, int bookId,
                      String userName, String bookTitle,
                      LocalDateTime issuedAt, LocalDateTime returnedAt) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.issuedAt = issuedAt;
        this.returnedAt = returnedAt;
    }

    @Override
    public String toString() {
        return "Loan #" + id + " | User: " + userName +
                " | Book: " + bookTitle +
                " | Issued: " + issuedAt +
                " | Returned: " + (returnedAt == null ? "Not yet" : returnedAt);
    }
}
