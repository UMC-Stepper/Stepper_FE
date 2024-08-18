package com.example.umc_stepper.ui.community.savedcontents.post

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexPostPartBinding
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.ui.community.part.CommunityPartHomeAdapter
import com.example.umc_stepper.utils.listener.ItemClickListener
import kotlinx.coroutines.launch

class CommunityIndexPostPartFragment :BaseFragment<FragmentCommunityIndexPostPartBinding>(R.layout.fragment_community_index_post_part),
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.getCommunityMyPosts()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    communityViewModel.communityMyPostsResponseItem.collect { response ->
                        val filteredItems = response.result?.filter { item ->
                            item.weeklyMissionTitle == null
                        } ?: emptyList()

                        Log.d("작성한 글_부위", filteredItems.toString())

                        // 데이터가 있으면 어댑터에 전달
                        communityIndexPostAdapter.submitList(filteredItems)

                        // 데이터가 없는 경우 로그 확인
                        if (filteredItems.isEmpty()) {
                            binding.communityIndexPostPartTv.visibility= View.VISIBLE
                            binding.communityIndexPostPartRv.visibility= View.GONE
                            Log.d("작성한 글_부위", "아이템이 없음.")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("작성한 글_부위 에러", "Error: ${e.message}")
                }
            }
        }
    }

    override fun onClick(item: Any) {
        if (item is ApiResponseListPostViewResponseItem) {
            val args = Bundle().apply {
                putString("indexPartPostId", item.id.toString())
            }
            // 추후 상세 화면을 표시하는 로직 추가
        }
    }
}