package com.example.umc_stepper.ui.community.part

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityPartHomeTabBinding
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.ui.community.CommunitySearchAdapter
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.ui.stepper.home.CalendarAdapter
import com.example.umc_stepper.utils.enums.LoadState
import com.example.umc_stepper.utils.listener.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface NavigationCallback {
    fun navigateToWeeklyShowPost(postId: String)
}

@AndroidEntryPoint
class CommunityPartHomeAskFragment : BaseFragment<FragmentCommunityPartHomeTabBinding>(R.layout.fragment_community_part_home_tab),
    ItemClickListener {

    private var navigationCallback: NavigationCallback? = null

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
                            item.subCategory == "QnA" && item.bodyPart == part
                        } ?: emptyList()

                        Log.d("부위홈", filteredItems.toString())

                        // 데이터가 없는 경우 로그 확인
                        if (filteredItems.isEmpty()) {
                            binding.communityPartHomeTabTv.text="QnA 글이 없습니다."
                            updateVisibility(false)
                            Log.d("부위홈", "아이템이 없음.")
                        }else{
                            updateVisibility(true)
                            // 데이터가 있으면 어댑터에 전달
                            communityPartHomeAdapter.submitList(filteredItems)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("부위홈 에러", "Error: ${e.message}")
                }
            }
        }
    }

    private fun updateVisibility(show : Boolean) {
        if (show) {
            binding.communityPartHomeTabTv.visibility = View.INVISIBLE
            binding.fragmentCommunityPartHomeRv.visibility = View.VISIBLE
        } else {
            binding.communityPartHomeTabTv.visibility = View.VISIBLE
            binding.fragmentCommunityPartHomeRv.visibility = View.INVISIBLE
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

