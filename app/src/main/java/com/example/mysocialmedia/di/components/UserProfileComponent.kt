package com.example.mysocialmedia.di.components

import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.fragments.userprofile.UserProfileFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        CommonServicesProvider::class
    ]
)
interface UserProfileComponent {

    fun inject(fragment: UserProfileFragment)

}