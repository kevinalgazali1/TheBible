package com.example.bible.Model;

public class Book {

    int id;
    String book, type;

    public Book(int id, String book, String type) {
        this.id = id;
        this.book = book;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
