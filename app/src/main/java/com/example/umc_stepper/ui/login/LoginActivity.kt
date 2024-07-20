package com.example.umc_stepper.ui.login

import android.content.Intent
import android.view.WindowManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityLoginBinding
import com.example.umc_stepper.ui.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login){
    override fun setLayout() {
        goHome()
        barTransparent()
    }

    private fun goHome(){
        binding.activityLoginStepperIv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun barTransparent(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}

