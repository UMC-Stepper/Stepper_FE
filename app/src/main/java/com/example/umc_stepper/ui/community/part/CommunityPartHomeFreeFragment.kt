package com.example.umc_stepper.ui.community.part

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityPartHomeTabBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityPartHomeFreeFragment :BaseFragment<FragmentCommunityPartHomeTabBinding>(R.layout.fragment_community_part_home_tab) {
    override fun setLayout() {
        binding.communityPartHomeTabTv.text="자유 Talk 글이 없습니다"
    }

}