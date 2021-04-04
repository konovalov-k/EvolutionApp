package com.paxet.evoapp.lesson8.data.db

import androidx.room.*

@Dao
interface GenresDao {
    @Query("SELECT * FROM genres")
    fun getAll(): List<Genres>

    @Insert
    fun insert(genre: Genres): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genres: List<Genres?>)

    @Delete
    fun delete(genre: Genres)

    @Delete
    fun delete(genres: List<Genres>)

    @Query("DELETE FROM genres")
    fun delete()
}