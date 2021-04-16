package com.paxet.evoapp.lesson8.viewmodels

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.paxet.evoapp.lesson8.data.network.tmdbapi.MovieItemAPI
import com.paxet.evoapp.lesson8.ui.fragments.BaseVM
import com.paxet.evoapp.lesson8.ui.fragments.moviedetails.MovieDetailsVM
import com.paxet.evoapp.lesson8.ui.fragments.movieslist.MoviesListVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieDetailsVMTest {
    lateinit var viewmodel: MovieDetailsVM

    @Before
    fun initVM(){
        viewmodel = MovieDetailsVM(ApplicationProvider.getApplicationContext<Context>())
    }

    @Test
    fun checkDetailsVM(){
        runBlocking {
            launch(Dispatchers.IO) {
                val remoteMovies: List<MovieItemAPI> = viewmodel.tmdbAPI.getNowPlaying(BaseVM.apiKey).results ?: listOf()
                Assert.assertTrue(remoteMovies.isNotEmpty())

                val bundle = Bundle().apply { putInt("movieId", remoteMovies[0].id ?: -1) }
                viewmodel.initMovie(bundle).await()

                Assert.assertTrue(viewmodel.movieLD.value?.second?.isNotEmpty() ?: false)

                var msg = ""
                viewmodel.movieLD.value?.run {
                    Assert.assertTrue(first?.title?.isNotEmpty() ?: false)
                    msg = first?.title ?: ""
                }

                Log.d("Test", "Test movie detail title: $msg")
            }
        }
    }
}