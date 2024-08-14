package com.example.umc_stepper.ui.community.weekly

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
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
    private lateinit var scrapDialog: CommunityDialog
    private lateinit var weeklyShowPostImageAdapter: WeeklyShowPostImageAdapter
    private lateinit var weeklyShowPostReplyAdapter: WeeklyShowPostReplyAdapter

    private val communityViewModel: CommunityViewModel by activityViewModels()
    private var postId by Delegates.notNull<Int>()
    private var isScrap: Boolean = false
    private var isAnonymous: Boolean = false

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        initSettings()
    }

    private fun initSettings() {
        updateMainToolbar()
        setPostId()
        setButton()
        setAdapter()
        observeViewModel()
    }

    // 버튼 설정 함수
    private fun setButton() {
        binding.fragmentCommunityWeeklyScrapTv.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                showDialog()
            }
        }

        // 좋아요 이미지 뷰 클릭시 좋아요 등록
        binding.fragmentCommunityWeeklyShowThumbsUpIv.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        communityViewModel.postLikeEdit(postId)
                    }
                }
            }
        }

        // 익명 체크 이미지뷰
        binding.fragmentCommunityWeeklyShowPostCheckAnonymousIv.setOnClickListener {
            if(!isAnonymous) {
                Log.d("isAnonymous", "isAnonymous T : $isAnonymous")
                binding.fragmentCommunityWeeklyShowPostCheckAnonymousIv.setImageResource(R.drawable.selector_checked_on)
                isAnonymous = true
            } else {
                Log.d("isAnonymous", "isAnonymous F : $isAnonymous")
                binding.fragmentCommunityWeeklyShowPostCheckAnonymousIv.setImageResource(R.drawable.selector_checked_off)
                isAnonymous = false
            }
        }
    }

    // EditText 엔터 누르면 댓글 작성되는 함수
    private fun setupCommentEditText() {
        binding.fragmentCommunityWeeklyShowPostEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {

                true
            } else {
                false
            }
        }
    }

    // 댓글 작성 로직처리 함수
    private fun leaveComment(text: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.apiResponsePostViewResponse.collectLatest {

                    launch {
                        // 작성자 인지 확인 (작성자는 익명 X)
                        if(it.result?.authorEmail?.equals(tokenManager.getEmail().first()) == true) {

                        } else {
                            // 익명 여부 확인
                            if (isAnonymous) {

                            }
                        }
                    }
                }
            }
        }
    }

    // 어댑터 설정 함수
    private fun setAdapter() {
        weeklyShowPostImageAdapter = WeeklyShowPostImageAdapter()
        binding.fragmentCommunityWeeklyShowPostImgRv.adapter = weeklyShowPostImageAdapter

        weeklyShowPostReplyAdapter = WeeklyShowPostReplyAdapter()
        binding.fragmentCommunityWeeklyShowPostReplyRv.adapter = weeklyShowPostReplyAdapter
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
                    communityViewModel.getCommunityMyScraps()
                }

                // 스크랩 상태 확인 및 UI 업데이트
                launch {
                    isScrap = communityViewModel.communityMyScrapResponseItem.value.result?.any {
                        it.id == postId
                    } == true

                    updateScrapText()
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
                            val date = it.result?.updatedAt?.let { inputFormat.parse(it) }
                            binding.fragmentCommunityWeeklyShowPostDateTv.text = date?.let { outputFormat.format(it) }

                            // 작성자와 현재 사용자가 동일한 경우만 수정하기 표시 (이 때, 스크랩 표시 X)
                            if(it.result?.authorEmail?.equals(tokenManager.getEmail().first()) == true) {
                                binding.fragmentCommunityWeeklyShowPostModifyTv.visibility = View.VISIBLE
                                binding.fragmentCommunityWeeklyScrapTv.visibility = View.INVISIBLE
                            } else {
                                binding.fragmentCommunityWeeklyShowPostModifyTv.visibility = View.INVISIBLE
                                binding.fragmentCommunityWeeklyScrapTv.visibility = View.VISIBLE
                            }
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

    private fun updateScrapText() {
        if (isScrap) {
            binding.fragmentCommunityWeeklyShowScrapsUpIv.setImageResource(R.drawable.ic_scrap_fill)
            binding.fragmentCommunityWeeklyScrapTv.text = "스크랩취소"
        } else {
            binding.fragmentCommunityWeeklyShowScrapsUpIv.setImageResource(R.drawable.ic_scraps_up)
            binding.fragmentCommunityWeeklyScrapTv.text = "스크랩하기"
        }
    }

    private fun showDialog() {
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
            if (btn1 == "확인") {
                if (!isScrap) {
                    communityViewModel.postCommitScrap(postId)
                    isScrap = true
                } else {
                    communityViewModel.deleteCancelScrap(postId)
                    isScrap = false
                }
                updateScrapText()
            }
        }
    }

    override fun onRemove(pos: Int) {}

}