package com.example.mysocialmedia.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysocialmedia.activities.splashactivity.SplashViewModel
import com.example.mysocialmedia.fragments.homemenufragment.HomeMenuViewModel
import com.example.mysocialmedia.fragments.userprofile.UserProfileViewModel
import com.example.mysocialmedia.utils.SessionController

class ViewModelFactoryWithSessionManager(private val sessionController: SessionController): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(sessionController) as T
        } else if (modelClass.isAssignableFrom(HomeMenuViewModel::class.java)) {
            return  HomeMenuViewModel(sessionController) as T
        } else if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(sessionController) as T
        }
        return super.create(modelClass)
    }
}