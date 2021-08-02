package com.example.buslinesapp.model.data

class Bus: BaseData() {
    private var busID: Int = 0      //编号
    private lateinit var busName :String    //汽车名/车次
    private lateinit var busType :String    //汽车类型
    private lateinit var start :String      //始发站
    private lateinit var terminal :String   //终点站
    private lateinit var busLine:String  //公交线路

    fun setID(id:Int){
        this.busID = id
    }
    fun setName(name:String){
        this.busName = name
    }
    fun setType(type:String){
        this.busType = type
    }
    fun setStart(start:String){
        this.start = start
    }
    fun setTerminal(terminal:String){
        this.terminal = terminal
    }
    fun setBusLine(line:String){
        this.busLine = line
    }

    fun getID():Int{
        return busID
    }
    fun getName():String{
        return busName
    }
    fun getType():String{
        return busType
    }
    fun getStart():String{
        return start
    }
    fun getTerminal():String{
        return terminal
    }
    fun getBusLine():String{
        return this.busLine
    }
}