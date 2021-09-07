package com.ssk.movies.ui

import android.os.Bundle
import com.ssk.movies.R
import com.ssk.movies.databinding.ActivityMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : BaseActivity<ActivityMoviesBinding>() {
    override fun getLayoutId() = R.layout.activity_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}