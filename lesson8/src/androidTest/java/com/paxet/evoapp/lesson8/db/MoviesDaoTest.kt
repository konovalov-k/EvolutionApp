package com.paxet.evoapp.lesson8.db

import android.content.Context
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.test.core.app.ApplicationProvider
import com.paxet.evoapp.lesson8.data.db.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MoviesDaoTest {
    private lateinit var appContext: Context
    private val db by lazy { AppDatabase.getDBInstance(appContext) }
    private lateinit var moviesDao: MoviesDao

    private val movie = Movies(-99, "Test", "0", overview = "")
    private val movies = listOf(
        Movies(-100, "Test", "0", overview = ""),
        Movies(-101, "Test", "0", overview = ""),
        Movies(-102, "Test", "0", overview = "")
    )

    @Before
    fun createDb() {
        appContext = ApplicationProvider.getApplicationContext<Context>()
        moviesDao = db.moviesDao
    }

    @Test
    fun checkInsert(){
        moviesDao.run {
            insertMovie(movie)
            Assert.assertTrue(getAll().contains(movie))

            insertAll(movies)
            Assert.assertTrue(getAll().containsAll(movies))
        }
    }

    @Test
    fun checkDelete(){
        moviesDao.run {
            delete(movie)
            Assert.assertFalse(getAll().contains(movie))

            delete(movies)
            Assert.assertFalse(getAll().containsAll(movies))

            delete()
            Assert.assertTrue(getAll().isEmpty())
        }
    }
}