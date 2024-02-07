package me.dungngminh.verygoodblogapp.features.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentHomeBinding
import me.dungngminh.verygoodblogapp.features.blog_detail.BlogDetailActivity
import me.dungngminh.verygoodblogapp.features.main.MainViewModel
import me.dungngminh.verygoodblogapp.features.main.home.adapters.HomeAdapter
import me.dungngminh.verygoodblogapp.models.Category
import me.dungngminh.verygoodblogapp.utils.extensions.addChip
import me.dungngminh.verygoodblogapp.utils.extensions.clearFocus
import me.dungngminh.verygoodblogapp.utils.extensions.onDone
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
                val intent =
                    Intent(requireActivity(), BlogDetailActivity::class.java).apply {
                        putExtras(bundleOf("blog" to it))
                    }
                startActivity(intent)
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
                    onCategoryPress = homeViewModel::selectCategory,
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
                homeViewModel.searchBlogs()
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
