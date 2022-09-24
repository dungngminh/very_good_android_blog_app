package me.dungngminh.verygoodblogapp.features.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentLoginBinding
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginContract.ValidationError
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginContract.ViewIntent
import me.dungngminh.verygoodblogapp.utils.hideKeyboard
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val viewModel by viewModels<LoginViewModel>()

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        bindViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.stateObservable.subscribeBy(onNext = { state ->
            binding.run {
                Timber.d("State = $state")
                when {
                    ValidationError.TOO_SHORT_USERNAME in state.usernameError -> {
                        "Username is too short"
                    }
                    ValidationError.EMPTY_USERNAME in state.usernameError -> {
                        "Username must be not empty"
                    }
                    else -> null
                }
                    .let {
                        if(usernameLayout.error != it && state.isUsernameChanged) usernameLayout.error = it
                    }
            }
        }).addTo(startStopDisposable)
    }

    private fun bindViewModel() {
        viewModel.processIntents(Observable.mergeArray(
            binding.etUsername.textChanges()
                .map { ViewIntent.UsernameChanged(it.toString()) },
            binding.etUsername.textChanges()
                .skipInitialValue()
                .take(1)
//                .unsubscribeOn(AndroidSchedulers.mainThread())
                .map { ViewIntent.UsernameFirstChanged }

        )).addTo(compositeDisposable)
    }

    private fun setupViews() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        hideKeyboard()

        viewModel.state.let { state ->
            binding.etUsername.setText(state.username)
            binding.etPassword.setText(state.password)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}