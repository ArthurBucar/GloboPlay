package com.exemplo.globoplay.services

import com.exemplo.globoplay.model.GetResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "12b7f2a35c49e6e7b30e49095c4785a1",
        @Query("page") page: Int
    ): Call<GetResponse>
}