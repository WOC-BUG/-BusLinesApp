package com.example.buslinesapp.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.CollectDao
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.dao.StationDao
import com.example.buslinesapp.model.data.CurUser
import com.example.buslinesapp.model.data.QueryStationData
import com.example.buslinesapp.view.activity.CollectActivity
import com.example.buslinesapp.view.adapter.QueryStation2StationAdapter
import com.example.buslinesapp.view.adapter.QueryStationAdapter
import kotlinx.android.synthetic.main.query_station_line_fragment.*
import kotlinx.android.synthetic.main.station_info.*
import kotlinx.android.synthetic.main.station_info.busRecyclerView
import kotlinx.android.synthetic.main.station_info.stationName
import kotlinx.android.synthetic.main.station_info2.*
import java.util.ArrayList

class QueryStationLineFragment:Fragment() {

    private var dbHelper : DatabaseHelper?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.query_station_line_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        replaceFragment(EmptyContentFragment())
        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        val db = dbHelper?.writableDatabase

        stationToStation.setOnClickListener{
            val searchContent1 = searchStationStartTextview.text.toString()
            val searchContent2 = searchStationOverTextview.text.toString()
            if(searchContent1 == "" && searchContent2 == ""){
                Toast.makeText(this.context,"请输入内容！", Toast.LENGTH_SHORT).show()
                replaceFragment(EmptyContentFragment())
            }
            else{
                val station = StationDao(dbHelper)
                if(!station.query(searchContent1)){  // 找不到该站点
                    Toast.makeText(this.context,searchContent1+"站不存在！", Toast.LENGTH_SHORT).show()
                    replaceFragment(EmptyContentFragment())
                }
                else if(!station.query(searchContent2)){  // 找不到该站点
                    Toast.makeText(this.context,searchContent2+"站不存在！", Toast.LENGTH_SHORT).show()
                    replaceFragment(EmptyContentFragment())
                }
                else{
                    replaceFragment(BusInfoFragment(searchContent1,searchContent2))
                }
            }
        }

        //取出从CollectActivity传递过来的参数
        val bundle = arguments
        if(bundle != null){
            val stationNeed = bundle.getStringArrayList("stations") as ArrayList<String>
            searchStationStartTextview.setText(stationNeed[0])
            searchStationOverTextview.setText(stationNeed[1])
            //搜索点击，查询路线
            stationToStation.performClick()
        }
    }

    private fun replaceFragment(fragment:Fragment){
        val fManager= fragmentManager
        val transaction = fManager?.beginTransaction()
        transaction?.replace(R.id.frameLayout,fragment)  //rightLayout替换为fragment
        transaction?.addToBackStack(null)    //手动添加到栈中，这样回退时可返回上一个fragment，而不是关闭整个activity
        transaction?.commitAllowingStateLoss()   //留住Fragment的前后关系
    }

}


class BusInfoFragment(private var searchContent1: String, private var searchContent2: String):Fragment(){

    private var dbHelper : DatabaseHelper ?= null
    private var adapter : QueryStation2StationAdapter?= null
    private var curUser = CurUser.instance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        return inflater.inflate(R.layout.station_info2,container,false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        val collectDao = CollectDao(dbHelper)

        stationName.text = "$searchContent1 —— $searchContent2"
        //查询是否收藏过改路线
        if(collectDao.exist(curUser.getID(),searchContent1,searchContent2)){
            println("该路线在我的收藏中")
            collect_star.background = context?.resources?.getDrawable(R.mipmap.star_select)

        }
        else{
            collect_star.background = context?.resources?.getDrawable(R.mipmap.star)
        }

        val station = StationDao(dbHelper)
        val layoutManager= LinearLayoutManager(context)
        layoutManager.orientation= LinearLayoutManager.VERTICAL    //垂直排列
        busRecyclerView.layoutManager=layoutManager

        val data : ArrayList<QueryStationData> = station.queryStation2Station(searchContent1,searchContent2)

        adapter = QueryStation2StationAdapter(data)
        busRecyclerView.adapter = adapter

        //收藏点击监听
        collect_star.setOnClickListener {
            if(collectDao.exist(curUser.getID(),searchContent1,searchContent2)){
                collect_star.background = context?.resources?.getDrawable(R.mipmap.star)
                collectDao.delete(curUser.getID(),searchContent1,searchContent2)

                Toast.makeText(this.context,"已取消收藏", Toast.LENGTH_SHORT).show()
            }
            else{
                collect_star.background = context?.resources?.getDrawable(R.mipmap.star_select)
                collectDao.addCollect(curUser.getID(),searchContent1,searchContent2)

                Toast.makeText(this.context,"已收藏线路", Toast.LENGTH_SHORT).show()
            }

        }
    }
}