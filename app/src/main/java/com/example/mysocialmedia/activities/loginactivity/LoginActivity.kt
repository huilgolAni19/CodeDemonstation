package com.example.mysocialmedia.activities.loginactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mysocialmedia.R
import com.example.mysocialmedia.activities.mainactivity.HomeActivity
import com.example.mysocialmedia.data.datasource.LoginDataSource
import com.example.mysocialmedia.databinding.ActivityLoginBinding
import com.example.mysocialmedia.di.components.DaggerLoginComponent
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.di.modules.LoginModule
import com.example.mysocialmedia.viewmodelfactories.LoginViewModelFactory
import io.github.rupinderjeet.kprogresshud.KProgressHUD
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var dataSource: LoginDataSource

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var factory:  LoginViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setSupportActionBar(binding.toolbar)
        inject()
        factory = LoginViewModelFactory(dataSource)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        binding.contentLogin.loginViewModel = loginViewModel
        binding.contentLogin.lifecycleOwner = this

        lifecycleScope.launchWhenCreated {

            loginViewModel.status.collectLatest { status ->

                if (status.isBlank()) {
                    return@collectLatest
                }

                val hud: KProgressHUD = KProgressHUD.create(this@LoginActivity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setDetailsLabel("Validating Credentials...")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)

                when(status) {

                    "Loading" -> {
                        withContext(Main) {
                            hud.show()
                        }
                    }

                    "Invalid EmailId" -> {
                        withContext(Main) {
                            if (hud.isShowing) {
                                hud.dismiss()
                            }
                        }
                    }

                    "result obtained" -> {
                        withContext(Main) {
                            if (hud.isShowing) {
                                hud.dismiss()
                            }
                        }
                    }

                    "Success" -> {
                        withContext(Main) {
                            if (hud.isShowing) {
                                hud.dismiss()
                            }
                            delay(20)
                            val goToMainScreen = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(goToMainScreen)
                            this@LoginActivity.finish()
                        }
                    }
                }
            }
        }
    }

    private fun inject() {
        DaggerLoginComponent
            .builder().apply {
                commonServicesProvider(CommonServicesProvider(this@LoginActivity.applicationContext))
                loginModule(LoginModule(this@LoginActivity.applicationContext))
            }
            .build()
            .inject(this)
    }
}