package com.example.umc_stepper.ui.community

import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexBinding

class CommunityIndexFragment :BaseFragment<FragmentCommunityIndexBinding>(R.layout.fragment_community_index) {
    override fun setLayout() {
        binding.commnunityIndexPostBtn.setOnClickListener {
            goCommunityIndexPost()
        }

        binding.commnunityIndexCommentBtn.setOnClickListener {
            goCommunityIndexComment()
        }
    }

    private fun goCommunityIndexPost(){
        findNavController().navigate(R.id.action_communityIndexFragment_to_communityIndexPostFragment)
    }

    private fun goCommunityIndexComment(){
        findNavController().navigate(R.id.action_communityIndexFragment_to_communityIndexCommentFragment)
    }
}