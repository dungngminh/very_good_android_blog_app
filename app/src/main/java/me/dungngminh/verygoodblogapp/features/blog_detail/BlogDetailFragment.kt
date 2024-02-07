package me.dungngminh.verygoodblogapp.features.blog_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentBlogDetailBinding
import me.dungngminh.verygoodblogapp.models.Blog
import me.dungngminh.verygoodblogapp.utils.extensions.getCompactParcelableExtra
import me.dungngminh.verygoodblogapp.utils.extensions.toTimeAgo

class BlogDetailFragment : BaseFragment() {
    private var _binding: FragmentBlogDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBlogDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        super.setupView()
        val blog = requireActivity().getCompactParcelableExtra("blog", Blog::class.java)
        binding.run {
            appBar.iconButton.setOnClickListener {
                requireActivity().finish()
            }
            ivCover.load(blog.imageUrl) {
                crossfade(true)
            }
            tvAuthorName.text = blog.creator.fullName
            tvBlogTitle.text = blog.title
            tvBlogCreatedDate.text = blog.createdAt.toTimeAgo()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
