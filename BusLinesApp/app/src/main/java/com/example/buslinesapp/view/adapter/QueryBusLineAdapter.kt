package com.example.buslinesapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.model.data.QueryBusData
import com.example.buslinesapp.view.holder.BaseViewHolder
import com.example.buslinesapp.view.holder.QueryBusLineHolder
import kotlinx.android.synthetic.main.bus_line_item.view.*


class QueryBusLineAdapter(private val queryBusDataList : List<QueryBusData>) :BaseAdapter(queryBusDataList){
    var dbHelper : DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryBusLineHolder {
        setResource(R.layout.bus_line_item)
        val view= LayoutInflater.from(parent.context).inflate(resourceId, parent,false)
        dbHelper = DatabaseHelper(parent.context, "BusLineData.db", 1)
        return QueryBusLineHolder(view)
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as QueryBusLineHolder
        val data = queryBusDataList[position]
        holder.dataItem.stationName.text = data.getStationName()
        holder.dataItem.stationArriavalTime.text=data.getArriveTime()
        holder.dataItem.stationLeaveTime.text=data.getLeaveTime()
        println("========================名字:"+data.getStationName())

    }



}