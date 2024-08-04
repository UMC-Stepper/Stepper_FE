package com.example.umc_stepper.ui.community

import android.view.WindowManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostPartBinding
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostWeeklyBinding

class CommunityIndexPostWeeklyFragment :BaseFragment<FragmentCommunityIndexPostWeeklyBinding>(R.layout.fragment_community_index_post_weekly) {
    override fun setLayout() {
        barTransparent()
    }
    private fun barTransparent() {
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    }
}