package com.example.umc_stepper.ui.community.weekly

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityWeeklyHomeBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.ui.community.savedcontents.comments.CommunityMyCommentsFragmentDirections
import com.example.umc_stepper.utils.listener.ItemClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommunityWeeklyHomeFragment : BaseFragment<FragmentCommunityWeeklyHomeBinding>(R.layout.fragment_community_weekly_home), ItemClickListener {

    private lateinit var mainActivity: MainActivity
    private lateinit var weeklyHomePostListAdapter: WeeklyHomePostListAdapter
    private val communityViewModel : CommunityViewModel by activityViewModels()
    private var weeklyMissionId = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        initSettings()
    }

    private fun initSettings() {
        updateMainToolbar()
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
            val action = CommunityWeeklyHomeFragmentDirections
            // 위클리 홈 게시글 작성 화면으로 이동
        }
    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("Weekly Mission")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_toolbar_community_home)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_community_search)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_community_menu)
    }

    override fun onClick(item: Any) {
        val args = Bundle().apply {
            putString("postId", "$item")
        }
        val action = CommunityWeeklyHomeFragmentDirections.actionCommunityWeeklyHomeFragmentToCommunityWeeklyShowPostFragment()
        findNavController().navigateSafe(action.actionId, args)
    }

}