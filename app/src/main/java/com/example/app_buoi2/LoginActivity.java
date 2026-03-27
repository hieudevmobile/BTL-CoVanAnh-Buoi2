package com.example.app_buoi2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_buoi2.auth.AuthPreferences;
import com.example.app_buoi2.data.AppDatabase;
import com.example.app_buoi2.data.entity.User;
import com.example.app_buoi2.ui.ToolbarHelper;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        View root = findViewById(R.id.loginRoot);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.login_title);
        ToolbarHelper.setupBack(toolbar, this);

        TextInputEditText user = findViewById(R.id.inputUsername);
        TextInputEditText pass = findViewById(R.id.inputPassword);
        MaterialButton btn = findViewById(R.id.buttonLogin);

        btn.setOnClickListener(
                v -> {
                    String u = user.getText() != null ? user.getText().toString().trim() : "";
                    String p = pass.getText() != null ? pass.getText().toString() : "";
                    if (u.isEmpty() || p.isEmpty()) {
                        Toast.makeText(this, R.string.login_fill_all, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AppDatabase.execute(
                            () -> {
                                User found = AppDatabase.getInstance(this).userDao().login(u, p);
                                runOnUiThread(
                                        () -> {
                                            if (found == null) {
                                                Toast.makeText(
                                                                this,
                                                                R.string.login_failed,
                                                                Toast.LENGTH_SHORT)
                                                        .show();
                                            } else {
                                                AuthPreferences.login(this, found.id, found.username);
                                                setResult(RESULT_OK, new Intent());
                                                finish();
                                            }
                                        });
                            });
                });
    }
}
