package com.submission.filmcatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.submission.filmcatalogue.data.FilmRepository
import com.submission.filmcatalogue.data.remote.response.TvItem
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
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<List<TvItem>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(filmRepository)
    }

    @Test
    fun testListTvShow() {
        val tvShow = MutableLiveData<List<TvItem>>()
        tvShow.value = DataDummy.getDummyTvShows()

        Mockito.`when`(filmRepository.getTvList()).thenReturn(tvShow)

        val dataTv = viewModel.listTvShow().value

        verify(filmRepository).getTvList()
        Assert.assertNotNull(dataTv)
        Assert.assertEquals(1, dataTv?.size)

        viewModel.listTvShow().observeForever(observer)
        verify(observer).onChanged(DataDummy.getDummyTvShows())
    }
}