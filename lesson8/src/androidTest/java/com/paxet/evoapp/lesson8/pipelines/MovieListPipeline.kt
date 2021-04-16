package com.paxet.evoapp.lesson8.pipelines

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.paxet.evoapp.lesson8.data.db.AppDatabase
import com.paxet.evoapp.lesson8.data.db.MoviesDao
import com.paxet.evoapp.lesson8.data.network.NetworkModule
import com.paxet.evoapp.lesson8.data.network.tmdbapi.MovieItemAPI
import com.paxet.evoapp.lesson8.data.network.tmdbapi.toMovies
import com.paxet.evoapp.lesson8.ui.fragments.BaseVM
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieListPipeline {
    //DB
    private lateinit var appContext: Context
    private val db by lazy { AppDatabase.getDBInstance(appContext) }
    private lateinit var moviesDao: MoviesDao
    //Network
    private val tmdbAPI by lazy { NetworkModule.tmdbAPI }
    private val apiKey = "c9e69769a0b528e00cf6da3c3199eb0e"

    @Before
    fun createDb() {
        appContext = ApplicationProvider.getApplicationContext<Context>()
        moviesDao = db.moviesDao
    }

    @Test
    fun checkNowPlayingGetAndStore() {
        val remoteMovies: List<MovieItemAPI> = tmdbAPI.getNowPlaying(apiKey).results ?: listOf()
        Assert.assertTrue(remoteMovies.isNotEmpty())
        if (remoteMovies.isNotEmpty()) writeMoviesToDb(remoteMovies)
    }

    private fun writeMoviesToDb(remoteMovies: List<MovieItemAPI>) {
        db.moviesDao.run {
            delete()
            db.moviesDao.insertAll(remoteMovies.map { it.toMovies() })
        }

        Assert.assertTrue(db.moviesDao.getAll().isNotEmpty())
    }
}