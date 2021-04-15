package com.paxet.evoapp.lesson8.network

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.paxet.evoapp.lesson8.data.network.NetworkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TmdbApiTest {
    private val tmdbAPI by lazy { NetworkModule.tmdbAPI }
    private val apiKey = "c9e69769a0b528e00cf6da3c3199eb0e"



    @Test
    fun checkApiConfig() {
        val config = tmdbAPI.getAPIConfiguration(apiKey)
        Assert.assertTrue(config.images?.secureBaseUrl?.isNotEmpty() ?: false)
    }

    @Test
    fun checkNowPlaying() {
        val movieList = tmdbAPI.getNowPlaying(apiKey)
        Assert.assertTrue(movieList.results?.isNotEmpty() ?: false)
    }

    @Test
    fun checkMovieDetails(){
        val movieList = tmdbAPI.getNowPlaying(apiKey)
        Assert.assertTrue(movieList.results?.isNotEmpty() ?: false)
        val movieId = movieList.results?.get(0)?.id ?: assert(false)

        val movieDetails = tmdbAPI.getMovieDetails(movieId.toString(), apiKey)
        Assert.assertTrue(movieDetails.genres?.isNotEmpty() ?: false)
    }

    @Test
    fun checkGenres() {
        val genres = tmdbAPI.getGenres(apiKey)
        Assert.assertTrue(genres.genres?.isNotEmpty() ?: false)
    }
}