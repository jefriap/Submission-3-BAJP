package com.submission.filmcatalogue.ui.favorite.movie

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.submission.filmcatalogue.R
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.databinding.FragmentMovieFavoriteBinding
import com.submission.filmcatalogue.ui.favorite.FavoriteViewModel
import com.submission.filmcatalogue.ui.movie.MovieRvAdapter
import com.submission.filmcatalogue.viewmodel.ViewModelFactory
import com.submission.filmcatalogue.vo.Status


class MovieFavoriteFragment : Fragment() {
    private var _binding: FragmentMovieFavoriteBinding? = null
    private val binding get() = _binding as FragmentMovieFavoriteBinding

    private val viewModel: FavoriteViewModel by viewModels {
        activity?.application?.let {
            ViewModelFactory.getInstance(requireActivity())
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Bind the layout for this fragment
        _binding = FragmentMovieFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentOrientation = resources.configuration.orientation

        _binding?.rvMovies?.apply {
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
        viewModel.getListMovieFavorite().observe(viewLifecycleOwner, {
            if (it != null) {
                binding.loading.visibility = View.GONE
                adapter(context, it)
            }
            else {
                binding.loading.visibility = View.GONE
                binding.rvMovies.visibility = View.GONE
                binding.lottieNoData.visibility = View.VISIBLE
            }
        })
    }

    private fun adapter(context: Context, list: PagedList<MovieEntity>) {
        val adapter = MovieRvAdapter(context)
        adapter.submitList(list)
        _binding?.rvMovies?.adapter = adapter
    }

}