package com.example.app_buoi2.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String synopsis;
    public int durationMinutes;
}
