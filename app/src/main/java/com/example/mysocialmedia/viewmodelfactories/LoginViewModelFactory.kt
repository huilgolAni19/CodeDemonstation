package com.example.mysocialmedia.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysocialmedia.activities.loginactivity.LoginViewModel
import com.example.mysocialmedia.data.datasource.LoginDataSource

class LoginViewModelFactory(private val dataSource: LoginDataSource): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(dataSource) as T
        }
        return super.create(modelClass)
    }
}