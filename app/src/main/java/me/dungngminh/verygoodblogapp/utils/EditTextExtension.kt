package me.dungngminh.verygoodblogapp.utils

import android.view.inputmethod.EditorInfo
import android.widget.EditText

inline fun EditText.onDone(crossinline callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callback.invoke()
        }
        false
    }
}
