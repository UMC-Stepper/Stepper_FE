package com.example.umc_stepper.ui.community

import android.view.WindowManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostPartBinding

class CommunityIndexCommentPartFragment :BaseFragment<FragmentCommunityIndexPostPartBinding>(R.layout.fragment_community_index_post_part) {
    override fun setLayout() {
        binding.communityIndexPostPartTv.text="작성한 댓글이 없습니다."
    }

}