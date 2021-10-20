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
import com.example.helloworld.db.UserDao;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private Button gotoregis, loginbtn;
    EditText logemail, logpassword;
    TextView errorreminder;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        logemail = findViewById(R.id.logemail);
        logpassword = findViewById(R.id.logpassword);
        gotoregis = (Button) findViewById(R.id.gotoregis);
        errorreminder = findViewById(R.id.errorreminder);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String email = sharedPreferences.getString(KEY_EMAIL, null);

        if (email != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        gotoregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignin();
            }
        });

        loginbtn = (Button) findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                String tempemail = logemail.getText().toString();
                String temppass = encrypt(logpassword.getText().toString());

                if (tempemail.isEmpty() || temppass.isEmpty()) {
                    errorreminder.setText("Please fill all blank");
                } else {

                    Pattern emailtype = Pattern.compile(("(@gmail.com)$|(@yahoo.com)$|(@outlook.com)$"));
                    Matcher matcher = emailtype.matcher(logemail.getText().toString());

                    if (matcher.find()) {

                        AppDatabase db = AppDatabase.getDBInstance(getApplicationContext());
                        UserDao userDao = db.userDao();

//                            Toast.makeText(LoginActivity.this, "l", Toast.LENGTH_SHORT).show();
                        USER user = userDao.login(tempemail, temppass);
                        if (user == null) {
                            runOnUiThread(() -> {
//                                    Toast.makeText(LoginActivity.this, "lo", Toast.LENGTH_SHORT).show();
                                errorreminder.setText("Invalid Credentials");
                            });
                        } else {
//                                Toast.makeText(LoginActivity.this, "lol", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_EMAIL, logemail.getText().toString());
                            editor.apply();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                    } else {
                        errorreminder.setText("Invalid Email");
                    }


                }
            }
        });
    }

    public void openSignin() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private static String encrypt(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}