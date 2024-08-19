package com.example.umc_stepper.ui.community

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityHomeBinding
import com.example.umc_stepper.ui.MainActivity

class CommunityHomeFragment : BaseFragment<FragmentCommunityHomeBinding>(R.layout.fragment_community_home) {

    override fun onPause() {
        super.onPause()
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.Purple_Black_BG_1)
    }

    override fun setLayout() {
        setButton()
        binding.fragmentCommunityHomeBannerAdIv.setOnClickListener {
            findNavController().navigateSafe(
                CommunityHomeFragmentDirections.actionCommunityHomeFragmentToWeeklySegmentEditFragment().actionId,
                Bundle().apply { "허리" }
                )
        }
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.Purple_300)
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

        // 머리 커뮤니티 이동
        binding.fragmentCommunityHomeHeadIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityPartHomeFragment()
            val bundle = Bundle().apply {
                putString("bodyPart", "머리")
            }
            findNavController().navigate(action.actionId,bundle)
        }

        // 가슴 커뮤니티 이동
        binding.fragmentCommunityHomeChestIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityPartHomeFragment()
            val bundle = Bundle().apply {
                putString("bodyPart", "가슴")
            }
            findNavController().navigate(action.actionId,bundle)
        }

        // 복부 커뮤니티 이동
        binding.fragmentCommunityHomeStomachIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityPartHomeFragment()
            val bundle = Bundle().apply {
                putString("bodyPart", "복부")
            }
            findNavController().navigate(action.actionId,bundle)
        }

        // 골반 커뮤니티 이동
        binding.fragmentCommunityHomePelvisIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityPartHomeFragment()
            val bundle = Bundle().apply {
                putString("bodyPart", "골반")
            }
            findNavController().navigate(action.actionId,bundle)
        }

        // 팔,어깨 커뮤니티 이동
        binding.fragmentCommunityHomeArmIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityPartHomeFragment()
            val bundle = Bundle().apply {
                putString("bodyPart", "팔,어깨")
            }
            findNavController().navigate(action.actionId,bundle)
        }

        // 무릎,다리 커뮤니티 이동
        binding.fragmentCommunityHomeLegIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityPartHomeFragment()
            val bundle = Bundle().apply {
                putString("bodyPart", "무릎,다리")
            }
            findNavController().navigate(action.actionId,bundle)
        }

        // 등 커뮤니티 이동
        binding.fragmentCommunityHomeBackIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityPartHomeFragment()
            val bundle = Bundle().apply {
                putString("bodyPart", "등")
            }
            findNavController().navigate(action.actionId,bundle)
        }

        // 허리 커뮤니티 이동
        binding.fragmentCommunityHomeWaistIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityPartHomeFragment()
            val bundle = Bundle().apply {
                putString("bodyPart", "허리")
            }
            findNavController().navigate(action.actionId,bundle)
        }

        // 발 커뮤니티 이동
        binding.fragmentCommunityHomeFootIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityPartHomeFragment()
            val bundle = Bundle().apply {
                putString("bodyPart", "발")
            }
            findNavController().navigate(action.actionId,bundle)
        }

        // 임시 버튼 (Weekly 글 작성)
        binding.fragmentCommunityHomePartTitleTv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToWeeklySegmentEditFragment()
            findNavController().navigate(action.actionId)
        }
    }

}