package com.example.mysocialmedia.data.restservices

import com.example.mysocialmedia.model.Albums
import com.example.mysocialmedia.model.Photos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumRestService {

    @GET("users/{id}/albums")
    suspend fun getAlbum(@Path("id") id: Int): Response<Albums>

    @GET("albums/{id}/photos")
    suspend fun getPhotosByAlbum(@Path("id") id: Int): Response<Photos>

}