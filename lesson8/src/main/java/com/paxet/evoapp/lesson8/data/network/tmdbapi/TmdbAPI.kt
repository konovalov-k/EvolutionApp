package com.paxet.evoapp.lesson8.data.network.tmdbapi

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("configuration")
    suspend fun getAPIConfiguration(
        @Query("api_key") apiKey : String
    ) : ConfigurationAPI

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey : String
    ) : MoviesListAPI

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")  movieId : String,
        @Query("api_key") apiKey : String
    ) : MovieDetailsAPI

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id")  movieId : String,
        @Query("api_key") apiKey : String
    ) : MovieCreditsAPI

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey : String
    ) : GenresAPI
}

