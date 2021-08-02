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
import com.example.buslinesapp.view.holder.QueryMiddleStationHolder
import kotlinx.android.synthetic.main.bus_item_middle.view.*

class QueryMiddleStationAdapter(private val queryStationDataList : List<QueryStationData>) : BaseAdapter(queryStationDataList){
    var dbHelper : DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryMiddleStationHolder {
        setResource(R.layout.bus_item_middle)
        val view= LayoutInflater.from(parent.context).inflate(resourceId, parent,false)
        dbHelper = DatabaseHelper(parent.context, "BusLineData.db", 1)
        return QueryMiddleStationHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as QueryMiddleStationHolder

        val data = queryStationDataList[position]
        holder.dataItemMiddle.stationBusNumber.text = data.getBusName()
        holder.dataItemMiddle.firstStation.text = data.getStart()
        holder.dataItemMiddle.middleStation.text = data.getTransfer()
        holder.dataItemMiddle.lastStation.text = data.getTerminal()
        holder.dataItemMiddle.carNameItem.text = data.getBusName()
        holder.dataItemMiddle.carTypeItem.text = data.getBusType()
        holder.dataItemMiddle.arriveTimeItem.text = data.getArrivalTime()

        holder.dataItemMiddle.stationBusNumber.setOnClickListener {

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