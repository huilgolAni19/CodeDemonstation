package com.example.mysocialmedia.fragments.photosfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mysocialmedia.data.datasource.AlbumAndPhotosDataSource
import com.example.mysocialmedia.model.Photos
import com.example.mysocialmedia.utils.NetworkStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import retrofit2.Response

class PhotosViewModel(private val aap: AlbumAndPhotosDataSource) : ViewModel() {

    private val _status: MutableSharedFlow<NetworkStatus> = MutableSharedFlow()

    val status: SharedFlow<NetworkStatus> = _status.asSharedFlow()
    var id: Int = 0

    val photos: LiveData<Photos> = liveData {
        _status.emit(NetworkStatus.REQUEST_SENT)
        val response: Response<Photos> = aap.getPhotosByAlbum(id)
        val photosData: Photos? = response.body()
        photosData?.let { pd ->
            emit(pd)
            _status.emit(NetworkStatus.RESPONSE_RECEIVED)
        }
    }

}