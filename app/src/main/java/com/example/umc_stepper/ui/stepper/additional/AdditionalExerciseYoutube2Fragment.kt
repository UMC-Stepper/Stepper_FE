package com.example.umc_stepper.ui.stepper.additional

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.umc_stepper.BuildConfig
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseYoutube2Binding
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.URL
import java.net.URLDecoder
@AndroidEntryPoint
class AdditionalExerciseYoutube2Fragment : BaseFragment<FragmentAdditionalExerciseYoutube2Binding>(R.layout.fragment_additional_exercise_youtube2) {

    val stepperViewModel:StepperViewModel by activityViewModels()
    val youtubeKey = BuildConfig.YOUTUBE_KEY
    override fun setLayout() {
        val urlText = arguments?.getString("urlText")
        if (!urlText.isNullOrEmpty()) {
            binding.fragmentDownloadYoutube2MainCardInputLinkEt.setText(urlText)
            initializeYouTubePlayer(urlText)
            fetchYouTubeVideoDetails(urlText)
        }

        binding.fragmentDownloadYoutube2Btn.setOnClickListener {
            goLastExercise()
        }
    }

    private fun initializeYouTubePlayer(url: String) {
        lifecycle.addObserver(binding.fragmentDownloadYoutube2MainCardPreviewYv)

        binding.fragmentDownloadYoutube2MainCardPreviewYv.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = extractVideoId(url)
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    fun extractVideoId(url: String): String {
        val patterns = listOf(
            "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*",
            "(?<=shorts/)([a-zA-Z0-9_-]+)"
        )

        for (pattern in patterns) {
            val regex = Regex(pattern)
            val matchResult = regex.find(url)
            if (matchResult != null) {
                return matchResult.value
            }
        }

        return ""
    }

    private fun fetchYouTubeVideoDetails(url: String) {
        val videoId = extractVideoId(url)
        // videoId를 사용하여 비디오 세부 정보를 가져오는 선호하는 방법 사용 (예: Retrofit, OkHttp 등)
        // 데이터를 가져오기 위한 플레이스홀더 함수
        fetchVideoDetailsFromApi("snippet",videoId,youtubeKey)
    }

    //유튜브api에서 세부정보 불러오는 함수..
    private fun fetchVideoDetailsFromApi(part: String, id: String, key: String) {
        stepperViewModel.getYoutubeVideoInfo(part,id,key)


    }

    private fun dataSetting(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                try{
                    stepperViewModel.provideYoutubeLink.collect{
                        binding.fragmentDownloadYoutube2MainCardChannelNameTv.text = it.items[0].snippet.title
                        binding.fragmentDownloadYoutube2ProfileNameTv.text = it.items[0].snippet.channelTitle
                        // 이미지 로딩 라이브러리를 사용하여 ImageView에 이미지 로드 (예: Glide, Picasso)
                        Glide.with(requireContext())
                            .load(it.items[0].snippet.thumbnails.default)
                            .into(binding.fragmentDownloadYoutube2MainCardChannelProfileIv)
                    }
                }catch(e: Exception) {
                }
            }
        }

    }

    private fun goLastExercise() {
        val urlText = binding.fragmentDownloadYoutube2MainCardInputLinkEt.text.toString()
        val bundle = Bundle().apply {
            putString("urlText", urlText)
        }
        findNavController().navigate(R.id.action_fragmentAdditionalExerciseYoutube2_to_fragmentLastExercise, bundle)
    }

}
