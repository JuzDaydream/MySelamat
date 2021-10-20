package com.example.helloworld;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today);

        FloatingActionButton btn_return = findViewById(R.id.btn_returnHot);
        TodayActivity activity = this;

        ProgressDialog progDailog = ProgressDialog.show(activity, "Loading", "Please wait...", true);
        progDailog.setCancelable(false);

        WebView Today = (WebView) findViewById(R.id.Web_Today);
        Today.setWebViewClient(new WebViewClient());

        Today.getSettings().setJavaScriptEnabled(true);
        Today.getSettings().setLoadWithOverviewMode(true);
        Today.getSettings().setUseWideViewPort(true);
        Today.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });

        Date datetdy = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("ddMMYYYY", Locale.getDefault());
        String format_datetdy = df.format(datetdy);

        Today.loadUrl("https://covid-19.moh.gov.my/terkini-negeri/2021/09/kemaskini-negeri-covid-19-di-malaysia-sehingga-" + format_datetdy);

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}