package com.paxet.evoapp.lesson9.data.network

import com.paxet.evoapp.lesson9.data.network.tmdbapi.GenresAPI

//To store genres data in memory after initGenres() called
object GenresData {
    var genresData : GenresAPI? = null
}