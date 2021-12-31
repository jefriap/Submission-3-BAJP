package com.submission.filmcatalogue.ui.favorite.tvshow

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.submission.filmcatalogue.R
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.databinding.FragmentTvShowFavoriteBinding
import com.submission.filmcatalogue.ui.favorite.FavoriteViewModel
import com.submission.filmcatalogue.ui.movie.MovieRvAdapter
import com.submission.filmcatalogue.ui.tvshow.TvShowRvAdapter
import com.submission.filmcatalogue.viewmodel.ViewModelFactory


class TvShowFavoriteFragment : Fragment() {
    private var _binding : FragmentTvShowFavoriteBinding? = null
    private val binding get() = _binding as FragmentTvShowFavoriteBinding

    private val viewModel: FavoriteViewModel by viewModels {
        activity?.application?.let {
            ViewModelFactory.getInstance(requireActivity())
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvShowFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentOrientation = resources.configuration.orientation

        _binding?.rvTvShow?.apply {
            layoutManager = if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 4)
            } else {
                GridLayoutManager(context, 2)
            }
            setHasFixedSize(true)
            this.adapter = adapter

            getList(context)

        }
    }

    private fun getList(context: Context) {
        binding.loading.visibility = View.VISIBLE
        viewModel.getListTvShowFavorite().observe(viewLifecycleOwner, {
            if (it != null) {
                binding.loading.visibility = View.GONE
                adapter(context, it)
            }
            else {
                binding.loading.visibility = View.GONE
                binding.rvTvShow.visibility = View.GONE
                binding.lottieNoData.visibility = View.VISIBLE
            }
        })
    }

    private fun adapter(context: Context, list: PagedList<TvShowEntity>) {
        val adapter = TvShowRvAdapter(context)
        adapter.submitList(list)
        _binding?.rvTvShow?.adapter = adapter
    }

}