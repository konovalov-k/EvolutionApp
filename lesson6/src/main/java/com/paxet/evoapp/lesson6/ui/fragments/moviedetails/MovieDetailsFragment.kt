package com.paxet.evoapp.lesson6.ui.fragments.moviedetails

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paxet.evoapp.lesson6.R

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    val viewModel : MovieDetailsVM by viewModels()
    val actorsAdapter = ActorsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers(view)

        view.findViewById<RecyclerView>(R.id.rv_actors_list).run {
            adapter = actorsAdapter
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.initMovie(arguments)
    }

    private fun initObservers(view : View) {
        val bw_poster : ImageView = view.findViewById(R.id.bw_poster)
        val age : TextView = view.findViewById(R.id.age)
        val name : TextView = view.findViewById(R.id.name)
        val genres : TextView = view.findViewById(R.id.genres)
        val rating : RatingBar = view.findViewById(R.id.ratingBar)
        val reviewCounter : TextView = view.findViewById(R.id.counter)
        val storyLine : TextView = view.findViewById(R.id.body)
        viewModel.movieLD.observe(viewLifecycleOwner, Observer {
            movie ->
            Glide.with(view).load(movie.backdrop).into(bw_poster)
            age.text = movie.minimumAge.toString() + "+"
            name.text = movie.title
            genres.text = movie.genres.joinToString { it.name }
            rating.rating = movie.ratings ?: 0f
            reviewCounter.text = movie.numberOfRatings.toString()
            storyLine.text = movie.overview

            actorsAdapter.actors = movie.actors
        })
    }
}