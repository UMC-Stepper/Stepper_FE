package com.example.umc_stepper.ui.badge

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentBadgeBinding
import com.example.umc_stepper.domain.model.response.Badge
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.login.MainViewModel
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.example.umc_stepper.ui.stepper.home.DayData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class BadgeFragment : BaseFragment<FragmentBadgeBinding>(R.layout.fragment_badge) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mainActivity : MainActivity
    private val badgeImageList = mutableListOf(
        R.drawable.ic_badge_1,
        R.drawable.ic_badge_2,
        R.drawable.ic_badge_3,
        R.drawable.ic_badge_4
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun setLayout() {
        updateMainToolbar()
        updateBadgeImages() //뱃지 이미지 업데이트
    }


    private fun updateBadgeImages(){
        //첫 운동 설정 완료
        if (mainViewModel.badgeList[0].hasBadge) {
            binding.fragmentBadgeYellow11Iv.setImageResource(badgeImageList[0])
        }
        //첫 오늘의 운동 완료
        if (mainViewModel.badgeList[1].hasBadge) {
            binding.fragmentBadgeYellow12Iv.setImageResource(badgeImageList[1])
        }
        //첫 추가 운동 완료
        if (mainViewModel.badgeList[2].hasBadge) {
            binding.fragmentBadgeYellow13Iv.setImageResource(badgeImageList[2])
        }
        //첫 게시글 작성 완료
        if (mainViewModel.badgeList[3].hasBadge) {
            binding.fragmentBadgeYellow14Iv.setImageResource(badgeImageList[3])
        }

    }

//    // 뷰모델의 함수 호출 -> 뷰모델 -> 리포지토리의 함수 호출 : 서버에서 api 받아오는 과정 전부 실행됨
//    // -> _getBadge 에 서버에서 받아온 값 저장됨
//    private fun getBadge() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                mainViewModel.getBadge()
//            }
//            }
//    }
//
//    // _getBadge 에 서버에서 받아온 값 꺼내서 파싱하여 사용 (읽기전용으로 값 가져옴)
//    // ainViewModel.getBadge.collect { response -> 이 부분임
//    private fun setBadge(){
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                try {
//                    // response -> BaseListResponse
//                    // response.result -> badgeResponseItem
//                    // badge -> badgeResponseItem.list
//                    mainViewModel.getBadge.collect { response ->
//                        val badgeResponseItem = response.result
//                        val badge = Badge()
//                        badgeResponseItem?.forEach { i ->
//                            binding.fragmentBadgeYellow1Tv.text = i.categoryName
//                            Log.e("mainViewModel ","it : ${i.categoryName}")
//                            i.list.forEach { j ->
//                            }
//
//                        }
//                    }
//                } catch (e:Exception) {
//
//                }
//            }
//        }
//    }
    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("활동 훈장")
    }
}