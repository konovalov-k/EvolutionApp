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
    fun insert(actors: List<Actors?>?)

    @Insert
    @PrimaryKey(autoGenerate = true)
    fun insert(actor: Actors)

    @Delete
    fun delete(actor: Actors)

    @Delete
    fun delete(actors: List<Actors>)

    @Query("DELETE FROM actors")
    fun delete()

}