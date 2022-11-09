package com.example.mysocialmedia.di.components

import com.example.mysocialmedia.di.modules.AlbumAndPhotosModule
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.fragments.albumsfragment.AlbumsFragment
import com.example.mysocialmedia.fragments.photosfragment.PhotosFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CommonServicesProvider::class,
        AlbumAndPhotosModule::class
    ]
)
interface AlbumAndPhotosComponent {

    fun inject(photosFragment: PhotosFragment)

    fun inject(albumsFragment: AlbumsFragment)
}