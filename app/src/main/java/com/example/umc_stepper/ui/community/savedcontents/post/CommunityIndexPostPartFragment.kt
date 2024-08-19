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
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostPartBinding
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyPostsResponseItem
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.ui.community.part.CommunityPartHomeAdapter
import com.example.umc_stepper.utils.listener.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityIndexPostPartFragment : BaseFragment<FragmentCommunityIndexPostPartBinding>(R.layout.fragment_community_index_post_part),
    ItemClickListener {

    private val communityViewModel: CommunityViewModel by activityViewModels()
    private lateinit var communityIndexPostAdapter: CommunityIndexPostAdapter

    override fun setLayout() {
        initRecyclerView()
        observePartPostList()
    }

    private fun initRecyclerView() {
        communityIndexPostAdapter = CommunityIndexPostAdapter(this)
        binding.communityIndexPostPartRv.apply {
            adapter = communityIndexPostAdapter
            layoutManager = LinearLayoutManager(context) // 리사이클러뷰의 레이아웃 매니저
        }
    }

    private fun observePartPostList() {
        // 데이터를 먼저 로드
        viewLifecycleOwner.lifecycleScope.launch {
            communityViewModel.getCommunityMyPosts()
        }

        // 데이터가 로드되면 수집하고 UI 업데이트
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.communityMyPostsResponseItem.collect { response ->
                    val partItems = response.result?.filter { item ->
                        item.weeklyMissionTitle == null
                    } ?: emptyList()

                    Log.d("작성한 글_부위", partItems.toString())

                    // 데이터가 없는 경우 로그 확인 및 UI 업데이트
                    if (partItems.isNullOrEmpty()) {
                        updateVisibility(false)
                        Log.d("작성한 글_부위", "아이템이 없음.")
                    } else {
                        updateVisibility(true)
                        // 데이터가 있으면 어댑터에 전달
                        communityIndexPostAdapter.submitList(partItems)
                    }
                }
            }
        }
    }

    private fun updateVisibility(show : Boolean) {
        if (show) {
            binding.communityIndexPostPartTv.visibility = View.INVISIBLE
            binding.communityIndexPostPartRv.visibility = View.VISIBLE
        } else {
            binding.communityIndexPostPartTv.visibility = View.VISIBLE
            binding.communityIndexPostPartRv.visibility = View.INVISIBLE
        }
    }

    override fun onClick(item: Any) {
        if (item is CommunityMyPostsResponseItem) {
            val args = Bundle().apply {
                putString("postId", item.id.toString())
            }
            findNavController().navigateSafe(
                R.id.action_communityIndexPostFragment_to_communityPartShowPostFragment, args)
        }
    }
}
