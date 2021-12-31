package com.submission.filmcatalogue.utils.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.submission.filmcatalogue.ui.favorite.movie.MovieFavoriteFragment
import com.submission.filmcatalogue.ui.favorite.tvshow.TvShowFavoriteFragment

class SectionsPagerFavoriteAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = MovieFavoriteFragment()
            }
            1 -> {
                fragment = TvShowFavoriteFragment()
            }
        }
        return fragment as Fragment
    }
}