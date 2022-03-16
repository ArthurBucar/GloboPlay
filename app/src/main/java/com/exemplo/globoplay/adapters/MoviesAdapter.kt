package com.exemplo.globoplay.adapters

import android.content.pm.ModuleInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.exemplo.globoplay.Movie
import com.exemplo.globoplay.R

class MoviesAdapter( private var movies: MutableList<Movie>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Movie)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return MovieViewHolder(view, mListener)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    inner class MovieViewHolder(itemView: View, val listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movie: Movie) {
            itemView.setOnClickListener(View.OnClickListener {
                listener.onItemClick(movie)
                //Toast.makeText(itemView.context, "voce clicou em: " + movie.title, Toast.LENGTH_SHORT).show()
            })
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/original${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
        }
    }
}