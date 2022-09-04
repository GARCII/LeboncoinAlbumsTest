package com.lbc.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.lbc.app.R
import com.lbc.app.databinding.LayoutAlbumListItemBinding
import com.lbc.presentation.model.AlbumUi

class AlbumsListAdapter : ListAdapter<AlbumUi, AlbumItemViewHolder>(AlbumsListDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder {
        return AlbumItemViewHolder(
            LayoutAlbumListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemCount() = currentList.size

    override fun getItemViewType(position: Int) = R.layout.layout_album_list_item

    class AlbumsListDiffCallBack : DiffUtil.ItemCallback<AlbumUi>() {
        override fun areItemsTheSame(
            oldItem: AlbumUi,
            newItem: AlbumUi
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: AlbumUi,
            newItem: AlbumUi
        ) = oldItem.id == newItem.id
    }
}