package com.submission.filmcatalogue.data.remote.network

import com.submission.filmcatalogue.data.remote.response.DetailMovieResponse
import com.submission.filmcatalogue.data.remote.response.DetailTvShowResponse
import com.submission.filmcatalogue.data.remote.response.ListMovieResponse
import com.submission.filmcatalogue.data.remote.response.ListTvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("movie/popular")
    fun getListMovies(
        @Query("api_key") api_key: String
    ): Call<ListMovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") api_key: String
    ): Call<DetailMovieResponse>

    @GET("tv/popular")
    fun getListTvShow(
        @Query("api_key") api_key: String
    ): Call<ListTvShowResponse>

    @GET("tv/{tv_id}")
    fun getTvShowDetails(
        @Path("tv_id") tvId: String,
        @Query("api_key") api_key: String
    ): Call<DetailTvShowResponse>
}