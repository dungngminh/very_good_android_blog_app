package me.dungngminh.verygoodblogapp.utils

fun String.isEmail(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
