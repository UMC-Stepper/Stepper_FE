package com.example.umc_stepper.ui.community.savedcontents.comments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityMyCommentsBinding
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.utils.listener.ItemClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class CommunityMyCommentsFragment : BaseFragment<FragmentCommunityMyCommentsBinding>(R.layout.fragment_community_my_comments),
    ItemClickListener {

    private lateinit var mainActivity: MainActivity
    private lateinit var myCommentsAdapter: MyCommentsAdapter
    private val communityViewModel : CommunityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        updateToolbar()
        setAdapter()
        observeViewModel()
    }

    private fun setAdapter() {
        myCommentsAdapter = MyCommentsAdapter(this)
        binding.fragmentCommunityMyCommentsRv.adapter = myCommentsAdapter
    }

    private fun observeViewModel() {

        // 내가 작성한 댓글의 게시글 조회 API 호출
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.getCommunityMyComments()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.communityMyCommentsResponseItem.collect {

                    // 성공 하면 게시글 보여줌 (실패하면 글이 없습니다 나옴 -> fragmentCommunityMyCommentsNoContentsTv)
                    if (it.isSuccess) {
                        it.result?.forEach { _ ->
                            updateVisibility(true)
                        }
                        myCommentsAdapter.submitList(it.result)    // 리사이클러뷰 갱신 (서버에서 불러온 내가 댓글단 글 목록)
                    } else {
                        updateVisibility(false)
                    }
                }
            }
        }
    }

    // 리사이클러뷰 , 글 없을 때 Visibility 설정하는 함수
    private fun updateVisibility(show : Boolean) {
        if (show) {
            binding.fragmentCommunityMyCommentsNoContentsTv.visibility = View.INVISIBLE
            binding.fragmentCommunityMyCommentsRv.visibility = View.VISIBLE
        } else {
            binding.fragmentCommunityMyCommentsNoContentsTv.visibility = View.VISIBLE
            binding.fragmentCommunityMyCommentsRv.visibility = View.INVISIBLE
        }
    }

    private fun updateToolbar() {
        mainActivity.updateToolbarTitle("목록")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_toolbar_community_home)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_community_search)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_community_menu)
    }

    // 리사이클러뷰 클릭하면 해당 글로 이동하는 함수 (postID 전송)
    override fun onClick(item: Any) {
        val args = Bundle().apply {
            putString("postId", "$item")
        }
        val action = CommunityMyCommentsFragmentDirections.actionCommunityMyCommentsFragmentToCommunityWeeklyShowPostFragment()
        findNavController().navigateSafe(action.actionId, args)
    }

}