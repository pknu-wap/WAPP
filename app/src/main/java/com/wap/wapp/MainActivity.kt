package com.wap.wapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.wap.wapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNav()
    }

    private fun initNav() = binding.apply {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fcv_main_fragmentContainverView) as NavHostFragment
        navController = navHostFragment.navController

        bnvMainBottomNaviView.itemIconTintList = null

        setBottomNavigationVisiblity()
    }

    private fun setBottomNavigationVisiblity() {
        val hideBottomNavigationFragments = mutableListOf<Int>()

        val typedArray = resources.obtainTypedArray(R.array.hide_bottomNavigation_fragments)

        for (index in 0..typedArray.length()) {
            hideBottomNavigationFragments.add(typedArray.getResourceId(index, 0,))
        }

        typedArray.recycle()

        navController.addOnDestinationChangedListener { _, destination, _ ->

            binding.bnvMainBottomNaviView.isVisible =
                (destination.id !in hideBottomNavigationFragments)
        }
    }
}
