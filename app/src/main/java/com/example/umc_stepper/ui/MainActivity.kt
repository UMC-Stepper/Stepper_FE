package com.example.umc_stepper.ui

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityMainBinding
import com.example.umc_stepper.token.TokenManager
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.ui.login.LoginViewModel
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.enums.LoadState
import com.example.umc_stepper.utils.extensions.navigateToTopLevelDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var tokenManager: TokenManager
    private lateinit var todayViewModel: TodayViewModel
    private lateinit var stepperViewModel: StepperViewModel
    private lateinit var navController: NavController

    //    private lateinit var mainViewModel: MainViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var communityViewModel: CommunityViewModel

    private val communityFragments = setOf(
        R.id.communityHomeFragment,
        R.id.communitySearchFragment,
        R.id.communityWeeklyHomeFragment,
        R.id.communityWeeklyShowPostFragment,
        R.id.weeklyEditFragment,
        R.id.weeklySegmentEditFragment,
        R.id.communityMyCommentsFragment,
        R.id.communityMyScrapFragment,
        R.id.communityIndexFragment,
        R.id.communityIndexPostFragment,
        R.id.communityIndexPostPartFragment,
        R.id.communityIndexWeelklyFragment,
        R.id.communityPartHomeFragment,
        R.id.communityPartHomeAskFragment,
        R.id.communityPartHomeFreeFragment,
        R.id.communityPartHomeHealthFragment,
        R.id.communityPartHomeMotivationFragment,
        R.id.communityPartShowPostFragment
    )

    override fun setLayout() {
        confirmAccessToken()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setViewModel()
        setNavigation()
        observeLifeCycle()
    }

    // 카메라 액티비티에서 사진 사용시 메인 액티비티로 이동한 뒤, fragmentEvaluationExercise로 이동
    override fun onResume() {
        super.onResume()

        val navigateTo = intent.getStringExtra("navigate_to")
        val photoUriString = intent.getStringExtra("photo_uri")

        if (navigateTo == "EvaluationExerciseFragment" && photoUriString != null) {
            val bundle = Bundle().apply {
                putString("photo_uri", photoUriString)
            }
            navController.navigate(R.id.fragmentEvaluationExercise, bundle)
        }
    }

    private fun confirmAccessToken() {
        lifecycleScope.launch {
            val token = tokenManager.getAccessToken().first() // Flow에서 첫 번째 값을 가져옴
            Log.d("토큰", token ?: "토큰이 없습니다.")
        }
    }

    private fun setViewModel() {
        todayViewModel = ViewModelProvider(this)[TodayViewModel::class.java]
        stepperViewModel = ViewModelProvider(this)[StepperViewModel::class.java]
        //mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        communityViewModel = ViewModelProvider(this)[CommunityViewModel::class.java]
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private fun updateToolbarClickListeners(destinationId: Int) {
        val currentFragmentId = navController.currentDestination?.id

        if (currentFragmentId in communityFragments) {
            setToolbarCommunityClickListener()
        } else {
            setToolbarBasicClickListener()
        }
    }

    private fun setNavigation() {
        val mainBottomNavigationBar = binding.mainBottomNavigationBar
        mainBottomNavigationBar.itemIconTintList = null

        val host =
            supportFragmentManager.findFragmentById(binding.mainNavHostFragment.id) as NavHostFragment
                ?: return
        navController = host.navController
        mainBottomNavigationBar.apply {
            setupWithNavController(navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateToolbarClickListeners(destination.id)
        }

        // 최상위 프래그먼트 이동 설정 (투데이, 뱃지, 스태퍼, 커뮤니티, 설정)
        mainBottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.todayHomeFragment -> navController.navigateToTopLevelDestination(
                    R.id.todayHomeFragment,
                    navController
                )

                R.id.stepperFragment -> navController.navigateToTopLevelDestination(
                    R.id.stepperFragment,
                    navController
                )

                R.id.badgeFragment -> navController.navigateToTopLevelDestination(
                    R.id.badgeFragment,
                    navController
                )

                R.id.communityHomeFragment -> navController.navigateToTopLevelDestination(
                    R.id.communityHomeFragment,
                    navController
                )

                R.id.settingsFragment -> navController.navigateToTopLevelDestination(
                    R.id.settingsFragment,
                    navController
                )

                else -> false
            }
        }

        // 평가 일지 (달력) 프래그먼트 -> 메인 툴바 제거
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val shouldHideToolbar = destination.id in setOf(
                R.id.todayHomeFragment,
                R.id.exerciseCardLastFragment,
                R.id.exerciseCompleteFragment,
                R.id.communityHomeFragment,
                R.id.settingsShowProfileFragment,
                R.id.settingsEditProfileFragment,
                R.id.settingsFragment,
                R.id.evaluationLogFragment,
                R.id.evaluationExerciseTodayFragment,
                R.id.communitySearchFragment,
                R.id.communityWeeklyHomeFragment,
                R.id.communityPartHomeFragment
            )
            binding.mainToolbar.apply {
                visibility = if (shouldHideToolbar) View.GONE else View.VISIBLE
                if (shouldHideToolbar) setOnClickListener(null)
            }
        }
    }

    // 외부 터치시 키보드 숨기기, 포커스 제거
