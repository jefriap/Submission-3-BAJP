package com.submission.filmcatalogue.data.local.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.data.remote.response.MovieItem

@Dao
interface FilmDao {

    @Query("SELECT * FROM movieentity")
    fun getAllMovies(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movie: List<MovieEntity>): LongArray

    @Update
    fun updateMovie(movie: MovieEntity)

    @Transaction
    @Query("SELECT * FROM movieentity WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieEntity>

    @WorkerThread
    @Query("SELECT * FROM movieentity where favorite = 1")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movieentity where favorite = 1")
    fun getMovieAsFavorite(): DataSource.Factory<Int, MovieEntity>



    @Query("SELECT * FROM tvshowentity")
    fun getAllTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Transaction
    @Query("SELECT * FROM tvshowentity WHERE tvId = :tvShowId")
    fun getTvShowById(tvShowId: Int): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<TvShowEntity>): LongArray

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @WorkerThread
    @Query("SELECT * FROM tvshowentity where favorite = 1")
    fun getTvShows(): LiveData<List<TvShowEntity>>

    @Query("SELECT * FROM tvshowentity where favorite = 1")
    fun getTvShowAsFavorite(): DataSource.Factory<Int, TvShowEntity>
}