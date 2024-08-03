package com.example.umc_stepper.ui.community

import android.view.WindowManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostPartBinding

class CommunityIndexScrapPartFragment :BaseFragment<FragmentCommunityIndexPostPartBinding>(R.layout.fragment_community_index_post_part) {
    override fun setLayout() {
         this.binding.communityIndexPostPartTv.text="스크랩한 글이 없습니다."
    }

}