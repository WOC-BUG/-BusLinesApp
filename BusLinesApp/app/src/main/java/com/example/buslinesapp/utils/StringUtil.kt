package com.example.buslinesapp.utils

class StringUtil {

    //按分隔符sep分隔字符串
    fun split(string:String,sep:String):ArrayList<String>{

       return string.split(sep) as ArrayList<String>

    }

    //拼接字符串
    fun join(array:ArrayList<String>):String{
        var longStr:String = array[0]
        var next:String
        for(i in 1 until array.size){
            next = array[i]
            longStr = "$longStr-$next"
        }
        return longStr
    }

    //判断输入的时间是否合法
    fun timeValidity(time:String):Boolean{

        val regex = Regex("([0-1][0-9]|2[0-3]):([0-5][0-9])")

        return regex.matches(time)
    }

    //将ArrayList<String>倒序
    fun reverseList(list:ArrayList<String>):ArrayList<String>{
        val reversedList = ArrayList<String>()
        for(i in list.size-1 downTo 0)
            reversedList.add(list[i])

        return reversedList
    }
}