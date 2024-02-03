package me.dungngminh.verygoodblogapp.features.main.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentUserBinding
import me.dungngminh.verygoodblogapp.features.main.MainViewModel

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

    override fun bindEvent() {
        binding.button.setOnClickListener {
            mainViewModel.requestLogout()
        }
    }

    override fun collectState() {
        // TODO: Handle UserFragment State from ViewModel or MainViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
