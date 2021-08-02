package com.example.buslinesapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.buslinesapp.R

class UserActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_main)

        //按钮监听
        val collect: Button =findViewById(R.id.login_btn)
        collect.setOnClickListener{
            val intent = Intent()
            intent.setClass(collect.context,CollectActivity::class.java)

        }
    }














}