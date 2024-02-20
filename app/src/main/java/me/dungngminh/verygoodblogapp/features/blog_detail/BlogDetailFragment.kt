package me.dungngminh.verygoodblogapp.features.blog_detail

import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentBlogDetailBinding
import me.dungngminh.verygoodblogapp.models.Blog
import me.dungngminh.verygoodblogapp.utils.extensions.getCompactParcelableExtra
import me.dungngminh.verygoodblogapp.utils.extensions.toTimeAgo

class BlogDetailFragment : BaseFragment(R.layout.fragment_blog_detail) {
    private val binding: FragmentBlogDetailBinding by viewBinding()

    override fun setupView() {
        super.setupView()
        val blog = requireActivity().getCompactParcelableExtra<Blog>()
        binding.run {
            appBar.iconButton.setOnClickListener {
                requireActivity().finish()
            }
            ivCover.load(blog?.imageUrl) {
                crossfade(true)
            }
            tvAuthorName.text = blog?.creator?.fullName
            tvBlogTitle.text = blog?.title
            tvBlogCreatedDate.text = blog?.createdAt?.toTimeAgo()
        }
    }
}
