package com.paxet.evoapp.lesson8.data.db

import androidx.room.*

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<Movies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movies>)

    @Insert
    fun insertMovie(movie: Movies): Long

    @Delete
    fun delete(movie: Movies)

    @Delete
    fun delete(movies: List<Movies>)

    @Query ("DELETE FROM movies")
    fun delete()
}