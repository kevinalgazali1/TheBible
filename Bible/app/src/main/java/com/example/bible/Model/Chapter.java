package com.example.bible.Model;

import java.util.List;

public class Chapter {
    int book_id;
    int chapter_num;
    String book;
    List<String> chapter_text;

    public Chapter(int book_id, int chapter_num, String book, List<String> chapter_text) {
        this.book_id = book_id;
        this.chapter_num = chapter_num;
        this.book = book;
        this.chapter_text = chapter_text;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getChapter_num() {
        return chapter_num;
    }

    public void setChapter_num(int chapter_num) {
        this.chapter_num = chapter_num;
    }

    public List<String> getChapter_text() {
        return chapter_text;
    }

    public void setChapter_text(List<String> chapter_text) {
        this.chapter_text = chapter_text;
    }
}
