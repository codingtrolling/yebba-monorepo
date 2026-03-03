package com.yebba.search;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.yebba_webview);
        searchBar = findViewById(R.id.search_bar);

        // Configure the engine for modern web
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // Handle the "GO" button
        findViewById(R.id.btn_go).setOnClickListener(v -> {
            String input = searchBar.getText().toString();
            performSearch(input);
        });
    }

    private void performSearch(String input) {
        if (input.startsWith("http://") || input.startsWith("https://")) {
            webView.loadUrl(input);
        } else {
            // YEBBA Search Logic: Send query to a search provider
            String queryUrl = "https://www.google.com/search?q=" + input;
            webView.loadUrl(queryUrl);
        }
    }
    
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
