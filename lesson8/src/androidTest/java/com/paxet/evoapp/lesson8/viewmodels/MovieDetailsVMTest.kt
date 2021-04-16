package com.paxet.evoapp.lesson8.viewmodels

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.paxet.evoapp.lesson8.data.network.tmdbapi.MovieItemAPI
import com.paxet.evoapp.lesson8.ui.fragments.BaseVM
import com.paxet.evoapp.lesson8.ui.fragments.moviedetails.MovieDetailsVM
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
                var msg = ""
                val remoteMovies: List<MovieItemAPI> = viewmodel.tmdbAPI.getNowPlaying(BaseVM.apiKey).results ?: listOf()
                Assert.assertFalse("Remote movies is empty", remoteMovies.isEmpty())

                val bundle = Bundle().apply { putInt("movieId", remoteMovies[0].id ?: -1) }
                Assert.assertFalse("Wrong id in first remote movie",remoteMovies[0].id == -1)
                viewmodel.initMovie(bundle).await()

                Assert.assertFalse("Movie details is null",viewmodel.movieLD.value == null)
                Assert.assertFalse("Movie details second is null",viewmodel.movieLD.value?.second == null)

                viewmodel.movieLD.value?.run {
                    Assert.assertFalse("Second is empty", second.isEmpty())
                    Assert.assertFalse("Details: Title is empty", first?.title?.isEmpty() ?: true)
                    msg = first?.title ?: ""
                }

                Log.d("Test", "Test movie detail title: $msg")
            }
        }
    }
}