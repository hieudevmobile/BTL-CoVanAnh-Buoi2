package com.example.app_buoi2.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app_buoi2.data.entity.Theater;

import java.util.List;

@Dao
public interface TheaterDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(Theater theater);

    @Query("SELECT * FROM theaters ORDER BY name")
    List<Theater> getAll();

    @Query("SELECT * FROM theaters WHERE id = :id LIMIT 1")
    Theater getById(long id);
}
