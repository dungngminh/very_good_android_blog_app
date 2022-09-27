package me.dungngminh.verygoodblogapp.features.authentication.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentLoginBinding
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginContract.*
import me.dungngminh.verygoodblogapp.features.main.MainActivity
import me.dungngminh.verygoodblogapp.utils.firstChange
import me.dungngminh.verygoodblogapp.utils.hideKeyboard
import me.dungngminh.verygoodblogapp.utils.snack
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val viewModel by viewModels<LoginViewModel>()

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
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
                        if (usernameLayout.error != it && state.isUsernameChanged) usernameLayout.error =
                            it
                    }
                when (ValidationError.EMPTY_PASSWORD) {
                    in state.passwordError -> {
                        "Password must be not empty"
                    }
                    else -> {
                        null
                    }
                }.let { message ->
                    if (passwordLayout.error != message && state.isPasswordChanged) passwordLayout.error =
                        message
                }
                if (state.isLoading) {
                    progressBar.visibility = View.VISIBLE
                    btnLogin.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                    btnLogin.visibility = View.VISIBLE
                }
            }

        }).addTo(startStopDisposable)

        viewModel.eventObservable.subscribeBy(onNext = { singleEvent ->
            Timber.d("State = $singleEvent")
            when (singleEvent) {
                SingleEvent.LoginSuccess -> {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
                is SingleEvent.LoginFailed -> {
                    view?.snack("Login Failed")
                }
            }

        }).addTo(startStopDisposable)
    }

   private fun bindViewModel() {
        viewModel.processIntents(Observable.mergeArray(
            binding.etUsername.textChanges()
                .map { ViewIntent.UsernameChanged(it.toString()) },
            binding.etUsername.firstChange()
                .map { ViewIntent.UsernameFirstChanged },
            binding.etPassword.textChanges()
                .map { ViewIntent.PasswordChanged(it.toString()) },
            binding.etPassword.firstChange()
                .map { ViewIntent.PasswordFirstChanged },
            binding.btnLogin.clicks()
                .map { ViewIntent.LoginSubmitted },
        ))
            .addTo(compositeDisposable)
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