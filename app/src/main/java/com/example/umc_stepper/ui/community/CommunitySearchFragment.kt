package com.example.umc_stepper.ui.community

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunitySearchBinding
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.utils.enums.LoadState
import com.example.umc_stepper.utils.listener.ItemClickListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CommunitySearchFragment :
    BaseFragment<FragmentCommunitySearchBinding>(R.layout.fragment_community_search),
    ItemClickListener {

    private lateinit var searchItemAdapter: CommunitySearchAdapter
    private val communityViewModel: CommunityViewModel by activityViewModels()

    override fun setLayout() {
        init()
    }

    private fun init() {
        initRecyclerView()
        setOnClickListeners()
        observeLifeCycle()
    }

    private fun initRecyclerView() {
        searchItemAdapter = CommunitySearchAdapter(this)
        binding.fragmentCommunitySearchItemRv.adapter = searchItemAdapter
    }

    private fun setOnClickListeners() {
        with(binding) {
            // EndIcon (검색 아이콘) 클릭 시 검색 수행
            textInputLayout.setEndIconOnClickListener {
                performSearch()
            }

            // '취소' 버튼 클릭 시 동작
            fragmentCommunitySearchCancelTv.setOnClickListener {
                fragmentCommunitySearchEditEt.text?.clear()
                findNavController().navigateUp()
            }

            // 키보드에서 '검색' 버튼을 눌렀을 때 검색 수행
            fragmentCommunitySearchEditEt.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch()
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun observeLifeCycle() {
        hideProgress()  // 초기 상태에서 ProgressBar 숨기기
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.weeklySearchViewListResponse.collectLatest {
                    if (it.isSuccess) {
                        when (it.loadState) {
                            LoadState.LOADING -> {
                                showProgress()
                                binding.fragmentCommunityEmptyTv.visibility = View.GONE
                            }

                            LoadState.SUCCESS -> {
                                hideProgress()
                                if (it.result.isNullOrEmpty()) {
                                    binding.fragmentCommunityEmptyTv.visibility = View.VISIBLE
                                    binding.fragmentCommunitySearchItemRv.visibility = View.GONE
                                } else {
                                    binding.fragmentCommunityEmptyTv.visibility = View.GONE
                                    binding.fragmentCommunitySearchItemRv.visibility = View.VISIBLE
                                    searchItemAdapter.submitList(it.result)
                                }
                            }

                            LoadState.EMPTY -> {
                                hideProgress()
                                binding.fragmentCommunityEmptyTv.visibility = View.VISIBLE
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }
    }


    private fun performSearch() {
        val searchQuery = binding.fragmentCommunitySearchEditEt.text.toString()
        if (searchQuery.length >= 3) {
            communityViewModel.getWeeklySearchList(1, searchQuery)
        } else {
            // 3글자 미만일 경우 사용자에게 알림
            Toast.makeText(context, "검색어는 3글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgress() {
        binding.fragmentCommunitySearchProgressPb.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.fragmentCommunitySearchProgressPb.visibility = View.GONE
    }

    override fun onClick(item: Any) {
        if (item is CommunityMyCommentsResponseItem) {
            val args = Bundle().apply {
                putString("postId", item.id.toString())
            }
            if (item.weeklyMissionTitle.isEmpty()) {
                findNavController().navigateSafe(
                    R.id.action_communitySearchFragment_to_communityPartShowPostFragment, args
                )
            } else {
                findNavController().navigateSafe(
                    R.id.action_communitySearchFragment_to_communityWeeklyShowPostFragment, args
                )
            }
        }
    }
}
