package com.example.mysocialmedia.fragments.albumsfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mysocialmedia.data.datasource.AlbumAndPhotosDataSource
import com.example.mysocialmedia.model.Albums
import com.example.mysocialmedia.model.UserDataItem
import com.example.mysocialmedia.utils.NetworkStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import retrofit2.Response

class AlbumsViewModel(private val aap: AlbumAndPhotosDataSource) : ViewModel() {

    lateinit var userDataItem: UserDataItem

    private val _status: MutableSharedFlow<NetworkStatus> = MutableSharedFlow()
    val status: SharedFlow<NetworkStatus> = _status.asSharedFlow()
    val albums: LiveData<Albums> = liveData {
        _status.emit(NetworkStatus.REQUEST_SENT)
        val response: Response<Albums> = aap.getAlbum(userDataItem.id)
        val _albums: Albums? = response.body()
        _albums?.let { albumData ->
            emit(albumData)
            _status.emit(NetworkStatus.RESPONSE_RECEIVED)
        }
    }
}