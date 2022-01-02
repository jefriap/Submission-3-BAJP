package com.submission.filmcatalogue.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.filmcatalogue.R
import com.submission.filmcatalogue.databinding.ActivityMainBinding
import com.submission.filmcatalogue.ui.favorite.FavoriteActivity
import com.submission.filmcatalogue.utils.adapter.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TILES[position])
        }.attach()

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnFavorite -> {
                    val favIntent = Intent(this, FavoriteActivity::class.java)
                    startActivity(favIntent)
                    true
                }
                else -> true
            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TILES = intArrayOf(
            R.string.movies,
            R.string.tv_show
        )
    }
}