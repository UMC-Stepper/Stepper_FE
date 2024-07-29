package com.example.umc_stepper.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    private lateinit var todayViewModel: TodayViewModel
    private lateinit var navController: NavController

    override fun setLayout() {
        setViewModel()
        setNavigation()
    }

    private fun setViewModel(){
        todayViewModel = ViewModelProvider(this)[TodayViewModel::class.java]
    }

    private fun setNavigation() {

        binding.mainBottomNavigationBar.itemIconTintList = null

        val host = supportFragmentManager
            .findFragmentById(binding.mainNavHostFragment.id) as NavHostFragment ?: return
        navController = host.navController
        binding.mainBottomNavigationBar.setupWithNavController(navController)

        // 최소 실행시 프래그먼트 설정
        binding.mainBottomNavigationBar.selectedItemId = R.id.todayHomeFragment
        navController.navigate(R.id.todayHomeFragment)
    }
}