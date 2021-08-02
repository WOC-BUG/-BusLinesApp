package com.example.buslinesapp.dao

import com.example.buslinesapp.model.data.BaseData
import com.example.buslinesapp.model.data.QueryStationData
import com.example.buslinesapp.model.data.Station
import com.example.buslinesapp.utils.StringUtil

class StationDao(var dbHelper : DatabaseHelper?) {

    //插入数据
    fun addStation(name:String, shortName:String, direction:Int){
        val db = dbHelper?.writableDatabase
        val sql = "insert into Station values(null, '$name', '$shortName', $direction)"
        db?.execSQL(sql)
    }

    fun initTable(obj: BaseData){
        val db = dbHelper?.writableDatabase
        val station = obj as Station
        val id = station.getID()
        val name = station.getName()
        val shortName = station.getShortName()
        val direction = station.getDirection()
        val sql = "insert into Station values($id, '$name', '$shortName', $direction)"
        db?.execSQL(sql)
    }
    //根据名称和方向查询车站ID
    fun getStationId(name: String,dire:Int):Int{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Station " +
                "where name = '$name' and direction = '$dire'" ,null)
        var res:Int = -1

        if(cursor?.moveToFirst()!!){
            do{
                res = cursor.getInt(cursor.getColumnIndex("id"))
            }while (cursor.moveToNext())
        }
        cursor.close()

