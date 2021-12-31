package com.submission.filmcatalogue.utils.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.submission.filmcatalogue.ui.movie.MovieFragment
import com.submission.filmcatalogue.ui.tvshow.TvShowFragment

class SectionsPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = MovieFragment()
            }
            1 -> {
                fragment = TvShowFragment()
            }
        }
        return fragment as Fragment
    }
}