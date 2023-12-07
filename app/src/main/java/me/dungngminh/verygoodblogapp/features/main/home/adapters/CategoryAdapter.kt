package me.dungngminh.verygoodblogapp.features.main.home.adapters

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.dungngminh.verygoodblogapp.databinding.ItemCategoryBinding
import me.dungngminh.verygoodblogapp.models.Category
import me.dungngminh.verygoodblogapp.utils.getLocalizedName

class CategoryAdapter(private val onCategoryClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(
    ) {

    private var selectedCategory = Category.ALL

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, position: Int) {
            // TODO: add chip group to handle one time filter chip
            binding.categoryItem.run {
                text = itemView.context.getString(category.getLocalizedName())
                isChecked = selectedCategory == category
                setOnClickListener {
                    if (selectedCategory == category) return@setOnClickListener
                    selectedCategory = category
                    onCategoryClick(category)
//                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Category.entries.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(Category.entries[position], position)
    }


}