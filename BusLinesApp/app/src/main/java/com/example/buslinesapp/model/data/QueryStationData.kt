package com.example.buslinesapp.model.data

class QueryStationData:BaseData() {
    private lateinit var busName : String
    private lateinit var busType : String
    private lateinit var start : String
    private lateinit var transfer: String
    private lateinit var terminal : String
    private lateinit var arrivalTime : String   // 到站时间
    private lateinit var leaveTime :String   //离站时间
    private lateinit var busLine:String  //公交线路

    fun setBusName(name: String){
        this.busName = name
    }
    fun setBusType(type: String){
        this.busType = type
    }
    fun setStart(start: String){
        this.start = start
    }
    fun setTransfer(transfer: String){
        this.transfer = transfer
    }
    fun setTerminal(terminal: String){
        this.terminal = terminal
    }
    fun setArrivalTime(arrivalTime: String){
        this.arrivalTime = arrivalTime
    }
    fun setLeaveTime(leaveTime: String){
        this.leaveTime = leaveTime
    }
    fun setBusLine(line:String){
        this.busLine = line
    }

    fun getBusName(): String{
        return this.busName
    }
    fun getBusType(): String{
        return this.busType
    }
    fun getStart(): String{
        return this.start
    }
    fun getTransfer(): String{
        return this.transfer
    }
    fun getTerminal(): String{
        return this.terminal
    }
    fun getArrivalTime(): String{
        return this.arrivalTime
    }
    fun getLeaveTime(): String{
        return this.leaveTime
    }
    fun getBusLine():String{
        return this.busLine
    }
}