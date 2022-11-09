package com.example.mysocialmedia.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysocialmedia.data.datasource.PostsAndCommentsDataSource
import com.example.mysocialmedia.fragments.commenstfragment.CommentsViewModel
import com.example.mysocialmedia.fragments.postsfragments.PostsViewModel

class PostsAndCommentsViewModelFactory(private val pacDs: PostsAndCommentsDataSource): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            return PostsViewModel(pacDs) as T
        } else if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            return CommentsViewModel(pacDs) as T
        }
        return super.create(modelClass)
    }
}