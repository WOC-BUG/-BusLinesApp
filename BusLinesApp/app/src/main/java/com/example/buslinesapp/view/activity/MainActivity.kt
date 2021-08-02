package com.example.buslinesapp.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.*
import com.example.buslinesapp.model.data.BaseData
import com.example.buslinesapp.utils.FileUtil
import com.example.buslinesapp.view.adapter.FragmentAdapter
import com.example.buslinesapp.view.fragment.BusLineAddFragment
import com.example.buslinesapp.view.fragment.BusLineQueryFragment
import com.example.buslinesapp.view.fragment.HomeFragment
import com.example.buslinesapp.view.fragment.MapFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView //底部导航栏
    private lateinit var viewPager: ViewPager //中间切换页面
    private lateinit var menuItem: MenuItem  //选中的按钮
    private var listFragment : ArrayList<Fragment> = ArrayList()
    private var dbHelper : DatabaseHelper? = null
    private var objList = ArrayList<BaseData>()   //存放读取文件获得的数据对象

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //设置顶部状态栏为透明
        window.statusBarColor = Color.TRANSPARENT;

        //初始化控件
        bottomNavigationView=findViewById(R.id.bottomNavigationView)
        viewPager=findViewById(R.id.viewPager)

        // 将四个Fragment加入Fragment列表中
        listFragment.add(MapFragment.instance)    //首页地图Fragment
        listFragment.add(BusLineAddFragment.instance)    //添加路线Fragment
        listFragment.add(BusLineQueryFragment.instance)    //查询Fragment
        listFragment.add(HomeFragment.instance)    //个人Fragment

        bottomNavigationView.menu.getItem(0).isChecked = true  //默认选中第一个页面
        bottomNavigationView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED //显示item的标签

        // 设置监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationSelectedListener)
        viewPager.setOnPageChangeListener(viewPagerListener)

        //设置页面适配器
        viewPager.adapter= FragmentAdapter(supportFragmentManager,listFragment)
        viewPager.offscreenPageLimit = 4

        //创建数据库和表
        createDB()


        // 先打开欢迎页面
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
    }

    //设置导航栏当前选中及显示的页面
    fun setSelectedID(checkedID: Int){

        bottomNavigationView.menu.getItem(checkedID).isChecked = true
        viewPager.currentItem = checkedID
    }

    //设置页面切换事件监听
    private val viewPagerListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                //让与当前Pager相应的item变为选中状态
                menuItem = bottomNavigationView.menu.getItem(position)
            }
        }


    //设置底部导航栏菜单监听
    private val bottomNavigationSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.map -> {
                        viewPager.currentItem = 0
                        return true
                    }
                    R.id.add -> {
                        viewPager.currentItem = 1
                        return true
                    }
                    R.id.query -> {
                        viewPager.currentItem = 2
                        return true
                    }
                    R.id.home -> {
                        viewPager.currentItem = 3
                        return true
                    }
                }
                return false
            }
        }

    //创建数据库及表
    private fun createDB() {
        dbHelper = DatabaseHelper(this, "BusLineData.db", 1)
        dbHelper?.writableDatabase

        initDBTable("User")
        initDBTable("Bus")
        initDBTable("Station")
        initDBTable("Trancepos")
        initDBTable("Collect")
    }
    //添加预存数据
    private fun initDBTable(tableName:String){
        if(isEmpty(tableName)){
            when(tableName){
                "User" -> {
                    objList = FileUtil(assets).readFile("user.csv")
                    for(obj in objList){
                        UserDao(dbHelper).initTable(obj)
                    }
                }
                "Bus" -> {
                    objList = FileUtil(assets).readFile("bus.csv")
                    for(obj in objList){
                        BusDao(dbHelper).initTable(obj)
                    }
                }
                "Station" -> {
                    objList = FileUtil(assets).readFile("station.csv")
                    for(obj in objList){
                        StationDao(dbHelper).initTable(obj)
                    }
                }
                "Trancepos" -> {
                    objList = FileUtil(assets).readFile("trancepos.csv")
                    for(obj in objList){
                        TranceposDao(dbHelper).initTable(obj)
                    }
                }
                "Collect" -> {
                    CollectDao(dbHelper).initTable()
                }
            }
        }
    }

    //判断表是否为空
    private fun isEmpty(tableName:String):Boolean{
        var empty = false
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from $tableName",null)
        if(cursor?.count == 0)
            empty = true
        cursor?.close()
        return empty
    }

}