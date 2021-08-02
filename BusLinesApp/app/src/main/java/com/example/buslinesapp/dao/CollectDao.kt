package com.example.buslinesapp.dao

import com.example.buslinesapp.model.data.Collect

//收藏表管理类
class CollectDao(var dbHelper : DatabaseHelper?) {

    //插入数据
    fun addCollect(userID:Int, start:String, target:String){
        val db = dbHelper?.writableDatabase
        val sql = "insert into Collect values(null, $userID , '$start', '$target')"
        db?.execSQL(sql)
    }
    //初始化表添加预存数据
    fun initTable(){
        val db = dbHelper?.writableDatabase

        //ruangong的历史收藏
        val sql = "insert into Collect values(null, 3 , '传媒大学', '天安门')"
        val sql2 = "insert into Collect values(null, 3 , '青年路', '白石桥南')"

        //user的历史收藏
        val insert = "insert into Collect values(null, 2 , '传媒大学', '国贸')"
        db?.execSQL(insert)
        db?.execSQL(sql)
        db?.execSQL(sql2)
    }

    //删除某条收藏记录
    fun delete(userID:Int, s1:String,s2:String){
        val db = dbHelper?.writableDatabase

        db?.execSQL("delete from Collect where userID = $userID and departure = '$s1' and target = '$s2'")

    }

    //查询历史收藏
    fun query(userID:Int):ArrayList<Collect>{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Collect where userID = $userID" ,null)
        val allCollect = ArrayList<Collect>()

        if(cursor?.moveToFirst()!!){
            do{
                val collect = Collect()
                collect.setStartStation(cursor.getString(cursor.getColumnIndex("departure")))
                collect.setEndStation(cursor.getString(cursor.getColumnIndex("target")))
                allCollect.add(collect)

            }while (cursor.moveToNext())
        }
        cursor.close()

        return allCollect

    }
    //查询某路线是否收藏过
    fun exist(id:Int,s1:String,s2:String):Boolean{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Collect where userID = $id and departure = '$s1' and target = '$s2' ", null)
        if (cursor?.count != 0) {
            return true
        }
        return false
    }
}