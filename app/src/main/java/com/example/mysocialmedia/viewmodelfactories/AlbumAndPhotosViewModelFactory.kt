package com.example.mysocialmedia.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysocialmedia.data.datasource.AlbumAndPhotosDataSource
import com.example.mysocialmedia.fragments.albumsfragment.AlbumsViewModel
import com.example.mysocialmedia.fragments.photosfragment.PhotosViewModel

class AlbumAndPhotosViewModelFactory(private val aap: AlbumAndPhotosDataSource): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotosViewModel::class.java)) {
            return PhotosViewModel(aap) as T
        } else if (modelClass.isAssignableFrom(AlbumsViewModel::class.java)) {
            return AlbumsViewModel(aap) as T
        }
        return super.create(modelClass)
    }
}