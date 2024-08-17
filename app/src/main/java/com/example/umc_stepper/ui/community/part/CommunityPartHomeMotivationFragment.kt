package com.example.umc_stepper.ui.community.part

import androidx.fragment.app.activityViewModels
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityPartHomeTabBinding
import com.example.umc_stepper.ui.community.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityPartHomeMotivationFragment :BaseFragment<FragmentCommunityPartHomeTabBinding>(R.layout.fragment_community_part_home_tab) {
    private val communityViewModel: CommunityViewModel by activityViewModels()
    override fun setLayout() {
        binding.communityPartHomeTabTv.text="동기부여 Talk 글이 없습니다"
    }

}