package com.example.app_buoi2.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public final class ToolbarHelper {

    private ToolbarHelper() {}

    public static void setupBack(MaterialToolbar toolbar, AppCompatActivity activity) {
        toolbar.setNavigationOnClickListener(
                v -> activity.getOnBackPressedDispatcher().onBackPressed());
    }
}
