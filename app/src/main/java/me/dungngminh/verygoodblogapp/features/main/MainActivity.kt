package me.dungngminh.verygoodblogapp.features.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseActivity
import me.dungngminh.verygoodblogapp.databinding.ActivityMainBinding
import me.dungngminh.verygoodblogapp.features.authentication.AuthenticationActivity
import me.dungngminh.verygoodblogapp.features.new_blog.NewBlogActivity
import me.dungngminh.verygoodblogapp.utils.extensions.hide
import me.dungngminh.verygoodblogapp.utils.extensions.show
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.bottomNavigationBar.setupWithNavController(navController)
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
                        binding.bottomNavigationBar.hide()
                    }

                    MainState.AuthStatus.LOGGED_IN -> {
                        binding.navHostFragment.visibility = View.VISIBLE
                        binding.loadingProgress.visibility = View.GONE
                        binding.bottomNavigationBar.show()
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
