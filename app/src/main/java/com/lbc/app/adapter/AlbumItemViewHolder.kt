package com.lbc.app.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lbc.app.R
import com.lbc.app.common.Utils
import com.lbc.app.databinding.LayoutAlbumListItemBinding
import com.lbc.presentation.model.AlbumUi

class AlbumItemViewHolder(private val binding: LayoutAlbumListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(album: AlbumUi) {
        binding.textViewAlbumTitle.text =
            String.format("%d - %s", album.id, album.title.replaceFirstChar { it.uppercaseChar() })

        Glide.with(binding.root.context)
            .load(Utils.getGlideUrl(album.thumbnailUrl))
            .placeholder(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.ic_album_place_holder
                )
            )
            .transition(DrawableTransitionOptions.withCrossFade(100))
            .into(binding.imageViewAlbumImage)
    }
}