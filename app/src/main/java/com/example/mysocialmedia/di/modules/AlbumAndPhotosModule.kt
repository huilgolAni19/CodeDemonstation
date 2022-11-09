package com.example.mysocialmedia.di.modules

import com.example.mysocialmedia.data.datasource.AlbumAndPhotosDataSource
import com.example.mysocialmedia.data.restservices.AlbumRestService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
class AlbumAndPhotosModule() {

    @Singleton
    @Provides
    fun provideAlbumRestService(retrofit: Retrofit): AlbumRestService {
        return retrofit.create(AlbumRestService::class.java)
    }

    @Singleton
    @Provides
    fun provideAlbumAndPhotosDataSource(albumRestService: AlbumRestService): AlbumAndPhotosDataSource {
        return AlbumAndPhotosDataSource(albumRestService)
    }
}