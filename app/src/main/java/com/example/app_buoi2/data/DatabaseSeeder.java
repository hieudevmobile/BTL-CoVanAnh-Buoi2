package com.example.app_buoi2.data;

import com.example.app_buoi2.data.entity.Movie;
import com.example.app_buoi2.data.entity.Showtime;
import com.example.app_buoi2.data.entity.Theater;
import com.example.app_buoi2.data.entity.Ticket;
import com.example.app_buoi2.data.entity.User;

/** Dữ liệu mẫu: users, phim, rạp, suất chiếu, một vé mẫu */
public final class DatabaseSeeder {

    private DatabaseSeeder() {}

    public static void seedIfEmpty(AppDatabase db) {
        db.runInTransaction(() -> {
            if (db.userDao().count() > 0) {
                return;
            }

            User admin = new User();
            admin.username = "admin";
            admin.password = "123456";
            long adminId = db.userDao().insert(admin);

            User demo = new User();
            demo.username = "demo";
            demo.password = "demo";
            db.userDao().insert(demo);

            Movie m1 = new Movie();
            m1.title = "Dune: Part Two";
            m1.synopsis = "Hành trình trên sao cát Arrakis.";
            m1.durationMinutes = 166;
            long movie1 = db.movieDao().insert(m1);

            Movie m2 = new Movie();
            m2.title = "Oppenheimer";
            m2.synopsis = "Tiểu sử nhà vật lý J. Robert Oppenheimer.";
            m2.durationMinutes = 180;
            long movie2 = db.movieDao().insert(m2);

            Movie m3 = new Movie();
            m3.title = "Spider-Man: Beyond";
            m3.synopsis = "Phiêu lưu đa vũ trụ.";
            m3.durationMinutes = 140;
            long movie3 = db.movieDao().insert(m3);

            Theater t1 = new Theater();
            t1.name = "CGV Landmark 81";
            t1.address = "Vincom Landmark 81, Q. Bình Thạnh, TP.HCM";
            long th1 = db.theaterDao().insert(t1);

            Theater t2 = new Theater();
            t2.name = "Lotte Cinema Diamond";
            t2.address = "Lotte Mart, Q.7, TP.HCM";
            long th2 = db.theaterDao().insert(t2);

            long base = 1_800_000_000_000L; // cố định cho demo

            Showtime s1 = new Showtime();
            s1.movieId = movie1;
            s1.theaterId = th1;
            s1.startTimeMillis = base + 36L * 60 * 60 * 1000;
            long sh1 = db.showtimeDao().insert(s1);

            Showtime s2 = new Showtime();
            s2.movieId = movie1;
            s2.theaterId = th2;
            s2.startTimeMillis = base + 40L * 60 * 60 * 1000;
            db.showtimeDao().insert(s2);

            Showtime s3 = new Showtime();
            s3.movieId = movie2;
            s3.theaterId = th1;
            s3.startTimeMillis = base + 48L * 60 * 60 * 1000;
            db.showtimeDao().insert(s3);

            Showtime s4 = new Showtime();
            s4.movieId = movie3;
            s4.theaterId = th2;
            s4.startTimeMillis = base + 52L * 60 * 60 * 1000;
            db.showtimeDao().insert(s4);

            Ticket sample = new Ticket();
            sample.showtimeId = sh1;
            sample.userId = adminId;
            sample.seat = "A1";
            db.ticketDao().insert(sample);
        });
    }
}
