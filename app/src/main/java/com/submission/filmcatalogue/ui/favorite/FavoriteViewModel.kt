package com.submission.filmcatalogue.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.submission.filmcatalogue.data.FilmRepository
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity

class FavoriteViewModel(private val dataRepo: FilmRepository) : ViewModel()  {

    fun getListMovieFavorite(): LiveData<PagedList<MovieEntity>> =
        dataRepo.getMovieFavorite()

    fun getListTvShowFavorite(): LiveData<PagedList<TvShowEntity>> =
        dataRepo.getTvShowFavorite()
}