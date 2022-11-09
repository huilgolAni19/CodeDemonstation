package com.example.mysocialmedia.di.components

import com.example.mysocialmedia.activities.splashactivity.SplashActivity
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.fragments.homemenufragment.HomeMenuFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        CommonServicesProvider::class
    ]
)
interface SplashComponent {

    fun inject(splashActivity: SplashActivity)

    fun inject(homeMenuFragment: HomeMenuFragment)
}