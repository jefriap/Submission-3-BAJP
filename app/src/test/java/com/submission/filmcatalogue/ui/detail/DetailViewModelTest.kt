package com.submission.filmcatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.submission.filmcatalogue.data.FilmRepository
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.utils.DataDummy
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest{

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observerMovie : Observer<MovieEntity>

    @Mock
    private lateinit var observerTv : Observer<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(filmRepository)
    }

    @Test
    fun testGetMovieDetail() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = DataDummy.viewModelMovieDetail()

        Mockito.`when`(filmRepository.getMovieDetail(movie.value!!.id.toString())).thenReturn(movie)
        
        val dataMovie = viewModel.getMovieDetail(movie.value!!.id.toString()).value

        Assert.assertEquals(movie.value!!.id,dataMovie?.id)
        Assert.assertEquals(movie.value!!.title,dataMovie?.title)
        Assert.assertEquals(movie.value!!.overview,dataMovie?.overview)
        Assert.assertEquals(movie.value!!.posterPath,dataMovie?.posterPath)
        Assert.assertEquals(movie.value!!.releaseDate,dataMovie?.releaseDate)

        viewModel.getMovieDetail(movie.value!!.id.toString()).observeForever(observerMovie)
        verify(observerMovie).onChanged(DataDummy.viewModelMovieDetail())
    }

    @Test
    fun testGetTvShowDetail() {
        val tvShow = MutableLiveData<TvShowEntity>()
        tvShow.value = DataDummy.viewModelTvShowDetail()
        Mockito.`when`(filmRepository.getTvDetail(tvShow.value!!.id.toString())).thenReturn(tvShow)

        val dataTv = viewModel.getTvShowDetail(tvShow.value!!.id.toString()).value
        
        Assert.assertEquals(tvShow.value!!.id,dataTv?.id)
        Assert.assertEquals(tvShow.value!!.name,dataTv?.name)
        Assert.assertEquals(tvShow.value!!.overview,dataTv?.overview)
        Assert.assertEquals(tvShow.value!!.posterPath,dataTv?.posterPath)
        Assert.assertEquals(tvShow.value!!.firstAirDate,dataTv?.firstAirDate)

        viewModel.getTvShowDetail(tvShow.value!!.id.toString()).observeForever(observerTv)
        verify(observerTv).onChanged(DataDummy.viewModelTvShowDetail())
    }
}