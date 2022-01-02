package com.submission.filmcatalogue.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.filmcatalogue.R
import com.submission.filmcatalogue.databinding.ActivityFavoriteBinding
import com.submission.filmcatalogue.utils.adapter.SectionsPagerAdapter
import com.submission.filmcatalogue.utils.adapter.SectionsPagerFavoriteAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionPagerAdapter = SectionsPagerFavoriteAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TILES[position])
        }.attach()

        //-- Handle navigation icon press --//
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
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