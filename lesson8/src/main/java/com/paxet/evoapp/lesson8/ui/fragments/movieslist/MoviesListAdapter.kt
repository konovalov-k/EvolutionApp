package com.paxet.evoapp.lesson8.ui.fragments.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.paxet.evoapp.lesson8.R
import com.paxet.evoapp.lesson8.data.network.tmdbapi.MovieItemAPI

class MoviesListAdapter(
    private val navigator : NavController
) : RecyclerView.Adapter<MovieViewHolder>() {
    var movies : List<MovieItemAPI> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =  movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply { putInt("movieId", movies[position].id ?: -1) }
            navigator.navigate(R.id.action_moviesList_to_movieDetails, bundle)
        }
    }
}