package com.submission.filmcatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.submission.filmcatalogue.data.local.data.LocalDataSource
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.data.remote.RemoteDataSource
import com.submission.filmcatalogue.data.remote.response.ApiResponse
import com.submission.filmcatalogue.data.remote.response.MovieItem
import com.submission.filmcatalogue.data.remote.response.TvItem
import com.submission.filmcatalogue.utils.AppExecutors
import com.submission.filmcatalogue.vo.Resource
import java.util.*

class FilmRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    val executors: AppExecutors
    ): FilmDataSource {

//Repository Movie//
    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieItem>>(executors) {

            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(6)
                    .setPageSize(6)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()
            
            public override fun createCall(): LiveData<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getMovieList()

            public override fun saveCallResult(data: List<MovieItem>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        movieId = response.movieId,
                        title = response.movieTitle,
                        overview = response.movieDescription,
                        releaseDate = response.movieRelease,
                        runtime = response.movieDuration,
                        posterPath = response.moviePoster,
                        voteAverage = response.movieRating,
                        genre = response.movieGenre,
                        favorite = false)
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun insertMovies(movies: List<MovieEntity>) {
        val runnable = {
            if(localDataSource.getMovies().value.isNullOrEmpty()){
                localDataSource.insertMovies(movies)
            }
        }
        executors.diskIO().execute(runnable)
    }

    override fun getMovieById(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieItem>(executors) {
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovieById(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<MovieItem>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieItem) {

            }

        }.asLiveData()
    }

    override fun getMovieFavorite(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(6)
            .setPageSize(6)
            .build()
        return LivePagedListBuilder(localDataSource.getMovieAsFavorite(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) =
        executors.diskIO().execute {
            localDataSource.setFavoriteMovie(movie, state)
        }

//Repository Tv Show//
    override fun getAllTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvItem>>(executors) {

            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(6)
                    .setPageSize(6)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShow(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvItem>>> {
                return remoteDataSource.getTvShowList()
            }

            public override fun saveCallResult(data: List<TvItem>) {
                val tvList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val tv = TvShowEntity(
                        tvId = response.tvShowId,
                        name = response.tvShowTitle,
                        overview = response.tvShowDescription,
                        firstAirDate = response.tvShowRelease,
                        episodeRunTime = response.tvShowDuration,
                        posterPath = response.tvShowPoster,
                        voteAverage = response.tvShowRating,
                        genre = response.tvShowGenre,
                        favorite = false)
                    tvList.add(tv)
                }
                localDataSource.insertTvShow(tvList)
            }
        }.asLiveData()
    }

    override fun insertTvShows(tvShows: List<TvShowEntity>) {
        val runnable = {
            if(localDataSource.getTvShows().value.isNullOrEmpty()){
                localDataSource.insertTvShow(tvShows)
            }
        }
        executors.diskIO().execute(runnable)
    }

    override fun getTvShowById(tvShowId: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvItem>(executors) {
            override fun loadFromDB(): LiveData<TvShowEntity> =
                localDataSource.getTvShowById(tvShowId)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<TvItem>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(data: TvItem) {

            }

        }.asLiveData()
    }

    override fun getTvShowFavorite(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(6)
            .setPageSize(6)
            .build()
        return LivePagedListBuilder(localDataSource.getTvShowAsFavorite(), config).build()
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, isFavorite: Boolean) {
        executors.diskIO().execute { localDataSource.setFavoriteTvShow(tvShow, isFavorite) }
    }

    companion object {

        @Volatile
        private var INSTANCE: FilmRepository? = null

        fun getInstance(local: LocalDataSource, remote: RemoteDataSource, appExecutors: AppExecutors): FilmRepository? {
            if (INSTANCE == null) {
                synchronized(FilmRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            FilmRepository(remote, local, appExecutors)
                    }
                }
            }
            return INSTANCE
        }
    }
}