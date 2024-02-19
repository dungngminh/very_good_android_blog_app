package me.dungngminh.verygoodblogapp.utils.extensions

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf

fun Activity.hideSoftKeyboard(windowToken: IBinder? = null) {
    (windowToken ?: currentFocus?.windowToken)?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this, InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(it, 0)
    }
}

const val EXTRA_KEY = "EXTRA"

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable?> Activity.getCompactParcelableExtra(): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.intent.getParcelableExtra(EXTRA_KEY, T::class.java)
    } else {
        this.intent.getParcelableExtra(EXTRA_KEY)
    }
}

fun Any.toBundle(): Bundle {
    return bundleOf(EXTRA_KEY to this)
}
