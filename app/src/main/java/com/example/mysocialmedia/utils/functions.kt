package com.example.mysocialmedia.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt


fun log(message: String) {
    Log.e("TAG", message)
}

fun dpToPx(context: Context, dp: Int): Int {
    val r = context.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
        .roundToInt()
}

fun toast(context: Context, message: String, duration: Int) = Toast.makeText(context, message, duration).show()

fun snackBar(view: View, message: CharSequence, duration: Int): Snackbar = Snackbar.make(view, message, duration)


fun hideOptionMenu(context: Context) {
    val intent = Intent().apply {
        action = "InValidateOptionsMenu"
    }
    Bundle().apply {
        putBoolean("HideMenuStatus", false)
    }.also {
        intent.putExtras(it)
    }
    LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
}

fun showOptionMenu(context: Context){
    val intent = Intent().apply {
        action = "InValidateOptionsMenu"
    }

    Bundle().apply {
        putBoolean("HideMenuStatus", true)
    }.also {
        intent.putExtras(it)
    }

    LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
}