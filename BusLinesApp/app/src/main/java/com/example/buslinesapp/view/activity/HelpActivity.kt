package com.example.buslinesapp.view.activity

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.buslinesapp.R
import kotlinx.android.synthetic.main.help_main.*
import kotlinx.android.synthetic.main.user_main.*

class HelpActivity: AppCompatActivity() {

    //帮助详情页按钮
//    private lateinit var
//    val stationBtn: ImageButton = findViewById(R.id.station_search)
//    val busBtn: ImageButton = findViewById(R.id.bus_search)
//    val stopBtn: ImageButton = findViewById(R.id.stop_search)
//    val addBtn: ImageButton = findViewById(R.id.bus_add)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_main)

        val stationBtn: ImageButton = findViewById(R.id.station_search)
        val busBtn: ImageButton = findViewById(R.id.bus_search)
        val stopBtn: ImageButton = findViewById(R.id.stop_search)
        val addBtn: ImageButton = findViewById(R.id.bus_add)

   //按钮监听
       stationBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(station_search.context,HelpOneActivity::class.java)
            startActivity(intent)
        }

        busBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(bus_search.context, HelpTwoActivity::class.java)
            startActivity(intent)
        }

        stopBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(stop_search.context, HelpThreeActivity::class.java)
            startActivity(intent)
        }

        addBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(bus_add.context, HelpFourActivity::class.java)
            startActivity(intent)
        }

        }





}