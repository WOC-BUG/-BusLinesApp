package com.example.buslinesapp.view.activity

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.buslinesapp.R


class PraiseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.praise_main)

        val webView = findViewById(R.id.webview1) as WebView
        val url = "https://guanjia.qq.com/sem/937/index.html?ADTAG=media.buy.baidu.SEM"

//        val url = "https://www.baidu.com"
        webView.loadUrl(url)
    }



}