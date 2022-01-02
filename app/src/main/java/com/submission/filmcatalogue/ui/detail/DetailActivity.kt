package com.submission.filmcatalogue.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.submission.filmcatalogue.R
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.databinding.ActivityDetailBinding
import com.submission.filmcatalogue.utils.loadImage
import com.submission.filmcatalogue.viewmodel.ViewModelFactory
import com.submission.filmcatalogue.vo.Status

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private var menu: Menu? = null

    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //-- Handle navigation icon press --//
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        val itemId = intent.getIntExtra(EXTRA_ID, 0)
        viewModel.setSelectedItem(itemId)

        if (intent.getStringExtra(TIPE) == "movie") {
            viewModel.movieItem.observe(this, {
                if (it != null) {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.loading.visibility = View.VISIBLE
                            binding.loading2.visibility = View.VISIBLE
                            binding.imgBackdrop.visibility = View.GONE
                            binding.viewDetail.visibility = View.GONE
                        }
                        Status.SUCCESS -> {
                            binding.loading.visibility = View.GONE
                            binding.loading2.visibility = View.GONE
                            binding.imgBackdrop.visibility = View.VISIBLE
                            binding.viewDetail.visibility = View.VISIBLE
                            bindDetailMovie(it.data!!)
                            menuItemClicked("Movie")
                        }
                        Status.ERROR -> {
                            binding.loading.visibility = View.VISIBLE
                            binding.loading2.visibility = View.VISIBLE
                        }
                    }
                } else {
                    binding.imgBackdrop.visibility = View.GONE
                    binding.viewDetail.visibility = View.GONE
                    Toast.makeText(this, "Data Tidak Ada", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else if (intent.getStringExtra(TIPE) == "tv") {
            viewModel.tvShowItem.observe(this, {
                if (it != null) {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.loading.visibility = View.VISIBLE
                            binding.loading2.visibility = View.VISIBLE
                            binding.imgBackdrop.visibility = View.GONE
                            binding.viewDetail.visibility = View.GONE
                        }
                        Status.SUCCESS -> {
                            binding.loading.visibility = View.GONE
                            binding.loading2.visibility = View.GONE
                            binding.imgBackdrop.visibility = View.VISIBLE
                            binding.viewDetail.visibility = View.VISIBLE
                            bindDetailTvShow(it.data!!)
                            menuItemClicked("TvShow")
                        }
                        Status.ERROR -> {
                            binding.loading.visibility = View.VISIBLE
                            binding.loading2.visibility = View.VISIBLE
                        }
                    }
                } else {
                    binding.imgBackdrop.visibility = View.GONE
                    binding.viewDetail.visibility = View.GONE
                    Toast.makeText(this, "Data Tidak Ada", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    private fun bindDetailMovie (data: MovieEntity) {
        with(binding) {
            imgMovie.loadImage(resources.getIdentifier(data.posterPath, "drawable", packageName))
            imgBackdrop.loadImage(resources.getIdentifier(data.posterPath, "drawable", packageName))
            tvJudul.text = data.title
            tvDurasi.text = data.runtime
            tvRilis.text = data.releaseDate
            tvScore.text = data.voteAverage.toString()
            tvoverview.text = data.overview
            topAppBar.apply {
                favoriteState(menu.findItem(R.id.btnFav), data.favorite)
            }
        }
    }

    private fun menuItemClicked (typeItem: String) {
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnFav -> {
                    setFavoriteItem(typeItem)
                    true
                }
                else -> true
            }
        }
    }

    private fun setFavoriteItem (typeItem: String) {
        if (typeItem == "Movie") {
            viewModel.setFavoriteMovie()
        }
        else if (typeItem == "TvShow") {
            viewModel.setFavoriteTvShow()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindDetailTvShow (data: TvShowEntity) {
        with(binding) {
            imgMovie.loadImage(resources.getIdentifier(data.posterPath, "drawable", packageName))
            imgBackdrop.loadImage(resources.getIdentifier(data.posterPath, "drawable", packageName))
            tvJudul.text = data.name
            tvDurasi.text = data.episodeRunTime
            tvRilis.text = data.firstAirDate
            tvScore.text = data.voteAverage.toString()
            tvoverview.text = data.overview
            topAppBar.apply {
                favoriteState(menu.findItem(R.id.btnFav), data.favorite)
            }
        }
    }

    private fun favoriteState(menu: MenuItem, state: Boolean?) {
        menu.apply {
            icon = if (state!!) {
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_baseline_favorite_red_24
                )
            } else {
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_baseline_favorite_24
                )
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val TIPE = "tipe"
    }
}