package com.example.mysocialmedia.di.components

import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.di.modules.PostAndCommentModule
import com.example.mysocialmedia.fragments.commenstfragment.CommentsFragment
import com.example.mysocialmedia.fragments.photosfragment.PhotosFragment
import com.example.mysocialmedia.fragments.postsfragments.PostsFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        CommonServicesProvider::class,
        PostAndCommentModule::class
    ]
)
interface PostAndCommentComponent {

    fun inject(fragment: PostsFragment)

    fun inject(fragment: CommentsFragment)

}