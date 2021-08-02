package com.example.buslinesapp.view.activity

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.buslinesapp.R
import kotlinx.android.synthetic.main.webview.*

class WebviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)

        webView.settings.javaScriptEnabled=true
        webView.webViewClient= WebViewClient()

        val intent = intent
        val url = intent.getStringExtra("url")
        if(url == null){
            println("url为空！")
        }
        else{
            webView.loadUrl(url)
        }
    }
}