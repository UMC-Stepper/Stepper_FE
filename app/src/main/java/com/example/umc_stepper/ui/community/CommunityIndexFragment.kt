package com.example.umc_stepper.ui.community

import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexBinding

class CommunityIndexFragment :BaseFragment<FragmentCommunityIndexBinding>(R.layout.fragment_community_index) {
    override fun setLayout() {
        //임시함수적용: 추후 아래의 goCommunityIndex()함수와 함께 꼭 삭제
        binding.commnunityIndexPostBtn.setOnClickListener {
            goCommunityIndexPost()
        }
    }
    //임시함수(꼭 추후삭제) 작성글목록확인으로 이동
    private fun goCommunityIndexPost(){
        findNavController().navigate(R.id.action_communityIndexFragment_to_communityIndexPostFragment)
    }
}