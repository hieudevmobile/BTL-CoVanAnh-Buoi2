package com.example.app_buoi2;

import android.app.Application;

import com.example.app_buoi2.data.AppDatabase;
import com.example.app_buoi2.data.DatabaseSeeder;

/** Khởi tạo Room (và seed) sớm để dữ liệu sẵn sàng */
public class MovieTicketApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Seed trên luồng nền — Room cấm runInTransaction/DAO trên main thread (sẽ crash khi mở app)
        AppDatabase.execute(
                () ->
                        DatabaseSeeder.seedIfEmpty(
                                AppDatabase.getInstance(getApplicationContext())));
    }
}
