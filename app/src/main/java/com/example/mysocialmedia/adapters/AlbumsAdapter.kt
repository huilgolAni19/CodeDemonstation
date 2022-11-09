package com.example.mysocialmedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.*
import com.example.mysocialmedia.R
import com.example.mysocialmedia.databinding.AlbumLayoutBinding
import com.example.mysocialmedia.model.Albums
import com.example.mysocialmedia.model.AlbumsItem

class AlbumsAdapter (
    private val albums: Albums,
    private val onAlbumClick: (albumItem: AlbumsItem) -> Unit
): Adapter<AlbumsAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: AlbumLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.album_layout, parent, false)
        return AlbumViewHolder(binding, onAlbumClick)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumsItem: AlbumsItem = albums[position]
        holder.bind(albumsItem)
    }

    fun setAlbum(albums: Albums) {
        this.albums.clear()
        this.albums.addAll(albums)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(
        private val binding: AlbumLayoutBinding,
        private val onAlbumClick: (albumItem: AlbumsItem) -> Unit
    ): ViewHolder(binding.root) {

        fun bind(albumItem: AlbumsItem) {
            binding.albumItem = albumItem
            binding.albumParentLayout.setOnClickListener { _ ->
                onAlbumClick.invoke(albumItem)
            }
        }
    }
}