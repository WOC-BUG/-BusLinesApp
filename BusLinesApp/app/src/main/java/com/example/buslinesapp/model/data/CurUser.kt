package com.example.buslinesapp.model.data

class CurUser :BaseData(){

    /**
     * 单例模式，使用方法:
     * CurUser.instance.方法()
     */
    companion object{
        val instance by lazy {
            CurUser()
        }
    }

    private var userID: Int = 0      //用户id
    private lateinit var userName :String    //用户名
    private lateinit var password :String    //密码

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
        return  this.userID
    }
    fun getName():String{
        return  this.userName
    }
    fun getPassword():String{
        return this.password
    }
}