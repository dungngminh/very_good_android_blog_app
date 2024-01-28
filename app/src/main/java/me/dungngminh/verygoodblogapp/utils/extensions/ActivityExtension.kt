package me.dungngminh.verygoodblogapp.utils.extensions

import android.app.Activity
import android.os.Build
import android.os.IBinder
import android.os.Parcelable
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


fun Activity.hideSoftKeyboard(windowToken: IBinder? = null) {
    (windowToken ?: currentFocus?.windowToken)?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this, InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(it, 0)
    }
}

@Suppress("DEPRECATION")
fun <T : Parcelable?> Activity.getCompactParcelableExtra(key: String, mClass: Class<T>): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.intent.getParcelableExtra(key, mClass)!!
    else
        this.intent.getParcelableExtra(key)!!
}