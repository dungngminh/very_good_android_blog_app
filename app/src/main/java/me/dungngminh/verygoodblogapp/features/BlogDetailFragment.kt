package me.dungngminh.verygoodblogapp.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseFragment
import me.dungngminh.verygoodblogapp.databinding.FragmentBlogDetailBinding


class BlogDetailFragment : BaseFragment() {

    private var _binding: FragmentBlogDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBlogDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        super.setupView()
        binding.run {
            appBar.iconButton.setOnClickListener {
                findNavController().popBackStack()
            }
            ivCover.setImageResource(R.drawable.komkat)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}