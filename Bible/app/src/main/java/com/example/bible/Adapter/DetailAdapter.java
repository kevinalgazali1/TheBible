package com.example.bible.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bible.R;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private List<String> chapterTextList;

    public DetailAdapter(List<String> chapterTextList) {
        this.chapterTextList = chapterTextList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String verseText = chapterTextList.get(position);
        holder.tvVerseText.setText(verseText);
    }

    @Override
    public int getItemCount() {
        return chapterTextList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvChapterDetail, tvVerseNum, tvVerseText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVerseText = itemView.findViewById(R.id.tv_verse);
            tvVerseNum = itemView.findViewById(R.id.tv_verseNumber);
            tvChapterDetail = itemView.findViewById(R.id.tvChapterDetail);
        }
    }
}
