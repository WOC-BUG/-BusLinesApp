package com.example.buslinesapp.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.dao.StationDao
import com.example.buslinesapp.view.activity.WebviewActivity
import kotlinx.android.synthetic.main.add_station_fragment.*
import kotlinx.android.synthetic.main.advertisement.*

class AddStationFragment: Fragment() {

    private var dbHelper: DatabaseHelper?=null;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_station_fragment,container,false)
    }

    @SuppressLint("ShowToast")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        val db = dbHelper?.writableDatabase

        // 点击提交按钮
        submitBtn.setOnClickListener{
            val stationName = input1.text.toString()
            val simpleName = input2.text.toString()

            if(stationName==""){
                Toast.makeText(this.context,"站名不能为空！",Toast.LENGTH_SHORT).show()
            }
            else{
                val station = StationDao(dbHelper)
                if(station.query(stationName)){
                    Toast.makeText(this.context,"该站已存在！",Toast.LENGTH_SHORT).show()
                }
                else{
                    station.addStation(stationName,simpleName,0)
                    station.addStation(stationName,simpleName,1)
                    Toast.makeText(this.context,"车站添加成功！",Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 点击清空按钮
        clearBtn.setOnClickListener{
            input1.setText("")
            input2.setText("")
        }

        // 点击广告1
        advertLayout.setOnClickListener{
            val intent = Intent(context, WebviewActivity::class.java)
            intent.putExtra("url","https://www.jd.com/")
            startActivity(intent)
        }

        // 点击广告2
        advertLayout2.setOnClickListener{
            val intent = Intent(context, WebviewActivity::class.java)
            intent.putExtra("url","https://www.taobao.com/")
            startActivity(intent)
        }
    }

    //判断表是否为空
    fun isEmpty(tableNmae:String):Boolean{
        var empty = false
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from $tableNmae",null)
        if(cursor?.count == 0)
            empty = true

        return empty

    }
}