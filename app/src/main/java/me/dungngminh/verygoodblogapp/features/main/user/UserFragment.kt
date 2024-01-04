package me.dungngminh.verygoodblogapp.features.main.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentUserBinding
import me.dungngminh.verygoodblogapp.features.main.MainViewModel
import me.dungngminh.verygoodblogapp.utils.dp
import reactivecircus.flowbinding.android.view.clicks
import timber.log.Timber

class UserFragment : BaseFragment() {

    private var _binding: FragmentUserBinding? = null

    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        bindEvent()
        collectState()
        binding.btnSignOut.textSize = 16.dp.toFloat()
    }


    private fun setupView() {
        // TODO: Handle setup User Fragment View
    }

    private fun bindEvent() {
        binding.btnSignOut.setOnClickListener {
            mainViewModel.requestLogout()
        }
    }

    private fun collectState() {
        // TODO: Handle UserFragment State from ViewModel or MainViewModel
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
