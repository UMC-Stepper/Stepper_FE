package com.example.umc_stepper.ui.community

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityHomeBinding
import com.example.umc_stepper.ui.MainActivity

class CommunityHomeFragment : BaseFragment<FragmentCommunityHomeBinding>(R.layout.fragment_community_home) {

    override fun setLayout() {
        setButton()
    }

    private fun setButton() {

        // 위클리 게시판 글 목록으로 이동
        binding.fragmentCommunityHomeGoWeeklyIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityWeeklyHomeFragment()
            findNavController().navigate(action.actionId)
        }

        // 검색 화면으로 이동
        binding.fragmentCommunityHomeToolbarGoSearch.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunitySearchFragment()
            findNavController().navigate(action.actionId)
        }

        // 메뉴 화면으로 이동
        binding.fragmentCommunityHomeToolbarGoMenu.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityIndexFragment()
            findNavController().navigate(action.actionId)
        }

        // 임시 버튼 (Weekly 글 작성)
        binding.fragmentCommunityHomePartTitleTv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToWeeklySegmentEditFragment()
            findNavController().navigate(action.actionId)
        }
    }

}