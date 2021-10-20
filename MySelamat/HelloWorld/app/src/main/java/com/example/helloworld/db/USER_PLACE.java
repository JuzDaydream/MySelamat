package com.example.helloworld.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"PID","UID","VisitTime"},
        tableName = "USER_PLACE",
        foreignKeys = {
        @ForeignKey(entity = USER.class,
                parentColumns = "UserID",
                childColumns = "UID",
                onUpdate = CASCADE, onDelete = CASCADE
        ),
                @ForeignKey(entity = PLACE.class,
                        parentColumns = "PlaceID",
                        childColumns = "PID",
                        onUpdate = CASCADE, onDelete = CASCADE
                )})

public class USER_PLACE {

    public int PID;

    public int UID;

    @ColumnInfo(name="VisitDate")
    public String VisitDate;

    @ColumnInfo(name="VisitTime")
    @NonNull
    public String VisitTime;
}
