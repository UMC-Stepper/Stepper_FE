package com.example.umc_stepper.ui.community

import android.view.WindowManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityPartHomeTabBinding

class CommunityPartHomeHealthFragment :BaseFragment<FragmentCommunityPartHomeTabBinding>(R.layout.fragment_community_part_home_tab) {
    override fun setLayout() {
        binding.communityPartHomeTabTv.text="건강정보 글이 없습니다"
    }

}