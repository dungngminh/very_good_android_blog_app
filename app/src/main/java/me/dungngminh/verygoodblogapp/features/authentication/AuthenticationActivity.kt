package me.dungngminh.verygoodblogapp.features.authentication

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.features.helpers.ViewHelpers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.dismissKeyboard()
        return super.dispatchTouchEvent(ev)
    }
}

fun Activity.dismissKeyboard(){
    if(currentFocus != null){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputMethodManager.isAcceptingText)
            inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }
}
