package com.example.app_buoi2;

import android.content.Intent;
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
import com.example.app_buoi2.data.model.ShowtimeRow;
import com.example.app_buoi2.ui.ShowtimeAdapter;
import com.example.app_buoi2.ui.ToolbarHelper;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class ShowtimeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_showtime_list);
        View root = findViewById(R.id.listRoot);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.showtimes_title);
        ToolbarHelper.setupBack(toolbar, this);

        RecyclerView rv = findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ShowtimeAdapter adapter =
                new ShowtimeAdapter(
                        row -> {
                            Intent i = new Intent(this, BookTicketActivity.class);
                            i.putExtra(BookTicketActivity.EXTRA_SHOWTIME_ID, row.showtimeId);
                            startActivity(i);
                        });
        rv.setAdapter(adapter);

        AppDatabase.execute(
                () -> {
                    List<ShowtimeRow> list =
                            AppDatabase.getInstance(this).showtimeDao().getAllWithDetails();
                    runOnUiThread(() -> adapter.submit(list));
                });
    }
}
