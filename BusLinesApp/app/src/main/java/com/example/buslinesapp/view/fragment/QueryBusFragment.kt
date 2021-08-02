package com.example.buslinesapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.BusDao
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.view.activity.WebviewActivity
import kotlinx.android.synthetic.main.add_fragment.*
import kotlinx.android.synthetic.main.advertisement.*
import kotlinx.android.synthetic.main.query_bus_fragment.*
import kotlinx.android.synthetic.main.query_station_fragment.*

class QueryBusFragment:Fragment (){
    private var dbHelper : DatabaseHelper ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =inflater.inflate(R.layout.query_bus_fragment,container,false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        replaceFragment(BusAdvert())
        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        val db = dbHelper?.writableDatabase

        //绑定单选按钮选择监听
        queryBusButton.setOnClickListener {

            val editBus = editBus.text.toString()
            if(editBus==""){
                Toast.makeText(this.context,"请输入内容！", Toast.LENGTH_SHORT).show()
            }else{
                val bus=BusDao(dbHelper)
                if (!bus.query(editBus)){
                    Toast.makeText(this.context,"oops！没有这路车", Toast.LENGTH_SHORT).show()
                    replaceFragment(BusAdvert())
                }else{
                    replaceFragment(BusInformationFragment(editBus))
                }
            }
             }

        val bundle = arguments
        if(bundle == null){
            println("============================bundle为空！")
        }
        else{
            editBus.setText(bundle.getString("busName"))
            queryBusButton.performClick()
            println("================================= busName = "+bundle.getString("busName"))
        }

//        // 点击广告1
//        advertLayout.setOnClickListener{
//            val intent = Intent(context, WebviewActivity::class.java)
//            intent.putExtra("url","https://www.jd.com/")
//            startActivity(intent)
//        }
//
//        // 点击广告2
//        advertLayout2.setOnClickListener{
//            val intent = Intent(context, WebviewActivity::class.java)
//            intent.putExtra("url","https://www.taobao.com/")
//            startActivity(intent)
//        }
    }
    //切换fragment
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = childFragmentManager  //获取碎片管理器
        val transaction =  fragmentManager.beginTransaction()  //Transaction事务
        transaction.replace(R.id.queryBusFragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    class BusAdvert: Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.bus_advert, container, false)
        }


        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
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
    }
}