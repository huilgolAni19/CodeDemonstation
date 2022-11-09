package com.example.mysocialmedia.di.components

import com.example.mysocialmedia.activities.loginactivity.LoginActivity
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.di.modules.LoginModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CommonServicesProvider::class,
        LoginModule::class
    ]
)
interface LoginComponent {

    fun inject(loginActivity: LoginActivity)
}