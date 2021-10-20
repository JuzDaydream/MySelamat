package com.example.helloworld;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.helloworld.db.AppDatabase;
import com.example.helloworld.db.PLACE;
import com.example.helloworld.db.USER;
import com.example.helloworld.db.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Base64;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton logout_btn;
    Dialog dialog, covidialog, susdialog;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private int CAMERA_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getDBInstance(getApplicationContext());
        UserDao userDao = db.userDao();

        CardView Scan = findViewById(R.id.card_scan);
        CardView Hot = findViewById(R.id.card_hot);
        CardView Today = findViewById(R.id.card_today);
        CardView History = findViewById(R.id.card_his);
        logout_btn = findViewById(R.id.logout_btn);

        dialog = new Dialog(MainActivity.this);
        covidialog = new Dialog(MainActivity.this);
        susdialog = new Dialog(MainActivity.this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        dialog.setContentView(R.layout.logout_dialog);
        covidialog.setContentView(R.layout.covid_dialog);
        Button coviddialog_ok = covidialog.findViewById(R.id.coviddialog_ok);
        susdialog.setContentView(R.layout.sus_dialog);
        Button susdialog_ok = susdialog.findViewById(R.id.susdialog_ok);


        String email = sharedPreferences.getString(KEY_EMAIL, null);

        int tempUID = AppDatabase.getDBInstance(getApplicationContext()).userDao().searchUID(email);

        String CovidCheck = AppDatabase.getDBInstance(getApplicationContext()).userDao().checkCovid(tempUID);
        String SusCheck = AppDatabase.getDBInstance(getApplicationContext()).userDao().checkSus(tempUID);


        int result = userDao.checkBeenCovidPlace(tempUID);

        if (result == 1) {

            userDao.updateSus(tempUID);

            susdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            susdialog.setCancelable(false);
            susdialog.show();

            susdialog_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    susdialog.dismiss();
                }
            });
//            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        } else if (result == 0) {
            //Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }


        Button logoutagree = dialog.findViewById(R.id.logoutbtn);
        Button logoutcancel = dialog.findViewById(R.id.cancelbtn);

        logoutagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                dialog.dismiss();
                finish();
            }
        });

        logoutcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.show();

            }
        });

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(MainActivity.this, CameraScanActivity.class));
                } else {
                    requestCameraPermission();
                }


            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });


        Hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HotActivity.class));
            }
        });


        Today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TodayActivity.class));
            }
        });

        if (AppDatabase.getDBInstance(getApplicationContext()).userDao().searchPID("Sunway Velocity") != 1) {
            PLACE place = new PLACE();

            place.PlaceID = 1;
            place.PlaceName = "Sunway Velocity";

            db.userDao().insertPlace(place);

            PLACE place2 = new PLACE();

            place2.PlaceID = 2;
            place2.PlaceName = "KLCC";

            db.userDao().insertPlace(place2);

            USER user = new USER();
            user.UserID = 2;
            user.UserName = "Covided Person";
            user.UserPassword = encrypt("password");
            user.UserEmail = "test@gmail.com";
            user.SusStat = "T";
            user.CovidStat = "T";

            db.userDao().insertUser(user);
        }


        if (SusCheck.equals("T")) {
            susdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            susdialog.setCancelable(false);
            susdialog.show();

            susdialog_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    susdialog.dismiss();
                }
            });
        }


        if (CovidCheck.equals("T")) {
            covidialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            covidialog.setCancelable(false);
            covidialog.show();

            coviddialog_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    covidialog.dismiss();
                }
            });
        }


    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed to scan QR Code with Camera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static String encrypt(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }


}