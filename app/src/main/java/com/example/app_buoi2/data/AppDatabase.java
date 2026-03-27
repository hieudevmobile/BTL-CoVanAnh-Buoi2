package com.example.app_buoi2.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.app_buoi2.data.dao.MovieDao;
import com.example.app_buoi2.data.dao.ShowtimeDao;
import com.example.app_buoi2.data.dao.TheaterDao;
import com.example.app_buoi2.data.dao.TicketDao;
import com.example.app_buoi2.data.dao.UserDao;
import com.example.app_buoi2.data.entity.Movie;
import com.example.app_buoi2.data.entity.Showtime;
import com.example.app_buoi2.data.entity.Theater;
import com.example.app_buoi2.data.entity.Ticket;
import com.example.app_buoi2.data.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {User.class, Movie.class, Theater.class, Showtime.class, Ticket.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "movie_ticket.db";
    private static volatile AppDatabase instance;
    private static final ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

    public abstract UserDao userDao();

    public abstract MovieDao movieDao();

    public abstract TheaterDao theaterDao();

    public abstract ShowtimeDao showtimeDao();

    public abstract TicketDao ticketDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance =
                            Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                                    .build();
                }
            }
        }
        return instance;
    }

    public static void execute(Runnable r) {
        dbExecutor.execute(r);
    }
}
