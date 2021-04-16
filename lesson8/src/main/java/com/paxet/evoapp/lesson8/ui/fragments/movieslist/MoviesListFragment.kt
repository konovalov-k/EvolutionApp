package com.paxet.evoapp.lesson8.ui.fragments.movieslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paxet.evoapp.lesson8.R

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {
    private val viewModel : MoviesListVM by lazy{MoviesListVM(requireContext())}
    private val moviesListAdapter by lazy { MoviesListAdapter(findNavController()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       requireActivity().title = getString(R.string.movies_list)
        initObserver()
        view.findViewById<RecyclerView>(R.id.rv_movies_list).run {
            adapter = moviesListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        viewModel.initConfiguration()
        viewModel.initGenres()
        viewModel.initMoviesListAsync()
    }

    private fun initObserver() {
        viewModel.moviesListLD.observe(viewLifecycleOwner, { moviesList ->
            moviesListAdapter.movies = moviesList ?: return@observe
        })
    }
}