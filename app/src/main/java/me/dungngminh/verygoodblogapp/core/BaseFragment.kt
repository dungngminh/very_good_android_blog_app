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
        Timber.tag("$this").d("$this::onCreate: $savedInstanceState")
    }

    @CallSuper
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag("$this").d("$this::onViewCreated: $view, $savedInstanceState")
        setupView()
        bindEvent()
        collectState()
    }

    protected open fun setupView() {}

    protected open fun bindEvent() {}

    protected open fun collectState() {}

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag("$this").d("$this::onDestroyView")
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("$this").d("$this::onDestroy")
    }
}

