package com.example.buslinesapp.dao

import android.text.TextUtils.join
import com.example.buslinesapp.model.data.BaseData
import com.example.buslinesapp.model.data.Bus
import com.example.buslinesapp.model.data.QueryBusData
import com.example.buslinesapp.model.data.QueryStationData
import com.example.buslinesapp.utils.StringUtil

import java.lang.String.join

class BusDao(var dbHelper : DatabaseHelper?) {

    //插入数据
    fun addBus(name: String, type: String, start: String, terminal: String, line: String) {
        val db = dbHelper?.writableDatabase
        val sql = "insert into Bus values(null, '$name', '$type', '$start', '$terminal','$line')"
        db?.execSQL(sql)
    }

    fun initTable(obj: BaseData) {
        val db = dbHelper?.writableDatabase
        val bus = obj as Bus
        val id = bus.getID()
        val name = bus.getName()
        val type = bus.getType()
        val start = bus.getStart()
        val terminal = bus.getTerminal()
        val line = bus.getBusLine()
        val sql = "insert into Bus values($id, '$name', '$type', '$start', '$terminal','$line')"
        db?.execSQL(sql)
    }

    //检查是否存在查询的汽车名
    fun query(busName: String): Boolean {
        val db = dbHelper?.writableDatabase
        val selectionArgs = arrayOf("id", "busName", "busType", "start", "terminal", "line")
        val cursor =
            db?.query("Bus", selectionArgs, "busName=?", arrayOf(busName), null, null, null)
        if (cursor?.count != 0) {
            return true;
        }
        return false;
    }

    //根据汽车名查询汽车路线
    fun queryBusLine(busName: String, direction: Int): ArrayList<String> {

        val db = dbHelper?.writableDatabase
        var lineString: String = ""
        var lineList = ArrayList<String>()
        var sql: String = ""
        if (direction == 0) {//下行
            sql =
                "select line from Bus,Trancepos,Station where Bus.busName=? and Bus.id = Trancepos.busID " +
                        "and Station.id=Trancepos.stationID and Station.direction=0"
        } else {
            sql =
                "select line from Bus,Trancepos,Station where Bus.busName=? and Bus.id = Trancepos.busID " +
                        "and Station.id=Trancepos.stationID and Station.direction=1"
        }
        val cursor = db?.rawQuery(sql, arrayOf(busName))//查询汽车路线

        if (cursor == null) {
            println("查询为空")
            return lineList
        }

        if (cursor.moveToFirst()) {
            do {
                //取出数据,调用cursor.getInt/getString等方法
                //得到路线数据
                lineString = cursor.getString(cursor.getColumnIndex("line"))
                lineList = StringUtil().split(lineString, "-")//分隔得到的路线字符串
                println("成功")
                println(lineString)
                for (i in lineList) {
                    println(i)
                }
                return lineList

            } while (cursor.moveToNext());
        }
        cursor.close();

        return lineList
    }

    //根据汽车名查询汽车路线和时间信息
    fun queryBus(busName: String, direction: Int): ArrayList<QueryBusData> {
        val dataList = ArrayList<QueryBusData>()
        val db = dbHelper?.writableDatabase
        val linelist: ArrayList<String> = queryBusLine(busName, direction)//得到汽车路线（经过的各个站点）

        if (linelist == null) {
            println("查询为空")
            return dataList
        }
        var sql: String = ""
        if (direction == 0) {//下行
            for (i in (1..linelist.size - 2)) {  // (i in (1..linelist.size-2)) 去掉初始站和终点站，查询中间经过的各个站

                sql =
                    "select busName,busType,start,terminal,name,arrivalTime,leaveTime,Trancepos.id " +
                            "from Bus,Trancepos,Station where Bus.busName=? " +
                            "and Station.name=? and Bus.id = Trancepos.busID and Station.id=Trancepos.stationID " +
                            "and direction=0 order by Trancepos.id limit 0,1 "
                val cursor = db?.rawQuery(sql, arrayOf(busName, linelist.get(i)))//查询汽车对应站的时间信息

                if (cursor == null) {
                    println("查询为空")
                    return dataList
                }
                //输出查询到的数据
                if (cursor.moveToFirst()) {
                    do {
                        val data = QueryBusData()
                        //取出数据,调用cursor.getInt/getString等方法

                        data.setStationName(cursor.getString(cursor.getColumnIndex("name")))
                        data.setArrivalTime(cursor.getString(cursor.getColumnIndex("arrivalTime")))
                        data.setLeaveTime(cursor.getString(cursor.getColumnIndex("leaveTime")))
                        println(
                            "到达时间" + cursor.getString(cursor.getColumnIndex("arrivalTime"))
                                    + "出发时间" + cursor.getString(cursor.getColumnIndex("leaveTime"))
                        )
                        dataList.add(data)


                    } while (cursor.moveToNext());
                }
                cursor.close();

            }
        } else {//上行
            sql =
                "select busName,busType,start,terminal,name,arrivalTime,leaveTime from Bus,Trancepos,Station where Bus.busName=? and Bus.id = Trancepos.busID and Station.id=Trancepos.stationID and Station.name=direction=1"
        }


        return dataList
    }

