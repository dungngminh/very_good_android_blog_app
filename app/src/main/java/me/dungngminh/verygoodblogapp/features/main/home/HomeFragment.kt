package me.dungngminh.verygoodblogapp.features.main.home

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hoc081098.flowext.select
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
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
import me.dungngminh.verygoodblogapp.utils.SpacesItemDecoration
import me.dungngminh.verygoodblogapp.utils.extensions.addChip
import me.dungngminh.verygoodblogapp.utils.extensions.clearFocus
import me.dungngminh.verygoodblogapp.utils.extensions.onDone
import me.dungngminh.verygoodblogapp.utils.extensions.toBundle
import reactivecircus.flowbinding.android.widget.textChanges
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding<FragmentHomeBinding>()

    private val mainViewModel: MainViewModel by activityViewModels()

    private val homeViewModel: HomeViewModel by viewModels()

    private val homeAdapter by lazy {
        HomeAdapter(
            onBlogClick = {
                val intent =
                    Intent(requireActivity(), BlogDetailActivity::class.java)
                        .apply { putExtras(it.toBundle()) }
                startActivity(intent)
            },
            onBookmarkClick = {},
        )
    }

    override fun setupView() {
        binding.run {
            etSearchBlog.onDone {
                clearFocus()
            }
            cgCategory.run {
                Category.entries.forEach {
                    this.addChip(
                        category = it,
                        fragment = this@HomeFragment,
                        onCategoryPress = homeViewModel::selectCategory,
                    )
                }
            }
            rcvHomeBlogs.run {
                adapter = homeAdapter
                addItemDecoration(
                    SpacesItemDecoration(
                        bottom = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    ),
                )
            }
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
            .onEach {
                homeViewModel.searchBlogs(it.trim().toString())
            }
            .launchIn(lifecycleScope)
    }

    override fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.state
                        .collectLatest { state ->
                            Timber.d("HomeViewModelState:: $state")
                            homeAdapter.submitList(state.homePageBlogs)
                            binding.rcvHomeBlogs.invalidateItemDecorations()
                            binding.cgCategory.check(state.selectedCategory.ordinal)
                        }
                }
                launch {
                    mainViewModel.state.select { it.user }.collectLatest { user ->
                        Timber.d("MainViewModelState:: $user")
                        binding.tvUsername.text =
                            getString(R.string.hello_user, user?.fullName)
                    }
                }
            }
        }
    }
}
