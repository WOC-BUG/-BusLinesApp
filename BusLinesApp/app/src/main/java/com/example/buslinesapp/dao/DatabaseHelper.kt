package com.example.buslinesapp.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(val context: Context, name:String, version:Int):         //第二个参数为数据库名
    SQLiteOpenHelper(context,name,null,version) {       //参数三：查询数据用的Cursor，参数四：版本号

    //公共汽车属性表
    private val createBus = "create table if not exists Bus (" +
            "id integer primary key autoincrement," +
            "busName text not null," +
            "busType text," +
            "start text not null,"+
            "terminal text not null,"+
            "line text not null)"
    //车站属性表
    private val createStation = "create table if not exists Station (" +
            "id integer primary key autoincrement," +
            "name text not null," +
            "shortName text," +
            "direction integer not null)"

    //汽车与车站关系表
    private val createTrancepos = "create table if not exists Trancepos (" +
            "id integer primary key autoincrement," +
            "stationID integer not null," +
            "busID integer not null," +
            "arrivalTime text," +
            "leaveTime text)"

    //用户表
    private val createUser = "create table if not exists User (" +
            "id integer primary key autoincrement," +
            "hostName text unique," +
            "password text not null)"

    //收藏记录表
    private val createCollect = "create table if not exists Collect (" +
            "id integer primary key autoincrement," +
            "userID integer not null," +
            "departure text not null," +
            "target text not null)"

    //创建数据库
    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL("drop table Station",null)
        db.execSQL(createUser)
        db.execSQL(createBus)
        db.execSQL(createStation)
        db.execSQL(createTrancepos)
        db.execSQL(createCollect)

        Log.d("DatabaseHelper","onCreate succeessed!")
    }

    //数据库升级
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}