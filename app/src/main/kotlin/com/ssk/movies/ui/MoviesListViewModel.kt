package com.ssk.movies.ui

import androidx.lifecycle.ViewModel
import com.ssk.movies.model.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel(){

    fun getPopularMovies() = repository.getPopularMovies()
}