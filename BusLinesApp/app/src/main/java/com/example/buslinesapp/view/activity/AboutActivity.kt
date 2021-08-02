package com.example.buslinesapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.buslinesapp.R
import kotlinx.android.synthetic.main.about_main.*
import kotlinx.android.synthetic.main.help_main.*

class AboutActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_main)

//按钮监听
        val privateBtn: Button = findViewById(R.id. private_button)
        val serviceBtn:Button=findViewById(R.id.service_button)

        privateBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(private_button.context,PrivateOneActivity::class.java)
            startActivity(intent)
        }
        serviceBtn.setOnClickListener{
            val intent = Intent()
            intent.setClass(service_button.context,PrivateTwoActivity::class.java)
            startActivity(intent)
        }





    }


}