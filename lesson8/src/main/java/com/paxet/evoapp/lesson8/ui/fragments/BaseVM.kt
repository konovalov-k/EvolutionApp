package com.paxet.evoapp.lesson8.ui.fragments

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.paxet.evoapp.lesson8.data.db.AppDatabase
import com.paxet.evoapp.lesson8.data.db.toGenresItem
import com.paxet.evoapp.lesson8.data.network.GenresData
import com.paxet.evoapp.lesson8.data.network.NetworkModule
import com.paxet.evoapp.lesson8.data.network.tmdbapi.GenresAPI
import com.paxet.evoapp.lesson8.data.network.tmdbapi.toGenres
import kotlinx.coroutines.*

abstract class BaseVM(context: Context) : ViewModel() {
    val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${throwable.localizedMessage}", throwable)
    }

    val tmdbAPI by lazy { NetworkModule.tmdbAPI }
    val db by lazy(Dispatchers.IO) { AppDatabase.getDBInstance(context) }

    fun initConfiguration() {
        coroutineScope.launch(exceptionHandler) {
            NetworkModule.baseImageUrl = tmdbAPI.getAPIConfiguration(apiKey).images?.secureBaseUrl ?: ""
        }
    }

    fun initGenres() {
        coroutineScope.launch(exceptionHandler) {
            GenresData.genresData = GenresAPI( db.genresDao.getAll().map { it.toGenresItem() } )
            val genresData = tmdbAPI.getGenres(apiKey)
            if (genresData.genres != null) {
                //Store genres to DB cache
                GenresData.genresData = genresData
                db.genresDao.insert(genresData.genres.map { it?.toGenres()})
            }
        }
    }

    companion object {
        private val TAG = BaseVM::class.java.simpleName
        val apiKey = "c9e69769a0b528e00cf6da3c3199eb0e"
    }
}