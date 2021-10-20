package com.example.helloworld.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(USER...users);

    @Query("SELECT * FROM USER WHERE UserEmail=(:UserEmail) and UserPassword=(:UserPassword)")
    USER login(String UserEmail, String UserPassword);

    @Query("SELECT UserName FROM USER WHERE UserEmail=(:UserEmail)")
    String searchname(String UserEmail);

    @Query("SELECT PlaceName FROM PLACE WHERE PlaceID=(:PlaceID)")
    String searchplace(int PlaceID);

    @Query("SELECT UserID FROM USER WHERE UserEmail=(:UserEmail)")
    int searchUID(String UserEmail);

    @Query("SELECT PlaceID FROM PLACE WHERE PlaceName=(:PlaceName)")
    int searchPID(String PlaceName);

    @Query("SELECT CovidStat FROM USER WHERE UserID=(:UserID)")
    String checkCovid(int UserID);

    @Query("SELECT SusStat FROM USER WHERE UserID=(:UserID)")
    String checkSus(int UserID);

    @Query("UPDATE USER SET SusStat='T'WHERE UserID=(:UserID) ")
    int updateSus(int UserID);

    @Query ("SELECT EXISTS(SELECT UserEmail FROM USER WHERE UserEmail=(:UserEmail))")
    int searchEmail(String UserEmail);

    @Query("SELECT EXISTS(" +
            "SELECT USER_PLACE.VisitDate,PID FROM USER,USER_PLACE WHERE USER.UserID=USER_PLACE.UID AND USER.USERID=(:UserID) " +
            "INTERSECT " +
            "SELECT USER_PLACE.VisitDate,PID FROM USER,USER_PLACE WHERE USER.Userid=USER_PLACE.UID AND USER.CovidStat='T')"
    )
    int checkBeenCovidPlace(int UserID);




    @Query("SELECT * FROM USER_PLACE WHERE UID=(:UID)")
    List<USER_PLACE> getAllUPlace(int UID);


    @Insert
    void insertPlace(PLACE...places);

    @Insert
    void insertUserPlace(USER_PLACE...userPlaces);
}
