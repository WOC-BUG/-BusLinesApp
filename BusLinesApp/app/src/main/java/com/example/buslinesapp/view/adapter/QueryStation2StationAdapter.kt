package com.example.buslinesapp.view.adapter

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.model.data.QueryStationData
import com.example.buslinesapp.view.fragment.BusLineQueryFragment
import com.example.buslinesapp.view.fragment.QueryBusFragment
import com.example.buslinesapp.view.holder.BaseViewHolder
import com.example.buslinesapp.view.holder.QueryStation2StationHolder
import kotlinx.android.synthetic.main.bus_item2.view.*

class QueryStation2StationAdapter(private val queryStationDataList : List<QueryStationData>) : BaseAdapter(queryStationDataList){
    var dbHelper : DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryStation2StationHolder {
        setResource(R.layout.bus_item2)
        val view= LayoutInflater.from(parent.context).inflate(resourceId, parent,false)
        dbHelper = DatabaseHelper(parent.context, "BusLineData.db", 1)
        return QueryStation2StationHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as QueryStation2StationHolder

        val data = queryStationDataList[position]
        holder.dataItem.stationBusNumber.text = data.getBusName()
        holder.dataItem.firstStation.text = data.getStart()
        holder.dataItem.nextStation.text = data.getTerminal()
        holder.dataItem.carNameItem.text = data.getBusName()
        holder.dataItem.departureTimeItem.text = data.getLeaveTime()    // 起点站的离站时间就是发车时间
        holder.dataItem.arriveTimeItem.text = data.getArrivalTime()   // 当前站的到站时间

        holder.dataItem.stationBusNumber.setOnClickListener{

            // 设置要传递的参数——线路名
            val bundle = Bundle()
            bundle.putString("busName",data.getBusName())
            val queryBusFragment = QueryBusFragment()
            queryBusFragment.arguments = bundle

            // 切换fragment
            BusLineQueryFragment.instance.setRadioButtonChecked("busQuery")
            BusLineQueryFragment.instance.replaceFragment(queryBusFragment)
        }
    }
}