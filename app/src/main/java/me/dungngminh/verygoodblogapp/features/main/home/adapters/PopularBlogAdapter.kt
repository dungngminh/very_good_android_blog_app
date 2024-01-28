package me.dungngminh.verygoodblogapp.features.main.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.dungngminh.verygoodblogapp.databinding.ItemPopularBlogBinding
import me.dungngminh.verygoodblogapp.models.Blog
import me.dungngminh.verygoodblogapp.utils.extensions.toTimeAgo

class PopularBlogAdapter(
    private val onBlogClick: (Blog) -> Unit,
    private val onBookmarkClick: (Blog) -> Unit,
) : ListAdapter<Blog, PopularBlogAdapter.PopularBlogItemViewHolder>(object :
    DiffUtil.ItemCallback<Blog>() {
    override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem == newItem
    }
}) {
    inner class PopularBlogItemViewHolder(private val binding: ItemPopularBlogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(blog: Blog) {
            binding.run {
                ivBlog.load(blog.imageUrl) {
                    crossfade(true)
                }
                tvPopularBlogFullname.text = blog.creator.fullName
                tvPopularBlogTitle.text = blog.title
                tvPopularBlogTimeAgo.text = blog.createdAt.toTimeAgo()
                btnBookmark.setOnClickListener { onBookmarkClick(blog) }
                itemView.setOnClickListener { onBlogClick(blog) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularBlogItemViewHolder {
        return PopularBlogItemViewHolder(
            ItemPopularBlogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularBlogItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}