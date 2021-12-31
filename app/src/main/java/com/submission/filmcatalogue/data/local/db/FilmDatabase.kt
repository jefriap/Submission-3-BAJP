package com.submission.filmcatalogue.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 3, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun catalogueDao(): FilmDao

    companion object{
        private var INSTANCE: FilmDatabase? = null

        private val sLock = Any()

        fun getInstance(context: Context): FilmDatabase {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FilmDatabase::class.java, "catalogue")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE as FilmDatabase
            }
        }
    }
}