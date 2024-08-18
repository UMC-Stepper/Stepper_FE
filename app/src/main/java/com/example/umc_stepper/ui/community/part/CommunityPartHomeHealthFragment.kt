package com.example.umc_stepper.ui.community.part

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityPartHomeTabBinding
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.utils.listener.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityPartHomeHealthFragment : BaseFragment<FragmentCommunityPartHomeTabBinding>(R.layout.fragment_community_part_home_tab),
    ItemClickListener {

    private val communityViewModel: CommunityViewModel by activityViewModels()
    private lateinit var communityPartHomeAdapter: CommunityPartHomeAdapter
    private val part: String by lazy {
        arguments?.getString("bodyPart").toString()
    }

    override fun setLayout() {
        initRecyclerView()
        observeCategoryList(part)
        Log.d("부위", part)
    }

    private fun initRecyclerView() {
        communityPartHomeAdapter = CommunityPartHomeAdapter(this)
        binding.fragmentCommunityPartHomeRv.apply {
            adapter = communityPartHomeAdapter
            layoutManager = LinearLayoutManager(context) // 리사이클러뷰의 레이아웃 매니저
        }
    }

    private fun observeCategoryList(categoryName: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            communityViewModel.getDetailPostList(categoryName)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    communityViewModel.apiResponseListPostViewResponse.collect { response ->
                        val filteredItems = response.result?.filter { item ->
                            item.subCategory == "건강정보" && item.bodyPart == part
                        } ?: emptyList()

                        Log.d("부위홈", filteredItems.toString())

                        // 데이터가 있으면 어댑터에 전달
                        communityPartHomeAdapter.submitList(filteredItems)

                        // 데이터가 없는 경우 로그 확인
                        if (filteredItems.isEmpty()) {
                            binding.communityPartHomeTabTv.text="건강정보 글이 없습니다."
                            binding.communityPartHomeTabTv.visibility= View.VISIBLE
                            binding.fragmentCommunityPartHomeRv.visibility= View.GONE
                            Log.d("부위홈", "아이템이 없음.")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("부위홈 에러", "Error: ${e.message}")
                }
            }
        }
    }

    override fun onClick(item: Any) {
        val args = Bundle().apply {
            putString("postId", "$item")
        }
        try {
            val action = CommunityPartHomeFragmentDirections.actionCommunityPartHomeFragmentToCommunityWeeklyShowPostFragment()
            findNavController().navigateSafe(action.actionId, args)
        } catch (e: Exception) {
            Log.e("NavigationError", "Navigation error: ${e.message}")
        }
    }

}

