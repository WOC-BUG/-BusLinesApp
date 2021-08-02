package com.example.buslinesapp.view.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.buslinesapp.R
import com.example.buslinesapp.model.data.CurUser
import com.example.buslinesapp.view.activity.AboutActivity
import com.example.buslinesapp.view.activity.CollectActivity
import com.example.buslinesapp.view.activity.HelpActivity
import com.example.buslinesapp.view.activity.PraiseActivity
import kotlinx.android.synthetic.main.user_main.*


//用户Fragment
class HomeFragment: Fragment() {

    /**
     * 单例模式，使用方法:
     * HomeFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            HomeFragment()
        }
    }

    val curHost = CurUser.instance  //获取当前用户

    //创建fragment的布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.user_main,null, false)
        //collect =view.findViewById(R.id.collect_button)
        return view
    }


    //功能们
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //收藏跳转
        collect_button.setOnClickListener{
            val intent = Intent()
            intent.setClass(collect_button.context,CollectActivity::class.java)
            startActivity(intent)

        }

        //帮助跳转
        help_button.setOnClickListener{
            val intent = Intent()
            intent.setClass(help_button.context, HelpActivity::class.java)
            startActivity(intent)
        }
        //关于跳转
        about_button.setOnClickListener{
            val intent = Intent()
            intent.setClass(about_button.context, AboutActivity::class.java)
            startActivity(intent)
        }

       //好评跳转
        praise_button.setOnClickListener {
            val intent = Intent()
            intent.setClass(praise_button.context, PraiseActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * 初始化当前登录的用户信息
     */
    fun getUserInfo(){
        hostName.text = curHost.getName()
        //Glide.with(this.context).load(curHost.getAvatar()).error(R.mipmap.ic_launcher).into(hostAvatar)

    }


}