package me.dungngminh.verygoodblogapp.features.main.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.dungngminh.verygoodblogapp.databinding.LayoutOtherBlogsBinding
import me.dungngminh.verygoodblogapp.databinding.LayoutPopularBlogsBinding
import me.dungngminh.verygoodblogapp.features.main.home.HomeState.HomePageBlog
import me.dungngminh.verygoodblogapp.models.Blog

class HomeAdapter(
    private val onBlogClick: (Blog) -> Unit,
    private val onBookmarkClick: (Blog) -> Unit,
) :
    ListAdapter<HomePageBlog, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<HomePageBlog>() {
        override fun areItemsTheSame(oldItem: HomePageBlog, newItem: HomePageBlog): Boolean {
            return when {
                oldItem is HomePageBlog.Other && newItem is HomePageBlog.Other -> oldItem.blog.id == newItem.blog.id
                oldItem is HomePageBlog.Popular && newItem is HomePageBlog.Other -> oldItem.blog.id == newItem.blog.id
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

        }
    }

    inner class OtherBlogsViewHolder(private val binding: LayoutOtherBlogsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(popularBlogs: List<Blog>) {

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
        TODO("Not yet implemented")
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