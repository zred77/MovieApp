package com.veresz.movieapp.ui.movielist

import androidx.recyclerview.widget.DiffUtil
import com.veresz.movieapp.model.Movie

class MovieListDiffUtil : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}