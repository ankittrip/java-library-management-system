package org.example;

import org.example.db.DBConnection;
import org.example.model.Book;
import org.example.model.LoanRecord;
import org.example.service.LibraryService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Quick DB check
        try (Connection ignored = DBConnection.getConnection()) {
            System.out.println("Connected to MySQL successfully!");
        } catch (Exception e) {
            System.out.println("MySQL connection failed: " + e.getMessage());
            return;
        }

        LibraryService service = new LibraryService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. View All Books");
            System.out.println("4. Search Books");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Active Loans");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter author: ");
                    String author = sc.nextLine();

                    System.out.print("Enter year (or blank to skip): ");
                    String yearStr = sc.nextLine();
                    Integer year = yearStr.isBlank() ? null : Integer.parseInt(yearStr);

                    service.addBook(title, author, year);
                }
                case 2 -> {
                    System.out.print("Enter user name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter email (optional): ");
                    String email = sc.nextLine();
                    if (email.isBlank()) email = null;

                    service.addUser(name, email);
                }
                case 3 -> {
                    List<Book> books = service.getAllBooks();
                    if (books.isEmpty()) {
                        System.out.println("No books found.");
                    } else {
                        books.forEach(System.out::println);
                    }
                }
                case 4 -> {
                    System.out.print("Enter keyword (title/author): ");
                    String keyword = sc.nextLine();
                    List<Book> books = service.searchBooks(keyword);
                    if (books.isEmpty()) {
                        System.out.println("No matching books.");
                    } else {
                        books.forEach(System.out::println);
                    }
                }
                case 5 -> {
                    System.out.print("Enter user ID: ");
                    int userId = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter book ID: ");
                    int bookId = Integer.parseInt(sc.nextLine());
                    service.issueBook(userId, bookId);
                }
                case 6 -> {
                    System.out.print("Enter book ID to return: ");
                    int bookId = Integer.parseInt(sc.nextLine());
                    service.returnBook(bookId);
                }
                case 7 -> {
                    List<LoanRecord> loans = service.getActiveLoans();
                    if (loans.isEmpty()) {
                        System.out.println("No active loans.");
                    } else {
                        loans.forEach(System.out::println);
                    }
                }
                case 8 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }
}
