package me.dungngminh.verygoodblogapp.features.authentication.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.core.clearFocus
import me.dungngminh.verygoodblogapp.databinding.FragmentLoginBinding
import me.dungngminh.verygoodblogapp.features.main.MainActivity
import me.dungngminh.verygoodblogapp.features.helpers.LoadingStatus
import me.dungngminh.verygoodblogapp.utils.onDone
import me.dungngminh.verygoodblogapp.utils.snack
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.widget.textChanges
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        collectState()
        bindEvent()
    }

    private fun bindEvent() {
        binding.etEmail
            .textChanges()
            .skipInitialValue()
            .flowWithLifecycle(lifecycle)
            .onEach { viewModel.changeEmail(it.toString()) }
            .launchIn(lifecycleScope)

        binding.etPassword
            .textChanges()
            .skipInitialValue()
            .flowWithLifecycle(lifecycle)
            .onEach { viewModel.changePassword(it.toString()) }
            .launchIn(lifecycleScope)

        binding.btnLogin
            .clicks()
            .flowWithLifecycle(lifecycle)
            .onEach { viewModel.requestLogin() }
            .launchIn(lifecycleScope)
    }

    private fun setupViews() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.etPassword.onDone { clearFocus() }
    }

    private fun collectState() {
        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                // UI State
                binding.run {
                    Timber.d("State = $state")
                    when (state.emailValidationError) {
                        LoginState.ValidationError.EMPTY -> getString(R.string.email_must_be_not_empty)

                        LoginState.ValidationError.INVALID -> getString(R.string.email_is_not_valid)

                        else -> null
                    }.let { message ->
                        if (state.isEmailFirstChanged) {
                            emailLayout.error = message
                        }
                    }

                    when (state.passwordValidationError) {
                        LoginState.ValidationError.EMPTY -> getString(R.string.password_must_be_not_empty)

                        LoginState.ValidationError.TOO_SHORT -> getString(R.string.password_must_be_more_than_8_characters_long)

                        else -> null
                    }.let { message ->
                        if (state.isPasswordFirstChanged) {
                            passwordLayout.error = message
                        }
                    }

                    if (state.loadingStatus == LoadingStatus.LOADING) {
                        progressBar.visibility = View.VISIBLE
                        btnLogin.visibility = View.GONE
                    } else {
                        progressBar.visibility = View.GONE
                        btnLogin.visibility = View.VISIBLE
                    }

                    btnLogin.isEnabled =
                        state.emailValidationError == null && state.passwordValidationError == null
                }

                // UI Event
                when (state.loadingStatus) {
                    LoadingStatus.DONE -> {
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }

                    LoadingStatus.ERROR -> {
                        requireView().snack(
                            state.error?.message ?: "Something went wrong! Please try again",
                        )
                        viewModel.errorMessageShown()
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
