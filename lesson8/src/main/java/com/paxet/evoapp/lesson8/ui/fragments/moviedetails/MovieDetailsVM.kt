package com.paxet.evoapp.lesson8.ui.fragments.moviedetails

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paxet.evoapp.lesson8.data.db.toCastItem
import com.paxet.evoapp.lesson8.data.network.tmdbapi.CastItem
import com.paxet.evoapp.lesson8.data.network.tmdbapi.MovieDetailsAPI
import com.paxet.evoapp.lesson8.data.network.tmdbapi.toActors
import com.paxet.evoapp.lesson8.ui.fragments.BaseVM
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MovieDetailsVM(app: Application) : BaseVM(app) {
    private val _movieLD = MutableLiveData<Pair<MovieDetailsAPI?, List<CastItem?>>>()
    val movieLD : LiveData<Pair<MovieDetailsAPI?, List<CastItem?>>> get() = _movieLD

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${coroutineScope.isActive}", throwable)
    }

    fun initMovie(arguments: Bundle?) {
        val movieId = arguments?.get("movieId").toString()
        coroutineScope.launch(exceptionHandler) {
            //Get movie credits (actors) from DB cache
            var movieCredits: List<CastItem?> = readCreditsFromDb(movieId)
            var movieDetails: MovieDetailsAPI? = null
            _movieLD.postValue(Pair(movieDetails, movieCredits))
            //Get movie credits (actors) and movie details from network
            try {
               movieDetails = tmdbAPI.getMovieDetails(movieId, apiKey)
               movieCredits = tmdbAPI.getMovieCredits(movieId, apiKey).cast
                _movieLD.postValue(Pair(movieDetails, movieCredits))
                //Store movie credits (actors) to DB cache
                writeCreditsFromDb(movieId, movieCredits)
            } catch (e: Exception) {
                print(e.message)
            }
        }
    }

    fun readCreditsFromDb(movieId: String): List<CastItem?> {
        return db.actorsDao.getActorsByMovieId(movieId).map( {it.toCastItem()} )
    }

    fun writeCreditsFromDb(movieId: String, movieCredits: List<CastItem?>?) {
        db.actorsDao.insert(movieCredits?.map( {it?.toActors(movieId)} ))
    }

    companion object {
        private val TAG = MovieDetailsVM::class.java.simpleName
    }
}