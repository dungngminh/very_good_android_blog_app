package me.dungngminh.verygoodblogapp.utils

import android.widget.EditText
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

fun EditText.firstChange(): Observable<Unit> {
    return textChanges()
        .skipInitialValue()
        .unsubscribeOn(AndroidSchedulers.mainThread())
        .filter { it.isNotEmpty() }
        .take(1)
        .map { }
}