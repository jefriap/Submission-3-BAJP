package com.submission.filmcatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.submission.filmcatalogue.data.FilmRepository
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.vo.Resource

class MovieViewModel(private val dataRepo: FilmRepository) : ViewModel() {

    fun getListMovies(): LiveData<Resource<PagedList<MovieEntity>>> =
        dataRepo.getAllMovies()
}