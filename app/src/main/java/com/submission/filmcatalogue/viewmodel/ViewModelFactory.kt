package com.submission.filmcatalogue.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.filmcatalogue.data.FilmRepository
import com.submission.filmcatalogue.di.Injection
import com.submission.filmcatalogue.ui.detail.DetailViewModel
import com.submission.filmcatalogue.ui.favorite.FavoriteViewModel
import com.submission.filmcatalogue.ui.movie.MovieViewModel
import com.submission.filmcatalogue.ui.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val dataRepo: FilmRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(dataRepo) as T

            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> TvShowViewModel(dataRepo) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(dataRepo) as T

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(dataRepo) as T

            else -> throw IllegalArgumentException("Unknowm ViewModel: " + modelClass.name)
        }
    }


    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                ViewModelFactory(Injection.filmRepository(context)).apply {
                    instance = this
                }
            }
    }
}