package com.example.mysocialmedia.activities.splashactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mysocialmedia.R
import com.example.mysocialmedia.databinding.ActivitySplashBinding
import com.example.mysocialmedia.di.components.DaggerSplashComponent
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.utils.SessionController
import com.example.mysocialmedia.viewmodelfactories.ViewModelFactoryWithSessionManager
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var sessionController: SessionController

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var factory: ViewModelFactoryWithSessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = ""
        inject()
        factory = ViewModelFactoryWithSessionManager(sessionController)
        splashViewModel = ViewModelProvider(this, factory)[SplashViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            splashViewModel.goToNextScreen()
            splashViewModel.gotoNextScreen.apply {
                collectLatest { nextToClass ->
                    withContext(Main) {
                        startActivity(Intent(this@SplashActivity, nextToClass))
                        this@SplashActivity.finish()
                    }
                }
            }
        }
    }

    private fun inject() {
        DaggerSplashComponent
            .builder()
            .commonServicesProvider(CommonServicesProvider(this.applicationContext))
            .build()
            .inject(this)
    }
}