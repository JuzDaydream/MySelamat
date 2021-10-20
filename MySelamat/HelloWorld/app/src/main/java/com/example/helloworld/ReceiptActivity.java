package com.example.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.db.AppDatabase;
import com.example.helloworld.db.USER_PLACE;
import com.example.helloworld.db.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReceiptActivity extends AppCompatActivity {


    TextView location_value, name_value, email_value, date_value, time_value;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt);

        location_value = findViewById(R.id.inform_location_value);
        name_value = findViewById(R.id.inform_name_value);
        email_value = findViewById(R.id.inform_email_value);
        date_value = findViewById(R.id.inform_date_value);
        time_value = findViewById(R.id.inform_time_value);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);

        AppDatabase db = AppDatabase.getDBInstance(getApplicationContext());
        UserDao userDao = db.userDao();


        Intent secondIntent = getIntent();
        String Visited = secondIntent.getStringExtra("test");


        location_value.setText(Visited);
        email_value.setText(email);

        Date datenow = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault());
        String formatDate = df.format(datenow);

        date_value.setText(formatDate);


        SimpleDateFormat tm = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());

        String time = tm.format(datenow);

        time_value.setAllCaps(true);
        time_value.setText(time);


        String name = AppDatabase.getDBInstance(getApplicationContext()).userDao().searchname(email);
        name_value.setText(name);

        FloatingActionButton btn_return = findViewById(R.id.btn_returnHot);

        int tempUID = AppDatabase.getDBInstance(getApplicationContext()).userDao().searchUID(email);
        int tempPID = AppDatabase.getDBInstance(getApplicationContext()).userDao().searchPID(Visited);

//        Toast.makeText(this, "UID="+tempUID+",PID="+tempPID, Toast.LENGTH_SHORT).show();

        saveUserPlace(tempUID, tempPID, formatDate, time);


        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void saveUserPlace(int UID, int PID, String VisitDate, String VisitTime) {
        AppDatabase db = AppDatabase.getDBInstance(this.getApplicationContext());

        USER_PLACE userPlace = new USER_PLACE();
        userPlace.UID = UID;
        userPlace.PID = PID;
        userPlace.VisitDate = VisitDate;
        userPlace.VisitTime = VisitTime;

        db.userDao().insertUserPlace(userPlace);


    }
}