package com.example.buslinesapp.dao

import com.example.buslinesapp.model.data.BaseData
import com.example.buslinesapp.model.data.User

class UserDao(var dbHelper : DatabaseHelper?) {

    //插入数据
    fun addUser(name:String, password:String){
        val db = dbHelper?.writableDatabase
        val sql = "insert into User values(null, '$name', '$password')"
        db?.execSQL(sql)
    }

    fun initTable(obj: BaseData){
        val db = dbHelper?.writableDatabase
        val user = obj as User
        val id = user.getID()
        val name = user.getName()
        val password = user.getPassword()
        val sql = "insert into User values($id, '$name', '$password')"
        db?.execSQL(sql)
    }

    //根据name查询用户
    fun queryUser(userName:String): User {
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from User where hostName = '$userName'" ,null)

        val user = User()
        if(cursor?.moveToFirst()!!){
            do{
                user.setID(cursor.getInt(cursor.getColumnIndex("id")))
                user.setName(cursor.getString(cursor.getColumnIndex("hostName")))
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")))
            }while (cursor.moveToNext())
    }

        cursor.close()

        return user
    }

}