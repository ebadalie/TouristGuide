package com.example.faizan.touristguide;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Ebad on 2/13/2015.
 */
public class ReservationActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

        String url = getIntent().getStringExtra("reservation_url");
        WebView  webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webView.loadUrl(url);

    }
}