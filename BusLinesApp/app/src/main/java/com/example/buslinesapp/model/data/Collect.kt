package com.example.buslinesapp.model.data

import android.widget.Button

class Collect:BaseData() {


    private lateinit var button: Button  //按钮
    /**
     * 单例模式，使用方法:
     * CurUser.instance.方法()
     */
    companion object{
        val instance by lazy {
            Collect()
        }
    }


    private var busName :String=""  //公交车名
    private var ID:Int = 0
    private var userID = 0
    private lateinit var startStation :String    //始发站
    private lateinit var endStation :String    //终点站

    fun setBusName(BusName:String){
        this.busName=BusName
    }
    fun setID(id:Int){
        this.ID = id
    }
    fun setStartStation(name:String){
        this.startStation = name
    }
    fun setEndStation(name:String){
        this.endStation=name
    }

    fun getBusName():String{
        return this.busName
    }
    fun getID():Int{
        return  this.ID
    }
    fun getStartStation():String{
        return  this.startStation
    }
    fun getEndStation():String{
        return this.endStation
    }
}
