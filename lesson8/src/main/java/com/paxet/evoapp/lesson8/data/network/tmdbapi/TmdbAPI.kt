package com.paxet.evoapp.lesson8.data.network.tmdbapi

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("configuration")
    fun getAPIConfiguration(
        @Query("api_key") apiKey : String
    ) : ConfigurationAPI

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey : String
    ) : MoviesListAPI

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id")  movieId : String,
        @Query("api_key") apiKey : String
    ) : MovieDetailsAPI

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id")  movieId : String,
        @Query("api_key") apiKey : String
    ) : MovieCreditsAPI

    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey : String
    ) : GenresAPI
}

