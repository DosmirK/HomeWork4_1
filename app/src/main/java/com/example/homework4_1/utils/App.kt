package com.example.homework4_1.utils

import android.app.Application
import com.example.homework4_1.utils.MySharedPreferences

class App: Application() {

    var mySharedPreferences: MySharedPreferences? = null

    override fun onCreate() {
        super.onCreate()
        mySharedPreferences = MySharedPreferences(this)
    }

    companion object{
        var userName: String = ""
    }
}