package com.example.umc_stepper.ui.community.weekly

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityWeeklyShowPostBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.community.CommunityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.properties.Delegates

class CommunityWeeklyShowPostFragment : BaseFragment<FragmentCommunityWeeklyShowPostBinding>(R.layout.fragment_community_weekly_show_post) {

    private lateinit var mainActivity: MainActivity
    private lateinit var weeklyShowPostImageAdapter: WeeklyShowPostImageAdapter
    private lateinit var weeklyShowPostReplyAdapter: WeeklyShowPostReplyAdapter
    private var postId by Delegates.notNull<Int>()
    private val communityViewModel: CommunityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        updateMainToolbar()
        setPostId()
        setAdapter()
        observeViewModel()
        onClickImageView()
    }

    private fun setAdapter() {
        weeklyShowPostImageAdapter = WeeklyShowPostImageAdapter()
        binding.fragmentCommunityWeeklyShowPostImgRv.adapter = weeklyShowPostImageAdapter

        weeklyShowPostReplyAdapter = WeeklyShowPostReplyAdapter()
        binding.fragmentCommunityWeeklyShowPostReplyRv.adapter = weeklyShowPostReplyAdapter
    }

    private fun onClickImageView() {

        // 좋아요 등록/취소
        binding.fragmentCommunityWeeklyShowThumbsUpIv.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        val currentState = communityViewModel.isLike.value
                        if (currentState) {
                            communityViewModel.deleteCancelLike(postId)
                        } else {
                            communityViewModel.postLikeEdit(postId)
                        }
                        communityViewModel.isLike.collectLatest { isLiked ->
                            binding.fragmentCommunityWeeklyShowThumbsUpIv.setImageResource(
                                if (isLiked) R.drawable.ic_thumbs_up_fill
                                else R.drawable.ic_thumbs_up
                            )
                        }
                    }
                }
            }
        }

        // 스크랩 등록/취소
        binding.fragmentCommunityWeeklyShowScrapsUpIv.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val currentState = communityViewModel.isScrap.value
                    if (currentState) {
                        communityViewModel.deleteCancelScrap(postId)
                    } else {
                        communityViewModel.postCommitScrap(postId)
                    }
                    communityViewModel.isLike.collectLatest { isLiked ->
                        binding.fragmentCommunityWeeklyShowScrapsUpIv.setImageResource(
                            if (isLiked) R.drawable. ic_scrap_fill
                            else R.drawable.ic_scraps_up
                        )
                    }
                }
            }
        }
    }

    private fun setPostId() {
        arguments?.let {
            postId = it.getString("postId")?.toInt() ?: 1
        }
    }

    private fun observeViewModel() {

        // 게시글 상세 조회
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    communityViewModel.getDetailPost(postId.toInt())
                }

                launch {
                    communityViewModel.apiResponsePostViewResponse.collectLatest {
                        if (it.isSuccess) {
                            binding.apiResponsePostViewResponse = it.result

                            // 입출력 데이터 포맷 설정
                            val inputFormat =
                                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                            val outputFormat =
                                SimpleDateFormat("yy.MM.dd  HH:mm", Locale.getDefault())

                            // 날짜 파싱 및 변환
                            val date = inputFormat.parse(it.result?.updatedAt)
                            binding.fragmentCommunityWeeklyShowPostDateTv.text = date?.let { it -> outputFormat.format(it) }
                        }
                    }
                }
            }
        }
    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("Weekly Mission")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_toolbar_community_home)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_community_search)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_community_menu)
    }

}