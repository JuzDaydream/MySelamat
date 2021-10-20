package com.example.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.helloworld.db.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ManualRegisterActivity extends AppCompatActivity {

    EditText Mregis_et_Name,Mregis_et_Email,Mregis_et_Location;
    TextView Mregis_error;
    AppCompatButton Mregis_btn;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_register);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String name = AppDatabase.getDBInstance(getApplicationContext()).userDao().searchname(email);

        Mregis_et_Name=findViewById(R.id.Mregis_et_Name);
        Mregis_et_Email=findViewById(R.id.Mregis_et_Email);
        Mregis_et_Location=findViewById(R.id.Mregis_et_Location);

        Mregis_error=findViewById(R.id.Mregis_error);

        Mregis_btn=findViewById(R.id.Mregis_btn);

        Mregis_et_Name.setText(name);
        Mregis_et_Email.setText(email);



        FloatingActionButton btn_return = findViewById(R.id.btn_return_Mregis);

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        Mregis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String VisitedPlace= Mregis_et_Location.getText().toString();
                int tempPID= AppDatabase.getDBInstance(getApplicationContext()).userDao().searchPID(VisitedPlace);

                if (tempPID==0){
                    Mregis_error.setText("Please ensure location is typed correctly with caps.");
                }else {

                    startActivity(new Intent(ManualRegisterActivity.this, ReceiptActivity.class).putExtra("test", VisitedPlace));
                    finish();

                }

            }
        });
    }
}