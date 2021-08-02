package com.example.buslinesapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.BusDao
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.dao.StationDao
import com.example.buslinesapp.model.data.Bus
import com.example.buslinesapp.view.activity.WebviewActivity
import kotlinx.android.synthetic.main.add_bus_fragment.*
import kotlinx.android.synthetic.main.advertisement.*

class AddBusFragment: Fragment() {

    private var onButtonClick: OnButtonClick? = null  //定义接口成员变量
    private var data: Bus? = null
    private var dbHelper: DatabaseHelper?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_bus_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        val busDao = BusDao(dbHelper)
        val stationDao = StationDao(dbHelper)

        var busName :String
        var type:String
        var start :String
        var terminal :String

        //如果接口成员变量不为空null，则调用接口变量的方法
        if (onButtonClick != null) {
            nextBtn.setOnClickListener {
                busName = inputBox1.text.toString()
                type = inputBox2.text.toString()
                start = inputBox3.text.toString()
                terminal = inputBox4.text.toString()
                var busValid = true  //标记表单输入是否合法

                val startExist = stationDao.query(start)
                val endExist = stationDao.query(terminal)
                if(busName !="" && busDao.exist(busName,start, terminal) != -1){
                    busValid = false
                    Toast.makeText(context, "汽车已存在,请重新输入！", Toast.LENGTH_SHORT).show()
                }
                if((start != "" && !startExist) || (terminal != "" && !endExist)){
                    busValid = false
                    Toast.makeText(context, "车站不存在，请重新输入！", Toast.LENGTH_SHORT).show()
                    if(!startExist)
                        inputBox3.setText("")
                    if(!endExist)
                        inputBox4.setText("")
                }
                if(busName =="" || start == "" || terminal ==""){
                    busValid = false
                    Toast.makeText(context, "请完善必填项！", Toast.LENGTH_SHORT).show()
                }

                if(busValid){
                    data = Bus()
                    val newBusId = BusDao(dbHelper).getCount() + 1
                    data!!.setID(newBusId)
                    data!!.setName(busName)
                    data!!.setType(type)
                    data!!.setStart(start)
                    data!!.setTerminal(terminal)
                    val s = data!!.getName()
                    println("获取表单数据并初始化data对象为:$s")

                    onButtonClick?.onClick(nextBtn,data)
                }
//                //测试下一页功能，可以先跳转
//                else{
//                    //数据不合法，将data设为空
//                    data = null
//                }
//                onButtonClick?.onClick(nextBtn,data)
            }
        }

        // 点击广告1
        advertLayout.setOnClickListener{
            val intent = Intent(context, WebviewActivity::class.java)
            intent.putExtra("url","https://www.jd.com/")
            startActivity(intent)
        }

        // 点击广告2
        advertLayout2.setOnClickListener{
            val intent = Intent(context, WebviewActivity::class.java)
            intent.putExtra("url","https://www.taobao.com/")
            startActivity(intent)
        }
    }

    //定义接口
    interface OnButtonClick {
        fun onClick(view: View?, data: Bus?)
    }
    //定义接口变量的set方法
    fun setOnButtonClick(onButtonClick: OnButtonClick) {
        this.onButtonClick = onButtonClick
    }

}