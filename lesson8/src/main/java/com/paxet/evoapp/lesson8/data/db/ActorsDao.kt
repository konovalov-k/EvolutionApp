package com.paxet.evoapp.lesson8.data.db

import androidx.room.*

@Dao
interface ActorsDao {
    @Query("SELECT * FROM actors")
    fun getAll(): List<Actors>

    @Query("SELECT * FROM actors where movieId=:movieId")
    fun getActorsByMovieId(movieId: String): List<Actors>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @PrimaryKey(autoGenerate = true)
    fun insertAll(actors: List<Actors?>?)

    @Delete
    fun deleteActor(actor: Actors)

    @Delete
    fun deleteAll(actors: List<Actors>)
}