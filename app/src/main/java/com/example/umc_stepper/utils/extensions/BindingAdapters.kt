package com.example.umc_stepper.utils.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.umc_stepper.R
import com.example.umc_stepper.domain.model.local.ExerciseState
import java.text.SimpleDateFormat
import java.util.Locale

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions().centerCrop())
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("imageUrl2")
    fun loadImage2(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions().centerCrop())
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("app:setTime")
    fun setTimeTv(view: TextView, createdAt: String) {
        if (createdAt.isNullOrEmpty()) {
            // Null 또는 빈 문자열인 경우 기본 값 또는 오류 메시지 설정
            view.text = "Unknown Date"
            return
        }

        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault()) // 입력 문자열 형식 정의
            val outputFormat = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault())   // 원하는 출력 형식 정의
            val date = inputFormat.parse(createdAt)  // 문자열을 Date 객체로 파싱
            val formattedDate = date?.let { outputFormat.format(it) }   // Date 객체를 원하는 문자열 형식으로 포맷
            view.text = formattedDate
        } catch (e: Exception) {
            view.text = "Invalid Date"
            e.printStackTrace()
        }
    }

    @JvmStatic
    @BindingAdapter("app:stepStatus")
    fun setStepStatusImg(view: ImageView, stepStatus: Boolean) {
        val imageRes = if (stepStatus) {
            R.drawable.ic_signup_pwd_check_ok
        } else {
            R.drawable.ic_signup_pwd_check_error
        }
        view.setImageResource(imageRes)
    }

    @JvmStatic
    @BindingAdapter("app:isVisible")
    fun setIsVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("app:setImg")
    fun setStepCompleteImg(view: ImageView, exerciseState: ExerciseState) {
        val imageRes = when (exerciseState.steps.size) {
            1 -> {
                if (exerciseState.steps[0].stepStatus) {
                    R.drawable.ic_exercise_card_complete
                } else {
                    R.drawable.ic_exercise_card_un_complete
                }
            }

            2 -> {
                if (exerciseState.steps[0].stepStatus && exerciseState.steps[1].stepStatus) {
                    R.drawable.ic_exercise_card_complete
                } else {
                    R.drawable.ic_exercise_card_un_complete
                }
            }

            3 -> {
                if (exerciseState.steps[0].stepStatus && exerciseState.steps[1].stepStatus && exerciseState.steps[2].stepStatus) {
                    R.drawable.ic_exercise_card_complete
                } else {
                    R.drawable.ic_exercise_card_un_complete
                }
            }

            else -> {
                R.drawable.ic_exercise_card_un_complete
            }
        }
        view.setImageResource(imageRes)
    }
}