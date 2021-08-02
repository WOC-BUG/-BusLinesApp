package com.example.buslinesapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.buslinesapp.R
import kotlinx.android.synthetic.main.welcome.*

class WelcomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)


        val timer:CountDownTimer = object : CountDownTimer(3000,1000) {
            override fun onFinish() {
                val intent = Intent (this@WelcomeActivity,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onTick(p0: Long) {
                println("剩余时间：$p0")
                time.text = (p0/1000+1).toString()
            }
        }

        timer.start()
    }
}