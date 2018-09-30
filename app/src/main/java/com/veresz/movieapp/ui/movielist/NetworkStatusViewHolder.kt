package com.veresz.movieapp.ui.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.veresz.movieapp.R
import com.veresz.movieapp.api.NetworkStatus
import com.veresz.movieapp.databinding.RowNetworkStatusBinding

class NetworkStatusViewHolder(view: View,
                              private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {

    private var binding: RowNetworkStatusBinding = DataBindingUtil.bind(view)!!

    init {
        binding.retryButton.setOnClickListener {
            retryCallback()
        }
    }

    fun bind(status: NetworkStatus?) {
        binding.status = status
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStatusViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_network_status, parent, false)
            return NetworkStatusViewHolder(view, retryCallback)
        }
    }
}