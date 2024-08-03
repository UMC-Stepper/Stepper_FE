package com.example.umc_stepper.ui.community

import android.view.WindowManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostPartBinding
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostWeeklyBinding

class CommunityIndexCommentWeeklyFragment :BaseFragment<FragmentCommunityIndexPostWeeklyBinding>(R.layout.fragment_community_index_post_weekly) {
    override fun setLayout() {
        binding.communityIndexPostWeeklyTv.text="작성한 댓글이 없습니다."
    }

}