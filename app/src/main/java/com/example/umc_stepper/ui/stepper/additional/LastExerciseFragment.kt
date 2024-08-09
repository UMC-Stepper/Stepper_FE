package com.example.umc_stepper.ui.stepper.additional

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.umc_stepper.BuildConfig
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentLastExerciseBinding
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.launch

class LastExerciseFragment : BaseFragment<FragmentLastExerciseBinding>(R.layout.fragment_last_exercise) {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private val stepperViewModel: StepperViewModel by activityViewModels()
    private val youtubeKey = BuildConfig.YOUTUBE_KEY

    private var isRunning = false
    private var time = 0L // 밀리초 단위

    private lateinit var startBtn: AppCompatButton
    private lateinit var resetBtn: AppCompatButton
    private lateinit var completeBtn : AppCompatButton

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

    override fun onStop() {
        super.onStop()

        isRunning = false
        time = 0
        startBtn.text = "시작"
        setButtonUI(startBtn.text.toString())
        updateTimerUI(time)
        handler.removeCallbacks(timerRunnable)
    }


    override fun setLayout() {
        updateToolbar()
        initSettings()

        val urlText = arguments?.getString("urlText")
        if (!urlText.isNullOrEmpty()) {
            initializeYouTubePlayer(urlText)
            fetchYouTubeVideoDetails(urlText)
            dataSetting()
        }

        completeBtn.setOnClickListener {
            goAdditionalExerciseSuccess()
        }
    }

    private fun initSettings() {
        initButton()
        setTimer()
        resetTimer()
    }

    private fun setButtonUI(text: String) {
        when(text) {
            "시작", "중지" -> {
                completeBtn.setBackgroundResource(R.drawable.radius_corners_61dp_stroke_1)
                completeBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.Purple_700))
            }
            "계속" -> {
                completeBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
                completeBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
            }
        }
    }

    private fun initButton() {
        startBtn = binding.fragmentLastExerciseStartBtn
        resetBtn = binding.fragmentLastExerciseResetBtn
        completeBtn = binding.fragmentLastExerciseExerciseCompleteBtn
    }

    private fun setTimer() {
        startBtn.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                startBtn.text = "중지"
                setButtonUI(startBtn.text.toString())
                handler.post(timerRunnable) // 타이머 시작
            } else {
                isRunning = false
                startBtn.text = "계속"
                setButtonUI(startBtn.text.toString())
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

        binding.fragmentLastExerciseHourTv.text = String.format("%02d", hours)
        binding.fragmentLastExerciseMinTv.text = String.format("%02d", minutes)
        binding.fragmentLastExerciseSecTv.text = String.format("%02d", seconds)
    }

    private fun resetTimer() {
        resetBtn.setOnClickListener {
            isRunning = false
            time = 0
            startBtn.text = "시작"
            setButtonUI(startBtn.text.toString())
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
        fetchVideoDetailsFromApi("snippet", videoId, youtubeKey)
    }

    // 유튜브 API에서 세부 정보를 불러오는 함수
    private fun fetchVideoDetailsFromApi(part: String, id: String, key: String) {
        stepperViewModel.getYoutubeVideoInfo(part, id, key)
        // ViewModel이 데이터를 적절히 설정하는지 확인
    }

    private fun dataSetting() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    stepperViewModel.provideYoutubeLink.collect { response ->
                        Log.d("dataSetting", "Response received: $response")
                        if (response != null && response.items.isNotEmpty()) {
                            val videoItem = response.items[0].snippet
                            Log.d("dataSetting", "Video title: ${videoItem.title}")
                            Log.d("dataSetting", "Channel title: ${videoItem.channelTitle}")
                            Log.d("dataSetting", "Thumbnail URL: ${videoItem.thumbnails.default.url}")

                            binding.fragmentLastExerciseTitleTv.text = videoItem.title
                            binding.fragmentLastExerciseChannelNameTv.text = videoItem.channelTitle

                            Glide.with(requireContext())
                                .load(videoItem.thumbnails.default.url)
                                .into(binding.fragmentLastExerciseProfileIv)
                        } else {
                            Log.e("dataSetting", "비디오 아이템이 없음")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("dataSetting", "비디오 디테일이 없음", e)
                }
            }
        }
    }

    private fun updateToolbar() {
        mainActivity.updateToolbarTitle("추가 운동하기")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_back)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_today)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_stepper)
    }

    private fun goAdditionalExerciseSuccess() {
        handler.removeCallbacks(timerRunnable)  // 타이머 중지

        val hour = binding.fragmentLastExerciseHourTv.text.toString()
        val min = binding.fragmentLastExerciseMinTv.text.toString()
        val sec = binding.fragmentLastExerciseSecTv.text.toString()

        val time = Time(hour, min, sec)
        val gson = Gson()

        val timeJson = gson.toJson(time)
        val args = Bundle().apply {
            putString("time", timeJson)
        }

        val action = LastExerciseFragmentDirections.actionFragmentLastExerciseToFragmentAdditionalExerciseSuccess()
        findNavController().navigate(action.actionId, args)
    }
}
