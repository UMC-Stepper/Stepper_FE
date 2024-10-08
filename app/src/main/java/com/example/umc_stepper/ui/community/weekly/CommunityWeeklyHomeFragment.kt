package com.example.umc_stepper.ui.community.weekly

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityWeeklyHomeBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.community.CommunityHomeFragmentDirections
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.ui.community.savedcontents.comments.CommunityMyCommentsFragmentDirections
import com.example.umc_stepper.utils.listener.ItemClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommunityWeeklyHomeFragment : BaseFragment<FragmentCommunityWeeklyHomeBinding>(R.layout.fragment_community_weekly_home), ItemClickListener {

    private lateinit var weeklyHomePostListAdapter: WeeklyHomePostListAdapter
    private val communityViewModel : CommunityViewModel by activityViewModels()
    private var weeklyMissionId = 0

    override fun setLayout() {
        initSettings()
    }

    private fun initSettings() {
        setButton()
        initAdapter()
        observeViewModel()
    }

    private fun initAdapter() {
        weeklyHomePostListAdapter = WeeklyHomePostListAdapter(this)
        binding.fragmentCommunityWeeklyHomeRv.adapter = weeklyHomePostListAdapter
    }

    private fun observeViewModel() {
        // 위클리 게시글 목록 조회 함수 호출
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    communityViewModel.getWeeklyMission(1)
                    getWeeklyMissionId()
                    communityViewModel.getWeeklyPostList(weeklyMissionId)
                }
            }
        }

        // 위클리 게시글 목록 어댑터에 전송
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    communityViewModel.weeklyPostViewListResponse.collect {
                        if (it.isSuccess) {
                            weeklyHomePostListAdapter.submitList(it.result)
                        }
                    }
                }
            }
        }
    }

    private suspend fun getWeeklyMissionId() {
        lifecycleScope.launch {
            communityViewModel.weeklyMissionResponse.collect {
                weeklyMissionId = it.result?.id ?: 1
            }
        }
        weeklyMissionId = communityViewModel.weeklyMissionResponse.value.result?.id ?: 1
    }

    private fun setButton() {
        binding.fragmentCommunityWeeklyHomeFab.setOnClickListener {
            val action = CommunityWeeklyHomeFragmentDirections.actionCommunityWeeklyHomeFragmentToWeeklyEditFragment()
            val weeklyMissionTitle = getString(R.string.Weekly_Mission_Demo_day)
            val args = Bundle().apply {
                putString("weeklyMissionTitle", "$weeklyMissionTitle")
            }
            findNavController().navigateSafe(action.actionId, args)
        }

        // 커뮤니티 홈으로 이동
        binding.fragmentCommunityWeeklyHomeToolbarBackIv.setOnClickListener {
            val action = CommunityWeeklyHomeFragmentDirections.actionCommunityWeeklyHomeFragmentToCommunityHomeFragment()
            findNavController().navigate(action.actionId)
        }

        // 검색 화면으로 이동
        binding.fragmentCommunityWeeklyHomeToolbarGoSearch.setOnClickListener {
            val action = CommunityWeeklyHomeFragmentDirections.actionCommunityWeeklyHomeFragmentToCommunitySearchFragment()
            findNavController().navigate(action.actionId)
        }

        // 메뉴 화면으로 이동
        binding.fragmentCommunityWeeklyHomeToolbarGoMenu.setOnClickListener {
            val action = CommunityWeeklyHomeFragmentDirections.actionCommunityWeeklyHomeFragmentToCommunityIndexFragment()
            findNavController().navigate(action.actionId)
        }
    }

    override fun onClick(item: Any) {
        val args = Bundle().apply {
            putString("postId", "$item")
        }
        val action = CommunityWeeklyHomeFragmentDirections.actionCommunityWeeklyHomeFragmentToCommunityWeeklyShowPostFragment()
        findNavController().navigateSafe(action.actionId, args)
    }

}