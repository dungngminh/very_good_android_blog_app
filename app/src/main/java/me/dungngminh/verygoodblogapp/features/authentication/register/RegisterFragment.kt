package me.dungngminh.verygoodblogapp.features.authentication.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentRegisterBinding
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterContract.*
import me.dungngminh.verygoodblogapp.utils.firstChange
import me.dungngminh.verygoodblogapp.utils.hideKeyboard


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
        viewModel.stateObservable.subscribeBy(onNext = {state ->
            binding.run {  }
        })
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