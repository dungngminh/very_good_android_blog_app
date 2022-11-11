package me.dungngminh.verygoodblogapp.features.authentication.register

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import me.dungngminh.verygoodblogapp.databinding.FragmentRegisterBinding
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterContract.*
import me.dungngminh.verygoodblogapp.utils.firstChange
import me.dungngminh.verygoodblogapp.utils.hideKeyboard
import me.dungngminh.verygoodblogapp.utils.snack
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {
    private var _binding: FragmentRegisterBinding? = null

    private val viewModel by viewModels<RegisterViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        bindVM()
    }

    override fun onStart() {
        super.onStart()
        viewModel.stateObservable.subscribeBy(onNext = { state ->
            binding.run {
                Timber.d("State = $state")

                when {
                    ValidationError.EMPTY_FIRSTNAME in state.firstnameError -> {
                        "Firstname must be not empty"
                    }
                    else -> null

                }.let {
                    if (tilFirstname.error != it && state.isFirstnameChanged)
                        tilFirstname.error = it
                }

                when {
                    ValidationError.EMPTY_LASTNAME in state.lastnameError -> {
                        "Lastname must be not empty"
                    }
                    else -> null
                }.let {
                    if (tilLastname.error != it && state.isLastnameChanged) {
                        tilLastname.error = it
                    }
                }

                when {
                    ValidationError.TOO_SHORT_USERNAME in state.usernameError -> {
                        "Username is too short"
                    }
                    ValidationError.EMPTY_USERNAME in state.usernameError -> {
                        "Username must be not empty"
                    }
                    else -> null
                }.let {
                    if (tilUsername.error != it && state.isUsernameChanged) {
                        tilUsername.error = it
                    }
                }

                when {
                    ValidationError.TOO_SHORT_PASSWORD in state.passwordError -> {
                        "Password is too short"
                    }
                    ValidationError.EMPTY_PASSWORD in state.passwordError -> {
                        "Password must be not empty"
                    }
                    else -> null
                }.let {
                    if (tilPassword.error != it && state.isPasswordChanged) {
                        tilPassword.error = it
                    }
                }

                when {
                    ValidationError.NOT_MATCH in state.confirmationPasswordError -> {
                        "Password is not match"
                    }
                    else -> null
                }.let {
                    if (tilConfirmationPassword.error != it && state.isConfirmationPasswordChanged) {
                        tilConfirmationPassword.error = it
                    }
                }

                if (state.isLoading) {
                    progressBar.visibility = View.VISIBLE
                    btnRegister.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                    btnRegister.visibility = View.VISIBLE
                }
            }
        }).addTo(startStopDisposable)

        viewModel.eventObservable.subscribeBy { singleEvent ->
            Timber.d("State =$singleEvent")
            when (singleEvent) {
                SingleEvent.RegisterSuccess -> {
                    view?.snack("Register Successfully")
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        .let {
                            findNavController()
                                .navigate(it)
                        }
                }
                is SingleEvent.RegisterError -> {
                    view?.snack("Register Failed")
                }
            }
        }.addTo(startStopDisposable)
    }

    private fun bindVM() {
        viewModel.processIntents(Observable.mergeArray(
            binding.etFirstname
                .textChanges()
                .map { ViewIntent.FirstnameChanged(it.toString()) },
            binding.etFirstname
                .firstChange()
                .map { ViewIntent.FirstnameFirstChanged },
            binding.etLastname
                .textChanges()
                .map { ViewIntent.LastnameChanged(it.toString()) },
            binding.etLastname
                .firstChange()
                .map { ViewIntent.LastnameFirstChanged },
            binding.etUsername
                .textChanges()
                .map { ViewIntent.UsernameChanged(it.toString()) },
            binding.etUsername
                .firstChange()
                .map { ViewIntent.UsernameFirstChanged },
            binding.etPassword
                .textChanges()
                .map { ViewIntent.PasswordChanged(it.toString()) },
            binding.etPassword
                .firstChange()
                .map { ViewIntent.PasswordFirstChanged },
            binding.etConfirmationPassword
                .textChanges()
                .map { ViewIntent.ConfirmationPasswordChanged(it.toString()) },
            binding.etConfirmationPassword
                .firstChange()
                .map { ViewIntent.ConfirmationPasswordFirstChanged },
            binding.btnRegister
                .clicks().map { ViewIntent.RegisterSubmitted },
        )).addTo(compositeDisposable)
    }

    private fun setupViews() {
        hideKeyboard()

        viewModel.state.let { state ->
            binding.etFirstname.setText(state.firstname)
            binding.etLastname.setText(state.lastname)
            binding.etUsername.setText(state.username)
            binding.etPassword.setText(state.password)
            binding.etConfirmationPassword.setText(state.confirmationPassword)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}