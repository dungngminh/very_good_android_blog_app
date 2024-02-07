package me.dungngminh.verygoodblogapp.utils.extensions

import androidx.fragment.app.Fragment

fun Fragment.clearFocus() {
    activity?.currentFocus?.clearFocus()
}
