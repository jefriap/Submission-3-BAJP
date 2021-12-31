package com.submission.filmcatalogue.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.submission.filmcatalogue.data.FilmDataSource
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.data.remote.RemoteDataSource
import com.submission.filmcatalogue.data.remote.response.DetailMovieResponse
import com.submission.filmcatalogue.data.remote.response.DetailTvShowResponse
import com.submission.filmcatalogue.data.remote.response.MovieItem
import com.submission.filmcatalogue.data.remote.response.TvItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class FakeFilmRepository(private val remoteDataSource: RemoteDataSource):
    FilmDataSource {


    override fun getMovieList(): LiveData<List<MovieItem>> {
        val movieList = MutableLiveData<List<MovieItem>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getMovie(object : RemoteDataSource.GetMovieCallback {
                override fun onResponse(movieResponse: List<MovieItem>) {
                    movieList.postValue(movieResponse)
                }
            })
        }
        return movieList
    }

    override fun getMovieDetail(movieId: String): LiveData<MovieEntity> {
        val movieDetails = MutableLiveData<MovieEntity>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getMovieDetail(movieId, object : RemoteDataSource.GetMovieDetailCallback {
                override fun onResponse(movieDetailResponse: DetailMovieResponse) {
                    with(movieDetailResponse) {
                        val detailMovie = MovieEntity(
                            id = id!!,
                            overview = overview!!,
                            posterPath = posterPath!!,
                            releaseDate = releaseDate!!,
                            runtime = runtime!!,
                            title = title!!,
                            voteAverage = voteAverage!!
                        )
                        movieDetails.postValue(detailMovie)
                    }
                }
            })
        }
        return movieDetails
    }

    override fun getTvList(): LiveData<List<TvItem>> {
        val tvShowList = MutableLiveData<List<TvItem>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getTvShow(object : RemoteDataSource.GetTvShowCallback {
                override fun onResponse(tvShowResponse: List<TvItem>) {
                    tvShowList.postValue(tvShowResponse)
                }
            })
        }
        return tvShowList
    }

    override fun getTvDetail(tvShowId: String): LiveData<TvShowEntity> {
        val tvShowDetails = MutableLiveData<TvShowEntity>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getTvShowDetail(tvShowId, object : RemoteDataSource.GetTvShowDetailCallback {
                override fun onResponse(tvShowDetailResponse: DetailTvShowResponse) {
                    with(tvShowDetailResponse) {
                        val detailTv = TvShowEntity(
                            id = id!!,
                            overview = overview!!,
                            posterPath = posterPath!!,
                            firstAirDate = firstAirDate!!,
                            episodeRunTime = episodeRunTime!!,
                            name = name!!,
                            voteAverage = voteAverage!!
                        )
                        tvShowDetails.postValue(detailTv)
                    }
                }
            })
        }
        return tvShowDetails
    }
}