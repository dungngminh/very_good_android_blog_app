package me.dungngminh.verygoodblogapp.features.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        bindViewModel()
        collectState()
    }

    private fun setupView() {
        binding.tvUsername.text = getString(R.string.hello_user, "DungNgMinh")
    }

    private fun bindViewModel() {

    }

    private fun collectState() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
