package com.paxet.evoapp.lesson10.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paxet.evoapp.lesson10.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //Clear shared preferences on app start
        val pref: SharedPreferences = getSharedPreferences("searchLine", 0)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.clear()
        editor.apply()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}