package com.example.buslinesapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations.map
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.MapView
import com.example.buslinesapp.R
import kotlinx.android.synthetic.main.map.*


//首页地图Fragment
class MapFragment: Fragment() {

    /**
     * 单例模式，使用方法:
     * MapFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            MapFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        return inflater.inflate(R.layout.map,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map.onCreate(savedInstanceState)
        map.map.mapType = AMap.MAP_TYPE_NORMAL

    }
}