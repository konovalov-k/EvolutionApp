package com.paxet.evoapp.lesson8.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paxet.evoapp.lesson8.data.network.tmdbapi.MovieItemAPI

@Entity(tableName="movies")
data class Movies(
    @PrimaryKey
    @ColumnInfo(name="id")
    val id: Int? = null,

    @ColumnInfo(name="title")
    val title: String? = null,

    @ColumnInfo(name="genres")
    val genres: String? = null,

    @ColumnInfo(name="vote_count")
    val voteCount: Int? = null,

    @ColumnInfo(name="adult")
    val adult: Boolean? = true,

    @ColumnInfo(name="rating")
    val rating: Double? = 0.0,

    @ColumnInfo(name="overview")
    val overview: String?,

)

//Convert DB Movies class to MovieItemAPI
fun Movies.toMoviesApi() : MovieItemAPI {
    return MovieItemAPI(this.overview,
        null,
        null,
        null,
        this.title,
        this.genres?.split(",")?.toList()?.map { it.toInt() },
        null,
        null,
        null,
        null,
        this.rating,
        this.id,
        this.adult,
        this.voteCount)
}