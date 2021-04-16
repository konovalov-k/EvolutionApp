package com.paxet.evoapp.lesson8.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.paxet.evoapp.lesson8.data.db.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GenresDaoTest {
    private lateinit var appContext: Context
    private val db by lazy { AppDatabase.getDBInstance(appContext) }
    private lateinit var genresDao: GenresDao

    private val movieId = "0"
    private val genre = Genres(-99, "Test")
    private val genres = listOf(
        Genres(-100, "Test"),
        Genres(-101, "Test"),
        Genres(-102, "Test")
    )

    @Before
    fun createDb() {
        appContext = ApplicationProvider.getApplicationContext<Context>()
        genresDao = db.genresDao
    }

    @Test
    fun checkInsert() {
        genresDao.run {
            insert(genre)
            Assert.assertTrue(getAll().contains(genre))

            insert(genres)
            Assert.assertTrue(getAll().containsAll(genres))
        }
    }

    @Test
    fun checkDelete(){
        genresDao.run {
            delete(genre)
            Assert.assertFalse(getAll().contains(genre))

            delete(genres)
            Assert.assertFalse(getAll().containsAll(genres))

            delete()
            Assert.assertTrue(getAll().isEmpty())
        }
    }
}