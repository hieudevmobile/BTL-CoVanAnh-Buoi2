package com.example.app_buoi2.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "theaters")
public class Theater {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String address;
}
