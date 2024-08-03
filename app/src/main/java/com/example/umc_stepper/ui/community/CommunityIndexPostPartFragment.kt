package com.example.umc_stepper.ui.community

import android.view.WindowManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostPartBinding

class CommunityIndexPostPartFragment :BaseFragment<FragmentCommunityIndexPostPartBinding>(R.layout.fragment_community_index_post_part) {
    override fun setLayout() {
        barTransparent()
    }
    private fun barTransparent() {
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    }
}