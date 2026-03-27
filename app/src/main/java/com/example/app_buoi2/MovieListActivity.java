package com.example.app_buoi2;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_buoi2.data.AppDatabase;
import com.example.app_buoi2.data.entity.Movie;
import com.example.app_buoi2.ui.MovieAdapter;
import com.example.app_buoi2.ui.ToolbarHelper;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_list);
        View root = findViewById(R.id.listRoot);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.movies_title);
        ToolbarHelper.setupBack(toolbar, this);

        RecyclerView rv = findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        MovieAdapter adapter = new MovieAdapter();
        rv.setAdapter(adapter);

        AppDatabase.execute(
                () -> {
                    List<Movie> list = AppDatabase.getInstance(this).movieDao().getAll();
                    runOnUiThread(() -> adapter.submit(list));
                });
    }
}
