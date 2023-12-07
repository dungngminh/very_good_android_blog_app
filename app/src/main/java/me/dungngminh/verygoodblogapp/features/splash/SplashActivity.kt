package me.dungngminh.verygoodblogapp.features.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.dungngminh.verygoodblogapp.core.BaseActivity
import me.dungngminh.verygoodblogapp.databinding.ActivitySplashBinding
import me.dungngminh.verygoodblogapp.features.authentication.AuthenticationActivity
import me.dungngminh.verygoodblogapp.features.main.MainActivity

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.isLoggedInState
            .flowWithLifecycle(lifecycle)
            .onEach { isLoggedIn ->
                if (isLoggedIn) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, AuthenticationActivity::class.java))
                }
                finish()
            }
            .launchIn(lifecycleScope)
    }
}