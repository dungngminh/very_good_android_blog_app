package me.dungngminh.verygoodblogapp.features.main.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.databinding.LayoutOtherBlogsBinding
import me.dungngminh.verygoodblogapp.databinding.LayoutPopularBlogsBinding
import me.dungngminh.verygoodblogapp.features.main.home.HomeState.HomePageBlog
import me.dungngminh.verygoodblogapp.models.Blog
import me.dungngminh.verygoodblogapp.utils.HorizontalItemDecoration
import me.dungngminh.verygoodblogapp.utils.VerticalItemDecoration

class HomeAdapter(
    private val onBlogClick: (Blog) -> Unit,
    private val onBookmarkClick: (Blog) -> Unit,
) :
    ListAdapter<HomePageBlog, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<HomePageBlog>() {
        override fun areItemsTheSame(oldItem: HomePageBlog, newItem: HomePageBlog): Boolean {
            return when {
                oldItem is HomePageBlog.Other && newItem is HomePageBlog.Other -> oldItem.blogs == newItem.blogs
                oldItem is HomePageBlog.Popular && newItem is HomePageBlog.Other -> oldItem.blogs == newItem.blogs
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: HomePageBlog, newItem: HomePageBlog): Boolean {
            return oldItem == newItem
        }
    }) {


    inner class PopularBlogsViewHolder(private val binding: LayoutPopularBlogsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(popularBlogs: List<Blog>) {
            binding.rcvPopularBlogs.run {
                adapter = PopularBlogAdapter(
                    onBlogClick = onBlogClick,
                    onBookmarkClick = onBookmarkClick,
                ).apply {
                    submitList(popularBlogs)
                }
                addItemDecoration(
                    HorizontalItemDecoration(
                        spacing = resources.getDimensionPixelSize(R.dimen.spacing_small)
                    )
                )
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    inner class OtherBlogsViewHolder(private val binding: LayoutOtherBlogsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(otherBlogs: List<Blog>) {
            binding.rcvOtherBlogs.run {
                adapter = OtherBlogAdapter(
                    onBlogClick = onBlogClick,
                ).apply {
                    submitList(otherBlogs)
                }
                // add space between items vertically
                addItemDecoration(
                    VerticalItemDecoration(
                        spacing = resources.getDimensionPixelSize(R.dimen.spacing_small)
                    )
                )
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            POPULAR_BLOG_VIEW_TYPE -> PopularBlogsViewHolder(
                LayoutPopularBlogsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            OTHER_BLOG_VIEW_TYPE -> OtherBlogsViewHolder(
                LayoutOtherBlogsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> error("Invalid viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PopularBlogsViewHolder -> holder.bind((getItem(position) as HomePageBlog.Popular).blogs)
            is OtherBlogsViewHolder -> holder.bind((getItem(position) as HomePageBlog.Other).blogs)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomePageBlog.Popular -> POPULAR_BLOG_VIEW_TYPE
            else -> OTHER_BLOG_VIEW_TYPE
        }
    }

    companion object {
        const val POPULAR_BLOG_VIEW_TYPE = 1
        const val OTHER_BLOG_VIEW_TYPE = 2
    }
}
