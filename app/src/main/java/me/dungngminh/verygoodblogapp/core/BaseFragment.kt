package me.dungngminh.verygoodblogapp.core

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

open class BaseFragment() : Fragment() {
    protected val compositeDisposable = CompositeDisposable()

    protected val startStopDisposable = CompositeDisposable()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$this::onCreate: $savedInstanceState")
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("$this::onViewCreated: $view, $savedInstanceState")
    }

    override fun onStop() {
        super.onStop()
        startStopDisposable.clear()
    }
    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        Timber.d("$this::onDestroyView")
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$this::onDestroy")
    }
}