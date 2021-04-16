package com.paxet.evoapp.lesson8.ui.fragments.moviedetails

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paxet.evoapp.lesson8.R
import com.paxet.evoapp.lesson8.data.network.NetworkModule
import com.paxet.evoapp.lesson8.ui.fragments.actors.ActorsAdapter

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    val viewModel : MovieDetailsVM by lazy{ MovieDetailsVM(requireContext())}
    val actorsAdapter = ActorsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers(view)

        view.findViewById<RecyclerView>(R.id.rv_actors_list).run {
            adapter = actorsAdapter
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.initMovie(arguments)

        view.findViewById<TextView>(R.id.back_txt).setOnClickListener {
            view.findNavController().navigate(R.id.action_movieDetails_to_moviesList)
        }
        view.findViewById<ImageView>(R.id.back_img).setOnClickListener {
            view.findNavController().navigate(R.id.action_movieDetails_to_moviesList)
        }
    }

    private fun initObservers(view: View) {
        val bw_poster : ImageView = view.findViewById(R.id.bw_poster)
        val age : TextView = view.findViewById(R.id.age)
        val name : TextView = view.findViewById(R.id.name)
        val genresTV : TextView = view.findViewById(R.id.genres)
        val rating : RatingBar = view.findViewById(R.id.ratingBar)
        val reviewCounter : TextView = view.findViewById(R.id.counter)
        val storyLine: TextView = view.findViewById(R.id.body)

        viewModel.movieLD.observe(viewLifecycleOwner, Observer { movie ->
            movie.first?.run {
                if(NetworkModule.baseImageUrl.isNotEmpty()) {
                    //TODO: Glide cache or dummy image if baseImageUrl is empty
                    Glide.with(view)
                        .load("${NetworkModule.baseImageUrl}/w342/${backdropPath}")
                        .into(bw_poster)
                }
                bw_poster.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })

                age.text = if (adult == true) "18+" else "0+"
                name.text = title ?: ""
                genresTV.text = genres?.joinToString { it?.name.toString() } ?: ""
                rating.rating = voteAverage?.toFloat() ?: 0f
                reviewCounter.text = voteCount.toString()
                storyLine.text = overview ?: ""
            }

            movie.second.run {
                actorsAdapter.actors = this
            }
        })
    }
}