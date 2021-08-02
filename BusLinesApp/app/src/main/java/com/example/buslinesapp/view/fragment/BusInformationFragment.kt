package com.example.buslinesapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.BusDao
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.model.data.QueryBusData
import com.example.buslinesapp.model.data.QueryStationData
import com.example.buslinesapp.view.adapter.QueryBusInfoAdapter
import com.example.buslinesapp.view.adapter.QueryBusLineAdapter
import kotlinx.android.synthetic.main.query_bus_information_fragment.*
import java.util.ArrayList

class BusInformationFragment(editBus:String): Fragment() {

    private var dbHelper : DatabaseHelper ?= null
    private var editBus: String = editBus
    private var adapter : QueryBusLineAdapter?= null
    private var adapterbus:QueryBusInfoAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.query_bus_information_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        busNumber.text = editBus+" (下行）"//显示几路车
        busInformation.text=editBus+" (约五分钟一趟)"//显示汽车信息
        val bus = BusDao(dbHelper)
        //车站路线和时间信息recyclerview
        val layoutManager= LinearLayoutManager(context)
        layoutManager.orientation= LinearLayoutManager.VERTICAL    //垂直排列
        busLineRecyclerview.layoutManager=layoutManager
        val data : ArrayList<QueryBusData> = bus.queryBus(editBus,0)//得到路线数据，默认都为下行
        val join : ArrayList<QueryBusData> = ArrayList()
        join.addAll(data)
        println("-=============车站:"+join[0].getStationName())
        adapter = QueryBusLineAdapter(join)
        busLineRecyclerview.adapter = adapter

        //显示固定页的初始站和终点站路线和时间信息
        val stationList:ArrayList<String> = bus.queryStartOver(editBus,0)
        if (stationList.size<4){
            Toast.makeText(this.context,"oops！这路车线有缺失", Toast.LENGTH_SHORT).show()

        }else {
            stationStart.text = stationList.get(0)+"（始发站）"
            stationStartArrivalTime.text = ""
            stationStartLeaveTime.text = stationList.get(1)
            stationOver.text = stationList.get(2)+"（终点站）"
            stationOverArrivalTime.text = stationList.get(3)
            //stationOverLeaveTime.text = stationList.get(5)
        }

        //显示汽车信息recyclerview
        val layoutManagerBus= LinearLayoutManager(context)
        layoutManagerBus.orientation= LinearLayoutManager.VERTICAL    //垂直排列
        busInfoRecyclerView.layoutManager=layoutManagerBus
        val busdata:ArrayList<QueryStationData> = bus.queryBusInfo(editBus,0)//得到查询的汽车信息数据
        val joinbus : ArrayList<QueryStationData> = ArrayList()
        joinbus.addAll(busdata)
        adapterbus = QueryBusInfoAdapter(joinbus)
        busInfoRecyclerView.adapter=adapterbus

    }


}