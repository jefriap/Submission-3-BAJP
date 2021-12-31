package com.submission.filmcatalogue.di

import android.app.Application
import android.content.Context
import com.submission.filmcatalogue.data.FilmRepository
import com.submission.filmcatalogue.data.local.data.LocalDataSource
import com.submission.filmcatalogue.data.local.db.FilmDatabase
import com.submission.filmcatalogue.data.remote.RemoteDataSource
import com.submission.filmcatalogue.utils.AppExecutors
import com.submission.filmcatalogue.utils.JsonHelper

object Injection {

    fun filmRepository(context: Context): FilmRepository {

        val localDataSource = LocalDataSource(
            FilmDatabase.getInstance(context).catalogueDao()
        )
        val remoteDataSource = RemoteDataSource.getInstance(
            JsonHelper(
                context
            )
        )

        val appExecutors = AppExecutors()
        return FilmRepository.getInstance(localDataSource, remoteDataSource, appExecutors)!!
    }
}