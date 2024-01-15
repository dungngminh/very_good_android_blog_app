package me.dungngminh.verygoodblogapp.features.authentication.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import clearFocus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentRegisterBinding
import me.dungngminh.verygoodblogapp.features.helpers.LoadingStatus
import me.dungngminh.verygoodblogapp.utils.onDone
import me.dungngminh.verygoodblogapp.utils.snack
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.widget.textChanges
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {
    private var _binding: FragmentRegisterBinding? = null

    private val viewModel by viewModels<RegisterViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        binding.etConfirmationPassword.onDone { clearFocus() }
    }

    override fun collectState() {
        viewModel
            .state
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                // UI State
                binding.run {
                    Timber.d("State = $state")

                    when (state.fullNameValidationError) {
                        RegisterState.ValidationError.EMPTY -> getString(R.string.fullname_must_be_not_empty)
                        else -> null
                    }.let {
                        if (state.isFullNameFirstChanged) {
                            tilFullname.error = it
                        }
                    }

                    when (state.emailValidationError) {
                        RegisterState.ValidationError.EMPTY ->
                            getString(R.string.email_must_be_not_empty)

                        RegisterState.ValidationError.INVALID ->
                            getString(R.string.email_is_not_valid)

                        else -> null
                    }.let { message ->
                        if (state.isEmailFirstChanged) {
                            tilEmail.error = message
                        }
                    }

                    when (state.passwordValidationError) {
                        RegisterState.ValidationError.EMPTY -> getString(R.string.password_must_be_not_empty)

                        RegisterState.ValidationError.TOO_SHORT -> getString(R.string.password_must_be_more_than_8_characters_long)

                        else -> null
                    }.let { message ->
                        if (state.isPasswordFirstChanged) {
                            tilPassword.error = message
                        }
                    }

                    when (state.confirmationPasswordValidationError) {
                        RegisterState.ValidationError.NOT_MATCH -> getString(R.string.password_is_not_match)
                        else -> null
                    }.let { message ->
                        if (state.isConfirmationPasswordFirstChanged) {
                            tilConfirmationPassword.error = message
                        }
                    }

                    if (state.loadingStatus == LoadingStatus.LOADING) {
                        progressBar.visibility = View.VISIBLE
                        btnRegister.visibility = View.GONE
                    } else {
                        progressBar.visibility = View.GONE
                        btnRegister.visibility = View.VISIBLE
                    }

                    btnRegister.isEnabled = listOfNotNull(
                        state.emailValidationError,
                        state.fullNameValidationError,
                        state.passwordValidationError,
                        state.confirmationPasswordValidationError
                    ).isEmpty()
                }

                // UI Event
                when (state.loadingStatus) {
                    LoadingStatus.DONE -> {
                        findNavController().popBackStack()
                    }

                    LoadingStatus.ERROR -> {
                        requireView().snack(
                            state.error?.message ?: "Something went wrong! Please try again",
                        )
                        viewModel.errorMessageShown()
                    }

                    else -> {}
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun bindEvent() {
        binding.etFullname
            .textChanges()
            .skipInitialValue()
            .flowWithLifecycle(lifecycle)
            .onEach { viewModel.changeFullName(it.toString()) }
            .launchIn(lifecycleScope)

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

        binding.etConfirmationPassword
            .textChanges()
            .skipInitialValue()
            .flowWithLifecycle(lifecycle)

            .onEach { viewModel.changeConfirmationPassword(it.toString()) }
            .launchIn(lifecycleScope)

        binding.btnRegister
            .clicks()
            .onEach { viewModel.requestRegister() }
            .launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
