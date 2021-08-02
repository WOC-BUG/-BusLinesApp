package com.example.buslinesapp.model.data

class QueryBusData:BaseData() {

    private lateinit var busName :String    //汽车名/车次
    private lateinit var busType :String    //汽车类型
    private lateinit var start :String      //始发站
    private lateinit var terminal :String   //终点站
    private lateinit var name:String    //车站名
    private lateinit var arrivalTime :String      //到站时间
    private lateinit var leaveTime :String   //离站时间

    fun setBusName(busName:String) {
        this.busName=busName
    }
    fun setBusType(type:String){
        this.busType = type
    }
    fun setStart(start:String){
        this.start = start
    }
    fun setTerminal(terminal:String){
        this.terminal = terminal
    }
    fun setStationName(name:String){
        this.name = name
    }
    fun setArrivalTime(aTime:String){
        this.arrivalTime = aTime
    }
    fun setLeaveTime(lTime:String){
        this.leaveTime = lTime
    }

    fun getBusName():String{
        return busName
    }
    fun getBusType():String{
        return busType
    }
    fun getStart():String{
        return start
    }
    fun getTerminal():String{
        return terminal
    }
    fun getStationName():String{
        return name
    }
    fun getArriveTime():String{
        return arrivalTime
    }
    fun getLeaveTime():String{
        return leaveTime
    }

}