package me.dungngminh.verygoodblogapp.features.main.user

import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentUserBinding
import me.dungngminh.verygoodblogapp.features.main.MainViewModel

class UserFragment : BaseFragment(R.layout.fragment_user) {
    private val binding: FragmentUserBinding by viewBinding()

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun bindEvent() {
        binding.button.setOnClickListener {
            mainViewModel.requestLogout()
        }
    }

    override fun collectState() {
        // TODO: Handle UserFragment State from ViewModel or MainViewModel
    }
}
