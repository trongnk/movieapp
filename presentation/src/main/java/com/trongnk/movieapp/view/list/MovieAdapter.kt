package com.trongnk.movieapp.view.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trongnk.movieapp.databinding.ItemMovieBinding
import com.trongnk.movieapp.util.extension.inflater

class MovieAdapter(val onMovieClick: (MovieListViewModel.Movie) -> Unit) : ListAdapter<MovieListViewModel.Movie, MovieAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(parent.inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = currentList[position]
        holder.binding.data = movie
        holder.itemView.setOnClickListener {
            onMovieClick(movie)
        }
    }

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    object DiffCallback : DiffUtil.ItemCallback<MovieListViewModel.Movie>() {
        override fun areItemsTheSame(oldItem: MovieListViewModel.Movie, newItem: MovieListViewModel.Movie) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: MovieListViewModel.Movie, newItem: MovieListViewModel.Movie) =
            oldItem.title == newItem.title
            && oldItem.description == newItem.description
            && oldItem.onMyWatchList == newItem.onMyWatchList
            && oldItem.releasedDate == newItem.releasedDate
            && oldItem.poster == newItem.poster
    }
}