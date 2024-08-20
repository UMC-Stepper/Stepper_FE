package com.example.umc_stepper.ui.stepper

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCameraDetailBinding
import com.example.umc_stepper.utils.GlobalApplication

class CameraDetailFragment : BaseFragment<FragmentCameraDetailBinding>(R.layout.fragment_camera_detail) {

    private var photoUri : Uri? = null

    override fun setLayout() {
        setImageView()
        setButton()
    }

    private fun setImageView() {
        photoUri = arguments?.getString("photo_uri")?.let { Uri.parse(it)}
        Log.d("포토", "photoUri : $photoUri")
        photoUri?.let {
            GlobalApplication.loadImage(binding.activityCameraDetailPhotoIv, it)
        }
    }

    private fun setButton() {
        // 다시 찍기 버튼
        binding.activityCameraDetailRetakeIv.setOnClickListener {
            findNavController().popBackStack()
        }

        // 사진 사용 버튼
        binding.activityCameraDetailUsePhotoIv.setOnClickListener {
            navigateToEvaluationExercise()
        }
    }

    private fun navigateToEvaluationExercise() {
        val action = CameraDetailFragmentDirections.actionCameraDetailFragmentToFragmentEvaluationExercise()
        val bundle = Bundle().apply {
            putString("photo_uri", photoUri.toString())
        }
        findNavController().navigateSafe(action.actionId, bundle)
    }

}