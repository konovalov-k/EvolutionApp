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

    private val actor = Actors(-99, "Test", "0")
    private val actors = listOf(
        Actors(-100, "Test", "0"),
        Actors(-101, "Test", "1"),
        Actors(-102, "Test", "1")
    )

    @Before
    fun createDb() {
        appContext = ApplicationProvider.getApplicationContext<Context>()
        actorsDao = db.actorsDao
    }

    @Test
    fun checkInsert() {
        actorsDao.run {
            insert(actor)
            Assert.assertTrue(getAll().contains(actor))

            insert(actors)
            Assert.assertTrue(getAll().containsAll(actors))
        }

    }

    @Test
    fun checkDelete(){
        actorsDao.run {
            delete(actor)
            Assert.assertFalse(getAll().contains(actor))

            delete(actors)
            Assert.assertFalse(getAll().containsAll(actors))

            delete()
            Assert.assertTrue(getAll().isEmpty())
        }
    }
}