package me.dungngminh.verygoodblogapp.features.main.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.databinding.LayoutGeneralBlogsBinding
import me.dungngminh.verygoodblogapp.databinding.LayoutPopularBlogsBinding
import me.dungngminh.verygoodblogapp.features.main.home.ui_model.HomePageBlog
import me.dungngminh.verygoodblogapp.models.Blog
import me.dungngminh.verygoodblogapp.models.Category
import me.dungngminh.verygoodblogapp.utils.SpacesItemDecoration
import me.dungngminh.verygoodblogapp.utils.extensions.getLocalizedName

class HomeAdapter(
    private val onBlogClick: (Blog) -> Unit,
    private val onBookmarkClick: (Blog) -> Unit,
) :
    ListAdapter<HomePageBlog, RecyclerView.ViewHolder>(HomeItemDiff) {
    inner class PopularBlogsViewHolder(private val binding: LayoutPopularBlogsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(popularBlogs: List<Blog>) {
            binding.rcvPopularBlogs.run {
                adapter =
                    PopularBlogAdapter(
                        onBlogClick = onBlogClick,
                        onBookmarkClick = onBookmarkClick,
                    ).apply { submitList(popularBlogs) }
                addItemDecoration(
                    SpacesItemDecoration(
                        right = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    ),
                )
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    inner class OtherBlogsViewHolder(private val binding: LayoutGeneralBlogsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(blogs: List<Blog>) {
            binding.tvTitle.text = itemView.context.getString(R.string.other_blogs)
            binding.rcvBlogs.run {
                adapter =
                    GeneralBlogAdapter(
                        onBlogClick = onBlogClick,
                    ).apply {
                        submitList(blogs)
                    }
                addItemDecoration(
                    SpacesItemDecoration(
                        bottom = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    ),
                )
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    inner class FilterBySearchBlogsViewHolder(private val binding: LayoutGeneralBlogsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(blogs: List<Blog>) {
            binding.tvTitle.text = itemView.context.getString(R.string.search_results)
            binding.rcvBlogs.run {
                adapter =
                    GeneralBlogAdapter(
                        onBlogClick = onBlogClick,
                    ).apply {
                        submitList(blogs)
                    }
                addItemDecoration(
                    SpacesItemDecoration(
                        bottom = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    ),
                )
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    inner class FilterByCategoryBlogsViewHolder(private val binding: LayoutGeneralBlogsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            blogs: List<Blog>,
            category: Category,
        ) {
            binding.tvTitle.text = itemView.context.getString(category.getLocalizedName())
            binding.rcvBlogs.run {
                adapter =
                    GeneralBlogAdapter(
                        onBlogClick = onBlogClick,
                    ).apply {
                        submitList(blogs)
                    }
                addItemDecoration(
                    SpacesItemDecoration(
                        bottom = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    ),
                )
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            POPULAR_BLOG_VIEW_TYPE ->
                PopularBlogsViewHolder(
                    LayoutPopularBlogsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )

            OTHER_BLOG_VIEW_TYPE ->
                OtherBlogsViewHolder(
                    LayoutGeneralBlogsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )

            FILTER_BY_SEARCH_BLOG_VIEW_TYPE ->
                FilterBySearchBlogsViewHolder(
                    LayoutGeneralBlogsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )

            FIlTER_BY_CATEGORY_BLOG_VIEW_TYPE ->
                FilterByCategoryBlogsViewHolder(
                    LayoutGeneralBlogsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )

            else -> error("Invalid viewType: $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is PopularBlogsViewHolder -> holder.bind((getItem(position) as HomePageBlog.Popular).blogs)
            is OtherBlogsViewHolder -> holder.bind((getItem(position) as HomePageBlog.Other).blogs)
            is FilterBySearchBlogsViewHolder -> holder.bind((getItem(position) as HomePageBlog.FilteredBySearch).blogs)
            is FilterByCategoryBlogsViewHolder -> {
                val item = getItem(position) as HomePageBlog.FilteredByCategory
                holder.bind(item.blogs, item.category)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomePageBlog.Popular -> POPULAR_BLOG_VIEW_TYPE
            is HomePageBlog.FilteredBySearch -> FILTER_BY_SEARCH_BLOG_VIEW_TYPE
            is HomePageBlog.FilteredByCategory -> FIlTER_BY_CATEGORY_BLOG_VIEW_TYPE
            else -> OTHER_BLOG_VIEW_TYPE
        }
    }

    companion object {
        const val POPULAR_BLOG_VIEW_TYPE = 1
        const val OTHER_BLOG_VIEW_TYPE = 2
        const val FILTER_BY_SEARCH_BLOG_VIEW_TYPE = 3
        const val FIlTER_BY_CATEGORY_BLOG_VIEW_TYPE = 4
    }
}

object HomeItemDiff : DiffUtil.ItemCallback<HomePageBlog>() {
    override fun areItemsTheSame(
        oldItem: HomePageBlog,
        newItem: HomePageBlog,
    ): Boolean {
        return when {
            oldItem is HomePageBlog.Other && newItem is HomePageBlog.Other -> oldItem.blogs == newItem.blogs
            oldItem is HomePageBlog.Popular && newItem is HomePageBlog.Popular -> oldItem.blogs == newItem.blogs
            oldItem is HomePageBlog.FilteredBySearch && newItem is HomePageBlog.FilteredBySearch -> oldItem.blogs == newItem.blogs
            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: HomePageBlog,
        newItem: HomePageBlog,
    ): Boolean {
        return oldItem == newItem
    }
}
