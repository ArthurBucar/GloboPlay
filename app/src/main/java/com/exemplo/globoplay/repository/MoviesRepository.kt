package com.exemplo.globoplay.repository

import com.exemplo.globoplay.model.GetResponse
import com.exemplo.globoplay.Movie
import com.exemplo.globoplay.services.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KFunction1

object MoviesRepository {

    private val api: API

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(API::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: KFunction1<List<Movie>, Unit>,
        onError: () -> Unit
    ) {
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<GetResponse> {
                override fun onResponse(
                    call: Call<GetResponse>,
                    response: Response<GetResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}