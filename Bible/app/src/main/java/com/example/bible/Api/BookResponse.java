package com.example.bible.Api;

import com.example.bible.Model.Book;

import java.util.List;

public class BookResponse {

    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
