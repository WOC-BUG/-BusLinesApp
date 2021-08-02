package com.example.buslinesapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.BusDao
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.dao.StationDao
import com.example.buslinesapp.dao.TranceposDao
import com.example.buslinesapp.model.data.Bus
import com.example.buslinesapp.utils.CountDownTextViewHelper
import com.example.buslinesapp.utils.StringUtil
import kotlinx.android.synthetic.main.add_trans_fragment.*
import kotlinx.android.synthetic.main.query_bus_information_fragment.*
import kotlin.collections.ArrayList

class AddTransFragment: Fragment() {

    private lateinit var newBus:Bus
    private var onViewClick: OnClickListener? = null  //定义接口成员变量
    private var dbHelper: DatabaseHelper?=null
    private var newBusLine = ArrayList<String>()

    //创建fragment布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_trans_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        val busDao = BusDao(dbHelper)
        val stationDao = StationDao(dbHelper)
        val tranceDao = TranceposDao(dbHelper)
        val stringUtil = StringUtil()

        var existDirection = -1
        var returnClick = false
        if(this.arguments != null && this.arguments!!.getSerializable("bean") != null){

            println("接收的arguments为Bus对象")
            //获取传递过来的汽车对象
            newBus = this.arguments!!.getSerializable("bean") as Bus
            newBusLine.add(newBus.getStart())

            //展示固定标签
            routeName.text = newBus.getName()
            startSite.text = newBus.getStart()
            endSite.text = newBus.getTerminal()
            addSuccess.visibility = GONE

            //若库中已有反方向的车次信息，则用户无需再选择方向
            //获取反方向busid和stationid后在关系表联查
            //然后根据stationID获取方向
            val existBusId = busDao.exist(newBus.getName(), newBus.getTerminal(), newBus.getStart())
            if(existBusId != -1){
                val allStation = stationDao.getIdByName(newBus.getTerminal())
                for(id in allStation){
                    if(tranceDao.exist(id,existBusId)){
                        existDirection = stationDao.getDirecByID(id)
                        if(existDirection == 0 ){
                            btnShang.isChecked = true
                            btnShang.isEnabled = false     //使button点击失效
                            btnXia.isEnabled = false
                        }
                        if(existDirection == 1){
                            btnXia.isChecked = true
                            btnShang.isEnabled = false      //使button点击失效
                            btnXia.isEnabled = false
                        }
                        println("反方向车次已存在,无须选择方向！")
                    }
                }
            }

        }

//        else{
//            println("从上一页面接收的arguments为null")
//            Toast.makeText(context, "请前往上一步添加车次信息", Toast.LENGTH_SHORT).show()
//            addNextBtn.isEnabled = false
//            submitBtn2.isEnabled = false
//            clearBtn2.isEnabled = false
//        }

        var direction = 0
        var startTime:String
        var terminalTime:String
        var stationName:String
        var arriveTime:String
        var leaveTime:String
        var addNum = 0

        //添加下一站点击监听
        addNextBtn.setOnClickListener {
            stationName = editBox2.text.toString()
            arriveTime = editBox3.text.toString()
            leaveTime = editBox4.text.toString()
            if(btnXia.isChecked) direction = 0
            if(btnShang.isChecked) direction = 1

            var stationValid = true    //标记车站表单输入合法性

            if(!btnXia.isChecked && !btnShang.isChecked){
                stationValid = false
                Toast.makeText(context, "请选择方向！", Toast.LENGTH_SHORT).show()
            }

            if(stationName == "" || arriveTime =="" || leaveTime == ""){
                stationValid = false
                Toast.makeText(context, "请填写必填项！", Toast.LENGTH_SHORT).show()
            }
            if(stationName != "" && !stationDao.query(stationName)){
                stationValid = false
                Toast.makeText(context, "该车站不存在，请重新输入！", Toast.LENGTH_SHORT).show()
                editBox2.setText("")
            }
            if((arriveTime != "" && !stringUtil.timeValidity(arriveTime)) || (leaveTime != "" && !stringUtil.timeValidity(leaveTime))){
                stationValid = false
                Toast.makeText(context, "时间格式不正确或不合法", Toast.LENGTH_SHORT).show()
            }

            //若表单输入都合法，则写入数据库
            if (stationValid){

                addNum += 1
                this.siteNum.text = addNum.toString()
                println("已添加途经车站个数：$addNum")
                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show()
                newBusLine.add(stationName)
                val stationID = stationDao.getStationId(stationName, direction)
                val busId = newBus.getID()
                println("插入Trancepos表：$stationID,$busId,$arriveTime,$leaveTime")
                tranceDao.addTrancepos(stationID,newBus.getID(),arriveTime,leaveTime)

                //清空输入框，以便添加下一站
                editBox2.setText("")
                editBox3.setText("")
                editBox4.setText("")
            }
        }

