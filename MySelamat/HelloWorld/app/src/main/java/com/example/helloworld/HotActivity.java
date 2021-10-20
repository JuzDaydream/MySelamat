package com.example.helloworld;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot);

        FloatingActionButton btn_return = findViewById(R.id.btn_returnHot);
        HotActivity activity = this;

        ProgressDialog progDailog = ProgressDialog.show(activity, "Loading", "Please wait...", true);
        progDailog.setCancelable(false);

        WebView Hot = (WebView) findViewById(R.id.Web_Hot);
        Hot.setWebViewClient(new WebViewClient());

        Hot.getSettings().setJavaScriptEnabled(true);
        Hot.getSettings().setLoadWithOverviewMode(true);
        Hot.getSettings().setUseWideViewPort(true);
        Hot.setWebViewClient(new WebViewClient() {

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


        Hot.loadUrl("https://www.arcgis.com/apps/View/index.html?appid=b5310fd4a23d4fe288d8bf68edecac3d&extent=98.7214,-2.2278,119.1340,8.4742");

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}