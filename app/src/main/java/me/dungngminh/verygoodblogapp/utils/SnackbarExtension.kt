package me.dungngminh.verygoodblogapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

enum class SnackbarLength(val rawValue: Int) {
    SHORT(Snackbar.LENGTH_SHORT),

    LONG(Snackbar.LENGTH_LONG),

    INDEFINITE(Snackbar.LENGTH_INDEFINITE),
}

inline fun View.snack(
    message: String,
    length: SnackbarLength = SnackbarLength.SHORT,
    crossinline f: Snackbar.() -> Unit = {},
) = Snackbar.make(this, message, length.rawValue).apply {
    f()
    show()
}
