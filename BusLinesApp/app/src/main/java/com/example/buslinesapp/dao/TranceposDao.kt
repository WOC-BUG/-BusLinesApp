package com.example.buslinesapp.dao

import com.example.buslinesapp.model.data.BaseData
import com.example.buslinesapp.model.data.Relation

class TranceposDao(var dbHelper : DatabaseHelper?) {

    //插入数据
    fun addTrancepos(stationID: Int, busID: Int, arriveTime: String, leaveTime: String){
        val db = dbHelper?.writableDatabase
        val sql = "insert into Trancepos values(null, $stationID, $busID, '$arriveTime', '$leaveTime')"
        db?.execSQL(sql)
    }

    fun initTable(obj: BaseData){
        val db = dbHelper?.writableDatabase
        val rel = obj as Relation
        val id = rel.getID()
        val stationID = rel.getStationID()
        val busID= rel.getBusID()
        val arriveTime=rel.getArriveTime()
        val leaveTime = rel.getLeaveTime()
        val sql = "insert into Trancepos values($id, $stationID, $busID, '$arriveTime', '$leaveTime')"
        db?.execSQL(sql)
    }

    //查询某车是否经过某站点
    fun exist(staionId:Int, busId:Int):Boolean{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Trancepos where stationID = '$staionId' and busID = '$busId' ", null)
        var exist = false

        if (cursor != null) {
            if (cursor.count != 0) {
                exist = true
            }
        }
        return exist

    }

}