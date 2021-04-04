package com.paxet.evoapp.lesson8.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.paxet.evoapp.lesson8.data.db.Actors
import com.paxet.evoapp.lesson8.data.db.ActorsDao
import com.paxet.evoapp.lesson8.data.db.AppDatabase
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@LargeTest
class ActorsDaoTest {
    private lateinit var appContext: Context
    private val db by lazy { AppDatabase.getDBInstance(appContext) }
    private lateinit var actorsDao: ActorsDao

    private val movieId = "0"
    private val actors = listOf(
        Actors(100, "Test", "0"),
        Actors(101, "Test", "1"),
        Actors(102, "Test", "1")
    )

    @Before
    fun createDb() {
        appContext = ApplicationProvider.getApplicationContext<Context>()
        actorsDao = db.actorsDao
    }

    @Test
    fun checkInsert(){
        actorsDao.checkInsert()
    }

    private fun ActorsDao.checkInsert() {
        insertAll(actors)
        Assert.assertEquals(actors.first(), getActorsByMovieId(movieId).first { it.id == actors.first().id })
        Assert.assertTrue(getAll().containsAll(actors))
    }

    @Test
    fun checkDelete(){
        actorsDao.checkDeleteOne()
        actorsDao.checkDeleteAll()
    }

    private fun ActorsDao.checkDeleteOne(){
        deleteActor(actors.first())
        Assert.assertTrue(!getAll().contains(actors.first()))
    }

    private fun ActorsDao.checkDeleteAll(){
        insertAll(listOf(actors.first()))
        deleteAll(actors)
        Assert.assertFalse(getAll().containsAll(actors))
    }
}