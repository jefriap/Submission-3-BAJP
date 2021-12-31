package com.submission.filmcatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.submission.filmcatalogue.vo.Resource
import com.submission.filmcatalogue.data.FilmRepository
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity

class DetailViewModel(private val dataRepo: FilmRepository) : ViewModel() {
    val itemId = MutableLiveData<Int>()

    fun setSelectedItem(itemId: Int) {
        this.itemId.value = itemId
    }

    var movieItem: LiveData<Resource<MovieEntity>> = Transformations.switchMap(itemId) {
        dataRepo.getMovieById(it)
    }

    var tvShowItem: LiveData<Resource<TvShowEntity>> = Transformations.switchMap(itemId) {
        dataRepo.getTvShowById(it)
    }

    fun setFavoriteMovie() {
        val movieResource = movieItem.value
        if (movieResource != null) {
            val movieData = movieResource.data

            if (movieData != null) {
                val newState = !movieData.favorite!!
                dataRepo.setFavoriteMovie(movieData, newState)
            }
        }
    }

    fun setFavoriteTvShow() {
        val tvResource = tvShowItem.value
        if (tvResource != null) {
            val tvShowData = tvResource.data

            if (tvShowData != null) {
                val newState = !tvShowData.favorite!!
                dataRepo.setFavoriteTvShow(tvShowData, newState)
            }
        }
    }
}