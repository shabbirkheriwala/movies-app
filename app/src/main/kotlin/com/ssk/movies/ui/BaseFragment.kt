package com.ssk.movies.ui

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ssk.movies.R
import com.ssk.movies.util.Utils
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.fragment_movies_list.*

abstract class BaseFragment : Fragment() {

    fun checkForNetwork() {
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
}
