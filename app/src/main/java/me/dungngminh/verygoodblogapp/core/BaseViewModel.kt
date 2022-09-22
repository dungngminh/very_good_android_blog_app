package me.dungngminh.verygoodblogapp.core

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

open class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        Timber.d("$this::init")
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        Timber.d("$this::onCleared")
    }
}