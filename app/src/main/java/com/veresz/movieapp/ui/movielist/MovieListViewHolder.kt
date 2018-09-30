package com.veresz.movieapp.ui.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.veresz.movieapp.R
import com.veresz.movieapp.databinding.RowMovieBinding
import com.veresz.movieapp.model.Movie

class MovieListViewHolder(var view: View) : ViewHolder(view) {

    private var binding: RowMovieBinding = DataBindingUtil.bind(view)!!

    fun bind(movie: Movie) {
        binding.item = movie
    }

    companion object {

        fun create(parent: ViewGroup): MovieListViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false)
            return MovieListViewHolder(view)
        }
    }
}
