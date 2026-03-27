package com.example.app_buoi2.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "showtimes",
        foreignKeys = {
                @ForeignKey(
                        entity = Movie.class,
                        parentColumns = "id",
                        childColumns = "movieId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = Theater.class,
                        parentColumns = "id",
                        childColumns = "theaterId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("movieId"), @Index("theaterId")}
)
public class Showtime {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long movieId;
    public long theaterId;
    /** Epoch millis for show start */
    public long startTimeMillis;
}
