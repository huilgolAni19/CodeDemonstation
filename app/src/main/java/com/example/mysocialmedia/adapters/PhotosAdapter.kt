package com.example.mysocialmedia.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mysocialmedia.R
import com.example.mysocialmedia.databinding.PhotoItemBinding
import com.example.mysocialmedia.model.Photos
import com.example.mysocialmedia.model.PhotosItem
import com.squareup.picasso.Picasso

class PhotosAdapter(
    private val photos: Photos,
    private val onPhotoItemClick: (photoItem: PhotosItem) -> Unit
    ): Adapter<PhotosAdapter.PhotosViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val context: Context = parent.context
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val binding: PhotoItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.photo_item, parent, false)
        return PhotosViewHolder(binding, onPhotoItemClick)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val photosItem: PhotosItem = photos[position]
        holder.bind(photosItem)
    }

    override fun getItemCount(): Int {
       return photos.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPhotos(photos: Photos) {
        this.photos.clear()
        this.photos.addAll(photos)
        this.notifyDataSetChanged()
    }

    class PhotosViewHolder(
        private val binding: PhotoItemBinding,
        private val onPhotoItemClick: (photoItem: PhotosItem) -> Unit
    ): ViewHolder(binding.root) {

        fun bind(photosItem: PhotosItem) {

            Picasso.get()
                .load(photosItem.thumbnailUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.photoThumbnail)

            "Photo ${photosItem.id}".also { binding.textViewPhotoId.text = it }

            binding.textViewPhotoId.setOnClickListener {
                onPhotoItemClick.invoke(photosItem)
            }
//            binding.parentLayoutPhotos.setOnClickListener {
//                onPhotoItemClick.invoke(photosItem)
//            }
//            binding.cardViewPhoto.setOnClickListener {
//                onPhotoItemClick.invoke(photosItem)
//            }
        }
    }
}