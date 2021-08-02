package com.example.buslinesapp.view.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.model.data.Collect
import com.example.buslinesapp.view.activity.MainActivity
import com.example.buslinesapp.view.fragment.BusLineQueryFragment
import com.example.buslinesapp.view.fragment.QueryStationLineFragment
import com.example.buslinesapp.view.holder.BaseViewHolder
import com.example.buslinesapp.view.holder.CollectViewHolder
import kotlinx.android.synthetic.main.collect_item.view.*


class CollectAdapter (private val collectList:ArrayList<Collect>):BaseAdapter(collectList){

    var dbHelper : DatabaseHelper? = null
    lateinit var mContext:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        setResource(R.layout.collect_item)
        val view= LayoutInflater.from(parent.context).inflate(resourceId, parent,false)
        mContext = parent.context
        dbHelper = DatabaseHelper(parent.context, "BusLineData.db", 1)
        return CollectViewHolder(view)
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as CollectViewHolder
        val collect=collectList[position]

        //holder.collectItem.collect_bus_name.text=collect.getBusName()
        holder.collectItem.collect_start.text=collect.getStartStation()
        holder.collectItem.collect_end.text=collect.getEndStation()

        holder.collectItem.setOnClickListener {
            val stations = ArrayList<String>()
            stations.add(collectList[position].getStartStation())
            stations.add(collectList[position].getEndStation())
            val bundle = Bundle()
            bundle.putStringArrayList("stations",stations)
            val queryStationLineFragment = QueryStationLineFragment()
            queryStationLineFragment.arguments = bundle

            BusLineQueryFragment.instance.setRadioButtonChecked("stationToStationQuery")
            BusLineQueryFragment.instance.replaceFragment(queryStationLineFragment)
            (BusLineQueryFragment.instance.activity as MainActivity).setSelectedID(2)

            val activity1:Activity = mContext as Activity
            activity1.finish()


        }

    }

}