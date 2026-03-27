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

import com.example.app_buoi2.auth.AuthPreferences;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private TextView textWelcome;
    private MaterialButton btnAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        View main = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textWelcome = findViewById(R.id.textWelcome);
        btnAuth = findViewById(R.id.buttonAuth);

        findViewById(R.id.buttonMovies)
                .setOnClickListener(v -> startActivity(new Intent(this, MovieListActivity.class)));
        findViewById(R.id.buttonTheaters)
                .setOnClickListener(v -> startActivity(new Intent(this, TheaterListActivity.class)));
        findViewById(R.id.buttonShowtimes)
                .setOnClickListener(v -> startActivity(new Intent(this, ShowtimeListActivity.class)));
        findViewById(R.id.buttonMyTickets)
                .setOnClickListener(v -> startActivity(new Intent(this, MyTicketsActivity.class)));

        btnAuth.setOnClickListener(
                v -> {
                    if (AuthPreferences.isLoggedIn(this)) {
                        AuthPreferences.logout(this);
                        refreshAuthUi();
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAuthUi();
    }

    private void refreshAuthUi() {
        if (AuthPreferences.isLoggedIn(this)) {
            String name = AuthPreferences.getUsername(this);
            textWelcome.setText(getString(R.string.home_welcome_user, name));
            btnAuth.setText(R.string.action_logout);
        } else {
            textWelcome.setText(R.string.home_welcome_guest);
            btnAuth.setText(R.string.action_login);
        }
    }
}
