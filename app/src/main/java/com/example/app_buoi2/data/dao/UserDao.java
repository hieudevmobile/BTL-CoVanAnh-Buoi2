package com.example.app_buoi2.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app_buoi2.data.entity.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(User user);

    @Query("SELECT COUNT(*) FROM users")
    int count();

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User getById(long id);
}
