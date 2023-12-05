package me.dungngminh.verygoodblogapp.utils

import android.app.Activity
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


fun Activity.hideSoftKeyboard(windowToken: IBinder? = null) {
    (windowToken ?: currentFocus?.windowToken)?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this, InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(it, 0)
    }
}