package com.example.mysocialmedia.fragments.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mysocialmedia.model.UserDataItem
import com.example.mysocialmedia.utils.SessionController

class UserProfileViewModel(private val sessionController: SessionController) : ViewModel() {

    val userProfile: LiveData<UserDataItem> = liveData {
        val userDataItem: UserDataItem? = sessionController.userDetails
        userDataItem?.let { udi ->
            emit(udi)
        }
    }
}