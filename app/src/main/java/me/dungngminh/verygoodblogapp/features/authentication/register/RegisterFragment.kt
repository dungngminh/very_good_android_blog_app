package me.dungngminh.verygoodblogapp.features.authentication.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentRegisterBinding

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
//        setupViews()
//        bindVM()
    }
//
//    override fun onStart() {
//        super.onStart()
//        viewModel.stateObservable.subscribeBy(onNext = { state ->
//            binding.run {
//                Timber.d("State = $state")
//
//                when (ValidationError.EMPTY_FULLNAME) {
//                    in state.fullNameError -> {
//                        "Full name must be not empty"
//                    }
//                    else -> null
//                }.let {
//                    if (tilFullname.error != it && state.isLastnameChanged) {
//                        tilFullname.error = it
//                    }
//                }
//
//                when {
//                    ValidationError.EMAIL_INVALID in state.emailError -> {
//                        "Email is not valid"
//                    }
//                    ValidationError.EMPTY_FULLNAME in state.emailError -> {
//                        "Email must be not empty"
//                    }
//                    else -> null
//                }.let {
//                    if (tilEmail.error != it && state.isEmailFirstChanged) {
//                        tilEmail.error = it
//                    }
//                }
//
//                when {
//                    ValidationError.TOO_SHORT_PASSWORD in state.passwordError -> {
//                        "Password is too short"
//                    }
//                    ValidationError.EMPTY_PASSWORD in state.passwordError -> {
//                        "Password must be not empty"
//                    }
//                    else -> null
//                }.let {
//                    if (tilPassword.error != it && state.isPasswordChanged) {
//                        tilPassword.error = it
//                    }
//                }
//
//                when (ValidationError.NOT_MATCH) {
//                    in state.confirmationPasswordError -> {
//                        "Password is not match"
//                    }
//                    else -> null
//                }.let {
//                    if (tilConfirmationPassword.error != it && state.isConfirmationPasswordChanged) {
//                        tilConfirmationPassword.error = it
//                    }
//                }
//
//                btnRegister.isCheckable = state.isValid
//                btnRegister.isEnabled = state.isValid
//
//                if (state.isLoading) {
//                    progressBar.visibility = View.VISIBLE
//                    btnRegister.visibility = View.GONE
//                } else {
//                    progressBar.visibility = View.GONE
//                    btnRegister.visibility = View.VISIBLE
//                }
//
//            }
//        }).addTo(startStopDisposable)
//
//        viewModel.eventObservable.subscribeBy { singleEvent ->
//            Timber.d("State =$singleEvent")
//            when (singleEvent) {
//                SingleEvent.RegisterSuccess -> {
//                    view?.snack("Register Successfully")
//                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
//                        .let {
//                            findNavController()
//                                .navigate(it)
//                        }
//                }
//                is SingleEvent.RegisterError -> {
//                    view?.snack("Register Failed")
//                }
//            }
//        }.addTo(startStopDisposable)
//
//    }
//
//    private fun bindVM() {
//        viewModel.processIntents(Observable.mergeArray(
//            binding.etFullname
//                .textChanges()
//                .map { ViewIntent.FullnameChanged(it.toString()) },
//            binding.etFullname
//                .firstChange()
//                .map { ViewIntent.FullnameFirstChanged },
//            binding.etEmail
//                .textChanges()
//                .map { ViewIntent.EmailChanged(it.toString()) },
//            binding.etEmail
//                .firstChange()
//                .map { ViewIntent.EmailFirstChanged },
//            binding.etPassword
//                .textChanges()
//                .map { ViewIntent.PasswordChanged(it.toString()) },
//            binding.etPassword
//                .firstChange()
//                .map { ViewIntent.PasswordFirstChanged },
//            binding.etConfirmationPassword
//                .textChanges()
//                .map { ViewIntent.ConfirmationPasswordChanged(it.toString()) },
//            binding.etConfirmationPassword
//                .firstChange()
//                .map { ViewIntent.ConfirmationPasswordFirstChanged },
//            binding.btnRegister
//                .clicks().map { ViewIntent.RegisterSubmitted },
//        )).addTo(compositeDisposable)
//    }
//
//    private fun setupViews() {
//        viewModel.state.let { state ->
//            binding.etFullname.setText(state.fullName)
//            binding.etEmail.setText(state.email)
//            binding.etPassword.setText(state.password)
//            binding.etConfirmationPassword.setText(state.confirmationPassword)
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}