    //根据汽车名查询车站的起始站和终点站
    fun queryStartOver(busName: String,direction: Int):ArrayList<String>{
        val stationlist=ArrayList<String>()
        val db = dbHelper?.writableDatabase
        var sqlstart: String = ""
        var sqlterminal:String=""
        if(direction==0){//下行
            //起始站查询
            sqlstart="select start,terminal,name,arrivalTime,leaveTime from Bus,Trancepos,Station " +
                    "where Bus.busName=? and Bus.start=Station.name and Bus.id = Trancepos.busID " +
                    "and Station.id=Trancepos.stationID and station.direction=0"
            //终点站查询
            sqlterminal= "select start,terminal,name,arrivalTime,leaveTime from Bus,Trancepos,Station " +
                    "where Bus.busName=? and Bus.terminal=Station.name and Bus.id = Trancepos.busID " +
                    "and Station.id=Trancepos.stationID and station.direction=0"
        }
        val cursor = db?.rawQuery(sqlstart, arrayOf(busName))//查询始发站信息

        if (cursor == null) {
            println("查询为空")
            return stationlist
        }
        if(cursor.moveToFirst()){
            do{
                //取出起始站的站名和到站发站时间

                var start=cursor.getString(cursor.getColumnIndex("start"))
                var arrivalTime=cursor.getString(cursor.getColumnIndex("arrivalTime"))
                var leaveTime=cursor.getString(cursor.getColumnIndex("leaveTime"))
                stationlist.add(start)
                //stationlist.add(arrivalTime)
                stationlist.add(leaveTime)

                println("始发站========"+start+arrivalTime+leaveTime)

            }while (cursor.moveToNext())
        }
        cursor.close();
        val cursorover=db?.rawQuery(sqlterminal, arrayOf(busName))//查询终点站信息
        if (cursorover == null) {
            println("查询为空")
            return stationlist
        }
        if(cursorover.moveToFirst()){
            do{
                //取出终点站对应的站名和到站出站时间
                var terminal=cursorover.getString(cursorover.getColumnIndex("terminal"))
                var arrivalTime=cursorover.getString(cursorover.getColumnIndex("arrivalTime"))
                var leaveTime=cursorover.getString(cursorover.getColumnIndex("leaveTime"))
                stationlist.add(terminal)
                stationlist.add(arrivalTime)
                //stationlist.add(leaveTime)
                println("终点站=========="+terminal+arrivalTime+leaveTime)
            }while (cursorover.moveToNext())
        }
        cursorover.close();

        for (i in stationlist) {
            println("========这是起始站和终点站"+i)
        }
        return stationlist
    }

    //通过汽车名查询汽车信息（如汽车名、汽车类型等）
    fun queryBusInfo(busName: String,direction: Int):ArrayList<QueryStationData> {
        var buslist = ArrayList<QueryStationData>()
        val db = dbHelper?.writableDatabase
        var sql=""

        if (direction == 0) {//下行路线
            sql = "select busName,busType,leaveTime from Bus,Trancepos,Station where busName=? " +
                    "and Bus.id=Trancepos.busID and Station.id=Trancepos.stationID and direction=0"
        } else {
            sql = "select busName,busType,leaveTime from Bus,Trancepos,Station where busName=? " +
                    "and Bus.id=Trancepos.busID and Station.id=Trancepos.stationID and direction=1"
        }
        val cursor=db?.rawQuery(sql, arrayOf(busName))
        if(cursor!=null){
            if(cursor.moveToFirst()){
                    do {
                        //车次信息
                        var data = QueryStationData()
                        data.setBusName(cursor.getString(cursor.getColumnIndex("busName")))
                        data.setBusType(cursor.getString(cursor.getColumnIndex("busType")))
                        data.setLeaveTime(cursor.getString(cursor.getColumnIndex("leaveTime")))
                        buslist.add(data)
                        println("车次信息============"+cursor.getString(cursor.getColumnIndex("busName"))+
                        cursor.getString(cursor.getColumnIndex("busType"))
                        +cursor.getString(cursor.getColumnIndex("leaveTime")))
                    }while (cursor.moveToNext())
            }
        }
        return buslist
    }

    //查询是否存在某车次
    fun exist(name: String,start: String,terminal:String): Int {
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Bus where busName = '$name' " +
                "and start = '$start' and terminal = '$terminal'", null)
        //var exist = false
        var resId:Int = -1

        if (cursor != null) {
            if (cursor.count != 0) {
                //exist = true
                cursor.moveToFirst()
                resId = cursor.getInt(cursor.getColumnIndex("id"))
            }
        }
        //return exist
        return resId


    }

    fun getCount(): Int {
        val db = dbHelper?.writableDatabase
        val sql = db?.rawQuery("select * from Bus ", null)
        var count = 0
        if (sql?.count != 0)
            count = sql!!.count
        sql.close()
        return count

    }
}