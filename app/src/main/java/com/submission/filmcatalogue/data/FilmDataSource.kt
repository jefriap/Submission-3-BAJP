package com.submission.filmcatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.data.remote.response.TvItem
import com.submission.filmcatalogue.vo.Resource

interface FilmDataSource {

    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun insertMovies(movies: List<MovieEntity>)
    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)
    fun getMovieById(movieId: Int): LiveData<Resource<MovieEntity>>
    fun getMovieFavorite(): LiveData<PagedList<MovieEntity>>

    fun getAllTvShow(): LiveData<Resource<PagedList<TvShowEntity>>>
    fun insertTvShows(tvShows: List<TvShowEntity>)
    fun setFavoriteTvShow(tvShow: TvShowEntity, isFavorite: Boolean)
    fun getTvShowById(tvShowId: Int): LiveData<Resource<TvShowEntity>>
    fun getTvShowFavorite(): LiveData<PagedList<TvShowEntity>>

}