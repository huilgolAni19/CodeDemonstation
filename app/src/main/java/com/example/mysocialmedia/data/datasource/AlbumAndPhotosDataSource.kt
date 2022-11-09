package com.example.mysocialmedia.data.datasource

import com.example.mysocialmedia.data.repository.AlbumsRepository
import com.example.mysocialmedia.data.restservices.AlbumRestService
import com.example.mysocialmedia.model.Albums
import com.example.mysocialmedia.model.Photos
import retrofit2.Response

class AlbumAndPhotosDataSource(private val albumRestService: AlbumRestService): AlbumsRepository {

    override suspend fun getAlbum(id: Int): Response<Albums> {
        return albumRestService.getAlbum(id)
    }

    override suspend fun getPhotosByAlbum(id: Int): Response<Photos> {
        return albumRestService.getPhotosByAlbum(id)
    }
}