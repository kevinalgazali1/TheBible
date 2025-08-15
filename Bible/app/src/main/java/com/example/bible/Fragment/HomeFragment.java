package com.example.bible.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bible.Adapter.BookAdapter;
import com.example.bible.Api.ApiService;
import com.example.bible.Api.RetrofitClient;
import com.example.bible.Model.Book;
import com.example.bible.R;
import com.example.bible.ChapterActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ApiService apiService;
    private ExecutorService executorService;
    private Handler mainThreadHandler;
    private LottieAnimationView loading;
    LinearLayout llNoInt;
    TextView tvNoInt;
    ImageView ivNoInt;
    Button btnNoInt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiService = RetrofitClient.getClient().create(ApiService.class);
        recyclerView = view.findViewById(R.id.rv_book);
        loading = view.findViewById(R.id.loadinghome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        llNoInt = view.findViewById(R.id.llNoIntHome);
        tvNoInt = view.findViewById(R.id.tvNoIntHome);
        ivNoInt = view.findViewById(R.id.ivNoIntHome);
        btnNoInt = view.findViewById(R.id.btnNoIntHome);

        btnNoInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                llNoInt.setVisibility(View.GONE);
                loadData();
            }
        });

        bookAdapter = new BookAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(bookAdapter);

        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(getContext(), ChapterActivity.class);
                intent.putExtra("book_id", book.getId());
                intent.putExtra("book_title", book.getBook());
                startActivity(intent);
            }
        });

        executorService = Executors.newSingleThreadExecutor();
        mainThreadHandler = new Handler(Looper.getMainLooper());

        loading.setVisibility(View.VISIBLE);
        loadData();
    }

    public void loadData() {
        recyclerView.setVisibility(View.GONE);
        llNoInt.setVisibility(View.GONE);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Call<List<Book>> call = apiService.getBooks();
                try {
                    Response<List<Book>> response = call.execute();
                    if (response.isSuccessful() && response.body() != null) {
                        List<Book> bookList = response.body();
                        mainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                bookAdapter.addBooks(bookList);
                                recyclerView.setVisibility(View.VISIBLE);
                                llNoInt.setVisibility(View.GONE);
                                loading.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        Log.e("HomeFragment", "Request failed with code: " + response.code());
                        mainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                llNoInt.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                loading.setVisibility(View.GONE);
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.e("HomeFragment", "Request failed: " + e.getMessage());
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            llNoInt.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
