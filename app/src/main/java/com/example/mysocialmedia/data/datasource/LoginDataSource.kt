package com.example.mysocialmedia.data.datasource

import com.example.mysocialmedia.data.repository.LoginRepository
import com.example.mysocialmedia.data.restservices.LoginRestServices
import com.example.mysocialmedia.model.UserData
import com.example.mysocialmedia.model.UserDataItem
import com.example.mysocialmedia.utils.SessionController
import retrofit2.Response

class LoginDataSource(
    private var sessionController: SessionController,
    private val loginRestServices: LoginRestServices
    )
    : LoginRepository {

    override suspend fun signInWithEmailAndPassword(email: String): Response<UserData> {
        return loginRestServices.signInWithEmailAndPassword(email)
    }

    override suspend fun setLoginStatus(status: Boolean) {
        sessionController.isLoggedIn = status
    }

    override suspend fun getLoginStatus(): Boolean {
        return sessionController.isLoggedIn
    }

    override suspend fun setUserData(userData: UserDataItem) {
        sessionController.userDetails = userData
    }

    override suspend fun getUserData(): UserDataItem? {
        return sessionController.userDetails
    }


}