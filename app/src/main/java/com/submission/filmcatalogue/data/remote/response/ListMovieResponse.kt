package com.submission.filmcatalogue.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ListMovieResponse(

	@field:SerializedName("results")
	val results: List<MovieItem>
)

@Parcelize
data class MovieItem(
	val movieId: Int? = 0,
	val movieTitle: String? = "",
	val movieDescription: String? = "",
	val movieRelease: String?= "",
	val movieGenre: String? = "",
	val movieDuration: String? = "",
	val movieRating : Double?= 0.0,
	val moviePoster : String?= ""
) : Parcelable
