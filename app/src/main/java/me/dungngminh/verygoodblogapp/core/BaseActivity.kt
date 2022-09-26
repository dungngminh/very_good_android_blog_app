package me.dungngminh.verygoodblogapp.core

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

open class BaseActivity(@LayoutRes layoutRes: Int) : AppCompatActivity(layoutRes){

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$this::onCreate: $savedInstanceState")
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.dismissKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$this::onDestroy")
    }
}

fun Activity.dismissKeyboard(){
    if(currentFocus != null){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputMethodManager.isAcceptingText)
            inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }
}