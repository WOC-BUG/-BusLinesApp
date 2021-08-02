package com.example.buslinesapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.buslinesapp.R
import com.example.buslinesapp.model.data.Bus
import kotlinx.android.synthetic.main.add_fragment.*

//添加Fragment
class BusLineAddFragment:Fragment() {

    /**
     * 单例模式，使用方法:
     * BusLineAddFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            BusLineAddFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_fragment,container,false)
    }

//    private lateinit var stationFrag:AddStationFragment
    private lateinit var busFrag:AddBusFragment
    private lateinit var transFrag:AddTransFragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        leftButton.isChecked = true  //默认选择第一个按钮
        //initFragment()
        replaceFragment(AddStationFragment())  //默认加载第一个fragment
        busFrag = AddBusFragment()
        //transFrag = AddTransFragment()

        //绑定单选按钮选择监听
        leftButton.setOnClickListener { replaceFragment(AddStationFragment()) }
        rightButton.setOnClickListener {

            replaceFragment(busFrag)

            //实现AddBusFragment中按钮点击监听接口
            busFrag.setOnButtonClick(object : AddBusFragment.OnButtonClick {
                override fun onClick(view: View?,data:Bus?) {
                    if(data != null){
                        println("AddBusFragment传递的data：Bus?不为空")
                        replaceFragmentWithTag(data)
                    }else{
                        println("AddBusFragment传递的data：Bus?为空")
                        replaceFragmentWithTag(null)
                    }

                }
            })

            //实现AddTransFragment中返回点击监听接口
//            transFrag.setOnViewClick(object : AddTransFragment.OnReturnClickListener{
//                override fun onClick(view: View?) {
//                    replaceFragment(busFrag)
//                }
//            })
        }
    }

    //切换fragment
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = childFragmentManager  //获取碎片管理器
        val transaction =  fragmentManager.beginTransaction()  //Transaction事务
        transaction.replace(R.id.addFrameLayout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //切换fragment并传参
    private fun replaceFragmentWithTag(bus: Bus?){
        val fragmentManager = childFragmentManager
        val transaction =  fragmentManager.beginTransaction()

//        val fragment = AddTransFragment().newInstance(bus)
//        transaction.replace(R.id.addFrameLayout,fragment)
        //初始化AddTransFragment
        transFrag = AddTransFragment().newInstance(bus)

        //实现AddTransFragment中返回点击监听接口
        transFrag.setOnViewClick(object : AddTransFragment.OnClickListener{
            override fun onBackClick(view: View?) {
                replaceFragment(busFrag)
            }

            override fun onSubmitClick(view: View?) {
                replaceFragment(busFrag)
                Toast.makeText(context, "车次添加成功", Toast.LENGTH_SHORT).show()
            }
        })

        transaction.replace(R.id.addFrameLayout,transFrag)
        //transaction.addToBackStack(null)
        transaction.commit()
    }

}