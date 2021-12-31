package com.submission.filmcatalogue.ui.tvshow

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import com.submission.filmcatalogue.data.remote.response.MovieItem
import com.submission.filmcatalogue.databinding.ItemRowBinding
import com.submission.filmcatalogue.ui.detail.DetailActivity
import com.submission.filmcatalogue.utils.loadImage

class TvShowRvAdapter(private val context : Context?)
    : PagedListAdapter<TvShowEntity, TvShowRvAdapter.ListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.tvId == newItem.tvId
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        with(holder.binding) {
            context.let {
                imgMovie.loadImage(
                    context?.resources?.getIdentifier(data?.posterPath, "drawable", context.packageName)!!
                )
            }
            tvJudulFilm.text = data?.name
            tvGenreFilm.text = data?.genre
            tvDate.text = data?.firstAirDate
            tvRating.text = data?.voteAverage.toString()
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, data?.tvId)
                    putExtra(DetailActivity.TIPE, "tv")
                }
                holder.itemView.context.startActivity(intent)
            }

        }
    }

    class ListViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        //        data class
        fun onItemClicked(data: MovieItem)
    }
}