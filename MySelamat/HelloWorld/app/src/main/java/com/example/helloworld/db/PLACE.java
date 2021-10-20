package com.example.helloworld.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PLACE")
public class PLACE {
    @PrimaryKey(autoGenerate = true)
    public int PlaceID;

    @ColumnInfo(name="PlaceName")
    public String PlaceName;


}
