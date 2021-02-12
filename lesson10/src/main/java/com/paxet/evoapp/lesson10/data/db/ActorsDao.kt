package com.paxet.evoapp.lesson10.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActorsDao {
    @Query("SELECT * FROM actors")
    fun getAll(): List<Actors>

    @Query("SELECT * FROM actors where movie_id=:movieId")
    fun getActorsByMovieId(movieId: String): List<Actors>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(actors: List<Actors?>?)
}