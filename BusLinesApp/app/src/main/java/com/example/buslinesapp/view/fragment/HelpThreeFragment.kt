package com.example.buslinesapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.buslinesapp.R

class HelpThreeFragment: Fragment() {

    /**
     * 单例模式，使用方法:
     * HomeFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            HelpThreeFragment()
        }
    }

    //创建fragment布局
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view: View =inflater.inflate(R.layout.help_3,null, false)
//        return view
//    }
}