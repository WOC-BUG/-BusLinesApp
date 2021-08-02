package com.example.buslinesapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.dao.StationDao
import com.example.buslinesapp.model.data.QueryStationData
import com.example.buslinesapp.view.adapter.QueryMiddleStationAdapter
import com.example.buslinesapp.view.adapter.QueryStationAdapter
import kotlinx.android.synthetic.main.query_station_fragment.*
import kotlinx.android.synthetic.main.station_info.*
import java.util.ArrayList

class QueryStationFragment:Fragment() {

    private var dbHelper : DatabaseHelper ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.query_station_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        replaceFragment(EmptyContentFragment())
        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        val db = dbHelper?.writableDatabase

        search.setOnClickListener{
            val searchContent = searchStationTextview.text.toString()
            if(searchContent==""){
                Toast.makeText(this.context,"请输入内容！", Toast.LENGTH_SHORT).show()
            }
            else{
                val station = StationDao(dbHelper)
                if(!station.query(searchContent)){  // 找不到该站点
                    Toast.makeText(this.context,"该站不存在！",Toast.LENGTH_SHORT).show()
                    replaceFragment(EmptyContentFragment())
                }
                else{
                    replaceFragment(StationInfoFragment(searchContent))
                }
            }
        }

        search2.setOnClickListener{
            val searchContent = searchStationTextview2.text.toString()
            if(searchContent == ""){
                Toast.makeText(this.context,"请输入内容！", Toast.LENGTH_SHORT).show()
                replaceFragment(EmptyContentFragment())
            }
            else{
                val station = StationDao(dbHelper)
                if(!station.query(searchContent)){  // 找不到该站点
                    Toast.makeText(this.context,"该站不存在！",Toast.LENGTH_SHORT).show()
                    replaceFragment(EmptyContentFragment())
                }
                else{
                    replaceFragment(MiddleStationInfoFragment(searchContent))
                }
            }
        }
    }

    private fun replaceFragment(fragment:Fragment){
        val fManager= fragmentManager
        val transaction = fManager?.beginTransaction()
        transaction?.replace(R.id.frameLayout,fragment)  //rightLayout替换为fragment
        transaction?.addToBackStack(null)    //手动添加到栈中，这样回退时可返回上一个fragment，而不是关闭整个activity
        transaction?.commit()   //留住Fragment的前后关系
    }
}


class EmptyContentFragment:Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        return inflater.inflate(R.layout.empty_content,container,false)
    }
}


class StationInfoFragment(private var searchContent: String):Fragment(){


    private var dbHelper : DatabaseHelper ?= null
    private var adapter : QueryStationAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        return inflater.inflate(R.layout.station_info,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        stationName.text = searchContent
        val station = StationDao(dbHelper)
        val layoutManager= LinearLayoutManager(context)

        layoutManager.orientation= LinearLayoutManager.VERTICAL    //垂直排列
        busRecyclerView.layoutManager=layoutManager

        val data : ArrayList<QueryStationData> = station.queryStation(searchContent,true)  // 查询该以站点为起点的信息
        val data2 : ArrayList<QueryStationData> = station.queryStation(searchContent,false)  // 查询该以站点为终点的信息
        val join : ArrayList<QueryStationData> = ArrayList<QueryStationData>()
        join.addAll(data)
        join.addAll(data2)

        adapter = QueryStationAdapter(join)
        busRecyclerView.adapter = adapter
    }
}



class MiddleStationInfoFragment(private var searchContent: String):Fragment(){


    private var dbHelper : DatabaseHelper ?= null
    private var adapter : QueryMiddleStationAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        return inflater.inflate(R.layout.station_info,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        stationName.text = searchContent
        val station = StationDao(dbHelper)
        val layoutManager= LinearLayoutManager(context)

        layoutManager.orientation= LinearLayoutManager.VERTICAL    //垂直排列
        busRecyclerView.layoutManager=layoutManager

        val data : ArrayList<QueryStationData> = station.queryMiddleStation(searchContent)  // 查询中转站信息

        adapter = QueryMiddleStationAdapter(data)
        busRecyclerView.adapter = adapter
    }
}