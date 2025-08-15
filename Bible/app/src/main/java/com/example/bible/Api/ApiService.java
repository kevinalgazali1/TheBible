package com.example.bible.Api;

import com.example.bible.Model.Book;
import com.example.bible.Model.Chapter;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({
            "X-RapidAPI-Key: 061787ae7bmshe04c585b9943059p1b9411jsn904164397ee8",
            "X-RapidAPI-Host: catholic-bible.p.rapidapi.com"
    })
    @GET("bible/books")
    Call<List<Book>> getBooks();

    @Headers({
            "X-RapidAPI-Key: 061787ae7bmshe04c585b9943059p1b9411jsn904164397ee8",
            "X-RapidAPI-Host: catholic-bible.p.rapidapi.com"
    })
    @GET("bible/chapter")
    Call<ChapterResponse> getChapter(@Query("book_id") int book_id, @Query("chapter_num") int chapter_num);
}