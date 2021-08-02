package com.example.buslinesapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.buslinesapp.R
import com.example.buslinesapp.dao.DatabaseHelper
import com.example.buslinesapp.dao.UserDao
import com.example.buslinesapp.model.data.CurUser

class RegisterFragment: Fragment() {
    private var dbHelper : DatabaseHelper? = null
    /**
     * 单例模式，使用方法:
     * RegisterFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            RegisterFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        return inflater.inflate(R.layout.register, null, false)
    }

    fun checkInput(view:View?):Boolean{
        if(view==null){
            print("view为空")
            return false
        }
        val userName:String=view.findViewById<EditText>(R.id.name).text.toString()
        val userPassword=view.findViewById<EditText>(R.id.password).text.toString()
        val userConfirmPassword=view.findViewById<EditText>(R.id.confirmPassword).text.toString()

        val userDao = UserDao(dbHelper)
        val user= userDao.queryUser(userName)

        if(userName==""||userPassword==""||userConfirmPassword==""){
            Toast.makeText(view.context,"输入不能为空！", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(user.getName()!=""){
            Toast.makeText(view.context,"该用户已存在！", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(userPassword!=userConfirmPassword){
            Toast.makeText(view.context,"密码不一致！", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            userDao.addUser(userName,userPassword)

            CurUser.instance.setID(userDao.queryUser(userName).getID())
            CurUser.instance.setName(userName)
            CurUser.instance.setPassword(userPassword)

            Toast.makeText(view.context,"注册成功！", Toast.LENGTH_SHORT).show()
            return true;
        }
    }

    private fun getRandomAvatar():String{
        val num:Int=(1..6).random()
        val resourceName="user"+num.toString()+"_img"
        val packageName= this.context?.packageName
        val url=this.getString(resources.getIdentifier(resourceName,"string",packageName))
        println("packageName=$packageName")
        println(url)
        return url
    }

}