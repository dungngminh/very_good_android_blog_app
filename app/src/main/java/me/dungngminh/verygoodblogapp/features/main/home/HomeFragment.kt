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
import androidx.navigation.fragment.findNavController
import clearFocus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.dungngminh.verygoodblogapp.MainGraphDirections
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentHomeBinding
import me.dungngminh.verygoodblogapp.features.main.MainViewModel
import me.dungngminh.verygoodblogapp.features.main.home.adapters.HomeAdapter
import me.dungngminh.verygoodblogapp.models.Category
import me.dungngminh.verygoodblogapp.utils.addChip
import me.dungngminh.verygoodblogapp.utils.onDone
import reactivecircus.flowbinding.android.widget.textChanges
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    private val homeViewModel: HomeViewModel by viewModels()

    private val homeAdapter by lazy {
        HomeAdapter(
            onBlogClick = {
                val action = MainGraphDirections.actionGlobalBlogDetailFragment(it)
                findNavController().navigate(action)
            },
            onBookmarkClick = {},
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        binding.etSearchBlog.onDone {
            clearFocus()
        }

        binding.cgCategory.run {
            Category.entries.forEach {
                this.addChip(
                    category = it,
                    fragment = this@HomeFragment,
                    onCategoryPress = homeViewModel::selectCategory
                )
            }
        }

        binding.rcvHomeBlogs.run {
            adapter = homeAdapter
        }
    }

    @OptIn(FlowPreview::class)
    override fun bindEvent() {
        binding
            .etSearchBlog
            .textChanges()
            .skipInitialValue()
            .flowWithLifecycle(lifecycle)
            .debounce(300)
            .filter { it.isNotEmpty() }
            .onEach {
                homeViewModel
            }
            .launchIn(lifecycleScope)
    }

    override fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.state.collectLatest { state ->
                        Timber.d("HomeViewModelState:: $state")
                        homeAdapter.submitList(state.homePageBlog)
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