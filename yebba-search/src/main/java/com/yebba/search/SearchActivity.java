package com.yebba.search;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    private WebView webView;
    private EditText searchBar;
    private final String HOME_URL = "https://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize UI Elements
        webView = findViewById(R.id.yebba_webview);
        searchBar = findViewById(R.id.search_bar);

        // 2. Configure WebView Settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); // Needed for modern sites
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // 3. Set WebViewClient to keep browsing inside the app
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                searchBar.setText(url); // Update address bar while loading
            }
        });

        // 4. Handle Search Input (Enter key on keyboard)
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO) {
                performSearch(searchBar.getText().toString());
                return true;
            }
            return false;
        });

        // 5. Button Listeners
        findViewById(R.id.btn_go).setOnClickListener(v -> performSearch(searchBar.getText().toString()));
        
        findViewById(R.id.nav_back).setOnClickListener(v -> {
            if (webView.canGoBack()) webView.goBack();
        });

        findViewById(R.id.nav_forward).setOnClickListener(v -> {
            if (webView.canGoForward()) webView.goForward();
        });

        findViewById(R.id.nav_home).setOnClickListener(v -> webView.loadUrl(HOME_URL));

        findViewById(R.id.nav_refresh).setOnClickListener(v -> webView.reload());

        // 6. ECOSYSTEM FEATURE: Share to YEBBA Chat
        findViewById(R.id.nav_share_chat).setOnClickListener(v -> {
            String currentUrl = webView.getUrl();
            if (currentUrl != null) {
                shareToChat(currentUrl);
            }
        });

        // Load Default Page
        webView.loadUrl(HOME_URL);
    }

    private void performSearch(String input) {
        if (input.isEmpty()) return;

        // If it looks like a URL, load it; otherwise, search Google
        if (input.contains(".") && !input.contains(" ")) {
            String url = input.startsWith("http") ? input : "https://" + input;
            webView.loadUrl(url);
        } else {
            webView.loadUrl("https://www.google.com/search?q=" + input);
        }
    }

    private void shareToChat(String url) {
        // This triggers a custom Intent that YEBBA Chat will be programmed to catch
        Intent intent = new Intent("com.yebba.chat.ACTION_SHARE_LINK");
        intent.setPackage("com.yebba.chat"); // Targeted only to your Chat app
        intent.putExtra("shared_url", url);
        intent.setType("text/plain");

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "YEBBA Chat not found!", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle Hardware Back Button
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
