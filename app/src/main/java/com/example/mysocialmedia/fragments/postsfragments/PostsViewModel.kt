package com.example.mysocialmedia.fragments.postsfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mysocialmedia.data.datasource.PostsAndCommentsDataSource
import com.example.mysocialmedia.model.Posts
import com.example.mysocialmedia.model.UserDataItem
import com.example.mysocialmedia.utils.NetworkStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PostsViewModel(private val pacDs: PostsAndCommentsDataSource) : ViewModel() {

    var userDataItem: UserDataItem?= null
    private val _status: MutableSharedFlow<NetworkStatus> = MutableSharedFlow()
    val status: SharedFlow<NetworkStatus> = _status.asSharedFlow()

    val posts: LiveData<Posts> = liveData {
        _status.emit(NetworkStatus.REQUEST_SENT)
        val response = pacDs.getPosts(userDataItem!!.id)
        val postsData = response.body()

        postsData?.let { pd ->
            emit(pd)
            _status.emit(NetworkStatus.RESPONSE_RECEIVED)
        }
    }

}
