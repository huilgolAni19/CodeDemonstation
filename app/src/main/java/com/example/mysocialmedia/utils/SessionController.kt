package com.example.mysocialmedia.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.mysocialmedia.model.UserData
import com.example.mysocialmedia.model.UserDataItem
import com.google.gson.Gson
import org.json.JSONObject

class SessionController private constructor() {

    companion object {
        private const val PREF_NAME: String = "SocialMediaPref"
        private const val MODE: Int = Context.MODE_PRIVATE
        private const val KEY_LOGIN_STATUS = "IsLoggedIn"
        private const val KEY_USER_DATA_ITEM = "UserDataItem"
        private lateinit var sharedPreferences: SharedPreferences
        fun getInstance(context: Context): SessionController {

            sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE)
            return SessionController()
        }
    }

    var isLoggedIn: Boolean get() = sharedPreferences.getBoolean(KEY_LOGIN_STATUS, false)
    set(value) = sharedPreferences.edit().apply { putBoolean(KEY_LOGIN_STATUS, value) }.apply()

    var userDetails: UserDataItem? get() {
        var strJson: String = sharedPreferences.getString(KEY_USER_DATA_ITEM, "")!!
        return if(strJson == "") {
            null
        } else {
            val gson = Gson()
            gson.fromJson(strJson, UserDataItem::class.java)
        }
    } set(value) {
        value?.let { userDataItem ->
            val gson = Gson()
            val strJson: String = gson.toJson(userDataItem).toString()
            sharedPreferences.apply {
                edit {
                    putString(KEY_USER_DATA_ITEM, strJson)
                    apply()
                }
            }
        }
    }

    fun logout() {
        sharedPreferences.apply {
            edit {
                clear()
                commit()
            }
        }
    }
}