package com.example.buslinesapp.model.data

open class User: BaseData() {
    private var userID: Int = 0      //用户id
    private var userName :String = ""    //用户名
    private var password :String = ""    //密码

    fun setID(id:Int){
        this.userID = id
    }
    fun setName(name:String){
        this.userName = name
    }
    fun setPassword(password:String){
        this.password=password
    }

    fun getID():Int{
        return this.userID
    }
    fun getName():String{
        return this.userName
    }
    fun getPassword():String{
        return this.password
    }
}