package com.example.buslinesapp.model.data

class Station: BaseData() {
    private var stationID: Int = 0      //编号
    private lateinit var name:String    //车站名
    private lateinit var shortName:String     //车站简称
    private var direction:Int = 1   //方向，默认0为下行(起始 → 终点)，1上行

    fun setID(id:Int){
        this.stationID = id
    }
    fun setName(name:String){
        this.name = name
    }
    fun setShortName(short:String){
        this.shortName = short
    }
    fun setDirection(direction:Int){
        this.direction = direction
    }

    fun getID():Int{
        return stationID
    }
    fun getName():String{
        return name
    }
    fun getShortName():String{
        return this.shortName
    }
    fun getDirection():Int{
        return direction
    }
}