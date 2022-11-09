package com.example.mysocialmedia.di.modules

import android.content.Context
import com.example.mysocialmedia.data.restservices.RestServiceFactory
import com.example.mysocialmedia.utils.SessionController
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class CommonServicesProvider(private val context: Context) {


        @Singleton
        @Provides
        fun provideSessionManager(): SessionController {
            return SessionController.getInstance(context)
        }

        @Singleton
        @Provides
        fun provideRetrofitInstance(): Retrofit {
            return RestServiceFactory.getRetrofitInstance()
        }

}