        //如果接口成员变量不为空null，则调用接口变量的方法
        if (onViewClick != null) {

            //返回点击监听
            returnLast.setOnClickListener {
                returnClick = true
                onViewClick?.onBackClick(returnLast) }

            //提交点击监听
            submitBtn2.setOnClickListener {
                startTime = editBox1.text.toString()
                terminalTime = editBox5.text.toString()

                var flag = true   //标记起终点时间合法性
                if(startTime =="" || terminalTime ==""){
                    flag = false
                    Toast.makeText(context, "请填写必填项，起点发车时间或终点到站时间不能为空", Toast.LENGTH_SHORT).show()
                }
                if((startTime != "" && !stringUtil.timeValidity(startTime)) || (terminalTime != "" && !stringUtil.timeValidity(terminalTime))){
                    flag = false
                    Toast.makeText(context, "时间格式不正确或不合法", Toast.LENGTH_SHORT).show()
                }
                //写入数据库
                if(flag){
                    newBusLine.add(newBus.getTerminal())
                    val startID = stationDao.getStationId(newBus.getStart(), direction)
                    tranceDao.addTrancepos(startID,newBus.getID(),startTime,startTime)  //添加起点发车时间
                    val endID = stationDao.getStationId(newBus.getTerminal(), direction)
                    tranceDao.addTrancepos(endID,newBus.getID(),terminalTime,"")    //添加终点到站时间

                    var line = stringUtil.join(newBusLine)
                    println("新增车次：${newBus.getName()}  行驶线路为：$line")
                    busDao.addBus(newBus.getName(), newBus.getType(), newBus.getStart(), newBus.getTerminal(), line)    //添加新车次
                    Toast.makeText(context, "车次添加成功", Toast.LENGTH_SHORT).show()

                    //val reversedArray = stringUtil.reverseList(newBusLine)
                    //line = stringUtil.join(reversedArray)
                    //println("反向车次线路为：$line")

                    //清空表单
                    btnXia.isChecked = false
                    btnShang.isChecked = false
                    editBox1.setText("")
                    editBox2.setText("")
                    editBox3.setText("")
                    editBox4.setText("")
                    editBox5.setText("")

                    //倒计时3秒 返回上一页面
                    addSuccess.visibility = VISIBLE
                    val helper_add =CountDownTextViewHelper(closeTimeTxt, "0", 3, 1)
                    helper_add.setOnFinishListener(object: CountDownTextViewHelper.OnFinishListener{
                        override fun finish() {
                            if(!returnClick){
                                onViewClick?.onBackClick(returnLast)
                            }
                        }
                    })
                    helper_add.start()
                }

            }
        }


        //清除点击监听
        clearBtn2.setOnClickListener {
            if(existDirection == -1){
                btnXia.isChecked = false
                btnShang.isChecked = false
            }
            editBox1.setText("")
            editBox2.setText("")
            editBox3.setText("")
            editBox4.setText("")
            editBox5.setText("")
        }

    }

    //定义接口
    interface OnClickListener {
        fun onBackClick(view: View?)
        fun onSubmitClick(view: View?)
    }

    //定义接口变量的set方法
    fun setOnViewClick(onClick: OnClickListener) {
        this.onViewClick = onClick
    }

    fun newInstance(newBus: Bus?) :AddTransFragment{
        // 通过bundle传递数据
        val bundle = Bundle()
        bundle.putSerializable("bean", newBus)
        val fragment = AddTransFragment()

        fragment.arguments = bundle
        return fragment
    }
}