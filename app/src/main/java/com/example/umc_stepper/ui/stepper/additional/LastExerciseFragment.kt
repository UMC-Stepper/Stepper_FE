package com.example.umc_stepper.ui.stepper.additional

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentLastExerciseBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class LastExerciseFragment : BaseFragment<FragmentLastExerciseBinding>(R.layout.fragment_last_exercise) {

    private var isRunning = false
    private var time = 0L // 밀리초 단위

    private lateinit var startBtn: Button
    private lateinit var resetBtn: Button

    private val handler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                time += 1000
                updateTimerUI(time)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun setLayout() {
        initButton()
        setTimer()
        resetTimer()
        val urlText = arguments?.getString("urlText")
        if (!urlText.isNullOrEmpty()) {
            initializeYouTubePlayer(urlText)
            fetchYouTubeVideoDetails(urlText)
        }

        binding.fragmentAdditionalMiddleExerciseCompleteBtn.setOnClickListener {
            goAdditionalExerciseSuccess()
            binding.fragmentAdditionalMiddleExerciseCompleteBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
            binding.fragmentAdditionalMiddleExerciseCompleteBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
        }
    }

    private fun initButton() {
        startBtn = binding.fragmentAdditionalMiddleStartBtn
        resetBtn = binding.fragmentAdditionalMiddleResetBtn
    }

    private fun setTimer() {
        startBtn.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                startBtn.text = "중지"
                handler.post(timerRunnable) // 타이머 시작
            } else {
                isRunning = false
                startBtn.text = "계속"
                handler.removeCallbacks(timerRunnable) // 타이머 중지
            }
        }
    }

    private fun updateTimerUI(time: Long) {
        val timeInSeconds = time / 1000
        val hours = timeInSeconds / 3600
        val minutes = (timeInSeconds % 3600) / 60
        val seconds = timeInSeconds % 60

        Log.d("updateTimerUI", "시: $hours 분: $minutes 초: $seconds")

        binding.fragmentAdditionalMiddleHourTv.text = String.format("%02d", hours)
        binding.fragmentAdditionalMiddleMinTv.text = String.format("%02d", minutes)
        binding.fragmentAdditionalMiddleSecTv.text = String.format("%02d", seconds)
    }

    private fun resetTimer() {
        resetBtn.setOnClickListener {
            isRunning = false
            time = 0
            startBtn.text = "시작"
            updateTimerUI(time)
            handler.removeCallbacks(timerRunnable) // 타이머 중지
        }
    }

    private fun initializeYouTubePlayer(url: String) {
        lifecycle.addObserver(binding.fragmentLastExerciseYoutubeViewYv)

        binding.fragmentLastExerciseYoutubeViewYv.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = extractVideoId(url)
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    private fun extractVideoId(url: String): String {
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
            binding.fragmentLastExerciseTitleTv.text = title
            binding.fragmentLastExerciseChannelNameTv.text = channelName
            // 이미지 로딩 라이브러리를 사용하여 ImageView에 이미지 로드 (예: Glide, Picasso)
            Glide.with(this)
                .load(channelProfileUrl)
                .into(binding.fragmentLastExerciseProfileIv)
        }
    }

    //유튜브api에서 세부정보 불러오는 함수..
    private fun fetchVideoDetailsFromApi(videoId: String, callback: (String, String, String) -> Unit) {
        //일단 불러왔다고 가정하고 임시데이터에요!

        callback("윗몸일으키기 제대로 하는 방법", "비타밍제이", "https://yt3.ggpht.com/l0AxbcHO4TRBQFka9rUZpiM19BQxueUZ_UE4wHW8qwaLZtZ_3J4fIXmay5HurJH03LJ7cGirxFY=s88-c-k-c0x00ffffff-no-rj")
    }


    private fun goAdditionalExerciseSuccess() {
        findNavController().navigate(R.id.action_fragmentLastExercise_to_fragmentAdditionalExerciseSuccess)
    }
}
