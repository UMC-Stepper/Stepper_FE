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
import com.example.umc_stepper.token.TokenManager
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.community.CommunityDialog
import com.example.umc_stepper.ui.community.CommunityDialogInterface
import com.example.umc_stepper.ui.community.CommunityRemoveInterface
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class CommunityWeeklyShowPostFragment : BaseFragment<FragmentCommunityWeeklyShowPostBinding>(R.layout.fragment_community_weekly_show_post),
    CommunityDialogInterface,
    CommunityRemoveInterface {

    private lateinit var mainActivity: MainActivity
    private lateinit var weeklyShowPostImageAdapter: WeeklyShowPostImageAdapter
    private lateinit var weeklyShowPostReplyAdapter: WeeklyShowPostReplyAdapter
    private var postId by Delegates.notNull<Int>()
    private val communityViewModel: CommunityViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()

    private lateinit var scrapDialog: CommunityDialog

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        updateMainToolbar()
        setButton()
        setPostId()
        setAdapter()
        observeViewModel()
        onClickImageView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                //loginViewModel.userData.em
            }
        }
    }

    private fun setButton() {
        binding.fragmentCommunityWeeklyScrapTv.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val isScrap = tokenManager.getIsScrap().first()
                showDialog(isScrap)
            }
        }
    }

    private fun setAdapter() {
        weeklyShowPostImageAdapter = WeeklyShowPostImageAdapter()
        binding.fragmentCommunityWeeklyShowPostImgRv.adapter = weeklyShowPostImageAdapter

        weeklyShowPostReplyAdapter = WeeklyShowPostReplyAdapter()
        binding.fragmentCommunityWeeklyShowPostReplyRv.adapter = weeklyShowPostReplyAdapter
    }

    // 좋아요 이미지 뷰 클릭시 좋아요 등록/취소 함수
    private fun onClickImageView() {
        binding.fragmentCommunityWeeklyShowThumbsUpIv.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        val currentState = communityViewModel.isLike.value
                        if (currentState) {
                            communityViewModel.deleteCancelLike(postId)
                            tokenManager.saveIsLike(false)
                            binding.fragmentCommunityWeeklyShowThumbsUpIv.setImageResource(R.drawable.ic_thumbs_up)
                        } else {
                            communityViewModel.postLikeEdit(postId)
                            tokenManager.saveIsLike(true)
                            binding.fragmentCommunityWeeklyShowThumbsUpIv.setImageResource(R.drawable.ic_thumbs_up_fill)
                        }
                    }
                }
            }
        }
    }

    // postId 이전 화면에서 넘겨받아서 저장하는 함수
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

                // 데이터바인딩 설정
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

                // 좋아요 및 스크랩 상태를 동시에 수집
                launch {
                    combine(
                        tokenManager.getIsLike(),
                        tokenManager.getIsScrap()
                    ) { isLiked, isScrap ->
                        binding.fragmentCommunityWeeklyShowThumbsUpIv.setImageResource(
                            if (isLiked) R.drawable.ic_thumbs_up_fill
                            else R.drawable.ic_thumbs_up
                        )
                        binding.fragmentCommunityWeeklyScrapTv.text = if (isScrap) "스크랩취소" else "스크랩하기"
                        binding.fragmentCommunityWeeklyShowScrapsUpIv.setImageResource(
                            if (isScrap) R.drawable.ic_scrap_fill
                            else R.drawable.ic_scraps_up
                        )
                    }.collect()
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

    private fun showDialog(isScrap: Boolean) {
        val title = if (!isScrap) "이 글을 스크랩하시겠습니까?" else "스크랩을 취소하시겠습니까?"
        val btn1 = "확인"
        val btn2 = "취소"

        activity?.let {
            scrapDialog = CommunityDialog(title, btn1, btn2, this)
            scrapDialog.isCancelable = false
            scrapDialog.show(it.supportFragmentManager, "CommunityDialog")
        } ?: run {
            Log.e("CommunityWeeklyShowPostFragment", "Activity is null")
        }
    }

    override fun OnClickBtn1(btn1: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            val isScrap = tokenManager.getIsScrap().first()
            if (btn1 == "확인") {
                if (!isScrap) {
                    communityViewModel.postCommitScrap(postId)
                    tokenManager.saveIsScrap(true)
                } else {
                    communityViewModel.deleteCancelScrap(postId)
                    tokenManager.saveIsScrap(false)
                }
            }
        }
    }

    override fun onRemove(pos: Int) {}

}