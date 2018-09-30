package com.veresz.movieapp.ui.movielist

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.veresz.movieapp.R
import com.veresz.movieapp.api.NetworkStatus
import com.veresz.movieapp.model.Movie

class MovieListAdapter(private val clickListener: NowPlayingClickListener,
                       private val retryCallback: () -> Unit,
                       diffUtil: MovieListDiffUtil) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(diffUtil) {

    private var networkStatus: NetworkStatus? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.row_network_status -> NetworkStatusViewHolder.create(parent, retryCallback)
            else -> MovieListViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)!!
        holder.itemView.setOnClickListener { view -> clickListener.onItemClick(view, movie) }
        when (getItemViewType(position)) {
            R.layout.row_movie -> (holder as MovieListViewHolder).bind(movie)
            R.layout.row_network_status -> (holder as NetworkStatusViewHolder).bind(networkStatus)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.row_network_status
        } else {
            R.layout.row_movie
        }
    }

    private fun hasExtraRow() = networkStatus != null && networkStatus != NetworkStatus.SUCCESS

    fun setNetworkStatus(newNetworkStatus: NetworkStatus?) {
        val previousStatus = this.networkStatus
        val hadExtraRow = hasExtraRow()
        this.networkStatus = newNetworkStatus
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousStatus != newNetworkStatus) {
            notifyItemChanged(itemCount - 1)
        }
    }

    interface NowPlayingClickListener {

        fun onItemClick(view: View, movie: Movie)
    }
}