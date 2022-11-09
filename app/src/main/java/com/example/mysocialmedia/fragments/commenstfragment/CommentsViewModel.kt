package com.example.mysocialmedia.fragments.commenstfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mysocialmedia.data.datasource.PostsAndCommentsDataSource
import com.example.mysocialmedia.model.Comments
import com.example.mysocialmedia.utils.NetworkStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import retrofit2.Response

class CommentsViewModel(private val pacDs: PostsAndCommentsDataSource) : ViewModel() {

    var id: Int = 0
    private val _status: MutableSharedFlow<NetworkStatus> = MutableSharedFlow()
    val status: SharedFlow<NetworkStatus> = _status.asSharedFlow()

    val comments: LiveData<Comments> = liveData {
        _status.emit(NetworkStatus.REQUEST_SENT)
        val response: Response<Comments> = pacDs.getCommentsByPost(id)
        val body: Comments? = response.body()
        body?.let { commentsData ->
            emit(commentsData)
            _status.emit(NetworkStatus.RESPONSE_RECEIVED)
        }
    }
}