package com.submission.filmcatalogue.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.submission.filmcatalogue.data.LiveDataTest
import com.submission.filmcatalogue.data.remote.RemoteDataSource
import com.submission.filmcatalogue.utils.DataDummy
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class FilmRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remoteDataSource = mock(RemoteDataSource::class.java)
    private val fakeFilmRepository = FakeFilmRepository(remoteDataSource)

    private val listMovie = DataDummy.getDummyMovie()
    private val movieId = DataDummy.getMovieDetail().id.toString()
    private val listTvShow = DataDummy.getDummyTvShows()
    private val tvId = listTvShow[0].id.toString()
    private val detailMovie = DataDummy.getMovieDetail()
    private val detailTvShow = DataDummy.getTvShowDetail()



    @Test
    fun testGetMovieList() {
        runBlocking {
            doAnswer {
                val callback = it.arguments[0] as RemoteDataSource.GetMovieCallback
                callback.onResponse(listMovie)
                null
            }.`when`(remoteDataSource).getMovie(any())
        }

        val result = LiveDataTest.getValue(fakeFilmRepository.getMovieList())

        runBlocking {
            verify(remoteDataSource).getMovie(any())
        }

        assertEquals(listMovie.size, result.size)
    }

    @Test
    fun testGetMovieDetail() {
        runBlocking {
            doAnswer {
                val callback = it.arguments[1] as RemoteDataSource.GetMovieDetailCallback
                detailMovie.let { it1 -> callback.onResponse(it1) }
                null
            }.`when`(remoteDataSource).getMovieDetail(eq(movieId),any())
        }

        val movieDetailEntity = LiveDataTest.getValue(fakeFilmRepository.getMovieDetail(movieId))

        runBlocking {
            verify(remoteDataSource, times(1)).getMovieDetail(eq(movieId),any())
        }

        Assert.assertNotNull(movieDetailEntity)
        assertEquals(listMovie[0].title, movieDetailEntity.title)
    }

    @Test
    fun testGetTvList() {
        runBlocking {
            doAnswer {
                val callback = it.arguments[0] as RemoteDataSource.GetTvShowCallback
                callback.onResponse(listTvShow)
                null
            }.`when`(remoteDataSource).getTvShow(any())
        }

        val result = LiveDataTest.getValue(fakeFilmRepository.getTvList())

        runBlocking {
            verify(remoteDataSource).getTvShow(any())
        }

        assertEquals(listTvShow.size, result.size)
    }

    @Test
    fun testGetTvDetail() {
        runBlocking {
            doAnswer {
                (it.arguments[1] as RemoteDataSource.GetTvShowDetailCallback)
                    .onResponse(detailTvShow)
                null
            }.`when`(remoteDataSource).getTvShowDetail(eq(tvId), any())
        }

        val tvDetailEntity = LiveDataTest.getValue(fakeFilmRepository.getTvDetail(tvId))

        runBlocking {
            verify(remoteDataSource).getTvShowDetail(eq(tvId), any())
        }

        Assert.assertNotNull(tvDetailEntity)
        assertEquals(listTvShow[0].name, tvDetailEntity.name)
    }
}