        return res
    }
    //根据
    fun getDirecByID(id:Int):Int{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Station where id = '$id' " ,null)
        var direc:Int = -1

        if(cursor != null){
            cursor.moveToFirst()
            direc = cursor.getInt(cursor.getColumnIndex("direction"))
        }
        cursor?.close()
        return direc
    }

    // 查询车站名
    fun getIdByName(name:String):ArrayList<Int> {
        val db = dbHelper?.writableDatabase
        val selectionArgs = arrayOf("id","name","shortName","direction")
        val selectionArgs2 = arrayOf(name)
        val list = ArrayList<Int>()
        val cursor = db?.query("Station",selectionArgs,"name=?",selectionArgs2,null,null,null)

        if(cursor!=null){
            if(cursor.moveToFirst()) {
                do {
                    list.add(cursor.getInt(cursor.getColumnIndex("id")))
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()

        return list
    }
    // 查询是否已经存在一样的数据
    fun query(name:String): Boolean {
        val db = dbHelper?.writableDatabase
        val selectionArgs = arrayOf("id","name","shortName","direction")
        val selectionArgs2 = arrayOf(name)
        val cursor = db?.query("Station",selectionArgs,"name=?",selectionArgs2,null,null,null)
        if(cursor?.count != 0){
            return true
        }
        return false
    }

    // 查询该站点所有路线信息
    fun queryStation(name: String, ifStart: Boolean) : ArrayList<QueryStationData>{
        val dataList = ArrayList<QueryStationData>()
        val db = dbHelper?.writableDatabase

        var sql: String = ""
        if(ifStart){
            sql="select busName,busType,start,terminal,arrivalTime,line from Bus,Trancepos where Bus.start=? and Bus.id = Trancepos.busID"
        }
        else{
            sql="select busName,busType,start,terminal,arrivalTime,line from Bus,Trancepos where Bus.terminal=? and Bus.id = Trancepos.busID"
        }
        val args= arrayOf(name)
        val cursor=db?.rawQuery(sql,args)

        if(cursor==null){
            println("查询结果为空！")
            return dataList
        }
        //输出查询到的数据
        cursor.moveToNext();
        while (!cursor.isAfterLast) {
            val data = QueryStationData()
            data.setBusName(cursor.getString(cursor.getColumnIndex("busName")))
            data.setBusType(cursor.getString(cursor.getColumnIndex("busType")))
            data.setStart(cursor.getString(cursor.getColumnIndex("start")))
            data.setTerminal(cursor.getString(cursor.getColumnIndex("terminal")))
            data.setArrivalTime(cursor.getString(cursor.getColumnIndex("arrivalTime")))
            data.setBusLine(cursor.getString(cursor.getColumnIndex("line")))
            dataList.add(data)

            println("name="+cursor.getString(cursor.getColumnIndex("busName"))
            +",busType="+cursor.getString(cursor.getColumnIndex("busType"))
            +",start="+cursor.getString(cursor.getColumnIndex("start"))
            +",terminal="+cursor.getString(cursor.getColumnIndex("terminal"))
            +",arrivalTime="+cursor.getString(cursor.getColumnIndex("arrivalTime"))
            +",line="+cursor.getString(cursor.getColumnIndex("line")))

            cursor.moveToNext();
        }

        return dataList
    }

    // 查询所有包含中转站的路线
    fun queryMiddleStation(name: String): ArrayList<QueryStationData>{
        val dataList = ArrayList<QueryStationData>()
        val db = dbHelper?.writableDatabase

        val sql = "select busName,busType,start,terminal,line,arrivalTime,leaveTime from Bus,Trancepos,Station where Bus.id = Trancepos.busID and Station.id = Trancepos.stationID"
        val cursor=db?.rawQuery(sql,null)

        if(cursor==null){
            println("查询结果为空！")
            return dataList
        }

        cursor.moveToNext()
        while (!cursor.isAfterLast) {
            val data = QueryStationData()

            // 确定该线路是否存在这个中转站
            val stringList: ArrayList<String> = StringUtil().split(cursor.getString(cursor.getColumnIndex("line")),"-")
            var ifHasStation: Boolean = false
            for(i in (1 until (stringList.size-1))){    // 遍历除了起始站和终点站以外的范围
                if(stringList[i] == name){
                    ifHasStation=true
                    break;
                }
            }

            if(ifHasStation){
                data.setBusName(cursor.getString(cursor.getColumnIndex("busName")))
                data.setBusType(cursor.getString(cursor.getColumnIndex("busType")))
                data.setStart(cursor.getString(cursor.getColumnIndex("start")))
                data.setTransfer(name)
                data.setTerminal(cursor.getString(cursor.getColumnIndex("terminal")))
                data.setBusLine(cursor.getString(cursor.getColumnIndex("line")))
                data.setArrivalTime(cursor.getString(cursor.getColumnIndex("arrivalTime")))
                data.setLeaveTime(cursor.getString(cursor.getColumnIndex("leaveTime")))
                dataList.add(data)

                println("name="+cursor.getString(cursor.getColumnIndex("busName"))
                        +",busType="+cursor.getString(cursor.getColumnIndex("busType"))
                        +",start="+cursor.getString(cursor.getColumnIndex("start"))
                        +",terminal="+cursor.getString(cursor.getColumnIndex("terminal"))
                        +",arrivalTime="+cursor.getString(cursor.getColumnIndex("arrivalTime"))
                        +",leaveTime="+cursor.getString(cursor.getColumnIndex("leaveTime"))
                        +",line="+cursor.getString(cursor.getColumnIndex("line")))
            }

            cursor.moveToNext()
        }
        return dataList
    }





    // 站站查询
    fun queryStation2Station(name1: String, name2: String): ArrayList<QueryStationData>{
        val dataList = ArrayList<QueryStationData>()
        val db = dbHelper?.writableDatabase

        val sql = "select Bus.id as busId,busName,busType,start,terminal,arrivalTime,leaveTime,line from Bus,Trancepos,Station where Station.name=? and Bus.id = Trancepos.busID and Station.id = Trancepos.stationID"
        val args = arrayOf(name1)
        val cursor=db?.rawQuery(sql,args)

        if(cursor==null){
            println("查询结果为空！")
            return dataList
        }

        cursor.moveToNext()
        while (!cursor.isAfterLast) {
            val data = QueryStationData()

            // 确定该线路是否存在这两个站，且站1必须在站2前面
            val stringList: ArrayList<String> = StringUtil().split(cursor.getString(cursor.getColumnIndex("line")),"-")
            var ifHasStation1: Boolean = false
            var ifHasStation2: Boolean = false
            for(i in stringList){
                if(i == name1 && !ifHasStation2){   // 找到站1且还没找到站2
                    ifHasStation1=true
                }
                else if(i == name2 && ifHasStation1){   //找到站1和站2
                    ifHasStation2=true
                    break
                }
            }

            if(ifHasStation1 && ifHasStation2){
                data.setBusName(cursor.getString(cursor.getColumnIndex("busName")))
                data.setBusType(cursor.getString(cursor.getColumnIndex("busType")))
                data.setStart(name1)
                data.setTerminal(name2)
                data.setBusLine(cursor.getString(cursor.getColumnIndex("line")))
                data.setLeaveTime(cursor.getString(cursor.getColumnIndex("leaveTime")))


                val sql2 = "select busName,busType,start,terminal,arrivalTime,leaveTime,line from Bus,Trancepos,Station where Bus.id = ? and Station.name=? and Bus.id = Trancepos.busID and Station.id = Trancepos.stationID"
                val args2 = arrayOf(cursor.getString(cursor.getColumnIndex("busId")),name2)
                val cursor2=db?.rawQuery(sql2,args2)

                cursor2.moveToNext()
                while (!cursor2.isAfterLast) {
                    // 确定该线路是否存在这两个站，且站1必须在站2前面
                    val stringList: ArrayList<String> = StringUtil().split(cursor.getString(cursor.getColumnIndex("line")),"-")
                    var ifHasStation1: Boolean = false
                    var ifHasStation2: Boolean = false
                    for(i in stringList){
                        if(i == name1 && !ifHasStation2){   // 找到站1且还没找到站2
                            ifHasStation1=true
                        }
                        else if(i == name2 && ifHasStation1){   //找到站1和站2
                            ifHasStation2=true
                            break
                        }
                    }

                    if(ifHasStation1 && ifHasStation2){
                        data.setArrivalTime(cursor2.getString(cursor2.getColumnIndex("arrivalTime")))
                        println("arrivalTime="+ cursor2.getString(cursor2.getColumnIndex("arrivalTime")))
                    }
                    cursor2.moveToNext()
                }

                dataList.add(data)

                println("name="+cursor.getString(cursor.getColumnIndex("busName"))
                        +",busType="+cursor.getString(cursor.getColumnIndex("busType"))
                        +",start="+cursor.getString(cursor.getColumnIndex("start"))
                        +",terminal="+cursor.getString(cursor.getColumnIndex("terminal"))
//                        +",arrivalTime="+ cursor.getString(cursor.getColumnIndex("arrivalTime"))
                        +",leaveTime="+ cursor.getString(cursor.getColumnIndex("leaveTime"))
                        +",line="+cursor.getString(cursor.getColumnIndex("line")))
            }

            cursor.moveToNext()
        }

        return dataList
    }
}