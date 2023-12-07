package me.dungngminh.verygoodblogapp.core

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

open class BaseActivity : AppCompatActivity() {
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$this::onCreate: $savedInstanceState")
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$this::onDestroy")
    }
}
