package com.example.app_buoi2.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app_buoi2.data.entity.Ticket;
import com.example.app_buoi2.data.model.TicketRow;

import java.util.List;

@Dao
public interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(Ticket ticket);

    @Query("SELECT seat FROM tickets WHERE showtimeId = :showtimeId")
    List<String> getSeatsForShowtime(long showtimeId);

    @Query(
            "SELECT ti.id AS ticketId, ti.seat AS seat, m.title AS movieTitle, "
                    + "th.name AS theaterName, s.startTimeMillis AS startTimeMillis "
                    + "FROM tickets ti "
                    + "INNER JOIN showtimes s ON s.id = ti.showtimeId "
                    + "INNER JOIN movies m ON m.id = s.movieId "
                    + "INNER JOIN theaters th ON th.id = s.theaterId "
                    + "WHERE ti.userId = :userId "
                    + "ORDER BY s.startTimeMillis DESC")
    List<TicketRow> getTicketsForUser(long userId);
}
