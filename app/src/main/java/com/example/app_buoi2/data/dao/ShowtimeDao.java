package com.example.app_buoi2.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app_buoi2.data.entity.Showtime;
import com.example.app_buoi2.data.model.ShowtimeRow;

import java.util.List;

@Dao
public interface ShowtimeDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(Showtime showtime);

    @Query(
            "SELECT s.id AS showtimeId, s.movieId AS movieId, m.title AS movieTitle, "
                    + "s.theaterId AS theaterId, t.name AS theaterName, s.startTimeMillis AS startTimeMillis "
                    + "FROM showtimes s "
                    + "INNER JOIN movies m ON m.id = s.movieId "
                    + "INNER JOIN theaters t ON t.id = s.theaterId "
                    + "ORDER BY s.startTimeMillis")
    List<ShowtimeRow> getAllWithDetails();

    @Query("SELECT * FROM showtimes WHERE id = :id LIMIT 1")
    Showtime getById(long id);

    @Query(
            "SELECT s.id AS showtimeId, s.movieId AS movieId, m.title AS movieTitle, "
                    + "s.theaterId AS theaterId, t.name AS theaterName, s.startTimeMillis AS startTimeMillis "
                    + "FROM showtimes s "
                    + "INNER JOIN movies m ON m.id = s.movieId "
                    + "INNER JOIN theaters t ON t.id = s.theaterId "
                    + "WHERE s.id = :showtimeId LIMIT 1")
    ShowtimeRow getRowById(long showtimeId);
}
