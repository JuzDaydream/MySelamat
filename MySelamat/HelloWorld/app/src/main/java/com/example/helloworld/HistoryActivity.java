package com.example.helloworld;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.db.AppDatabase;
import com.example.helloworld.db.USER_PLACE;
import com.example.helloworld.db.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private HistoryAdapter historyAdapter;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_EMAIL="email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String email= sharedPreferences.getString(KEY_EMAIL,null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        RecyclerView recyle_his = findViewById(R.id.recycle_his);
        historyAdapter= new HistoryAdapter(this);
        recyle_his.setAdapter(historyAdapter);
        recyle_his.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db= AppDatabase.getDBInstance(getApplicationContext());
        UserDao userDao = db.userDao();

        List<USER_PLACE> UPlaceList=userDao.getAllUPlace(userDao.searchUID(email));
        historyAdapter.setHisList(UPlaceList);


        FloatingActionButton btn_return= findViewById(R.id.btn_returnHis);

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}