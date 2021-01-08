package com.paxet.evoapp.lesson7.ui.fragments.movieslist

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.paxet.evoapp.lesson7.R
import com.paxet.evoapp.lesson7.data.network.NetworkModule
import com.paxet.evoapp.lesson7.data.tmdbapi.MovieItemAPI


class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val poster : ImageView = itemView.findViewById(R.id.color_poster)
    private val like_enabled : ImageView = itemView.findViewById(R.id.like_enabled)
    private val like_disabled : ImageView = itemView.findViewById(R.id.like_disabled)
    private val title : TextView = itemView.findViewById(R.id.movie_name)
    private val genres : TextView = itemView.findViewById(R.id.genres)
    private val reviews_counter : TextView = itemView.findViewById(R.id.reviews_counter)
    private val age : TextView = itemView.findViewById(R.id.age)
    private val duration : TextView = itemView.findViewById(R.id.duration)
    private val ratingBar : RatingBar = itemView.findViewById(R.id.ratingBar)
    private val view = view

    fun bind(movie: MovieItemAPI) {
        Glide.with(view)
            .load("${NetworkModule.baseImageUrl}/w154/${movie.posterPath}")
            .apply(RequestOptions.bitmapTransform(RoundedCorners(150)))
            .into(poster)

        title.text = movie.title
        //TODO change this
        genres.text = movie.genreIds?.joinToString { it.toString() } ?: ""
        reviews_counter.text = movie.voteCount.toString()
        //TODO do not see age restrictions in TMDB API
        age.text = if(movie.adult == true) "18+" else "0+"
        //TODO check if works correct, calibrate to 5 stars indicator
        ratingBar.rating = movie.voteAverage?.toFloat() ?: 0f
        //TODO do not see movie duration in TMDB API
        duration.text = ""
    }
}