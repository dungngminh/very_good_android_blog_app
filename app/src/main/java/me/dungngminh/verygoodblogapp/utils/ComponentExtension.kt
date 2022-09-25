package me.dungngminh.verygoodblogapp.utils

import android.widget.EditText
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

fun EditText.getFirstChange() = this.textChanges()
.skipInitialValue()
.take(1)
.unsubscribeOn(AndroidSchedulers.mainThread())