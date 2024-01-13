package me.dungngminh.verygoodblogapp.features.main.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.dungngminh.verygoodblogapp.databinding.ItemOtherBlogBinding
import me.dungngminh.verygoodblogapp.models.Blog

class OtherBlogAdapter(
    private val onBlogClick: (Blog) -> Unit,
) : ListAdapter<Blog, OtherBlogAdapter.OtherBlogItemViewHolder>(object :
    DiffUtil.ItemCallback<Blog>() {
    override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem == newItem
    }
}) {
    inner class OtherBlogItemViewHolder(private val binding: ItemOtherBlogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(blog: Blog) {
            binding.run {
                ivOtherBlog.load(blog.imageUrl) {
                    crossfade(true)
                    crossfade(500)
                    error(android.R.drawable.stat_notify_error)
                    allowHardware(false)
                }
                tvOtherBlogCategory.text = blog.category.name
                tvOtherBlogTitle.text = blog.title
                root.setOnClickListener {
                    onBlogClick(blog)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherBlogItemViewHolder {
        return OtherBlogItemViewHolder(
            ItemOtherBlogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OtherBlogItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}