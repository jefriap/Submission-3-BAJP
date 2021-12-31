package com.submission.filmcatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.submission.filmcatalogue.data.FilmRepository
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.data.remote.response.TvItem
import com.submission.filmcatalogue.vo.Resource

class TvShowViewModel(private val dataRepo: FilmRepository): ViewModel() {

    fun getListTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> =
        dataRepo.getAllTvShow()
}