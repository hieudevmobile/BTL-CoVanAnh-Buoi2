package com.example.app_buoi2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_buoi2.auth.AuthPreferences;
import com.example.app_buoi2.data.AppDatabase;
import com.example.app_buoi2.data.model.TicketRow;
import com.example.app_buoi2.ui.TicketAdapter;
import com.example.app_buoi2.ui.ToolbarHelper;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MyTicketsActivity extends AppCompatActivity {

    private TextView empty;
    private MaterialButton goLogin;
    private RecyclerView rv;
    private TicketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_tickets);
        View root = findViewById(R.id.myTicketsRoot);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.my_tickets_title);
        ToolbarHelper.setupBack(toolbar, this);

        empty = findViewById(R.id.textEmpty);
        goLogin = findViewById(R.id.buttonGoLogin);
        rv = findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TicketAdapter();
        rv.setAdapter(adapter);

        goLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUi();
    }

    private void refreshUi() {
        if (!AuthPreferences.isLoggedIn(this)) {
            rv.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            empty.setText(R.string.my_tickets_need_login);
            goLogin.setVisibility(View.VISIBLE);
            adapter.submit(List.of());
            return;
        }

        rv.setVisibility(View.VISIBLE);
        goLogin.setVisibility(View.GONE);

        long userId = AuthPreferences.getUserId(this);
        AppDatabase.execute(
                () -> {
                    List<TicketRow> rows =
                            AppDatabase.getInstance(this).ticketDao().getTicketsForUser(userId);
                    runOnUiThread(
                            () -> {
                                adapter.submit(rows);
                                empty.setVisibility(rows.isEmpty() ? View.VISIBLE : View.GONE);
                                if (rows.isEmpty()) {
                                    empty.setText(R.string.my_tickets_empty);
                                }
                            });
                });
    }
}
