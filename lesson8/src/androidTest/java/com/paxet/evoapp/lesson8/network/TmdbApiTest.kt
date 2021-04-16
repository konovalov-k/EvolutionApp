package com.paxet.evoapp.lesson8.network

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.paxet.evoapp.lesson8.data.network.NetworkModule
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TmdbApiTest {
    private val tmdbAPI by lazy { NetworkModule.tmdbAPI }
    private val apiKey = "c9e69769a0b528e00cf6da3c3199eb0e"
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun getApi() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun checkApiConfig() {
        runBlocking {
            launch(Dispatchers.IO) {
                val config = tmdbAPI.getAPIConfiguration(apiKey)
                Assert.assertTrue(config.images?.secureBaseUrl?.isNotEmpty() ?: false)
                Log.d("Test" , "baseUrl: " + config.images?.baseUrl)
            }
        }
    }

    @Test
    fun checkNowPlaying() {
        runBlocking {
            launch(Dispatchers.IO) {
                val movieList = tmdbAPI.getNowPlaying(apiKey)
                Assert.assertTrue(movieList.results?.isNotEmpty() ?: false)
            }
        }
    }

    @Test
    fun checkMovieDetails() {
        runBlocking {
            launch(Dispatchers.IO) {
                val movieList = tmdbAPI.getNowPlaying(apiKey)
                Assert.assertTrue(movieList.results?.isNotEmpty() ?: false)
                val movieId = movieList.results?.get(0)?.id ?: assert(false)

                val movieDetails = tmdbAPI.getMovieDetails(movieId.toString(), apiKey)
                Assert.assertTrue(movieDetails.genres?.isNotEmpty() ?: false)
            }
        }
    }

    @Test
    fun checkGenres() {
        runBlocking {
            launch(Dispatchers.IO) {
                val genres = tmdbAPI.getGenres(apiKey)
                Assert.assertTrue(genres.genres?.isNotEmpty() ?: false)
            }
        }
    }
}