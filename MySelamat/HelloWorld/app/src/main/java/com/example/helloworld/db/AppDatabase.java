package com.example.helloworld.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {USER.class,PLACE.class,USER_PLACE.class},version=9)

public abstract class AppDatabase extends RoomDatabase {


    public abstract UserDao userDao();

    private static AppDatabase INSTANCE;
    public static  AppDatabase getDBInstance(Context context){

        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"DB_Name")
                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
                    .build();


        }
        return INSTANCE;

    }


}
