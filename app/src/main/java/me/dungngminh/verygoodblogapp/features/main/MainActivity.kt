package me.dungngminh.verygoodblogapp.features.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.core.BaseActivity
import me.dungngminh.verygoodblogapp.databinding.ActivityMainBinding
import me.dungngminh.verygoodblogapp.features.main.bookmark.BookmarkFragment
import me.dungngminh.verygoodblogapp.features.main.home.HomeFragment
import me.dungngminh.verygoodblogapp.features.main.notification.NotificationFragment
import me.dungngminh.verygoodblogapp.features.main.user.UserFragment

class MainActivity : BaseActivity(R.layout.activity_main){


    private var _binding: ActivityMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)


        val homeFragment = HomeFragment()
        val notificationFragment = NotificationFragment()
        val bookmarkFragment = BookmarkFragment()
        val userFragment = UserFragment()

        setCurrentFragment(homeFragment)

        binding.navView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.itemHomePage -> setCurrentFragment(homeFragment)
                R.id.itemNotification -> setCurrentFragment(notificationFragment)
                R.id.itemBookmark -> setCurrentFragment(bookmarkFragment)
                R.id.itemUser -> setCurrentFragment(userFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}