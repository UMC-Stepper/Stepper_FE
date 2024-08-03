package com.example.umc_stepper.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityMainBinding
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.extensions.navigateToTopLevelDestination
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

        val mainBottomNavigationBar =  binding.mainBottomNavigationBar
        mainBottomNavigationBar.itemIconTintList = null

        val host = supportFragmentManager.findFragmentById(binding.mainNavHostFragment.id) as NavHostFragment ?: return
        navController = host.navController
        mainBottomNavigationBar.apply { setupWithNavController(navController) }

        // 최상위 프래그먼트 이동 설정 (투데이, 뱃지, 스태퍼, 커뮤니티, 설정)
        mainBottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.todayHomeFragment -> navController.navigateToTopLevelDestination(R.id.todayHomeFragment, navController)
                R.id.stepperFragment -> navController.navigateToTopLevelDestination(R.id.stepperFragment, navController)
                R.id.badgeFragment -> navController.navigateToTopLevelDestination(R.id.badgeFragment, navController)
                R.id.communityHomeFragment -> navController.navigateToTopLevelDestination(R.id.communityHomeFragment, navController)
                R.id.settingsFragment -> navController.navigateToTopLevelDestination(R.id.settingsFragment, navController)
                else -> false
            }
        }


        // 최소 실행시 프래그먼트 설정
        mainBottomNavigationBar.selectedItemId = R.id.todayHomeFragment
        navController.navigate(R.id.todayHomeFragment)

        // 평가 일지 (달력) 프래그먼트 -> 메인 툴바 제거
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val shouldHideToolbar = destination.id in setOf(
                R.id.todayHomeFragment,
                R.id.exerciseCardLastFragment,
                R.id.exerciseCompleteFragment,
                R.id.communityHomeFragment
            )
            binding.mainToolbar.apply {
                visibility = if (shouldHideToolbar) View.GONE else View.VISIBLE
                if (shouldHideToolbar) setOnClickListener(null)
            }
        }
    }

    // 툴바 타이틀 변경 함수
    fun updateToolbarTitle(title: String) {
        binding.mainToolbarTitleTv.text = title
    }

    // 툴바 가장 왼쪽 이미지 변경 함수
    fun updateToolbarLeftImg(imgSrc: Int) {
        binding.mainToolbarBackIv.setImageResource(imgSrc)
    }

    fun updateToolbarLeftPlusImg(text : String , tag : String){
        binding.mainToolbarSelectTagTv.visibility = View.GONE
        binding.mainToolbarTitleTv.text = text
        binding.mainToolbarSelectTagTv.text = tag
    }

    fun visibleTag(){
        binding.mainToolbarTitleTv.visibility = View.VISIBLE
    }


    // 툴바 중간 이미지 변경 함수
    fun updateToolbarMiddleImg(imgSrc: Int) {
        binding.mainToolbarGoToday.setImageResource(imgSrc)
    }

    // 툴바 가장 오른쪽 이미지 변경 함수
    fun updateToolbarRightImg(imgSrc: Int) {
        binding.mainToolbarGoStepper.setImageResource(imgSrc)
    }

    fun setBg(){
        binding.mainToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Purple_Black_BG_1))
    }


}