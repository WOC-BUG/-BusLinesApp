package com.example.buslinesapp.utils

import android.content.res.AssetManager
import com.example.buslinesapp.model.data.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FileUtil(val asset:AssetManager){

    private var resList = ArrayList<BaseData>()
    private lateinit var scan : InputStreamReader
    private lateinit var reader :BufferedReader

    fun readFile(filename:String):ArrayList<BaseData>{

        try {
            scan = InputStreamReader(asset.open(filename),"GBK")
            reader = BufferedReader(scan)
            reader.readLine()   //忽略表头
            var line:String?
            do{
                line =  reader.readLine()
                if(line == null)
                    break
                else{
                    val list = StringUtil().split(line,",")
                    when(filename) {
                        "user.csv" -> {
                            val user=User()
                            user.setID(list[0].toInt())
                            user.setName(list[1])
                            user.setPassword(list[2])

                            resList.add(user)
                        }
                        "bus.csv" -> {
                            val bus = Bus()
                            bus.setID(list[0].toInt())
                            bus.setName(list[1])
                            bus.setType(list[2])
                            bus.setStart(list[3])
                            bus.setTerminal(list[4])

                            val lineStr = list[5]
                            bus.setBusLine(lineStr)

                            resList.add(bus)
                        }

                        "station.csv" -> {
                            val station = Station()
                            station.setID(list[0].toInt())
                            station.setName(list[1])
                            station.setShortName(list[2])
                            station.setDirection(list[3].toInt())

                            resList.add(station)
                        }
                        "trancepos.csv" -> {
                            val trans=Relation()
                            trans.setID(list[0].toInt())
                            trans.setStationID(list[1].toInt())
                            trans.setBusID(list[2].toInt())
                            trans.setArrivalTime(list[3])
                            trans.setLeaveTime(list[4])

                            resList.add(trans)
                        }
                    }
                }
            }while (true)

            scan.close()

        }catch (e: IOException) {
            e.printStackTrace()
        }

        return resList
    }

}