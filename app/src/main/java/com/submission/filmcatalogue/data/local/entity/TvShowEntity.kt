package com.submission.filmcatalogue.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class TvShowEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tvId")
    val tvId: Int? = 0,

    @ColumnInfo(name = "name")
    val name: String? = "",

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = "",

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = "",

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = 0.0,

    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String? = "",

    @ColumnInfo(name = "episode_run_time")
    val episodeRunTime: String? = "",

    @ColumnInfo(name = "number_of_episodes")
    val numberOfEpisodes: Int? = 0,

    @ColumnInfo(name = "genre")
    val genre: String? = "",

    @ColumnInfo(name = "overview")
    val overview: String? = "",

    @ColumnInfo(name = "favorite")
    var favorite: Boolean? = false

) : Parcelable
