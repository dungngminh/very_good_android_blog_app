package me.dungngminh.verygoodblogapp.features.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.core.clearFocus
import me.dungngminh.verygoodblogapp.databinding.FragmentHomeBinding
import me.dungngminh.verygoodblogapp.features.main.MainViewModel
import me.dungngminh.verygoodblogapp.features.main.home.adapters.CategoryAdapter
import me.dungngminh.verygoodblogapp.utils.HorizontalItemDecoration
import me.dungngminh.verygoodblogapp.utils.dp
import me.dungngminh.verygoodblogapp.utils.onDone
import reactivecircus.flowbinding.android.widget.textChanges
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    private val homeViewModel: HomeViewModel by viewModels()

    private val categoryAdapter by lazy {
        CategoryAdapter(
            homeViewModel::selectCategory,
        )
    }

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
        bindEvent()
        collectState()
    }

    private fun setupView() {
        binding.etSearchBlog.onDone {
            clearFocus()
        }

        binding.rvCategory.run {
            layoutManager =
                LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            adapter = categoryAdapter
            addItemDecoration(HorizontalItemDecoration(8.dp))
        }
    }

    private fun bindEvent() {
        binding
            .etSearchBlog
            .textChanges()
            .skipInitialValue()
            .flowWithLifecycle(lifecycle)
            .onEach {

            }
            .launchIn(lifecycleScope)
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.state.collectLatest { state ->
                        Timber.d("HomeState:: $state")
                    }
                }
                launch {
                    mainViewModel.state.collectLatest { state ->
                        Timber.d("MainViewModelState:: $state")
                        binding.tvUsername.text =
                            getString(R.string.hello_user, state.user?.fullName)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
