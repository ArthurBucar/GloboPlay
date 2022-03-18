package com.exemplo.globoplay.views

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.globoplay.Movie
import com.exemplo.globoplay.R
import com.exemplo.globoplay.adapters.MoviesAdapter
import com.exemplo.globoplay.repository.MoviesRepository


class HomeActivity : AppCompatActivity() {
    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager

    private lateinit var popularNoveo: RecyclerView
    private lateinit var popularNoveoAdapter: MoviesAdapter
    private lateinit var popularNoveoLayoutMgr: LinearLayoutManager

    private lateinit var popularSeries: RecyclerView
    private lateinit var popularSeriesAdapter: MoviesAdapter
    private lateinit var popularSeriesLayoutMgr: LinearLayoutManager

    private var popularMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setTitle("globoplay")

        popularMovies = findViewById(R.id.popular_movies)
        popularNoveo = findViewById(R.id.popular_noves)
        popularSeries = findViewById(R.id.popular_series)

        popularMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularNoveoLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        popularSeriesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MoviesAdapter(mutableListOf())
        popularMovies.adapter = popularMoviesAdapter

        popularNoveo.layoutManager = popularNoveoLayoutMgr
        popularNoveoAdapter = MoviesAdapter(mutableListOf())
        popularNoveo.adapter = popularNoveoAdapter

        popularSeries.layoutManager = popularSeriesLayoutMgr
        popularSeriesAdapter = MoviesAdapter(mutableListOf())
        popularSeries.adapter = popularSeriesAdapter

        getPopularMovies()
        //getPopularNoves()
        //getPopularSeries()
    }

    private fun setTitle(title: String?) {
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val textView = TextView(this)
        textView.text = title
        textView.textSize = 26f
        textView.setTypeface(null, Typeface.BOLD)
        textView.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        textView.gravity = Gravity.CENTER
        textView.setMargins(16, 16, 16, 16)
        textView.setTextColor(resources.getColor(R.color.white))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.customView = textView
    }

    fun View.setMargins(
        left: Int = this.marginLeft,
        top: Int = this.marginTop,
        right: Int = this.marginRight,
        bottom: Int = this.marginBottom,
    ) {
        layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
            setMargins(left, top, right, bottom)
        }
    }

    private fun getPopularMovies() {
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun getPopularNoves() {
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularNoveoFetched,
            ::onError
        )
    }

    private fun getPopularSeries() {
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularSeriesFetched,
            ::onError
        )
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    private fun attachPopularNoveoOnScrollListener() {
        popularNoveo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularNoveoLayoutMgr.itemCount
                val visibleItemCount = popularNoveoLayoutMgr.childCount
                val firstVisibleItem = popularNoveoLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularNoveo.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularNoves()
                }
            }
        })
    }

    private fun attachPopularSeriesOnScrollListener() {
        popularSeries.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularSeriesLayoutMgr.itemCount
                val visibleItemCount = popularSeriesLayoutMgr.childCount
                val firstVisibleItem = popularSeriesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularSeries.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularNoves()
                }
            }
        })
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
        var adapter = popularMoviesAdapter
        popularMovies.adapter = adapter
        adapter.setOnItemClickListener(object : MoviesAdapter.onItemClickListener {
            override fun onItemClick(movie: Movie) {
                Toast.makeText(
                    this@HomeActivity,
                    "voce clicou em: " + movie.overview,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        attachPopularMoviesOnScrollListener()
    }

    private fun onPopularNoveoFetched(movies: List<Movie>) {
        popularNoveoAdapter.appendMovies(movies)
        attachPopularNoveoOnScrollListener()
    }

    private fun onPopularSeriesFetched(movies: List<Movie>) {
        popularSeriesAdapter.appendMovies(movies)
        attachPopularSeriesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_search_movies), Toast.LENGTH_SHORT).show()
    }

}