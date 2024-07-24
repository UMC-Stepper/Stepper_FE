package com.example.umc_stepper.ui.login

import android.content.Intent
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityLoginBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.signup.SignUpActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    lateinit var loginViewModel: LoginViewModel

    override fun setLayout() {
        setting()
    }

    private fun setting() {
        setViewModel()
        setViewModel()
        barTransparent()
        onClicked()
        goHome()
    }

    private fun setViewModel() {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private fun goHome() {
        binding.activityLoginStepperIv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goSignUp() {
        binding.activityLoginStepperIv.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun barTransparent() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun onClicked() {
        with(binding) {
            activityLoginBtn.setOnClickListener {
                val email = activityLoginEmailEt.text.toString()
                val password = activityLoginPasswordEt.text.toString()
                //전송 api 호출 loginViewModel.getUser(email,password)
            }

            activityRegisterBtn.setOnClickListener {
                goSignUp()
            }
        }
    }

    private fun requestForUserData() { //응답
        with(binding) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    loginViewModel.userData.collectLatest { response ->
                        loginViewModel.updateUser(response) //유저 업데이트
                        goHome()
                        finish()
                    }
                }
            }
        }
    }
}

