package com.example.umc_stepper.ui.community.part

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityPartHomeTabBinding

class CommunityPartHomeMotivationFragment :BaseFragment<FragmentCommunityPartHomeTabBinding>(R.layout.fragment_community_part_home_tab) {
    override fun setLayout() {
        binding.communityPartHomeTabTv.text="동기부여 Talk 글이 없습니다"
    }

}