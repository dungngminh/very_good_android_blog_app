package me.dungngminh.verygoodblogapp.features.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseActivity
import me.dungngminh.verygoodblogapp.databinding.ActivityMainBinding
import me.dungngminh.verygoodblogapp.features.authentication.AuthenticationActivity
import me.dungngminh.verygoodblogapp.features.new_blog.NewBlogActivity
import me.dungngminh.verygoodblogapp.utils.hide
import me.dungngminh.verygoodblogapp.utils.show
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupNavigationBar()
        collectState()
    }

    private fun setupView() {
        binding.fab.setOnClickListener {
            startActivity(Intent(this, NewBlogActivity::class.java))
        }
    }

    private fun setupNavigationBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavView.isVisible = destination.id != R.id.blogDetailFragment
            binding.fab.isVisible = destination.id != R.id.blogDetailFragment
        }
    }

    private fun collectState() {
        viewModel
            .state
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                Timber.d("State::$state")
                when (state.authStatus) {
                    MainState.AuthStatus.LOADING -> {
                        binding.navHostFragment.visibility = View.GONE
                        binding.loadingProgress.visibility = View.VISIBLE
                        binding.bottomNavView.hide()
                    }

                    MainState.AuthStatus.LOGGED_IN -> {
                        binding.navHostFragment.visibility = View.VISIBLE
                        binding.loadingProgress.visibility = View.GONE
                        binding.bottomNavView.show()
                    }

                    MainState.AuthStatus.SIGNED_OUT -> {
                        startActivity(Intent(this, AuthenticationActivity::class.java))
                        finish()
                    }

                    else -> {}
                }
            }
            .launchIn(lifecycleScope)
    }
}
