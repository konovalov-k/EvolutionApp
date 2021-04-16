package com.paxet.evoapp.lesson8.ui.fragments.movieslist

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paxet.evoapp.lesson8.data.db.toMoviesApi
import com.paxet.evoapp.lesson8.data.network.tmdbapi.MovieItemAPI
import com.paxet.evoapp.lesson8.data.network.tmdbapi.toMovies
import com.paxet.evoapp.lesson8.ui.fragments.BaseVM
import kotlinx.coroutines.*
import java.util.*

class MoviesListVM(app: Context) : BaseVM(app) {
    private val _moviesListLD = MutableLiveData<List<MovieItemAPI>>()
    val moviesListLD : LiveData<List<MovieItemAPI>> get() = _moviesListLD

    fun initMoviesListAsync() = coroutineScope.async(exceptionHandler) {
        //Get genres from network or DB cache
        initGenres()
        //Get movies from DB cache
        val localMovies: List<MovieItemAPI> = readMoviesFromDb()
        if (localMovies.isNotEmpty()) {
            _moviesListLD.postValue(localMovies)
        }

        //Get movies from network

        val remoteMovies: List<MovieItemAPI> = tmdbAPI.getNowPlaying(apiKey).results ?: listOf()
        if (remoteMovies.isNotEmpty()) {
            _moviesListLD.postValue(remoteMovies)
            //Store movies to DB cache
            writeMoviesToDb(remoteMovies)
        }
    }

    private fun readMoviesFromDb() : List<MovieItemAPI> {
        return db.moviesDao.getAll().map{ it.toMoviesApi() }
    }

    private fun writeMoviesToDb(remoteMovies: List<MovieItemAPI>) {
        db.moviesDao.insertAll(remoteMovies.map{ it.toMovies() })
    }

    companion object {
        private val TAG = MoviesListVM::class.java.simpleName
    }

}