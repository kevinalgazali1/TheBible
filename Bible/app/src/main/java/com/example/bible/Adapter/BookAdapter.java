package com.example.bible.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bible.Model.Book;
import com.example.bible.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void addBooks(List<Book> books) {
        if (books != null && !books.isEmpty()) {
            bookList.addAll(books);
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setBooks(List<Book> filteredList) {
        bookList.clear(); // Clear the previous book list
        bookList.addAll(filteredList); // Add all items from the new book list
        notifyDataSetChanged(); // Update the RecyclerView view
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView bookName, bookType;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookType = itemView.findViewById(R.id.tv_type);
            bookName = itemView.findViewById(R.id.tv_book);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        Book book = bookList.get(position);
                        onItemClickListener.onItemClick(book);
                    }
                }
            });
        }

        public void bind(Book book) {
            bookName.setText(book.getBook());
            bookType.setText(book.getType());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
}
