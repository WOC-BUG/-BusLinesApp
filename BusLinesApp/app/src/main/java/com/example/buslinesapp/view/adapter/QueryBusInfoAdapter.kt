package com.example.buslinesapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.model.data.QueryStationData
import com.example.buslinesapp.view.holder.BaseViewHolder
import com.example.buslinesapp.view.holder.QueryBusInfoHolder
import com.example.buslinesapp.view.holder.QueryStationHolder
import kotlinx.android.synthetic.main.bus_item.view.*
import kotlinx.android.synthetic.main.bus_item.view.carNameItem
import kotlinx.android.synthetic.main.bus_item.view.carTypeItem
import kotlinx.android.synthetic.main.bus_single_item.view.*

class QueryBusInfoAdapter(private val queryBusInfoList : List<QueryStationData>):BaseAdapter(queryBusInfoList) {

    var dbHelper : DatabaseHelper? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryBusInfoHolder {
        setResource(R.layout.bus_single_item)
        val view= LayoutInflater.from(parent.context).inflate(resourceId, parent,false)
        dbHelper = DatabaseHelper(parent.context, "BusLineData.db", 1)
        return QueryBusInfoHolder(view)
    }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as QueryBusInfoHolder
        val data = queryBusInfoList[position]
        holder.dataItem.carNameItem.text = data.getBusName()
        holder.dataItem.carTypeItem.text=data.getBusType()
        holder.dataItem.leaveTimeItem.text=data.getLeaveTime()
    }
}