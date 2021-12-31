package com.submission.filmcatalogue.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListTvShowResponse(

	@field:SerializedName("results")
	val results: List<TvItem>? = null
)

data class TvItem(
	val tvShowId: Int? = 0,
	val tvShowTitle: String? = "",
	val tvShowDescription: String? = "",
	val tvShowRelease: String? = "",
	val tvShowGenre : String? = "",
	val tvShowDuration: String? = "",
	val tvShowRating : Double? = 0.0,
	val tvShowPoster : String? = ""
)
