package me.dungngminh.verygoodblogapp.features.main

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseActivity
import me.dungngminh.verygoodblogapp.databinding.ActivityMainBinding
import me.dungngminh.verygoodblogapp.features.main.bookmark.BookmarkFragment
import me.dungngminh.verygoodblogapp.features.main.home.HomeFragment
import me.dungngminh.verygoodblogapp.features.main.notification.NotificationFragment
import me.dungngminh.verygoodblogapp.features.main.user.UserFragment
import timber.log.Timber

class MainActivity : BaseActivity(R.layout.activity_main) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.setupWithNavController(navController)
    }

}