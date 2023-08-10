package com.agusw.test_list_games.rvAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.agusw.test_list_games.databinding.MovieItemBinding
import com.agusw.test_list_games.db.entities.Games
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class GameAdapter(
    val glide: RequestManager,
    val onClick: (game: Games) -> Unit
) : PagingDataAdapter<Games, GameAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Games>() {
        override fun areItemsTheSame(old: Games, new: Games): Boolean = old.id == new.id

        override fun areContentsTheSame(old: Games, new: Games): Boolean = old == new
    }
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    inner class ViewHolder(
        parent: ViewGroup,
        private val layout: MovieItemBinding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(layout.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Games) {
            glide.load(item.image)
                .transform(FitCenter(), RoundedCorners(16))
                .into(layout.image)

            layout.title.text = item.name
            layout.released.text = "Release date ${item.released}"
            layout.rating.text = item.rating.toString()

            layout.root.setOnClickListener { _ -> onClick.invoke(item) }
        }
    }
}