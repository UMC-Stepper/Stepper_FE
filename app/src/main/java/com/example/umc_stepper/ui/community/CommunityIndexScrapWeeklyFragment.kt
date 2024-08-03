package com.example.umc_stepper.ui.community

import android.view.WindowManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostPartBinding
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostWeeklyBinding

class CommunityIndexScrapWeeklyFragment :BaseFragment<FragmentCommunityIndexPostWeeklyBinding>(R.layout.fragment_community_index_post_weekly) {
    override fun setLayout() {
        binding.communityIndexPostWeeklyTv.text="스크랩한 글이 없습니다."
    }

}