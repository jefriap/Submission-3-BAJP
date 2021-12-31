package com.submission.filmcatalogue.data.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.submission.filmcatalogue.data.remote.response.ApiResponse
import com.submission.filmcatalogue.data.remote.response.MovieItem
import com.submission.filmcatalogue.data.remote.response.TvItem
import com.submission.filmcatalogue.utils.EspressoIdlingResource
import com.submission.filmcatalogue.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper){
    
    private val handler = Handler(Looper.getMainLooper())

    fun getMovieList(): LiveData<ApiResponse<List<MovieItem>>> {
        EspressoIdlingResource.increment()
        val resultCourse = MutableLiveData<ApiResponse<List<MovieItem>>>()
        handler.postDelayed({
            resultCourse.value = ApiResponse.success(jsonHelper.getMovieList())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultCourse
    }

    fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieItem>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<MovieItem>>()
        handler.postDelayed({
            resultDetailMovie.value = ApiResponse.success(jsonHelper.getMovieById(movieId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultDetailMovie
    }

    fun getTvShowList(): LiveData<ApiResponse<List<TvItem>>> {
        EspressoIdlingResource.increment()
        val resultCourse = MutableLiveData<ApiResponse<List<TvItem>>>()
        handler.postDelayed({
            resultCourse.value = ApiResponse.success(jsonHelper.getTvShowList())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultCourse
    }

     fun getTvShowDetail(tvShowId: Int): LiveData<ApiResponse<TvItem>> {
         EspressoIdlingResource.increment()
         val resultDetailMovie = MutableLiveData<ApiResponse<TvItem>>()
         handler.postDelayed({
             resultDetailMovie.value = ApiResponse.success(jsonHelper.getTvShowById(tvShowId))
             EspressoIdlingResource.decrement()
         }, SERVICE_LATENCY_IN_MILLIS)
         return resultDetailMovie
    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(helper).apply { instance = this }
            }
    }

}