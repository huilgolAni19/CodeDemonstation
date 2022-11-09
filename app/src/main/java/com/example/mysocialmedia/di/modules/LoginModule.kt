package com.example.mysocialmedia.di.modules

import android.content.Context
import com.example.mysocialmedia.data.datasource.LoginDataSource
import com.example.mysocialmedia.data.restservices.LoginRestServices
import com.example.mysocialmedia.utils.SessionController
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class LoginModule(private var context: Context) {

    @Provides
    @Singleton
    fun provideDataSource(
        sessionController: SessionController,
         retrofit: Retrofit
    ): LoginDataSource {
        val loginRestServices: LoginRestServices = retrofit.create(LoginRestServices::class.java)
        return LoginDataSource(sessionController, loginRestServices)
    }

}