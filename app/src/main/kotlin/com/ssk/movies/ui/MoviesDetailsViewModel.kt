package com.ssk.movies.ui

import androidx.lifecycle.ViewModel
import com.ssk.movies.model.repositories.MovieDetailsRepository
import com.ssk.movies.model.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesDetailsViewModel @Inject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel(){

    fun getMovieDetails(movieId: Int) = repository.getMovieDetails(movieId)
}
