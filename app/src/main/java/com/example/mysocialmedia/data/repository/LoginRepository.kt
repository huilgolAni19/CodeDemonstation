package com.example.mysocialmedia.data.repository

import com.example.mysocialmedia.model.UserData
import com.example.mysocialmedia.model.UserDataItem
import retrofit2.Response

interface LoginRepository {

    suspend fun signInWithEmailAndPassword(email: String): Response<UserData>

    suspend fun setLoginStatus(status: Boolean)

    suspend fun getLoginStatus(): Boolean

    suspend fun setUserData(userData: UserDataItem)

    suspend fun getUserData(): UserDataItem?
}