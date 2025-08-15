package com.example.bible;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bible.Api.ApiService;
import com.example.bible.Api.RetrofitClient;
import com.example.bible.Api.ChapterResponse;
import com.example.bible.Model.Chapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterActivity extends AppCompatActivity {

    private LottieAnimationView loading;
    private TextView tvChapter;
    private TextInputEditText chapterInput;
    private Button btn, btnBack;
    private ApiService apiService;
    private int bookId;
    private String bookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        chapterInput = findViewById(R.id.chapterInput);
        tvChapter = findViewById(R.id.tvChapter);
        btn = findViewById(R.id.btnChapter);
        btnBack = findViewById(R.id.btnBackChapter);
        loading = findViewById(R.id.loadingchapter);

        bookTitle = getIntent().getStringExtra("book_title");
        bookId = getIntent().getIntExtra("book_id", -1);

        // Atur teks TextView
        tvChapter.setText(bookTitle);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chapterNumber = chapterInput.getText().toString();
                if (!chapterNumber.isEmpty()) {
                    fetchChapter(bookId, Integer.parseInt(chapterNumber));
                } else {
                    Toast.makeText(ChapterActivity.this, "Please enter a chapter number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChapterActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void fetchChapter(int book_id, int chapter_num) {
        loading.setVisibility(View.VISIBLE);

        Call<ChapterResponse> call = apiService.getChapter(book_id, chapter_num);
        call.enqueue(new Callback<ChapterResponse>() {
            @Override
            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    ChapterResponse chapterResponse = response.body();
                    if (chapterResponse != null && chapterResponse.getChapter() != null) {
                        displayChapter(chapterResponse.getChapter(), chapter_num);
                    } else {
                        Toast.makeText(ChapterActivity.this, "Chapter not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChapterActivity.this, "Failed to get chapter", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChapterResponse> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(ChapterActivity.this, "Failed to get chapter", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayChapter(Chapter chapter, int chapter_num) {
        Intent intent = new Intent(ChapterActivity.this, DetailChapterActivity.class);
        intent.putExtra("chapter_num", chapter_num);
        intent.putStringArrayListExtra("chapter_text", new ArrayList<>(chapter.getChapter_text()));
        startActivity(intent);
    }
}