//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        val imm: InputMethodManager = ContextCompat.getSystemService(
//            this,
//            InputMethodManager::class.java
//        ) as InputMethodManager
//        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
//
//        if (currentFocus is EditText) {
//            currentFocus!!.clearFocus()
//        }
//        return super.dispatchTouchEvent(ev)
//    }

    // 커뮤니티 클릭 리스너
    private fun setToolbarCommunityClickListener() {

        // 커뮤니티 홈
        binding.mainToolbarBackIv.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(navController.graph.startDestinationId, true)
                .build()

            navController.navigate(
                R.id.communityHomeFragment,
                null,
                navOptions
            )
        }

        // 검색
        binding.mainToolbarGoToday.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(navController.graph.startDestinationId, true)
                .build()

            navController.navigate(
                R.id.communitySearchFragment,
                null,
                navOptions
            )
        }

        // 목록
        binding.mainToolbarGoStepper.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(navController.graph.startDestinationId, true)
                .build()

            navController.navigate(
                R.id.communityIndexFragment,
                null,
                navOptions
            )
        }
    }

    // 기본 클릭 리스너
    private fun setToolbarBasicClickListener() {

        // BackPressedDispatcher 설정
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navController.navigateUp()) {
                    super.handleOnBackCancelled()
                }
            }
        })

        // 뒤로 가기
        binding.mainToolbarBackIv.setOnClickListener {
            if (!navController.navigateUp()) {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // 투데이 버튼
        binding.mainToolbarGoToday.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(navController.graph.startDestinationId, true)
                .build()

            navController.navigate(
                R.id.todayHomeFragment,
                null,
                navOptions
            )
        }

        // 스테퍼 버튼
        binding.mainToolbarGoStepper.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(navController.graph.startDestinationId, true)
                .build()

            navController.navigate(
                R.id.stepperFragment,
                null,  // arguments
                navOptions  // 백스택 제거 옵션 적용
            )
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

    fun updateToolbarLeftPlusImg(text: String, tag: String) {
        binding.mainToolbarSelectTagTv.visibility = View.GONE
        binding.mainToolbarTitleTv.text = text
        binding.mainToolbarSelectTagTv.text = tag
    }

    fun visibleTag() {
        binding.mainToolbarTitleTv.visibility = View.VISIBLE
    }

    // 툴바 오른쪽에서 두번째 이미지 변경 함수
    fun updateToolbarMiddleImg(imgSrc: Int) {
        binding.mainToolbarGoToday.setImageResource(imgSrc)
    }

    // 툴바 가장 오른쪽 이미지 변경 함수
    fun updateToolbarRightImg(imgSrc: Int) {
        binding.mainToolbarGoStepper.setImageResource(imgSrc)
    }

    fun setBg() {
        binding.mainToolbar.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.Purple_Black_BG_1
            )
        )
    }

    fun setBg2() {
        binding.mainToolbar.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.Purple_Black_BG_2
            )
        )
    }

    fun showProgress(){
        binding.pg.visibility = View.VISIBLE
    }
    fun hideProgress(){
        binding.pg.visibility = View.GONE
    }

    fun observeLifeCycle(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                todayViewModel.dataLoadState.collectLatest { state ->
                    when(state) {
                        LoadState.LOADING -> showProgress()
                        LoadState.SUCCESS, LoadState.ERROR -> hideProgress()
                        else ->{}
                    }
                }
            }
        }
    }

}