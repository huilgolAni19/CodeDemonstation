package com.example.mysocialmedia.di.modules

import com.example.mysocialmedia.data.datasource.PostsAndCommentsDataSource
import com.example.mysocialmedia.data.restservices.PostsRestServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class PostAndCommentModule() {

    @Singleton
    @Provides
    fun providePostsRestServices(retrofit: Retrofit): PostsRestServices {
        return retrofit.create(PostsRestServices::class.java)
    }

    @Singleton
    @Provides
    fun providePostAndCommentDataSource(postsRestServices: PostsRestServices): PostsAndCommentsDataSource {
        return PostsAndCommentsDataSource(postsRestServices)
    }
}