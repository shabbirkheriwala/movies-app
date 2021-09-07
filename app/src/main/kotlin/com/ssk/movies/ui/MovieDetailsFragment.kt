package com.ssk.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.ssk.movies.R
import com.ssk.movies.di.AppModule
import com.ssk.movies.util.Resource
import com.ssk.movies.util.Utils
import com.ssk.movies.util.Utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.fragment_movie_details.*

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val viewModel: MoviesDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkForNetwork()
        setupObservers()
    }

    private fun checkForNetwork() {
        activity?.let {
            if (!Utils.isNetworkConnected(it.application)) {
                it.progressBar.visibility = View.GONE
                Snackbar.make(
                    constraint_layout,
                    getString(R.string.network_unavailable),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupObservers() {
        val movieId = arguments?.getInt("movieId")
        movieId?.let {
            viewModel.getMovieDetails(movieId).observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        activity?.progressBar?.visibility = View.VISIBLE
                    }

                    Resource.Status.SUCCESS -> {
                        activity?.progressBar?.visibility = View.GONE
                        iv_movie_poster.loadImage(AppModule.BASE_IMAGE_URL + it.data?.poster_path)
                        lbl_movie_title_txt.text = it.data?.title
                        lbl_overview_txt.text = it.data?.overview
                        lbl_release_date_txt.text = it.data?.release_date
                        lbl_rating_txt.text = it.data?.vote_average?.toString()
                    }

                    Resource.Status.ERROR -> {
                        // TODO: Handle error
                        android.util.Log.e(
                            "APP_DEBUG",
                            "MovieDetailsFragment.setupObservers() : Resource.Status.ERROR ${it.message}"
                        )
                    }
                }
            })
        }
    }
}