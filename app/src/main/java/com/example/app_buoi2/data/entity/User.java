package com.example.app_buoi2.data.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = "username", unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String username;
    public String password;
}
