package com.example.umc_stepper.ui.community.savedcontents.post

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostWeeklyBinding
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyPostsResponseItem
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.ui.community.part.CommunityPartHomeAdapter
import com.example.umc_stepper.utils.listener.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityIndexPostWeeklyFragment : BaseFragment<FragmentCommunityIndexPostWeeklyBinding>(R.layout.fragment_community_index_post_weekly),
    ItemClickListener {

    private val communityViewModel: CommunityViewModel by activityViewModels()
    private lateinit var communityIndexPostAdapter: CommunityIndexPostAdapter

    override fun setLayout() {
        initRecyclerView()
        observeWeeklyPostList()
    }

    private fun initRecyclerView() {
        communityIndexPostAdapter = CommunityIndexPostAdapter(this)
        binding.communityIndexPostWeeklyRv.apply {
            adapter = communityIndexPostAdapter
            layoutManager = LinearLayoutManager(context) // 리사이클러뷰의 레이아웃 매니저
        }
    }

    private fun observeWeeklyPostList() {
        // 데이터를 먼저 로드
        viewLifecycleOwner.lifecycleScope.launch {
            communityViewModel.getCommunityMyPosts()
        }

        // 데이터가 로드되면 수집하고 UI 업데이트
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    communityViewModel.communityMyPostsResponseItem.collect { response ->
                        // weeklyMissionTitle이 null이 아닌 값만 필터링
                        val weeklyItems = response.result?.filter { item ->
                            item.weeklyMissionTitle !== null
                        } ?: emptyList()

                        Log.d("작성한 글_위클리", weeklyItems.toString())

                        // 데이터가 없는 경우 UI 업데이트
                        if (weeklyItems.isNullOrEmpty()) {
                            updateVisibility(false)
                            Log.d("작성한 글_위클리", "아이템이 없음.")
                        } else {
                            // 데이터가 있는 경우 UI 업데이트
                            updateVisibility(true)
                            communityIndexPostAdapter.submitList(weeklyItems)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("작성한 글_위클리 에러", "Error: ${e.message}")
                }
            }
        }
    }

    private fun updateVisibility(show : Boolean) {
        if (show) {
            binding.communityIndexPostWeeklyTv.visibility = View.INVISIBLE
            binding.communityIndexPostWeeklyRv.visibility = View.VISIBLE
        } else {
            binding.communityIndexPostWeeklyTv.visibility = View.VISIBLE
            binding.communityIndexPostWeeklyRv.visibility = View.INVISIBLE
        }
    }

    override fun onClick(item: Any) {
        if (item is CommunityMyPostsResponseItem) {
            val args = Bundle().apply {
                putString("postId", item.id.toString())
            }
            findNavController().navigateSafe(
                R.id.action_communityIndexPostFragment_to_communityWeeklyShowPostFragment, args)
        }
    }
}
