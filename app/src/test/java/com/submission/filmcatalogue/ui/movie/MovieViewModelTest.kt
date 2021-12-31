package com.submission.filmcatalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.submission.filmcatalogue.data.FilmRepository
import com.submission.filmcatalogue.data.remote.response.MovieItem
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
class MovieViewModelTest{

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<List<MovieItem>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(filmRepository)
    }

    @Test
    fun testListMovie() {
        val movie = MutableLiveData<List<MovieItem>>()
        movie.value = DataDummy.getDummyMovie()

        Mockito.`when`(filmRepository.getMovieList()).thenReturn(movie)

        val dataMovie = viewModel.listMovie().value

        verify(filmRepository).getMovieList()
        Assert.assertNotNull(dataMovie)
        Assert.assertEquals(2, dataMovie?.size)

        viewModel.listMovie().observeForever(observer)
        verify(observer).onChanged(DataDummy.getDummyMovie())
    }
}