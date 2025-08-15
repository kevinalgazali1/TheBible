package com.example.bible;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bible.Adapter.DetailAdapter;

import java.util.List;

public class DetailChapterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chapter);

        recyclerView = findViewById(R.id.rv_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int chapterNum = getIntent().getIntExtra("chapter_num", -1);
        List<String> chapterText = getIntent().getStringArrayListExtra("chapter_text");

        if (chapterText != null) {
            adapter = new DetailAdapter(chapterText);
            recyclerView.setAdapter(adapter);
        }
    }
}
