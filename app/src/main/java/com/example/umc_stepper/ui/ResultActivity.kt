package com.example.umc_stepper.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityResultBinding

class ResultActivity : BaseActivity<ActivityResultBinding>(R.layout.activity_result) {
    override fun setLayout() {
        binding.name.text = intent.getStringExtra("name")
        binding.textView.text = intent.getStringExtra("message")
        when (intent.getStringExtra("name")) {
            "루피" -> {
                binding.iv.setImageResource(R.drawable.fe1)
            }

            "아리" -> {
                binding.iv.setImageResource(R.drawable.des)

            }

            "비모" -> {
                binding.iv.setImageResource(R.drawable.be3)

            }

            "빈트" -> {
                binding.iv.setImageResource(R.drawable.be2)

            }

            "채리" -> {
                binding.iv.setImageResource(R.drawable.be1)

            }

            "마야" -> {
                binding.iv.setImageResource(R.drawable.be5)

            }

            "호준" -> {
                binding.iv.setImageResource(R.drawable.be4)

            }

            "미니" -> {
                binding.iv.setImageResource(R.drawable.fe3)

            }

            else -> {
                binding.iv.setImageResource(R.drawable.pm)

            }
        }
    }
}
