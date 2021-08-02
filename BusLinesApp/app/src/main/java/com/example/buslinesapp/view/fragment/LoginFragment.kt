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


class LoginFragment:Fragment() {
    private var dbHelper : DatabaseHelper? = null
    /**
     * 单例模式，使用方法:
     * LoginFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            LoginFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = this.activity?.let { DatabaseHelper(it, "BusLineData.db", 1) }
        return inflater.inflate(R.layout.login, null, false)
    }

    fun checkInput(view:View?):Boolean{
        if(view==null){
            print("view为空")
            return false
        }
        val userDao = UserDao(dbHelper)
        val userName:String=view.findViewById<EditText>(R.id.name).text.toString()
        val userPassword=view.findViewById<EditText>(R.id.password).text.toString()
        val user= userDao.queryUser(userName)
        if(user.getName() != "")
            println("user.getName() = ${user.getName()}")

        if(userName==""||userPassword==""){
            Toast.makeText(view.context,"用户名或密码不能为空！",Toast.LENGTH_SHORT).show()
            return false
        }
        else if(user.getName()==""){
            Toast.makeText(view.context,"该用户不存在！",Toast.LENGTH_SHORT).show()
            return false
        }
        else if(user.getPassword()!=userPassword){
            println("user.getPassword()="+user.getPassword())
            println("userPassword $userPassword")
            Toast.makeText(view.context,"密码错误！",Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            CurUser.instance.setID(user.getID())
            CurUser.instance.setName(user.getName())
            CurUser.instance.setPassword(user.getPassword())

            // 登录成功
            Toast.makeText(view.context,"登录成功！", Toast.LENGTH_SHORT).show()
            return true;
        }
    }
}