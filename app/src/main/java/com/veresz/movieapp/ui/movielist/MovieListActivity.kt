package com.veresz.movieapp.ui.movielist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.veresz.movieapp.R.layout
import com.veresz.movieapp.api.NetworkStatus
import com.veresz.movieapp.api.NetworkStatus.SUCCESS
import com.veresz.movieapp.databinding.ActivityMovielistBinding
import com.veresz.movieapp.model.Movie
import com.veresz.movieapp.ui.detail.MovieDetailActivity
import com.veresz.movieapp.ui.movielist.MovieListAdapter.NowPlayingClickListener
import kotlinx.android.synthetic.main.activity_movielist.recyclerView
import kotlinx.android.synthetic.main.activity_movielist.swipeRefresh

class MovieListActivity : AppCompatActivity() {

    private lateinit var adapter: MovieListAdapter
    private lateinit var model: MovieListViewModel
    private val itemClickListener = object : NowPlayingClickListener {
        override fun onItemClick(view: View, movie: Movie) {
            startActivity(MovieDetailActivity.newIntent(view.context, movie))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        model = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
        super.onCreate(savedInstanceState)
        val binding: ActivityMovielistBinding = DataBindingUtil.setContentView(this, layout.activity_movielist)
        setupAdapter()
        setupSwipeRefresh()

        model.networkStatus.observe(this, Observer<NetworkStatus> {
            adapter.setNetworkStatus(it)
            if (model.refreshStatus.value != SUCCESS) {
                binding.networkStatus = it
            }
        })
        model.movies.observe(this, Observer<PagedList<Movie>> {
            adapter.submitList(it)
        })
    }

    private fun setupSwipeRefresh() {
        model.refreshStatus.observe(this, Observer {
            swipeRefresh.isRefreshing = it == NetworkStatus.LOADING
        })
        swipeRefresh.setOnRefreshListener {
            model.refresh()
        }
    }

    fun retry(view: View) {
        model.refresh()
    }

    private fun setupAdapter() {
        adapter = MovieListAdapter(
                itemClickListener,
                { model.retry() },
                MovieListDiffUtil())

        recyclerView.also {
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(this, 2)
        }
    }
}
