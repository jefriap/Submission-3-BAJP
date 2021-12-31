package com.submission.filmcatalogue.ui.movie

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.databinding.FragmentMovieBinding
import com.submission.filmcatalogue.ui.detail.DetailActivity
import com.submission.filmcatalogue.viewmodel.ViewModelFactory
import com.submission.filmcatalogue.vo.Status


class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding as FragmentMovieBinding

    private val movieViewModel: MovieViewModel by viewModels {
        activity?.application?.let {
            ViewModelFactory.getInstance(requireActivity())
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
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
        movieViewModel.getListMovies().observe(viewLifecycleOwner, {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> binding.loading.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.loading.visibility = View.GONE
                        adapter(context, it.data!!)
                    }
                    Status.ERROR -> {
                        binding.loading.visibility = View.GONE
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun adapter(context: Context, list: PagedList<MovieEntity>) {
        val adapter = MovieRvAdapter(context)
        adapter.submitList(list)
        _binding?.rvMovies?.adapter = adapter
    }

}