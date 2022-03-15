package com.exemplo.globoplay.model

import com.exemplo.globoplay.Movie
import com.google.gson.annotations.SerializedName

data class GetResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)
