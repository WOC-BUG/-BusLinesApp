package com.example.buslinesapp.view.activity

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.buslinesapp.R

class PrivateTwoActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_2)

        val webView = findViewById(R.id.webview3) as WebView
        val url = "file:///android_asset/service.html"

        webView.loadUrl(url)
    }
}