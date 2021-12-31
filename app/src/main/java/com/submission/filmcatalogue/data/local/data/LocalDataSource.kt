package com.submission.filmcatalogue.data.local.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.submission.filmcatalogue.data.local.db.FilmDao
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.data.remote.response.MovieItem

open class LocalDataSource constructor(private val filmDao: FilmDao){

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = filmDao.getAllMovies()

    fun insertMovies(movies: List<MovieEntity>){
        filmDao.insertMovies(movies)
    }

    fun setFavoriteMovie(movie: MovieEntity, isFavorite:Boolean){
        movie.favorite = isFavorite
        filmDao.updateMovie(movie)
    }
    
    fun getMovies(): LiveData<List<MovieEntity>> = filmDao.getMovies()

    fun getMovieById(movieId: Int): LiveData<MovieEntity> = filmDao.getMovieById(movieId)

    fun getMovieAsFavorite(): DataSource.Factory<Int, MovieEntity> {
        return filmDao.getMovieAsFavorite()
    }


    fun getAllTvShow(): DataSource.Factory<Int, TvShowEntity> = filmDao.getAllTvShow()

    fun getTvShows(): LiveData<List<TvShowEntity>> = filmDao.getTvShows()

    fun getTvShowById(tvShowId: Int): LiveData<TvShowEntity> = filmDao.getTvShowById(tvShowId)

    fun insertTvShow(tvShow: List<TvShowEntity>){
        filmDao.insertTvShow(tvShow)
    }

    fun setFavoriteTvShow(tvShow: TvShowEntity, isFavorite:Boolean){
        tvShow.favorite = isFavorite
        filmDao.updateTvShow(tvShow)
    }

    fun getTvShowAsFavorite(): DataSource.Factory<Int, TvShowEntity> {
        return filmDao.getTvShowAsFavorite()
    }

    companion object {

        private var INSTANCE: LocalDataSource? = null

        fun getInstance(filmDao: FilmDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE =
                    LocalDataSource(filmDao)
            }
            return INSTANCE as LocalDataSource
        }
    }
}