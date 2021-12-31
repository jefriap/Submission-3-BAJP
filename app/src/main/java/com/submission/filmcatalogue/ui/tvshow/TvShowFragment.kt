package com.submission.filmcatalogue.ui.tvshow

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
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.databinding.FragmentTvShowBinding
import com.submission.filmcatalogue.ui.detail.DetailActivity
import com.submission.filmcatalogue.viewmodel.ViewModelFactory
import com.submission.filmcatalogue.vo.Status

class TvShowFragment : Fragment() {
    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding as FragmentTvShowBinding

    private val tvShowViewModel: TvShowViewModel by viewModels {
        activity?.application?.let {
            ViewModelFactory.getInstance(it)
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
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
        tvShowViewModel.getListTvShow().observe(viewLifecycleOwner, {
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

    private fun adapter(context: Context, list: PagedList<TvShowEntity>) {
        val adapter = TvShowRvAdapter(context)
        adapter.submitList(list)
        _binding?.rvTvShow?.adapter = adapter
    }

    private fun showSelectedUser(data: Int?) {
        val detailContent = Intent(activity, DetailActivity::class.java)
        detailContent.putExtra(DetailActivity.EXTRA_ID, data)
        startActivity(detailContent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}