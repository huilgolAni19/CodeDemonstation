package com.example.mysocialmedia.data.repository

import com.example.mysocialmedia.model.Albums
import com.example.mysocialmedia.model.Photos
import retrofit2.Response

interface AlbumsRepository {

    suspend fun getAlbum(id: Int): Response<Albums>

    suspend fun getPhotosByAlbum(id: Int): Response<Photos>
}