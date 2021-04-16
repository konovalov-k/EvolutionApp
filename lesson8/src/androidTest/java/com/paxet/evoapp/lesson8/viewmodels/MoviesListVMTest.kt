package com.paxet.evoapp.lesson8.viewmodels

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.paxet.evoapp.lesson8.ui.fragments.movieslist.MoviesListVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MoviesListVMTest {
    lateinit var viewmodel: MoviesListVM

    @Before
    fun initVM(){
        viewmodel = MoviesListVM(ApplicationProvider.getApplicationContext<Context>())
    }

    @Test
    fun checkListVM(){
        runBlocking {
            launch(Dispatchers.IO) {
                viewmodel.initMoviesListAsync().await()
                Assert.assertTrue(viewmodel.moviesListLD.value?.isNotEmpty() ?: false)

                var msg = ""
                viewmodel.moviesListLD.value?.forEach {
                    msg += "Title: ${it.title}\n"
                }

                Log.d("Test", "Test list titles:\n$msg")
            }
        }
    }
}