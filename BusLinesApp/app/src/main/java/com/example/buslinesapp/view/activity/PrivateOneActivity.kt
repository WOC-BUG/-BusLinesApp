package com.example.buslinesapp.view.activity

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.buslinesapp.R

class PrivateOneActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_1)
        
        val webView = findViewById(R.id.webview2) as WebView
        val url = "file:///android_asset/private.html"

        webView.loadUrl(url)
    }
}