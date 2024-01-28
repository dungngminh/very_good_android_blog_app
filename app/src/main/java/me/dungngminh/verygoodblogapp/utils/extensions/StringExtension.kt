package me.dungngminh.verygoodblogapp.utils.extensions

fun String.isEmail(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
