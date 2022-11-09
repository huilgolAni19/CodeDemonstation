package com.example.mysocialmedia.activities.splashactivity


import androidx.lifecycle.ViewModel
import com.example.mysocialmedia.activities.loginactivity.LoginActivity
import com.example.mysocialmedia.activities.mainactivity.HomeActivity
import com.example.mysocialmedia.utils.SessionController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Suppress("NAME_SHADOWING")
class SplashViewModel(private val sessionController: SessionController): ViewModel() {

    private var goToStateFlow: MutableSharedFlow<Class<*>> = MutableStateFlow(LoginActivity::class.java)
    val gotoNextScreen: SharedFlow<Class<*>> =  goToStateFlow.asSharedFlow()

    companion object {
        const val DELAY: Long =  3000L
    }

    suspend fun goToNextScreen() {

        var loginStatus: Boolean = sessionController.isLoggedIn
        var whichClass: Class<*> = if (loginStatus) {
            HomeActivity::class.java
        } else {
            LoginActivity::class.java
        }
        delay(DELAY)
        goToStateFlow.emit(whichClass)
    }
}