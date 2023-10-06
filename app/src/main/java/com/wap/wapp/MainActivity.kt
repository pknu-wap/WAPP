package com.wap.wapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

import com.wap.wapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNav()
    }

    private fun initNav() = binding.apply {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main_fragmentContainverView) as NavHostFragment
            navController = navHostFragment.navController
        }
}
