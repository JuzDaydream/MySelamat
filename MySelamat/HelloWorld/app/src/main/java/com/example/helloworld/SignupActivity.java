package com.example.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.db.AppDatabase;
import com.example.helloworld.db.USER;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private Button gotologin, signupbtn;
    EditText regname, regemail, regpassword;
    TextView errorreminder;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        errorreminder = findViewById(R.id.errorreminder);

        regname = findViewById(R.id.regname);
        regemail = findViewById(R.id.regemail);
        regpassword = findViewById(R.id.regpassword);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String email = sharedPreferences.getString(KEY_EMAIL, null);
        AppDatabase db = AppDatabase.getDBInstance(this.getApplicationContext());

        if (email != null) {
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
            this.finish();
        }


        gotologin = (Button) findViewById(R.id.gotologin);

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });


        signupbtn = (Button) findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (regemail.getText().toString().isEmpty() || regname.getText().toString().isEmpty() || regpassword.getText().toString().isEmpty()) {
                    errorreminder.setText("Do not leave any blank");
                } else {

                    Pattern emailtype = Pattern.compile("(@gmail.com)$|(@yahoo.com)$|(@outlook.com)$");
                    Matcher matcher = emailtype.matcher(regemail.getText().toString());

                    if (matcher.find()) {

                        if (db.userDao().searchEmail(regemail.getText().toString()) == 1) {

                            errorreminder.setText("Email Used!");

                        } else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_EMAIL, regemail.getText().toString());
                            editor.apply();


                            saveNewUser(regemail.getText().toString(),
                                    regname.getText().toString(),
                                    regpassword.getText().toString()
                            );
                        }


                    } else {

                        errorreminder.setText("Invalid Email");

                    }


                }
            }
        });


    }

    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void saveNewUser(String email, String name, String password) {
        AppDatabase db = AppDatabase.getDBInstance(this.getApplicationContext());

        USER user = new USER();

        user.UserName = name;
        user.UserEmail = email;
        user.UserPassword = encrypt(password);
        user.CovidStat = "F";
        user.SusStat = "F";


        db.userDao().insertUser(user);


        startActivity(new Intent(SignupActivity.this, MainActivity.class));
        this.finish();
    }

    private static String encrypt(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}