package com.paxet.evoapp.lesson8.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.paxet.evoapp.lesson8.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setupActionBarWithNavController(findViewById<FragmentContainerView>(R.id.nav_host_fragment_container).findNavController())
    }
}