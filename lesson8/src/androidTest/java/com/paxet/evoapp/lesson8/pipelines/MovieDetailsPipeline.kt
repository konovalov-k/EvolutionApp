package com.paxet.evoapp.lesson8.pipelines

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.paxet.evoapp.lesson8.data.db.AppDatabase
import com.paxet.evoapp.lesson8.data.db.MoviesDao
import com.paxet.evoapp.lesson8.data.network.NetworkModule
import com.paxet.evoapp.lesson8.data.network.tmdbapi.CastItem
import com.paxet.evoapp.lesson8.data.network.tmdbapi.MovieItemAPI
import com.paxet.evoapp.lesson8.data.network.tmdbapi.toActors
import com.paxet.evoapp.lesson8.ui.fragments.BaseVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieDetailsPipeline {
    //DB
    private lateinit var appContext: Context
    private val db by lazy { AppDatabase.getDBInstance(appContext) }
    private lateinit var moviesDao: MoviesDao
    //Network
    private val tmdbAPI by lazy { NetworkModule.tmdbAPI }
    private val apiKey = "c9e69769a0b528e00cf6da3c3199eb0e"

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun getApi() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Before
    fun createDb() {
        appContext = ApplicationProvider.getApplicationContext<Context>()
        moviesDao = db.moviesDao
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun checkMovieDetails(){
        runBlocking {
            launch(Dispatchers.IO) {
                val remoteMovies: List<MovieItemAPI> = tmdbAPI.getNowPlaying(apiKey).results ?: listOf()
                Assert.assertTrue(remoteMovies.isNotEmpty())
                val details = tmdbAPI.getMovieDetails(remoteMovies[0].id.toString(), BaseVM.apiKey)
                Assert.assertTrue(details.title?.isNotEmpty() ?: false)
            }
        }
    }

    fun writeCreditsFromDb(movieId: String, movieCredits: List<CastItem?>?) {
        db.actorsDao.insert(movieCredits?.map( {it?.toActors(movieId)} ))
    }
}