package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.helloworld.db.AppDatabase;
import com.google.zxing.Result;

public class CameraScanActivity extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannerView;

    String VisitedPlace;
    TextView errorreminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_scan);
        scannerView= findViewById(R.id.scannerView);
        codeScanner= new CodeScanner(this, scannerView);
        errorreminder=findViewById(R.id.errorreminder);

        AppCompatButton Mregis=findViewById(R.id.Mregis);

        Mregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CameraScanActivity.this, ManualRegisterActivity.class));
                finish();
            }
        });

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        VisitedPlace = result.getText();

                        int tempPID= AppDatabase.getDBInstance(getApplicationContext()).userDao().searchPID(VisitedPlace);

                        if (tempPID==0){
                            errorreminder.setText("invalid QR Code");
                            codeScanner.startPreview();
                        }else {

                            startActivity(new Intent(CameraScanActivity.this, ReceiptActivity.class).putExtra("test", VisitedPlace));
                            finish();

                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        codeScanner.startPreview();
    }

}

