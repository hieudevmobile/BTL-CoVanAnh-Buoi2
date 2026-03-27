package com.example.app_buoi2.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app_buoi2.data.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(Movie movie);

    @Query("SELECT * FROM movies ORDER BY title")
    List<Movie> getAll();

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    Movie getById(long id);
}
