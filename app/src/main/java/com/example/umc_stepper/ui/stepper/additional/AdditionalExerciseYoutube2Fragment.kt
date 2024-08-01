package com.example.umc_stepper.ui.stepper.additional

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseYoutube2Binding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.net.URL
import java.net.URLDecoder

class AdditionalExerciseYoutube2Fragment : BaseFragment<FragmentAdditionalExerciseYoutube2Binding>(R.layout.fragment_additional_exercise_youtube2) {

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

        binding.fragmentDownloadYoutube2MainCardPreviewYv.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
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
        fetchVideoDetailsFromApi(videoId) { title, channelName, channelProfileUrl ->
            binding.fragmentDownloadYoutube2MainCardChannelNameTv.text = title
            binding.fragmentDownloadYoutube2ProfileNameTv.text = channelName
            // 이미지 로딩 라이브러리를 사용하여 ImageView에 이미지 로드 (예: Glide, Picasso)
            Glide.with(this)
                .load(channelProfileUrl)
                .into(binding.fragmentDownloadYoutube2MainCardChannelProfileIv)
        }
    }

    //유튜브api에서 세부정보 불러오는 함수..
    private fun fetchVideoDetailsFromApi(videoId: String, callback: (String, String, String) -> Unit) {
        //일단 불러왔다고 가정하고 임시데이터에요!

        callback("윗몸일으키기 제대로 하는 방법", "비타밍제이", "https://yt3.ggpht.com/l0AxbcHO4TRBQFka9rUZpiM19BQxueUZ_UE4wHW8qwaLZtZ_3J4fIXmay5HurJH03LJ7cGirxFY=s88-c-k-c0x00ffffff-no-rj")
    }

    private fun goLastExercise() {
        val urlText = binding.fragmentDownloadYoutube2MainCardInputLinkEt.text.toString()
        val bundle = Bundle().apply {
            putString("urlText", urlText)
        }
        findNavController().navigate(R.id.action_fragmentAdditionalExerciseYoutube2_to_fragmentLastExercise, bundle)
    }
}
