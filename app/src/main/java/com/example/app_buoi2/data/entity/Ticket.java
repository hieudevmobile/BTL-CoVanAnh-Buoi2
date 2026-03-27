package com.example.app_buoi2.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "tickets",
        foreignKeys = {
                @ForeignKey(
                        entity = Showtime.class,
                        parentColumns = "id",
                        childColumns = "showtimeId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index("showtimeId"),
                @Index("userId"),
                @Index(value = {"showtimeId", "seat"}, unique = true)
        }
)
public class Ticket {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long showtimeId;
    public long userId;
    /** e.g. A3 */
    public String seat;
}
