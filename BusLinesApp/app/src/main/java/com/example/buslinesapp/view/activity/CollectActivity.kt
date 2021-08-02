package com.example.buslinesapp.view.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.CollectDao
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.model.data.Collect
import com.example.buslinesapp.model.data.CurUser
import com.example.buslinesapp.view.adapter.CollectAdapter
import com.example.buslinesapp.view.fragment.BusLineAddFragment
import kotlinx.android.synthetic.main.collection_main.*

class CollectActivity: AppCompatActivity() {

    val curHost = CurUser.instance  //获取当前用户

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collection_main)

        //连接数据库
        val dbHelper : DatabaseHelper? = DatabaseHelper(this, "BusLineData.db", 1)

        //绑定表
        val collectDao= CollectDao(dbHelper)

        //设置LayoutManager
        val layoutManager= LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL//垂直排列
        collectRecyclerView.layoutManager=layoutManager

        //绑定Adapter
        val collectList: ArrayList<Collect> = collectDao.query(curHost.getID())
        //若无收藏内容，展示“暂无内容”
        if(collectList.size == 0){
            noCollect.visibility = VISIBLE
            collectRecyclerView.visibility = GONE
        }
        else{
            noCollect.visibility = GONE
            collectRecyclerView.adapter = CollectAdapter(collectList)
        }

    }

//    fun setAdapter(collectDao:CollectDao){
//        val collectList: ArrayList<Collect> = collectDao.query(curHost.getID())
//        collectRecyclerView.adapter = CollectAdapter(collectList)
//    }



}