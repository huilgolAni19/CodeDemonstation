package com.example.mysocialmedia.activities.loginactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysocialmedia.data.datasource.LoginDataSource
import com.example.mysocialmedia.model.UserData
import com.example.mysocialmedia.model.UserDataItem
import com.example.mysocialmedia.utils.log
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response

@Suppress("NAME_SHADOWING")
class LoginViewModel(private val loginDataSource: LoginDataSource): ViewModel() {

    val email: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _status :MutableSharedFlow<String> = MutableSharedFlow()
     val status: SharedFlow<String> = _status.asSharedFlow()

    fun login() {

        viewModelScope.launch {
            _status.emit("Loading")
            email.collectLatest { emailId ->
                emailId?.let { email ->
                    if((email == "")) {
                        _status.emit("Invalid EmailId")
                    } else {
                        val response: Response<UserData> = loginDataSource.signInWithEmailAndPassword(email)
                        val userData: UserData? = response.body()
                        _status.emit("result obtained")
                        userData?.let { userData ->
                            if (userData.size > 0) {
                                val userDataItem: UserDataItem = userData[0]
                                log("UserDataItem: $userDataItem")
                                loginDataSource.apply {
                                    setLoginStatus(true)
                                    setUserData(userDataItem)
                                }
                                _status.emit("Success")
                            } else {
                                _status.emit("User Not Found...")
                            }
                        }
                    }
                }
            }
        }
    }
}