package com.example.buslinesapp.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.model.data.QueryStationData
import com.example.buslinesapp.utils.StringUtil
import com.example.buslinesapp.view.fragment.BusLineQueryFragment
import com.example.buslinesapp.view.fragment.QueryBusFragment
import com.example.buslinesapp.view.holder.BaseViewHolder
import com.example.buslinesapp.view.holder.QueryStationHolder
import kotlinx.android.synthetic.main.bus_item.view.*

class QueryStationAdapter(private val queryStationDataList : List<QueryStationData>) : BaseAdapter(queryStationDataList){
    var dbHelper : DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryStationHolder {
        setResource(R.layout.bus_item)
        val view= LayoutInflater.from(parent.context).inflate(resourceId, parent,false)
        dbHelper = DatabaseHelper(parent.context, "BusLineData.db", 1)
        return QueryStationHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as QueryStationHolder

        val data = queryStationDataList[position]
        holder.dataItem.stationBusNumber.text = data.getBusName()
        holder.dataItem.firstStation.text = data.getStart()
        holder.dataItem.lastStation.text = data.getTerminal()
        holder.dataItem.carNameItem.text = data.getBusName()
        holder.dataItem.carTypeItem.text = data.getBusType()
        holder.dataItem.arriveTimeItem.text = data.getArrivalTime()

        val lineList: ArrayList<String> = StringUtil().split(data.getBusLine(),"-")
        holder.dataItem.middleStation.text = (lineList.size -2).toString()

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