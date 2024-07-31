package com.example.umc_stepper.ui.stepper.additional

import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseYoutube2Binding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class AdditionalExerciseYoutube2Fragment : BaseFragment<FragmentAdditionalExerciseYoutube2Binding>(R.layout.fragment_additional_exercise_youtube2) {

    override fun setLayout() {
        val urlText = arguments?.getString("urlText")
        if (!urlText.isNullOrEmpty()) {
            binding.fragmentDownloadYoutube2MainCardInputLinkEt.setText(urlText)
            initializeYouTubePlayer(urlText)
            fetchYouTubeVideoDetails(urlText)
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

    private fun extractVideoId(url: String): String {
        // URL에서 비디오 ID 추출
        val regex = Regex("v=([a-zA-Z0-9_-]+)")
        val match = regex.find(url)
        return match?.groupValues?.get(1) ?: ""
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
}
