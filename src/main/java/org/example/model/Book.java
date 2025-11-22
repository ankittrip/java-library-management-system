package org.example.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private Integer year;
    private boolean issued;

    public Book(int id, String title, String author, Integer year, boolean issued) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.issued = issued;
    }

    public int getId() {
        return id;
    }

    public boolean isIssued() {
        return issued;
    }

    @Override
    public String toString() {
        String yearStr = (year == null) ? "-" : year.toString();
        return id + " | " + title + " | " + author + " | " + yearStr + " | "
                + (issued ? "Issued" : "Available");
    }
}
