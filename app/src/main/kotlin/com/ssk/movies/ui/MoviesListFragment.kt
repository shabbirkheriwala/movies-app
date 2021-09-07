package com.ssk.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssk.movies.R
import com.ssk.movies.di.AppModule
import com.ssk.movies.model.entities.Movie
import com.ssk.movies.util.Resource
import com.ssk.movies.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.android.synthetic.main.list_item_movie.view.*
import com.google.android.material.snackbar.Snackbar

@AndroidEntryPoint
class MoviesListFragment : Fragment() {
    private val viewModel: MoviesListViewModel by viewModels()
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkForNetwork()
        setupRecyclerView()
        setupObservers()
    }

    private fun checkForNetwork() {
        activity?.let {
            if (!Utils.isNetworkConnected(it.application)) {
                it.progressBar.visibility = View.GONE
                Snackbar.make(
                    recyclerView,
                    getString(R.string.network_unavailable),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MovieListAdapter()
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getPopularMovies().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    activity?.progressBar?.visibility = View.VISIBLE
                }

                Resource.Status.SUCCESS -> {
                    activity?.progressBar?.visibility = View.GONE
                    it.data?.let {
                        if (it.isNotEmpty()){
                            adapter.setData(ArrayList(it))
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    // TODO: handle error
                    android.util.Log.e(
                        "APP_DEBUG",
                        "MoviesListFragment.setupObservers() : Resource.Status.ERROR ${it.message}"
                    )
                }
            }
        })
    }

    class MovieListAdapter() :
        RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

        private val data = ArrayList<Movie>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MovieViewHolder {
            return MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
            )
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            data.let { data[position] }.let { holder.bind(it) }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        fun setData(movieData: ArrayList<Movie>) {
            data.clear()
            data.addAll(movieData)
            notifyDataSetChanged()
        }

        class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val title: TextView = itemView.findViewById(R.id.tv_movie_title)
            private val image: ImageView = itemView.findViewById(R.id.iv_movie_poster)

            fun bind(movie: Movie) {
                title.text = movie.title
                Glide.with(image)
                    .load(AppModule.BASE_IMAGE_URL + movie.poster_path)
                    .into(image)

                itemView.setOnClickListener {
                    val direction = R.id.action_movie_details
                    Navigation.findNavController(itemView).navigate(direction, bundleOf("movieId" to movie.id))
                }
            }
        }
    }
}