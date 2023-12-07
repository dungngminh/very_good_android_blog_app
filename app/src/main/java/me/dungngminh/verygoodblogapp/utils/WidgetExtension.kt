package me.dungngminh.verygoodblogapp.utils

import com.google.android.material.bottomnavigation.BottomNavigationView


fun BottomNavigationView.hide(duration: Long = 250) {
    this.clearAnimation()
    this.animate().translationY(this.height.toFloat()).setDuration(duration)
}

fun BottomNavigationView.show(duration: Long = 250) {
    this.clearAnimation()
    this.animate().translationY(0f).setDuration(duration)
}