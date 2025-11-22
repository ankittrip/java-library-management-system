package org.example.service;

import org.example.dao.BookDAO;
import org.example.dao.LoanDAO;
import org.example.dao.UserDAO;
import org.example.model.Book;
import org.example.model.LoanRecord;
import org.example.model.User;

import java.util.List;

public class LibraryService {

    private final BookDAO bookDAO = new BookDAO();
    private final UserDAO userDAO = new UserDAO();
    private final LoanDAO loanDAO = new LoanDAO();

    public void addBook(String title, String author, Integer year) {
        bookDAO.addBook(title, author, year);
    }

    public void addUser(String name, String email) {
        userDAO.addUser(name, email);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public List<Book> searchBooks(String keyword) {
        return bookDAO.searchBooks(keyword);
    }

    public boolean issueBook(int userId, int bookId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            System.out.println("User not found with id " + userId);
            return false;
        }

        Book book = bookDAO.findById(bookId);
        if (book == null) {
            System.out.println("Book not found with id " + bookId);
            return false;
        }

        if (book.isIssued()) {
            System.out.println("Book is already issued to someone else.");
            return false;
        }

        // create loan + mark book issued
        loanDAO.createLoan(userId, bookId);
        bookDAO.updateIssuedStatus(bookId, true);
        System.out.println("✅ Book issued successfully to " + user.toString());
        return true;
    }

    public boolean returnBook(int bookId) {
        Book book = bookDAO.findById(bookId);
        if (book == null) {
            System.out.println("Book not found with id " + bookId);
            return false;
        }
        if (!book.isIssued()) {
            System.out.println("Book is already available.");
            return false;
        }

        loanDAO.closeLoan(bookId);
        bookDAO.updateIssuedStatus(bookId, false);
        System.out.println("Book returned successfully.");
        return true;
    }

    public List<LoanRecord> getActiveLoans() {
        return loanDAO.getActiveLoans();
    }
}
