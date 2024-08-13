package com.example.umc_stepper.ui.stepper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityCameraDetailBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.utils.GlobalApplication

class CameraDetailActivity : BaseActivity<ActivityCameraDetailBinding>(R.layout.activity_camera_detail) {

    private var photoUri : Uri? = null

    override fun setLayout() {
        setImageView()
        setButton()
    }

    private fun setImageView() {
        photoUri = intent.getStringExtra("photo_uri")?.let { Uri.parse(it)}
        Log.d("CameraDetailActivity", "photoUri : $photoUri")
        photoUri?.let {
            GlobalApplication.loadImage(binding.activityCameraDetailPhotoIv, it)
        }
    }

    private fun setButton() {
        // 다시 찍기 버튼
        binding.activityCameraDetailRetakeIv.setOnClickListener {
            startNextActivity(CameraActivity::class.java)
        }

        // 사진 사용 버튼
        binding.activityCameraDetailUsePhotoIv.setOnClickListener {
            navigateToEvaluationExercise()
        }
    }

    private fun navigateToEvaluationExercise() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("navigate_to", "EvaluationExerciseFragment")
            putExtra("photo_uri", photoUri.toString())
        }
        startActivity(intent)
        finish()
    }

}