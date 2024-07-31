package com.example.umc_stepper.ui.today

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentMyExercise3Binding
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.YouTubeVideo
import com.example.umc_stepper.utils.GlobalApplication
import com.example.umc_stepper.utils.extensions.navigateSafe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyExercise3Fragment :
    BaseFragment<FragmentMyExercise3Binding>(R.layout.fragment_my_exercise_3) {

    private val todayViewModel: TodayViewModel by activityViewModels()
    lateinit var tagList: List<TextView>
    var selectItem = -1
    var url1 : String = ""
    var url2 : String = ""

    override fun setLayout() {
        binding.fragmentMyExerciseSelectTagTv.setOnClickListener{
            val action = MyExercise3FragmentDirections.actionFragmentMyExercise3ToFragmentMyExercise2()
            findNavController().navigateSafe(action.actionId)
        }
        initSetting()
    }

    private fun initSetting() {
        remoteLifeCycle()
        setList()
        setOnClickBtn()
    }

    private fun remoteLifeCycle() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    todayViewModel.provideAiVideo.collectLatest { response -> // AI 서버
                        val (firstUrl, secondUrl) = formatUrl(response)
                        if (firstUrl.isNotEmpty() || secondUrl.isNotEmpty()) {
                            todayViewModel.getYoutubeVideoInfoSequentially(firstUrl, secondUrl)
                            url1 = response.video_urls.last()
                            url2 = response.video_urls.first()
                            Log.d(
                                "MyExercise3Fragment",
                                "Fetching YouTube info for URLs: $firstUrl, $secondUrl"
                            )
                        } else {
                            // URL이 없는 경우에 대한 처리
                            Log.e("MyExercise3Fragment", "No valid URLs provided by AI")
                            // 사용자에게 알림을 표시하거나 다른 적절한 처리를 수행
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.successYoutubeLink.collectLatest { response ->
                    if(response.ylist.size >= 2){
                        updateUI(response.ylist)
                    }
                }
            }
        }
    }

    private fun setList() {
        with(binding) {
            tagList = listOf(
                fragmentMyExerciseSelectTag1Tv,
                fragmentMyExerciseSelectTag2Tv,
                fragmentMyExerciseSelectTag3Tv,
                fragmentMyExerciseSelectTag4Tv,
                fragmentMyExerciseSelectTag5Tv,
                fragmentMyExerciseSelectTag6Tv,
                fragmentMyExerciseSelectTag7Tv
            )
        }
    }

    private fun setOnClickBtn() {
        with(binding) {
            fragmentMyExerciseSelectTag1Tv.setOnClickListener { selectListen(0) }
            fragmentMyExerciseSelectTag2Tv.setOnClickListener { selectListen(1) }
            fragmentMyExerciseSelectTag3Tv.setOnClickListener { selectListen(2) }
            fragmentMyExerciseSelectTag4Tv.setOnClickListener { selectListen(3) }
            fragmentMyExerciseSelectTag5Tv.setOnClickListener { selectListen(4) }
            fragmentMyExerciseSelectTag6Tv.setOnClickListener { selectListen(5) }
            fragmentMyExerciseSelectTag7Tv.setOnClickListener { selectListen(6) }
            fragmentMyExerciseSearchResultCard1Cl.setOnClickListener {
                ableUrlCheck(url1)
            }
            fragmentMyExerciseSearchResultCard2Cl.setOnClickListener {
                ableUrlCheck(url2)
            }
            fragmentMyExerciseCompleteInputBt.setOnClickListener {
                val youtubeUrl = fragmentMyExerciseUploadYoutubeLinkEt.text.toString()
                // url 담아서 다음 화면 전송
            }
        }
    }
    private fun ableUrlCheck(url : String){
        if(url.isNotEmpty()){
            Log.d("링크","$url1 $url2")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun selectListen(select: Int) {

        for (i in 0..6) {
            if (i == select) {
                tagList[i].setBackgroundResource(setColor(requireContext(), true).first)
                tagList[i].setTextColor(setColor(requireContext(), true).second)
            } else {
                tagList[i].setBackgroundResource(setColor(requireContext(), false).first)
                tagList[i].setTextColor(setColor(requireContext(), false).second)
            }
        }
        callAIVideo(select)
    }

    private fun setColor(context: Context, selectState: Boolean): Pair<Int, Int> {
        val backGroundColor: Int
        val textColor: Int
        if (selectState) {
            backGroundColor = R.drawable.shape_rounded_square_yellow700_40dp
            textColor = ContextCompat.getColor(context, R.color.Purple_Black_BG_2)
        } else {
            backGroundColor = R.drawable.shape_rounded_square_purpleblack_bg2_25dp
            textColor = ContextCompat.getColor(context, R.color.Yellow_700)
        }
        return Pair(backGroundColor, textColor)
    }

    private fun callAIVideo(select: Int) {
        with(binding) {
            fragmentMyExerciseProgressbarPbCl.visibility = View.VISIBLE // 콘스트레인트
            fragmentMyExerciseSearchResultCard1Cl.visibility = View.GONE // 1번 영상 창 숨김
            fragmentMyExerciseSearchResultCard2Cl.visibility = View.GONE // 2번 영상 창 숨김
            fragmentMyExerciseSelectOkTagTv.text = tagList[select].text.toString() // 타이틀 세팅
            fragmentMyExerciseSelectOkTagTv.visibility = View.VISIBLE
            fragmentMyExerciseSelectOkDescriptionTagTv.visibility = View.VISIBLE
            downloadSetting(select)
        }
    }

    private fun formatUrl(aiVideoInfo: AiVideoInfo): Pair<String, String> {
        val url1 = aiVideoInfo.video_urls.getOrNull(0)?.let { extractYouTubeVideoId(it) } ?: ""
        val url2 = aiVideoInfo.video_urls.getOrNull(1)?.let { extractYouTubeVideoId(it) } ?: ""
        return Pair(url1, url2)
    }

    private fun extractYouTubeVideoId(url: String): String? {
        val regex =
            "(?:https?://)?(?:www\\.)?(?:youtube\\.com/(?:[^/\\n\\s]+/\\S+/|(?:v|e(?:mbed)?)|\\S*?[?&]v=)|youtu\\.be/)([a-zA-Z0-9_-]{11})".toRegex()
        val matchResult = regex.find(url)
        return matchResult?.groups?.get(1)?.value
    }

    private fun downloadSetting(select: Int) {
        lifecycleScope.launch {
            with(binding) {
                fragmentMyExercise3ProgressbarPb.visibility = View.VISIBLE
                fragmentMyExercise3ProgressbarTextTv.visibility = View.VISIBLE

                // 25%로 고정된 프로그레스 바 설정
                fragmentMyExercise3ProgressbarPb.progress = 25

                // 회전 애니메이션 설정
                val rotateAnimation =
                    ObjectAnimator.ofFloat(fragmentMyExercise3ProgressbarPb, "rotation", 0f, 360f)
                rotateAnimation.duration = 2000 // 2초 동안 한 바퀴 회전
                rotateAnimation.repeatCount = ValueAnimator.INFINITE
                rotateAnimation.interpolator = LinearInterpolator()
                rotateAnimation.start()

                val select1 = fragmentMyExerciseSelectTagTv.text.toString()
                val select2 = tagList[select].text.toString()
                todayViewModel.postAiVideoInfo(AiVideoDto(select1, select2))
            }
        }
    }

    private fun updateUI(setList: List<YouTubeVideo>) {
        Log.d("MyExercise3Fragment", "updateUI called with setList size: ${setList.size}")

        if (setList.size >= 2) {
            val response1 = setList.first()
            val response2 = setList.last()

            with(binding) {
                hideProgressBar()
                Log.d("MyExercise3Fragment", "프로그레스 바 숨김")

                fragmentMyExerciseSearchResultCard1Cl.visibility = View.VISIBLE
                fragmentMyExerciseSearchResultCard2Cl.visibility = View.VISIBLE

                fragmentMyExercisePlayerTitle1Tv.text = response1.items[0].snippet.title
                fragmentMyExerciseChannelName1Tv.text = response1.items[0].snippet.channelTitle

                fragmentMyExercisePlayerTitle2Tv.text = response2.items[0].snippet.title
                fragmentMyExerciseChannelName2Tv.text = response2.items[0].snippet.channelTitle

                GlobalApplication.loadProfileImage(
                    fragmentMyExerciseProfile1Iv,
                    response1.items[0].snippet.thumbnails.medium.url
                )
                GlobalApplication.loadCropRoundedSquareImage(
                    requireContext(),
                    fragmentMyExerciseThumbnail1Iv,
                    response1.items[0].snippet.thumbnails.medium.url,
                    25
                )

                GlobalApplication.loadProfileImage(
                    fragmentMyExerciseProfile2Iv,
                    response2.items[0].snippet.thumbnails.medium.url
                )
                GlobalApplication.loadCropRoundedSquareImage(
                    requireContext(),
                    fragmentMyExerciseThumbnail2Iv,
                    response2.items[0].snippet.thumbnails.medium.url,
                    25
                )
            }
        } else {
            Log.e("MyExercise3Fragment", "setList size is less than 2")
        }
    }

    private fun hideProgressBar() {
        with(binding) {
            fragmentMyExercise3ProgressbarPb.visibility = View.GONE
            fragmentMyExercise3ProgressbarTextTv.visibility = View.GONE
            todayViewModel.clearList()
        }
    }
}
