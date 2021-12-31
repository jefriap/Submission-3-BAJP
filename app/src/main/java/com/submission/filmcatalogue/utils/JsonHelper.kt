package com.submission.filmcatalogue.utils

import android.app.Application
import android.content.Context
import android.util.Log
import com.submission.filmcatalogue.data.remote.response.MovieItem
import com.submission.filmcatalogue.data.remote.response.TvItem
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*


class JsonHelper(private val context: Context) {

    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun getMovieList(): List<MovieItem> {
        val list = ArrayList<MovieItem>()
        try {
            val responseObject = JSONObject(parsingFileToString("movies.json").toString())
            val listArray = responseObject.getJSONArray("result")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                val movieId = movie.getInt("movieId")
                val movieTitle = movie.getString("movieTitle")
                val movieDescription = movie.getString("movieDescription")
                val movieRelease = movie.getString("movieRelease")
                val movieGenre = movie.getString("movieGenre")
                val movieDuration = movie.getString("movieDuration")
                val movieRating = movie.getDouble("movieRating")
                val moviePoster = movie.getString("moviePoster")

                val movieResponse = MovieItem(
                    movieId,
                    movieTitle,
                    movieDescription,
                    movieRelease,
                    movieGenre,
                    movieDuration,
                    movieRating,
                    moviePoster
                )
                list.add(movieResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun getMovieById(id: Int): MovieItem {
        val movieId = id.toString()
        val movieData = String.format("movie_%s.json", movieId)
        var movieItem: MovieItem? = null
        try {
            val result = parsingFileToString(movieData)
            if (result != null) {
                val responseObject = JSONObject(result)
                val content = responseObject.getString("movie")
                movieItem = MovieItem(id, content)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return movieItem as MovieItem
    }

    fun getTvShowList():List<TvItem>{
        val listTvShow = ArrayList<TvItem>()

        try {
            val responseObject = JSONObject(parsingFileToString("tvshows.json").toString())
            val listArray = responseObject.getJSONArray("result")
            for (i in 0 until listArray.length()) {
                val tvShow = listArray.getJSONObject(i)

                val tvShowId = tvShow.getInt("tvShowId")
                val tvShowTitle = tvShow.getString("tvShowTitle")
                val tvShowDescription = tvShow.getString("tvShowDescription")
                val tvShowRelease = tvShow.getString("tvShowRelease")
                val tvShowGenre = tvShow.getString("tvShowGenre")
                val tvShowDuration = tvShow.getString("tvShowDuration")
                val tvShowRating = tvShow.getDouble("tvShowRating")
                val tvShowPoster = tvShow.getString("tvShowPoster")
                val courseResponse = TvItem(
                    tvShowId = tvShowId,
                    tvShowTitle = tvShowTitle,
                    tvShowDescription = tvShowDescription,
                    tvShowRelease = tvShowRelease,
                    tvShowGenre = tvShowGenre,
                    tvShowDuration = tvShowDuration,
                    tvShowRating = tvShowRating,
                    tvShowPoster = tvShowPoster
                )
                listTvShow.add(courseResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return listTvShow
    }

    fun getTvShowById(id: Int): TvItem {
        val tvId = id.toString()
        val tvData = String.format("movie_%s.json", tvId)
        var tvItem: MovieItem? = null
        try {
            val result = parsingFileToString(tvData)
            if (result != null) {
                val responseObject = JSONObject(result)
                val content = responseObject.getString("movie")
                tvItem = MovieItem(id, content)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return tvItem as TvItem
    }
}