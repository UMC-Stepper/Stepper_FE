package com.example.umc_stepper.ui.community.part

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityPartHomeTabBinding
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.ui.community.CommunitySearchAdapter
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.utils.enums.LoadState
import com.example.umc_stepper.utils.listener.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityPartHomeAskFragment :BaseFragment<FragmentCommunityPartHomeTabBinding>(R.layout.fragment_community_part_home_tab),
    ItemClickListener {
    private val communityViewModel: CommunityViewModel by activityViewModels()
    private lateinit var communityPartHomeAdapter: CommunityPartHomeAdapter

    override fun setLayout() {
        observeCategoryList("QnA")
        initRecyclerView()

    }

    private fun initRecyclerView() {
        communityPartHomeAdapter = CommunityPartHomeAdapter(this)
        binding.fragmentCommunityPartHomeRv.adapter = communityPartHomeAdapter
    }


    private fun observeCategoryList(categoryName:String) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.getDetailPostList(categoryName)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.apiResponseListPostViewResponse.collect {
                    communityPartHomeAdapter.submitList(it.result)
                }
            }
        }
    }

    override fun onClick(item: Any) {
        if (item is ApiResponseListPostViewResponseItem) {
            val args = Bundle().apply {
                putString("partPostId", item.id.toString())
            }
        }
    }
}