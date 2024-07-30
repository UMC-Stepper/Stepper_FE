package com.example.umc_stepper.ui.stepper

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityCameraDetailBinding

class CameraDetailActivity : BaseActivity<ActivityCameraDetailBinding>(R.layout.activity_camera_detail) {

    override fun setLayout() {
        setImageView()
        setButton()
    }

    private fun setImageView() {
        val photoUri: Uri? = intent.getStringExtra("photo_uri")?.let { Uri.parse(it)}
        Log.d("로그", "$photoUri")
        photoUri?.let {
            binding.activityCameraDetailPhotoIv.setImageURI(it)
        }
    }

    private fun setButton() {

        // 다시 찍기 버튼
        binding.activityCameraDetailRetakeIv.setOnClickListener {
            startNextActivity(CameraActivity::class.java)
        }

        // 사진 사용 버튼
        binding.activityCameraDetailUsePhotoIv.setOnClickListener {

        }
    }

}