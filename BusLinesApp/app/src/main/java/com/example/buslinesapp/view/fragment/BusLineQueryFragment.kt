package com.example.buslinesapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.buslinesapp.R
import kotlinx.android.synthetic.main.query_fragment.*


//查询路线Fragment
class BusLineQueryFragment:Fragment() {
    private lateinit var button: Button

    /**
     * 单例模式，使用方法:
     * BusLineQueryFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            BusLineQueryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.query_fragment, null, false)
//        button = view.findViewById(R.id.busSearch)
//        button.setOnClickListener(ClickButton())
        return view;
    }
//    private class ClickButton : View.OnClickListener {
//        override fun onClick(v: View) {
//
//        }
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        busQuery.isChecked = true  //默认选择第一个
        replaceFragment(QueryBusFragment())  //默认加载第一个fragment

        //绑定单选按钮选择监听
        busQuery.setOnClickListener { replaceFragment(QueryBusFragment()) }
        stationToStationQuery.setOnClickListener { replaceFragment(QueryStationLineFragment()) }
        stationQuery.setOnClickListener{replaceFragment(QueryStationFragment())}

    }
    //切换fragment
    fun replaceFragment(fragment: Fragment){
        val fragmentManager = childFragmentManager  //获取碎片管理器
        val transaction =  fragmentManager.beginTransaction()  //Transaction事务
        transaction.replace(R.id.queryFrameLayout,fragment)
        transaction.addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }


    fun setRadioButtonChecked(name:String){
        if(name == "busQuery"){
            busQuery.isChecked = true
        }
        else if(name == "stationToStationQuery"){
            stationToStationQuery.isChecked = true
        }
        else if(name == "stationQuery"){
            stationQuery.isChecked = true
        }
    }

}