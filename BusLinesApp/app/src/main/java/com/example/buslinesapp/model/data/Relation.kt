package com.example.buslinesapp.model.data

//车次车站关系
class Relation: BaseData() {
    private var ID: Int = 0      //编号
    private var stationID :Int = 0    //车站id
    private var busID :Int = 0   //汽车id
    private lateinit var arrivalTime :String      //到站时间
    private lateinit var leaveTime :String   //离站时间

    fun setID(id:Int){
        this.ID = id
    }
    fun setStationID(sID:Int){
        this.stationID = sID
    }
    fun setBusID(bID:Int){
        this.busID = bID
    }
    fun setArrivalTime(aTime:String){
        this.arrivalTime = aTime
    }
    fun setLeaveTime(lTime:String){
        this.leaveTime = lTime
    }

    fun getID():Int{
        return ID
    }
    fun getStationID():Int{
        return stationID
    }
    fun getBusID():Int{
        return busID
    }
    fun getArriveTime():String{
        return arrivalTime
    }
    fun getLeaveTime():String{
        return leaveTime
    }

}