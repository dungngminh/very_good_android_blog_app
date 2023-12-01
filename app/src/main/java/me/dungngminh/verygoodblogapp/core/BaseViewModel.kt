package me.dungngminh.verygoodblogapp.core

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import timber.log.Timber

open class BaseViewModel : ViewModel() {
    init {
        @Suppress("LeakingThis")
        Timber.d("$this::init")
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        Timber.d("$this::onCleared")
    }
}
