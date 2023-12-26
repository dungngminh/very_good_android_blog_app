package me.dungngminh.verygoodblogapp.features.main.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.dungngminh.verygoodblogapp.databinding.ItemOtherBlogBinding
import me.dungngminh.verygoodblogapp.models.Blog
import me.dungngminh.verygoodblogapp.utils.extensions.toTimeAgo

class GeneralBlogAdapter(
    private val onBlogClick: (Blog) -> Unit,
) : ListAdapter<Blog, GeneralBlogAdapter.BlogItemViewHolder>(
        object :
            DiffUtil.ItemCallback<Blog>() {
            override fun areItemsTheSame(
                oldItem: Blog,
                newItem: Blog,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Blog,
                newItem: Blog,
            ): Boolean {
                return oldItem == newItem
            }
        },
    ) {
    inner class BlogItemViewHolder(private val binding: ItemOtherBlogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(blog: Blog) {
            binding.run {
                ivBlog.load(blog.imageUrl) {
                    crossfade(true)
                }

                tvCategory.text = blog.category.name
                tvOtherBlogTitle.text = blog.title
                tvOtherBlogTimeAgo.text = blog.createdAt.toTimeAgo()

                itemView.setOnClickListener {
                    onBlogClick(blog)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BlogItemViewHolder {
        return BlogItemViewHolder(
            ItemOtherBlogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: BlogItemViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
