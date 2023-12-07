package me.dungngminh.verygoodblogapp.features.authentication

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseActivity

@AndroidEntryPoint
class AuthenticationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }
}
