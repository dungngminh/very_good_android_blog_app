package me.dungngminh.verygoodblogapp.core

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import timber.log.Timber

open class BaseFragment : Fragment() {
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$this::onCreate: $savedInstanceState")
    }

    @CallSuper
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("$this::onViewCreated: $view, $savedInstanceState")
        setupView()
        bindEvent()
        collectState()
    }

    open fun setupView() {

    }

    open fun bindEvent() {

    }

    open fun collectState() {

    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("$this::onDestroyView")
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$this::onDestroy")
    }
}

fun Fragment.clearFocus() {
    activity?.currentFocus?.clearFocus()
}
