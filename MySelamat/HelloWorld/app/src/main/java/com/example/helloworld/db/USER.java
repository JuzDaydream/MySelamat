package com.example.helloworld.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "USER")
public class USER {


    @PrimaryKey(autoGenerate = true)
    public int UserID;

    @ColumnInfo(name="UserName")
    public String UserName;

    @ColumnInfo(name="UserEmail")
    public String UserEmail;

    @ColumnInfo(name="UserPassword")
    public String UserPassword;

    @ColumnInfo(name="CovidStat")
    public String CovidStat;

    @ColumnInfo(name="SusStat")
    public String SusStat;

}
